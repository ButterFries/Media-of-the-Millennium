        package server.motm.servlets;
import server.motm.database.*;
import server.motm.utils.*;
import server.motm.session.*;


import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import java.io.IOException;
import java.io.OutputStream;

import org.json.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;


import java.sql.*;

import com.sun.net.httpserver.HttpsExchange;

/* 
 * Client sends { mediaID: int, email/username: string, new_rating: float, session_token: string } 
 * and gets 
 * { error_code: int, rating: float, num_raters: int }
 * The client asks the server to update the rating info of some media title for
 * a user and checks that the session is valid.
 * 
 * 
 * Error Codes: 
 *      0 --  successfully updated rating info
 *      1 --  mediaID does not exist
 *      2 --  username does not exist
 *      3 --  email does not exist
 *      4 --  invalid session
 *      ~~
 */

public class updateUsersRating implements HttpHandler
{
    private static AppDatabase db;
    private static SessionManager sm;
    private static Connection conn = null;

    public updateUsersRating(AppDatabase appDB, SessionManager appSM) {
        db = appDB;
        sm = appSM;

    }

    public void handle(HttpExchange r) {
        System.out.println("\n-Received request [updateUsersRating]");
        HttpsExchange rs = (HttpsExchange) r;
        try {
            if (r.getRequestMethod().equals("PUT")) {
                System.out.println("--request type: PUT");
                conn = db.connect();
                handleReq(r, conn);
            }
            else {
                System.out.println("--request type unsupported: "+r.getRequestMethod());
                rs.sendResponseHeaders(405, -1);
            }
        } 
        catch (Exception e) {
            System.out.println("# ERROR updateUsersRating.handle ::  " + e);
            if (r.getResponseCode() < 0 ){ //header hasnt been sent yet
                try{
                    rs.sendResponseHeaders(500, -1);
                }catch (Exception eH500) {
                    System.out.println("# error sending h500 ::  "+eH500);
                }
            }
        }
        finally {
            try { //this is to safely disconnect from the db if a connection was made
                if (conn != null)
                    db.disconnect(conn);
            }
            catch (Exception eDisconnect){
                System.out.println("# handled error disconnecting :: "+eDisconnect);
            }
        }
    }

    public void handleReq(HttpExchange r, Connection conn) throws Exception {
        String body = Utils.convert(r.getRequestBody());
        JSONObject requestJSON = new JSONObject(body);
        JSONObject responseJSON = new JSONObject();

        HttpsExchange rs = (HttpsExchange) r;
        

        if ( requestJSON.has("mediaID") && requestJSON.has("session_token") &&
                requestJSON.has("new_rating") &&
                (requestJSON.has("username") || requestJSON.has("email")) ) {
            Integer mediaID = requestJSON.getInt("mediaID");
            String sessionID = requestJSON.getString("session_token");
            float newRating = requestJSON.getFloat("new_rating");

            System.out.println("new_rating is " + newRating);


            /*  check if mediaID is valid  */
            System.out.println("--checking validity of mediaID ["+mediaID+"]");
            if ( !db.mediaExists(conn, mediaID) ){
                response_no_media(r, responseJSON, mediaID);
                return;
            }
            System.out.println("----valid mediaID");
            

            /*  check if user exists  */
            AppDatabase.accountInfo acc = null;
            String user = null;
            if ( requestJSON.has("username") ) {
                String username = requestJSON.getString("username");
                user = username;
                System.out.println("----username: "+username);
                /*  check if username exists  */
                if ( !db.usernameExists(conn, username) ){ //exception will be forwarded up to .handle
                    response_no_user(r, responseJSON);
                    return;
                }
                /*  get userID  */
                acc = db.get_user_from_name(conn, username);
            }
            else { //(  requestJSON.has("email") 
                String email = requestJSON.getString("email");
                System.out.println("----email: "+email);
                user = email;
                /*  check if email exists  */
                if ( !db.emailExists(conn, email) ){ //exception will be forwarded up to .handle
                    response_no_email(r, responseJSON);
                    return;
                }
                /*  get userID  */
                acc = db.get_user_from_email(conn, email);
            }
            int userID = acc.get_ID();


            /*  check if session is valid  */
            if ( !sm.isValidSession(userID+"", sessionID) ){
                response_no_session(r, responseJSON, sessionID, user);
                return;
            }


            /*  update rating  */
            System.out.println("--updating mediaID ["+mediaID+"] rating with ["+newRating+"] for user ["+user+"]");
            db.update_mediaRating(conn, userID, mediaID, newRating);


            /*  send response  */
            try {
                responseJSON.put("error_code", 0);
                responseJSON.put("error_description", "successfully updated rating");
                    
                
                String response = responseJSON.toString() + "\n";
                rs.sendResponseHeaders(200, response.length());
                OutputStream os = rs.getResponseBody();
                os.write(response.getBytes());
                os.close();
                System.out.println("--responese :   "+response.trim());
                System.out.println("--request fufilled");
            }
            catch (Exception e){
                throw new Exception("(handleReq) -- something went wrong when sending response:  "+e);
            }
        }
        else {
            rs.sendResponseHeaders(400, -1);
        }
    }

    private void response_no_user(HttpExchange r, JSONObject responseJSON) throws Exception {
        System.out.println("----username doesn't exist");
        responseJSON.put("error_code", 2);
        responseJSON.put("error_description", "username doesn't exist");
        String response = responseJSON.toString() + "\n";
        r.sendResponseHeaders(200, response.length());
        OutputStream os = r.getResponseBody();
        os.write(response.getBytes());
        os.close();
        System.out.println("--responese :   "+response.trim());
        System.out.println("--request fufilled");
    }

    private void response_no_email(HttpExchange r, JSONObject responseJSON) throws Exception {
        System.out.println("----email doesn't exist");
        responseJSON.put("error_code", 3);
        responseJSON.put("error_description", "email doesn't exists");
        String response = responseJSON.toString() + "\n";
        r.sendResponseHeaders(200, response.length());
        OutputStream os = r.getResponseBody();
        os.write(response.getBytes());
        os.close();
        System.out.println("--responese :   "+response.trim());
        System.out.println("--request fufilled");
    }

    private void response_no_media(HttpExchange r, JSONObject responseJSON, int mediaID) throws Exception {
        System.out.println("----mediaID ["+mediaID+"] doesn't exist");
        responseJSON.put("error_code", 1);
        responseJSON.put("error_description", "invalid mediaID");
        String response = responseJSON.toString() + "\n";
        r.sendResponseHeaders(404, response.length());
        OutputStream os = r.getResponseBody();
        os.write(response.getBytes());
        os.close();
        System.out.println("--responese :   "+response.trim());
        System.out.println("--request fufilled");
    }

    private void response_no_session(HttpExchange r, JSONObject responseJSON, String sID, String user) throws Exception {
        System.out.println("----invalid session for user ["+user+"] with session ID ["+sID+"]");
        responseJSON.put("error_code", 4);
        responseJSON.put("error_description", "invalid session");
        String response = responseJSON.toString() + "\n";
        r.sendResponseHeaders(404, response.length());
        OutputStream os = r.getResponseBody();
        os.write(response.getBytes());
        os.close();
        System.out.println("--responese :   "+response.trim());
        System.out.println("--request fufilled");
    }
}
