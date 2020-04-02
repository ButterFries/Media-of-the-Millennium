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

public class getReviews implements HttpHandler
{
    private static AppDatabase db;
    private static SessionManager sm;
    private static Connection conn = null;

    public getReviews(AppDatabase appDB, SessionManager appSM) {
        db = appDB;
        sm = appSM;

    }

    public void handle(HttpExchange r) {
        System.out.println("\n-Received request [getReviews]");
        HttpsExchange rs = (HttpsExchange) r;
        try {
            if (r.getRequestMethod().equals("POST")) {
                System.out.println("--request type: POST");
                conn = db.connect();
                handleReq(r, conn);
            }
            else {
                System.out.println("--request type unsupported: "+r.getRequestMethod());
                rs.sendResponseHeaders(405, -1);
            }
        }
        catch (Exception e) {
        	e.printStackTrace();
            System.out.println("# ERROR getReviews.handle ::  " + e);
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
    // Requires userType, username/password, sessionID, mediaID, and reviewText
    public void handleReq(HttpExchange r, Connection conn) throws Exception {
        String body = Utils.convert(r.getRequestBody());
        JSONObject requestJSON = new JSONObject(body);

        HttpsExchange rs = (HttpsExchange) r;

        if (requestJSON.has("mediaID")) {
            int mediaID = requestJSON.getInt("mediaID");
            int limit = 0;
            
            if(requestJSON.has("limit"))
            	limit = requestJSON.getInt("limit");
            
            JSONObject responseJSON = new JSONObject();

            System.out.println("--searching for reviews");

            // Should not store username as part of the review since the saved username will not update if the poster changes their username
            // Should instead dynamically retrieve the username from the userID when retrieving the review
            responseJSON.put("reviews", db.get_all_reviews(conn, mediaID, limit)); //exception will be forwarded up to .handle

            System.out.println("----finished searching for reviews");

            /*  send response  */
            try {
                responseJSON.put("error_code", 0);
                responseJSON.put("error_description", "successfully searched for reviews");
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
