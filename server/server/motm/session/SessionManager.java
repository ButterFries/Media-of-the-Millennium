package server.motm.session;

import server.motm.database.AppDatabase;
import server.motm.database.AppDatabase.accountInfo;

import java.util.concurrent.*;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;




/* Store sessionIDs in an in-memory storage map
 * --to improve this for scalability, instead store sessions in the database
 * 
 * --there is a concurrency issue with the current management method using two maps
 *     which could be alleviated using locks or other synchronization methods.
 *     alternatively a concurrency safe database could be used to hold, query, update, and 
 *     delete entries safely.
 */

public class SessionManager
{
    private HashMap<String,sessionInfo> sessions = null;
    private HashMap<String,String> actives = null;
    public static final int DEFAULT_SESSION_DURATION = 3600; //3600 seconds / 60 minutes / 1 hour
    private int session_duration;

    /* Holds the relevant info to a session.
     */
    private class sessionInfo {
        private String uID;
        private String uName;
        private String email;
        private String sID;
        private long time_stamp;
        private Timer timer;
        public sessionInfo(String uID, String username, String email, String sID, long ts, Timer timer){
            this.uID = uID;
            this.uName = username;
            this.email = email;
            this.sID = sID;
            this.time_stamp = ts;
            this.timer = timer;
        }
        public String getUID() { return this.uID; }
        public String getUsername() { return this.uName; }
        public String getEmail() { return this.email; }
        public String getSID() { return this.sID; }
        public long getTimeStamp() { return this.time_stamp; }
        public Timer getTimer() { return this.timer; }
        public void setSID(String sID) { this.sID = sID; }
        public void setTimeStamp(long ts) { this.time_stamp = ts; }
        public void setTimer(Timer timer) { this.timer = timer; }
    }


    public SessionManager() {
        this.sessions = new HashMap<String, sessionInfo>();
        this.actives = new HashMap<String, String>();
        this.session_duration = DEFAULT_SESSION_DURATION;
        System.out.println("%%  Session duration set to "+this.session_duration+" seconds");
    }
    public SessionManager(int session_duration) {
        this.sessions = new HashMap<String, sessionInfo>();
        this.actives = new HashMap<String, String>();
        this.session_duration = session_duration;
        System.out.println("%%  Session duration set to "+this.session_duration+" seconds");
    }

    /* Return the session duration.
     */
    public int getSessionDuration(){
        return this.session_duration;
    }


    /* Check if the sID for uID/username/email is valid and return true if so,
     * otherwise or if user doesn't have a session entry then return false
     */
    public boolean isValidSession(String uID, String sID){
        return sessions.containsKey(sID) ? (sessions.get(sID).getUID() == uID) : false;
    }
    public boolean isValidSession_u(String username, String sID){
        return sessions.containsKey(sID) ? (sessions.get(sID).getUsername() == username) : false;
    }
    public boolean isValidSession_e(String email, String sID){
        return sessions.containsKey(sID) ? (sessions.get(sID).getEmail() == email) : false;
    }
    
    public String getUID(String sID) {
    	return sessions.containsKey(sID) ? sessions.get(sID).getUID() : "";
    }


    /* The session ID consists of both a random number and a 
     * hash combining some properties of the user such as the 
     * username and IP address.
     *     sessionId = randomNum + SHA256( userId + timestamp + salt + ipAddr )
     */
    public String createSession(accountInfo acc, String ipAddr) throws Exception{
        long time_stamp = (new Timestamp(System.currentTimeMillis())).getTime();
        String uID = acc.get_ID()+"";
        String username = acc.get_username();
        String email = acc.get_email();
        String str = uID + time_stamp + "MOTM session salt" + ipAddr;
        MessageDigest dig = MessageDigest.getInstance("SHA-256");
        byte[] sha2hash = dig.digest(str.getBytes(StandardCharsets.UTF_8));
        String shaHalf = DatatypeConverter.printHexBinary(sha2hash).toLowerCase();
        String prefix = UUID.randomUUID().toString() + "-";
        String sessID = prefix + shaHalf;
        while (this.sessions.containsKey(sessID)){ 
            //if sessID already exists then generate new prefix
            prefix = UUID.randomUUID().toString() + "-";
            sessID = prefix + shaHalf;
        }

        System.out.println("%% creating session for uID ["+uID+"] username ["+username+"] email ["+email+"]  with sID: ["+sessID+"]");
        addSession(uID, username, email, sessID, time_stamp);
        System.out.println("%%-- session for uID ["+uID+"] created.");
        return sessID;
    }
    

