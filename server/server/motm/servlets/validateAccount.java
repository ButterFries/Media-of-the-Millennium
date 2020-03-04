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



/* First send the username/email alone then it will return the hash,
 * apply the hash on the password and send it along with the username/email
 * 
 * Send Send { u_name: "my_username"} or { email: "my_email@example"}
 * and it will return { error_code: int, hash: "x:yy:zzzz" }
 * use applyHash.java on the password and the retrieved hash
 * then send { u_name: "my_username", pw: "hashed_password" }
 * or { email: "my_email@example", pw: "hashed_password" }
 * and it will return (TBD) something like { error_code: int, session_token: String?, etc } 
 */

public class validateAccount implements HttpHandler
{
    private static AppDatabase db;
    private static SessionManager sm;
    private static Connection conn = null;

    public validateAccount(AppDatabase appDB, SessionManager appSM) {
        db = appDB;
        sm = appSM;
        conn = db.connect();
    }

    public void handle(HttpExchange r) {
        System.out.println("\n-Received request [validateAccount]");
        //Connection conn = null;
        HttpsExchange rs = (HttpsExchange) r;
        try {
            if (r.getRequestMethod().equals("POST")) {
                System.out.println("--request type: POST (GET)"); //System.out.println("--request type: GET");
                //conn = db.connect();
                handleReq(r, conn);
            }
            else {
                System.out.println("--request type unsupported: "+r.getRequestMethod());
                rs.sendResponseHeaders(405, -1);
            }
        } 
        catch (Exception e) {
            if (r.getResponseCode() < 0 ){ //header hasnt been sent yet
                try{
                    rs.sendResponseHeaders(500, -1);
                }catch (Exception eH500) {
                    System.out.println("# error sending h500 ::  "+eH500);
                }
            }
        }
        /*finally {
            try { //this is to safely disconnect from the db if a connection was made
                if (conn != null)
                    db.disconnect(conn);
            }
            catch (Exception eDisconnect){
                System.out.println("# handled error disconnecting :: "+eDisconnect);
            }
        }*/
    }

    public void handleReq(HttpExchange r, Connection conn) throws Exception {
        String body = Utils.convert(r.getRequestBody());
        JSONObject requestJSON = new JSONObject(body);
        JSONObject responseJSON = new JSONObject();
        AppDatabase.accountInfo acc = null;
        String username, email, user_password = null, uID;

        HttpsExchange rs = (HttpsExchange) r;

        /*  Validate credentials  */
        
        System.out.println("--Validating credentials");
        if ( requestJSON.has("pw") ){
            /*  username login -> validate */
            if ( requestJSON.has("u_name") ){
                username = requestJSON.getString("u_name");
                user_password = requestJSON.getString("pw");
                System.out.println("--client sent u_name: "+username);
                System.out.println("--client sent pw: "+user_password);
                /*  check if username exists  */
                if ( !db.usernameExists(conn, username) ){ //exception will be forwarded up to .handle
                    response_no_user(r, responseJSON);
                    return;
                }
                acc = db.get_user_from_name(conn, username); //exception will be forwarded up to .handle
            }
            /*  email login -> validate */
            else if ( requestJSON.has("email") ){
                email = requestJSON.getString("email");
                user_password = requestJSON.getString("pw");
                System.out.println("--client sent email: "+email);
                System.out.println("--client sent pw: "+user_password);
                /*  check if email exists  */
                if ( !db.emailExists(conn, email) ){ //exception will be forwarded up to .handle
                    response_no_email(r, responseJSON);
                    return;
                }
                acc = db.get_user_from_email(conn, email); //exception will be forwarded up to .handle
            }
        }
        /* if invalid request body */
        else {
            r.sendResponseHeaders(400, -1);
            return;
        }

        /* validate with the given userID */
        try {
            //uID = acc.get_ID()+"";
            if (db.validatePassword(conn, user_password, acc)){
                System.out.println("--the given account credentials are valid");
            
                responseJSON.put("error_code", 0);
                responseJSON.put("error_description", "successfully verified account credentials");
                
                //get client addr (if forwarded then get that from header, otherwise get the remote addr)
                Headers reqHeader = r.getRequestHeaders();
                List<String> ipList = reqHeader.get("X-FORWARDED-FOR");
                System.out.println("--reqHeader [x-forwarded-for]: "+ipList);
                String ipAddress = ipList == null ? r.getRemoteAddress().getAddress().toString() : ipList.get(0); 
                System.out.println("--client ip addr: "+ipAddress);
                
                
                String sessionID = sm.createSession(acc, ipAddress);
                System.out.println("--obtained session");
                //add both session id to cookie header and token to json (same purpose)
                Headers headers = r.getResponseHeaders();
                headers.add("User-agent", "HTTPTool/1.0");
                headers.add("Set-cookie", "motm_sessionID="+sessionID+"; Max-Age="+(sm.getSessionDuration()-60)+"; HttpOnly;"); // Secure;");
                String session_token = "motm_sessionID="+sessionID;
                responseJSON.put("session_token", session_token);

                String response = responseJSON.toString() + "\n";
                r.sendResponseHeaders(200, response.length());
                OutputStream os = r.getResponseBody();
                os.write(response.getBytes());
                os.close();
                System.out.println("--responese :   "+response.trim());
                System.out.println("--request fufilled");
            }
            else {
                System.out.println("--the given account credentials are invalid");
            
                responseJSON.put("error_code", 3);
                responseJSON.put("error_description", "invalid account credentials");
                String response = responseJSON.toString() + "\n";
                r.sendResponseHeaders(200, response.length());
                OutputStream os = r.getResponseBody();
                os.write(response.getBytes());
                os.close();
                System.out.println("--responese :   "+response.trim());
                System.out.println("--request fufilled");
            }                
        }
        catch (Exception e){
            System.out.println("## ERROR ::  " + e);
            throw new Exception("(handleReq) -- something went wrong when sending validation response");
        }

    }

    private void response_no_user(HttpExchange r, JSONObject responseJSON) throws Exception {
        System.out.println("--username doesn't exist");
        responseJSON.put("error_code", 1);
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
        System.out.println("--email doesn't exist");
        responseJSON.put("error_code", 2);
        responseJSON.put("error_description", "email doesn't exists");
        String response = responseJSON.toString() + "\n";
        r.sendResponseHeaders(200, response.length());
        OutputStream os = r.getResponseBody();
        os.write(response.getBytes());
        os.close();
        System.out.println("--responese :   "+response.trim());
        System.out.println("--request fufilled");
    }
}

