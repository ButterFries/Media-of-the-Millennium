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
 * Client sends { mediaID: int,  email/username: string } and gets 
 * { error_code: int, rating: float }
 * The client asks the server to for the rating that a user made on some media 
 * title.
 * 
 * 
 * Error Codes: 
 *      0 --  successfully fetched rating info
 *      1 --  mediaID does not exist
 *      2 --  username does not exist
 *      3 --  email does not exist
 *      4 --  user has not rated
 *      ~~
 */

public class getUsersRating implements HttpHandler
{
    private static AppDatabase db;
    private static SessionManager sm;
    private static Connection conn = null;

    public getUsersRating(AppDatabase appDB, SessionManager appSM) {
        db = appDB;
        sm = appSM;
        conn = db.connect();
    }

    public void handle(HttpExchange r) {
        System.out.println("\n-Received request [getUsersRating]");
        HttpsExchange rs = (HttpsExchange) r;
        try {
            if (r.getRequestMethod().equals("POST")) {
                System.out.println("--request type: POST (GET)");
                handleReq(r, conn);
            }
            else {
                System.out.println("--request type unsupported: "+r.getRequestMethod());
                rs.sendResponseHeaders(405, -1);
            }
        } 
        catch (Exception e) {
            System.out.println("# ERROR getUsersRating.handle ::  " + e);
            if (r.getResponseCode() < 0 ){ //header hasnt been sent yet
                try{
                    rs.sendResponseHeaders(500, -1);
                }catch (Exception eH500) {
                    System.out.println("# error sending h500 ::  "+eH500);
                }
            }
        }
    }

    public void handleReq(HttpExchange r, Connection conn) throws Exception {
        String body = Utils.convert(r.getRequestBody());
        JSONObject requestJSON = new JSONObject(body);
        JSONObject responseJSON = new JSONObject();

        HttpsExchange rs = (HttpsExchange) r;
        

        if ( requestJSON.has("mediaID") ) {
            Integer mediaID = requestJSON.getInt("mediaID");


            /*  search the database for matches  */
            System.out.println("--fetching rating info for mediaID ["+mediaID+"]");
            if ( !db.mediaExists(conn, mediaID) ){
                response_no_media(r, responseJSON, mediaID);
                return;
            }
            

            /*  check if user has rated  */
            String user = null;
            AppDatabase.accountInfo acc = null;
            System.out.println("--checking if user has rated");
            if ( requestJSON.has("username") ) {
                String username = requestJSON.getString("username");
                System.out.println("----username: "+username);
                user = username;
                if ( !db.usernameExists(conn, username) ){ //exception will be forwarded up to .handle
                    response_no_user(r, responseJSON);
                    return;
                }
                acc = db.get_user_from_name(conn, username);
                if (! db.hasRated(conn, acc.get_ID(), mediaID) ){
                    response_no_rating(r, responseJSON, mediaID, user);
                    return;
                }
            }
            else if (  requestJSON.has("email") ){
                String email = requestJSON.getString("email");
                System.out.println("----email: "+email);
                user = email;
                if ( !db.emailExists(conn, email) ){ //exception will be forwarded up to .handle
                    response_no_email(r, responseJSON);
                    return;
                }
                acc = db.get_user_from_email(conn, email);
                if (! db.hasRated(conn, acc.get_ID(), mediaID) ){
                    response_no_rating(r, responseJSON, mediaID, user);
                    return;
                }
            }
            else {
                rs.sendResponseHeaders(400, -1);
            }
            


            /*  get user's rating  */
            System.out.println("----fetching user's rating info on media title");
            float rating =  db.get_userMediaRating(conn, acc.get_ID(), mediaID);
            System.out.println("----successfully fetched rating info");
            System.out.println("----user ["+user+"] had rating: ["+rating+"] on mediaID ["+mediaID+"]");


            /*  send response  */
            try {
                responseJSON.put("error_code", 0);
                responseJSON.put("error_description", "successfully fetched rating info");
                responseJSON.put("rating", rating);
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

    private void response_no_rating(HttpExchange r, JSONObject responseJSON, int mediaID, String user) throws Exception {
        System.out.println("----user ["+user+"] has not rated on mediaID ["+mediaID+"]");
        responseJSON.put("error_code", 4);
        responseJSON.put("error_description", "user has not rated");
        String response = responseJSON.toString() + "\n";
        r.sendResponseHeaders(404, response.length());
        OutputStream os = r.getResponseBody();
        os.write(response.getBytes());
        os.close();
        System.out.println("--responese :   "+response.trim());
        System.out.println("--request fufilled");
    }
}
