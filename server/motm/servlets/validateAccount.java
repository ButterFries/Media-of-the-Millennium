package server.motm.servlets;
import server.motm.database.*;
import server.motm.utils.*;


import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import java.io.IOException;
import java.io.OutputStream;

import org.json.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.sql.*;



/* First send the username/email alone then it will return the hash,
 * apply the hash on the password and send it along with the username/email
 * 
 * Send Send { u_name: "my_username"} or { email: "my_email@example"}
 * and it will return { error_code: int, hash: "x:yy:zzzz" }
 * use applyHash.java on the password and the retrieved hash
 * then send { u_name: "my_username", s_pw: "hashed_password" }
 * or { email: "my_email@example", s_pw: "hashed_password" }
 * and it will return (TBD) something like { error_code: int, session_token: String?, etc } 
 */

public class validateAccount implements HttpHandler
{
    private static appDatabase db;

    public validateAccount(appDatabase appDB) {
        db = appDB;
    }

    public void handle(HttpExchange r) {
        System.out.println("\n-Received request [validateAccount]");
        Connection conn = null;
        try {
            if (r.getRequestMethod().equals("POST")) {
                System.out.println("--request type: POST (GET)"); //System.out.println("--request type: GET");
                conn = db.connect();
                handleReq(r, conn);
            }
            else {
                System.out.println("--request type unsupported: "+r.getRequestMethod());
                r.sendResponseHeaders(405, -1);
            }
        } 
        catch (Exception e) {
            System.out.println("# ERROR HelloWorld.handle ::  " + e);
            try{
                r.sendResponseHeaders(500, -1);
            }catch (Exception eH500) {
                System.out.println("# handled error sending h500 ::  "+eH500);
            }
        }
        finally {
            try { //this is to safely disconnect from the db if a connection was made
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
        appDatabase.accountInfo acc = null;
        String username, email, secured_password;

        /*  Get password hash for given username/email  */
        if ( !requestJSON.has("s_pw") ){
            System.out.println("--[Stage 1] Retrieving stored hash");
            /*  username request -> return the hash info  */
            if ( requestJSON.has("u_name") ){
                username = requestJSON.getString("u_name");
                System.out.println("--client sent u_name: "+username);
                /*  check if username exists  */
                if ( !db.usernameExists(conn, username) ){ //exception will be forwarded up to .handle
                    responose_no_user(r, responseJSON);
                    return;
                }
                acc = db.get_user_from_name(conn, username); //exception will be forwarded up to .handle
            }
            /*  email request -> return the hash info  */
            else if ( requestJSON.has("email") ){
                email = requestJSON.getString("email");
                System.out.println("--client sent email: "+email);
                /*  check if email exists  */
                if ( !db.emailExists(conn, email) ){ //exception will be forwarded up to .handle
                    responose_no_email(r, responseJSON);
                    return;
                }
                acc = db.get_user_from_email(conn, email); //exception will be forwarded up to .handle
            }
            /* if invalid request body */
            else {
                r.sendResponseHeaders(400, -1);
                return;
            }

            System.out.println("--valid account, password verification pending");
            
            try {
                responseJSON.put("error_code", 0);
                responseJSON.put("error_description", "successfully verified accountID");
                responseJSON.put("hash", acc.get_password());
                String response = responseJSON.toString() + "\n";
                r.sendResponseHeaders(200, response.length());
                OutputStream os = r.getResponseBody();
                os.write(response.getBytes());
                os.close();
                System.out.println("--responese :   "+response.trim());
                System.out.println("--request fufilled");
            }
            catch (Exception e){
                System.out.println("## ERROR ::  " + e);
                throw new Exception("(handleReq 1) -- something went wrong when sending initial response");
            }
        }
    
        



        /*  Validate credentials  */
        else { //requestJSON.has("s_pw")
            System.out.println("--[Stage 2] Validating credentials");
            /*  username login -> validate */
            if ( requestJSON.has("u_name") ){
                username = requestJSON.getString("u_name");
                secured_password = requestJSON.getString("s_pw");
                System.out.println("--client sent u_name: "+username);
                System.out.println("--client sent s_pw: "+secured_password);
                /*  check if username exists  */
                if ( !db.usernameExists(conn, username) ){ //exception will be forwarded up to .handle
                    responose_no_user(r, responseJSON);
                    return;
                }
                acc = db.get_user_from_name(conn, username); //exception will be forwarded up to .handle
            }
            /*  email login -> validate */
            else if ( requestJSON.has("email") ){
                email = requestJSON.getString("email");
                secured_password = requestJSON.getString("s_pw");
                System.out.println("--client sent email: "+email);
                System.out.println("--client sent s_pw: "+secured_password);
                /*  check if email exists  */
                if ( !db.emailExists(conn, email) ){ //exception will be forwarded up to .handle
                    responose_no_email(r, responseJSON);
                    return;
                }
                acc = db.get_user_from_email(conn, email); //exception will be forwarded up to .handle
            }
            /* if invalid request body */
            else {
                r.sendResponseHeaders(400, -1);
                return;
            }

            /* validate with the given userID */
            try {
                if (db.validatePassword(conn, secured_password, acc)){
                    System.out.println("--the given account credentials are valid");
                
                    responseJSON.put("error_code", 0);
                    responseJSON.put("error_description", "successfully verified account credentials");
                    /* TODO: get session token */
                    //ex.getSession();
                    String session_token = "none";
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
                throw new Exception("(handleReq 2) -- something went wrong when sending final response");
            }
        }
    }

    private void responose_no_user(HttpExchange r, JSONObject responseJSON) throws Exception {
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

    private void responose_no_email(HttpExchange r, JSONObject responseJSON) throws Exception {
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

