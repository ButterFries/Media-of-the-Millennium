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

import java.sql.*;

import com.sun.net.httpserver.HttpsExchange;

public class getReports implements HttpHandler
{
    private static AppDatabase db;
    private static SessionManager sm;
    private static Connection conn = null;

    public getReports(AppDatabase appDB, SessionManager appSM) {
        db = appDB;
        sm = appSM;

    }

    /* Client sends the reviewID, text and type and in return the report gets added
     *
     * Error Codes:
     *      0 --  successfully added/deleted report
     *      1 --  failed to add/delete
     *      ~~
     */
    public void handle(HttpExchange r) {
        System.out.println("\n-Received request [getReports.java]");
        HttpsExchange rs = (HttpsExchange) r;
        try {
            if (r.getRequestMethod().equals("PUT")) {
                System.out.println("--request type: PUT");
                conn = db.connect();
                handlePut(r, conn);
            }
            else if (r.getRequestMethod().equals("DELETE")) {
                System.out.println("--request type: DELETE");
                conn = db.connect();
                handleDelete(r, conn);
            }
            else {
                System.out.println("--request type unsupported: "+r.getRequestMethod());
                rs.sendResponseHeaders(405, -1);
            }
        }
        catch (Exception e) {
            System.out.println("# ERROR ::  " + e);
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

    /**
     * adding report to database
     */
    public void handlePut(HttpExchange r, Connection conn) throws Exception {
        String body = Utils.convert(r.getRequestBody());
        JSONObject requestJSON = new JSONObject(body);
        JSONObject responseJSON = new JSONObject();
        HttpsExchange rs = (HttpsExchange) r;

        if (requestJSON.has("reviewID") &&
        (requestJSON.has("username") || requestJSON.has("email")) && requestJSON.has("session_token")){
            int reviewID = requestJSON.getInt("reviewID");
            String sessionID = requestJSON.getString("session_token");

            System.out.println("--client send reviewID: "+reviewID);

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

            //check if user already made a report
            if (db.hasReport(conn, reviewID, user)){
                System.out.println("--user already reported on review");
                responseJSON.put("error_code", 1);
                responseJSON.put("error_description", "error: user already reported on this review");
                String response = responseJSON.toString() + "\n";
                rs.sendResponseHeaders(200, response.length());
                OutputStream os = rs.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            }



            /* add report to reports table */
            try {
                db.add_report(conn, reviewID, user);

                responseJSON.put("error_code", 0);
                responseJSON.put("error_description", "successfully added report to reports");
                String response = responseJSON.toString() + "\n";
                rs.sendResponseHeaders(200, response.length());
                OutputStream os = rs.getResponseBody();
                os.write(response.getBytes());
                os.close();
                System.out.println("--response :   "+response.trim());
                System.out.println("--request fufilled");
            }
            catch (Exception e){
                System.out.println("## ERROR ::  " + e);
                throw new Exception("(handlePut) -- something went wrong when sending response");
            }
        }
        else {
            rs.sendResponseHeaders(400, -1);
        }
    }

    /**
     * removing report from database (only usable in backend/cmd line)
     */
    public void handleDelete(HttpExchange r, Connection conn) throws Exception {
        String body = Utils.convert(r.getRequestBody());
        JSONObject requestJSON = new JSONObject(body);
        JSONObject responseJSON = new JSONObject();
        HttpsExchange rs = (HttpsExchange) r;

        if (requestJSON.has("reportID")){
            int reportID = requestJSON.getInt("reportID");

            System.out.println("--client send reportID: "+reportID);

            try {
                db.delete_report(conn, reportID);

                responseJSON.put("error_code", 0);
                responseJSON.put("error_description", "successfully deleted report from reports");
                String response = responseJSON.toString() + "\n";
                rs.sendResponseHeaders(200, response.length());
                OutputStream os = rs.getResponseBody();
                os.write(response.getBytes());
                os.close();
                System.out.println("--response :   "+response.trim());
                System.out.println("--request fufilled");
            }
            catch (Exception e){
                System.out.println("## ERROR ::  " + e);
                throw new Exception("(handleDelete) -- something went wrong when sending response");
            }
        }
        else {
            rs.sendResponseHeaders(400, -1);
        }

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

}
