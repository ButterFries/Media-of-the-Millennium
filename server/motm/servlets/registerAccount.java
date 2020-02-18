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



/* Send { u_name: "my_username", email: "my_email@example", s_pw: "hashed_password" }
 * and it will return (TBD) something like { error_code: int } 
 */

public class registerAccount implements HttpHandler
{
    private static appDatabase db;

    public registerAccount(appDatabase appDB) {
        db = appDB;
    }

    public void handle(HttpExchange r) {
        System.out.println("\n-Received request [registerAccount]");
        Connection conn = null;
        try {
            if (r.getRequestMethod().equals("PUT")) {
                System.out.println("--request type: PUT");
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

        if (requestJSON.has("u_name") && requestJSON.has("email") && requestJSON.has("s_pw")){
            String username = requestJSON.getString("u_name");
            String email = requestJSON.getString("email");
            String secured_password = requestJSON.getString("s_pw");
            System.out.println("--client sent u_name: "+username);
            System.out.println("--client sent email: "+email);
            System.out.println("--client sent s_pw: "+secured_password);

            JSONObject responseJSON = new JSONObject();

            /*  check if username exists  */
            if (db.usernameExists(conn, username)){ //exception will be forwarded up to .handle
                System.out.println("--username already exists");
                responseJSON.put("error_code", 1);
                responseJSON.put("error_description", "username already exists");
                String response = responseJSON.toString() + "\n";
                r.sendResponseHeaders(200, response.length());
                OutputStream os = r.getResponseBody();
                os.write(response.getBytes());
                os.close();
                System.out.println("--responese :   "+response.trim());
                System.out.println("--request fufilled");
                return;
            }

            /*  check if email exists  */
            else if (db.emailExists(conn, email)){ //exception will be forwarded up to .handle
                System.out.println("--email already exists");
                responseJSON.put("error_code", 2);
                responseJSON.put("error_description", "email already exists");
                String response = responseJSON.toString() + "\n";
                r.sendResponseHeaders(200, response.length());
                OutputStream os = r.getResponseBody();
                os.write(response.getBytes());
                os.close();
                System.out.println("--responese :   "+response.trim());
                System.out.println("--request fufilled");
                return;
            }

            /*  register the account;  both username and email can be used  */
            db.add_account(conn, username, email, secured_password); //exception will be forwarded up to .handle
            System.out.println("--successfully added account to database");
            

            /*  send response  */
            try {
                responseJSON.put("error_code", 0);
                responseJSON.put("error_description", "successfully registered account to database");
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
            r.sendResponseHeaders(400, -1);
        }
    }
}
