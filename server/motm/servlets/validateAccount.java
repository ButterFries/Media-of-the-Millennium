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



/* Send { u_name: "my_username", s_pw: "hashed_password" }
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
            if (r.getRequestMethod().equals("GET")) {
                System.out.println("--request type: GET");
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

        /*  username login  */
        if (requestJSON.has("u_name") && requestJSON.has("s_pw")){
            username = requestJSON.getString("u_name");
            secured_password = requestJSON.getString("s_pw");
            System.out.println("--client sent u_name: "+username);
            System.out.println("--client sent s_pw: "+secured_password);
            /*  check if username exists  */
            if ( !db.usernameExists(conn, username) ){ //exception will be forwarded up to .handle
                System.out.println("--username already exists");
                responseJSON.put("error_code", 1);
                responseJSON.put("error_description", "username doesn't exist");
                String response = responseJSON.toString() + "\n";
                r.sendResponseHeaders(200, response.length());
                OutputStream os = r.getResponseBody();
                os.write(response.getBytes());
                os.close();
                System.out.println("--responese :   "+response.trim());
                System.out.println("--request fufilled");
                return;
            }
            acc = db.get_user_from_name(conn, username); //exception will be forwarded up to .handle
        }


        /*  email login  */
        else if (requestJSON.has("email") && requestJSON.has("s_pw")){
            email = requestJSON.getString("email");
            secured_password = requestJSON.getString("s_pw");
            System.out.println("--client sent email: "+email);
            System.out.println("--client sent s_pw: "+secured_password);
            /*  check if email exists  */
            if ( !db.emailExists(conn, email) ){ //exception will be forwarded up to .handle
                System.out.println("--email already exists");
                responseJSON.put("error_code", 2);
                responseJSON.put("error_description", "email doesn't exists");
                String response = responseJSON.toString() + "\n";
                r.sendResponseHeaders(200, response.length());
                OutputStream os = r.getResponseBody();
                os.write(response.getBytes());
                os.close();
                System.out.println("--responese :   "+response.trim());
                System.out.println("--request fufilled");
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
        if (db.validatePassword(conn, secured_password, acc)){
            System.out.println("--the given account credentials are valid");
            try {
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
            catch (Exception e){
                System.out.println("## ERROR ::  " + e);
                throw new Exception("(handleReq) -- something went wrong when sending response");
            }
        }
        else {
            System.out.println("--the given account credentials are invalid");
            try {
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
            catch (Exception e){
                System.out.println("## ERROR ::  " + e);
                throw new Exception("(handleReq) -- something went wrong when sending response");
            }
        }
    }
}

