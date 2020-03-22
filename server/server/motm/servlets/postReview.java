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

public class addMediaProfile implements HttpHandler
{
    private static AppDatabase db;
    private static SessionManager sm;
    private static Connection conn = null;

    public addMediaProfile(AppDatabase appDB, SessionManager appSM) {
        db = appDB;
        sm = appSM;
        conn = db.connect();
    }

    public void handle(HttpExchange r) {
        System.out.println("\n-Received request [postReview]");
        HttpsExchange rs = (HttpsExchange) r;
        try {
            if (r.getRequestMethod().equals("PUT")) {
                System.out.println("--request type: PUT");
                handleReq(r, conn);
            }
            else {
                System.out.println("--request type unsupported: "+r.getRequestMethod());
                rs.sendResponseHeaders(405, -1);
            }
        } 
        catch (Exception e) {
            System.out.println("# ERROR addMediaProfile.handle ::  " + e);
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

        HttpsExchange rs = (HttpsExchange) r;

        if (requestJSON.has("email") && requestJSON.has("text")) {
            String email = requestJSON.getString("email");
            String text = requestJSON.getString("text");
            
            JSONObject responseJSON = new JSONObject();

            /*  register the account;  both username and email can be used  */
            System.out.println("--adding review to database");
            db.add_media_title(conn, email, text); //exception will be forwarded up to .handle
            System.out.println("----successfully added media to database");

            /*  send response  */
            try {
                responseJSON.put("error_code", 0);
                responseJSON.put("error_description", "successfully added review to database");
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
