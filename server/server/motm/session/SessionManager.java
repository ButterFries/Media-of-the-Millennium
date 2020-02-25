package server.motm.session;

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
 */

public class SessionManager
{
    private HashMap<String,sessionInfo> sessions = null;
    public static final int DEFAULT_SESSION_DURATION = 3600; //3600 seconds / 60 minutes / 1 hour
    private int session_duration;

    /* Holds the relevant info to a session.
     */
    private class sessionInfo {
        private String uID;
        String sID;
        long time_stamp;
        Timer timer;
        public sessionInfo(String uID, String sID, long ts, Timer timer){
            this.uID = uID;
            this.sID = sID;
            this.time_stamp = ts;
            this.timer = timer;
        }
        public String getUID() { return this.uID; }
        public String getSID() { return this.sID; }
        public long getTimeStamp() { return this.time_stamp; }
        public Timer getTimer() { return this.timer; }
        public void setSID(String sID) { this.sID = sID; }
        public void setTimeStamp(long ts) { this.time_stamp = ts; }
        public void setTimer(Timer timer) { this.timer = timer; }
    }


    public SessionManager() {
        this.sessions = new HashMap<String, sessionInfo>();
        this.session_duration = DEFAULT_SESSION_DURATION;
        System.out.println("%%  Session duration set to "+this.session_duration+" seconds");
    }
    public SessionManager(int session_duration) {
        this.sessions = new HashMap<String, sessionInfo>();
        this.session_duration = session_duration;
        System.out.println("%%  Session duration set to "+this.session_duration+" seconds");
    }

    /* Return the session duration.
     */
    public int getSessionDuration(){
        return this.session_duration;
    }


    /* Check if the sID for uID is valid and return true if so,
     * otherwise or if uID doesn't have a session entry then return false
     */
    public boolean isValidSession(String uID, String sID){
        return sessions.containsKey(uID) ? (sessions.get(uID).getSID() == sID) : false;
    }


    /* The session ID consists of both a random number and a 
     * hash combining some properties of the user such as the 
     * username and IP address.
     *     sessionId = randomNum + SHA256( userId + timestamp + salt + ipAddr )
     */
    public String createSession(String uID, String ipAddr) throws Exception{
        long time_stamp = (new Timestamp(System.currentTimeMillis())).getTime();
        String str = uID + time_stamp + "MOTM session salt" + ipAddr;
        MessageDigest dig = MessageDigest.getInstance("SHA-256");
        byte[] sha2hash = dig.digest(str.getBytes(StandardCharsets.UTF_8));
        String sessID = UUID.randomUUID().toString() + "-" + DatatypeConverter.printHexBinary(sha2hash); //encodeHexString(sha2hash);


        System.out.println("%% creating session for uID ["+uID+"] with sID: ["+sessID+"]");
        addSession(uID, sessID, time_stamp);
        System.out.println("%%-- session for uID ["+uID+"] created.");
        return sessID;
    }
    

    /* TimerTask to destory the session entry.
     */
    class destroySessionEvent extends TimerTask {
        private final String uID;
        private final HashMap<String,sessionInfo> sessions;
        public destroySessionEvent ( String uID, HashMap<String,sessionInfo> sessions){
            this.uID = uID;
            this.sessions = sessions;
        }    
        @Override
        public void run() {
            System.out.println("\n%% session for uID ["+this.uID+"] expired.\n");
            sessions.remove(this.uID);
        }
    }

    /* Add the sessionID to the set of sessionIDs for tracking.
     * At the same time, setup a timed event to delete the sessionID.
     * If a session is already active for uID then overwrite it.
     */
    private void addSession(String uID, String sID, long time_stamp){
        Timer timer = new Timer(uID+"_sessionTimer");
        TimerTask dse = new destroySessionEvent(uID, sessions);
        timer.schedule( dse, TimeUnit.SECONDS.toMillis(session_duration) ); 
        // increase accuracy with `sess_dur - (getTime - time_stamp).toMilli`
        
        if (sessions.containsKey(uID)){
            System.out.println("%%-- overwriting existing session for uID ["+uID+"]");
            sessionInfo sess = sessions.get(uID);
            sess.getTimer().cancel();
            sess.setSID(sID);
            sess.setTimeStamp(time_stamp);
            sess.setTimer(timer);
        }
        else {
            System.out.println("%%-- creating new session for uID ["+uID+"]");
            sessionInfo sess = new sessionInfo(uID, sID, time_stamp, timer);
            sessions.put(uID,sess);
        }
    }

    /* Safely remove the sessionID from the set of sessionIDs and
     * destroy the timed event
     */
    public void removeSession(String uID, String sID) throws Exception{
        System.out.println("%% removing session for uID ["+uID+"]");
        if ( !sessions.containsKey(uID) ){
            System.out.println("%%-- session for uID ["+uID+"] does not exist. (may have expired or wasn't created)");    
            return;
        }
        sessionInfo sess = sessions.get(uID);
        if (sID != sess.getSID()){
            System.out.println("%%-- session for uID ["+uID+"] does not match given sID. Session will not be removed");
            throw new Exception("Invalid session ID for user. Session will not be removed.");
        }
        sess.getTimer().cancel();
        sessions.remove(uID);
        System.out.println("%%-- session for uID ["+uID+"] has been destroyed.");
    }

    /* Convert a byte array to hex string.
     * Source:  https://www.baeldung.com/java-byte-arrays-hex-strings
     */
    public String encodeHexString(byte[] byteArray) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString();
    }
    public String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }


}

