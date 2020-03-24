package server.motm.servlets;
import server.motm.database.*;
import server.motm.utils.*;
import server.motm.session.*;


import java.util.Map;

import javax.security.auth.login.CredentialException;

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

/* (NOT USED YET)
 * Client sends {} and gets {}
 * 
 * Error Codes: 
 *      0 --  
 *      1 --  
 *      2 --  
 *      ~~
 */

public class addReview implements HttpHandler
{
    private static AppDatabase db;
    private static SessionManager sm;
    private static Connection conn = null;

    public addReview(AppDatabase appDB, SessionManager appSM) {
        db = appDB;
        sm = appSM;
        conn = db.connect();
    }

    public void handle(HttpExchange r) {
        System.out.println("\n-Received request [addReview]");
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
            System.out.println("# ERROR addReview.handle ::  " + e);
            if (r.getResponseCode() < 0 ){ //header hasnt been sent yet
                try{
                    rs.sendResponseHeaders(500, -1);
                }catch (Exception eH500) {
                    System.out.println("# error sending h500 ::  "+eH500);
                }
            }
        }
    }
    // Requires userType, username/password, sessionID, mediaID, and reviewText
    public void handleReq(HttpExchange r, Connection conn) throws Exception {
        String body = Utils.convert(r.getRequestBody());
        JSONObject requestJSON = new JSONObject(body);

        HttpsExchange rs = (HttpsExchange) r;

        if (requestJSON.has("mediaID") && requestJSON.has("userType") && requestJSON.has("user") && requestJSON.has("sessionID") && requestJSON.has("review")) {
            int mediaID = requestJSON.getInt("mediaID");
            String user = requestJSON.getString("user");
            String userType = requestJSON.getString("userType");
            String sessionId = requestJSON.getString("sessionID");
            String reviewText = requestJSON.getString("review");
            
            JSONObject responseJSON = new JSONObject();
            
            boolean validID = false;
            if (userType.equals("email"))
            	validID = sm.isValidSession_e(user, sessionId);
            else if (userType.equals("username"))
            	validID = sm.isValidSession_u(user, sessionId);
            
            // If the sessionID doesn't match the given username/email or sessionID is invalid
            if (!validID) {
            	throw new CredentialException("sessionID doesn't match the given username/email or sessionID is invalid");
            }
            int userID = Integer.parseInt(sm.getUID(sessionId));
            
            /*  register the title to db  */
            System.out.println("--adding review to database");
            
            // Should not store username as part of the review since the saved username will not update if the poster changes their username
            // Should instead dynamically retrieve the username from the userID when retrieving the review
            db.add_review(conn, userID, mediaID, reviewText); //exception will be forwarded up to .handle
            
            System.out.println("----successfully added review to database");
            
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