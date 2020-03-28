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
 * Client sends { u_name: "my_username", email: "my_email@example", pw: "password" }
 * and it will return something like { error_code: int, session_token: "x-yy-zzzz" } 
 * if successful (error_code 0) and a session_token will be returned
 * 
 * Error Codes: 
 *      0 --  successfully verified
 *      1 --  username already taken
 *      2 --  email already taken
 *      3 --  SQL error (DEPREC)
 *      ~~
 */

public class registerAccount implements HttpHandler
{
    private static AppDatabase db;
    private static SessionManager sm;
    private static Connection conn = null;

    public registerAccount(AppDatabase appDB, SessionManager appSM) {
        db = appDB;
        sm = appSM;
        conn = db.connect();
    }

    public void handle(HttpExchange r) {
        System.out.println("\n-Received request [registerAccount]");
        //Connection conn = null;
        HttpsExchange rs = (HttpsExchange) r;
        try {
            if (r.getRequestMethod().equals("PUT")) {
                System.out.println("--request type: PUT");
                //conn = db.connect();
                handleReq(r, conn);
            }
            else {
                System.out.println("--request type unsupported: "+r.getRequestMethod());
                rs.sendResponseHeaders(405, -1);
            }
        } 
        catch (Exception e) {
            System.out.println("# ERROR HelloWorld.handle ::  " + e);
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

        HttpsExchange rs = (HttpsExchange) r;

        if (requestJSON.has("u_name") && requestJSON.has("email") && requestJSON.has("pw")){
            String username = requestJSON.getString("u_name");
            String email = requestJSON.getString("email");
            String user_password = requestJSON.getString("pw");
            System.out.println("--client sent u_name: "+username);
            System.out.println("--client sent email: "+email);
            System.out.println("--client's pw: "+user_password);

            JSONObject responseJSON = new JSONObject();

            /*  check if username exists  */
            System.out.println("--checking if username exists already");
            if (db.usernameExists(conn, username)){ //exception will be forwarded up to .handle
                System.out.println("--username already exists");
                responseJSON.put("error_code", 1);
                responseJSON.put("error_description", "username already exists");
                String response = responseJSON.toString() + "\n";
                rs.sendResponseHeaders(200, response.length());
                OutputStream os = rs.getResponseBody();
                os.write(response.getBytes());
                os.close();
                System.out.println("--responese :   "+response.trim());
                System.out.println("--request fufilled");
                return;
            }
            System.out.println("----username not taken");

            /*  check if email exists  */
            System.out.println("--checking if username exists already");
            if (db.emailExists(conn, email)){ //exception will be forwarded up to .handle
                System.out.println("--email already exists");
                responseJSON.put("error_code", 2);
                responseJSON.put("error_description", "email already exists");
                String response = responseJSON.toString() + "\n";
                rs.sendResponseHeaders(200, response.length());
                OutputStream os = rs.getResponseBody();
                os.write(response.getBytes());
                os.close();
                System.out.println("--responese :   "+response.trim());
                System.out.println("--request fufilled");
                return;
            }
            System.out.println("----email not taken");



            /*  register the account;  both username and email can be used  */
            System.out.println("--adding account to database");
            db.add_account(conn, username, email, user_password); //exception will be forwarded up to .handle
            System.out.println("----successfully added account to database");

            

            /*  send response  */
            try {
                AppDatabase.accountInfo acc = db.get_user_from_email(conn, email);

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

                responseJSON.put("error_code", 0);
                responseJSON.put("error_description", "successfully registered account to database");
                String response = responseJSON.toString() + "\n";
                rs.sendResponseHeaders(200, response.length());
                OutputStream os = rs.getResponseBody();
                os.write(response.getBytes());
                os.close();
                System.out.println("--responese :   "+response.trim());
                System.out.println("--request fufilled");
            }
            catch (Exception e){
                System.out.println("## ERROR ::  " + e);
                throw new Exception("(handleReq) -- something went wrong when sending response");
            }
        }
        else {
            rs.sendResponseHeaders(400, -1);
        }
    }
}