    /* TimerTask to destory the session entry.
     */
    class destroySessionEvent extends TimerTask {
        private final String sID;
        public destroySessionEvent ( String sID ){
            this.sID = sID;
        }    
        @Override
        public void run() {
            System.out.println("\n%% session with sID ["+this.sID+"] expired.\n");
            String uID = null;
            String err = "";
            try{
                uID = sessions.get(this.sID).getUID();
                sessions.remove(this.sID);
            } catch (Exception e){
                err += (e+": sID ["+this.sID+"] not found in sessions;  ");
            }
            try {
                if (actives.get(uID) == this.sID)
                    actives.remove(uID);
                else 
                    System.out.println("\n%% session with sID ["+this.sID+"] expired but was overwritten while deleting.\n");
            }
            catch (Exception e){
                err += (e+": uID ["+this.sID+"] not found in actives;  ");
            }
            if (!err.equals(""))
                throw new NullPointerException(err);
            System.out.println("active sessions: "+actives.toString()); // for debugging
            System.out.println("sessions: "+sessions.toString()); // for debugging
        }
    }

    /* Add the sessionID to the set of sessionIDs for tracking.
     * At the same time, setup a timed event to delete the sessionID.
     * If a session is already active for uID then overwrite it.
     */
    private void addSession(String uID, String username, String email, String sID, long time_stamp){
        Timer timer = new Timer(sID+"_sessionTimer");
        TimerTask dse = new destroySessionEvent(sID);
        timer.schedule( dse, TimeUnit.SECONDS.toMillis(session_duration) ); 
        // increase accuracy with `sess_dur - (getTime - time_stamp).toMilli`
        
        if (actives.containsKey(uID)){
            System.out.println("%%-- overwriting existing session for uID ["+uID+"]");
            String old_sessID = actives.get(uID);
            sessionInfo sess = sessions.get(old_sessID);
            sess.getTimer().cancel();
            sessions.remove(old_sessID);
            actives.replace(uID, sID);
            /*sess.setSID(sID);
            sess.setTimeStamp(time_stamp);
            sess.setTimer(timer);*/
            sess = new sessionInfo(uID, username, email, sID, time_stamp, timer);
            sessions.put(sID,sess);
        }
        else {
            System.out.println("%%-- creating new session for uID ["+uID+"]");
            sessionInfo sess = new sessionInfo(uID, username, email, sID, time_stamp, timer);
            sessions.put(sID,sess);
            actives.put(uID,sID);
        }
    }

    /* Safely remove the sessionID from the set of sessionIDs and
     * destroy the timed event
     * 
     * mode = 'uID' , 'username' , 'email' 
     */
    public void removeSession(String sID, String user, String mode) throws Exception{
        if ( !mode.equals("uID") && !mode.equals("username") && !mode.equals("email") )
            throw new Exception("Invalid session removal mode: ["+mode+"]");
        System.out.println("%% removing session for sID ["+sID+"]");
        if ( !sessions.containsKey(sID) ){
            System.out.println("%%-- session for sID ["+sID+"] does not exist. (may have expired or wasn't created)");    
            return;
        }
        sessionInfo sess = sessions.get(sID);
        if ( mode.equals("uID") && !user.equals(sess.getUID()) ){
            System.out.println("%%-- session for sID ["+sID+"] does not match the uID. Session will not be removed");
            throw new Exception("Invalid session ID for user ["+user+"]. Session will not be removed.");
        }
        else if ( mode.equals("username") && !user.equals(sess.getUsername()) ){
            System.out.println("%%-- session for sID ["+sID+"] does not match the username. Session will not be removed");
            throw new Exception("Invalid session ID for user ["+user+"]. Session will not be removed.");
        }
        else if ( mode.equals("email") && !user.equals(sess.getEmail()) ){
            System.out.println("%%-- session for sID ["+sID+"] does not match the email. Session will not be removed");
            throw new Exception("Invalid session ID for user ["+user+"]. Session will not be removed.");
        }
        sess.getTimer().cancel();
        sessions.remove(sID);
        System.out.println("%%-- session for sID ["+sID+"] has been destroyed.");
    }
}

