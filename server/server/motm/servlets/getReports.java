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
        conn = db.connect();
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
                handlePut(r, conn);
            }
            else if (r.getRequestMethod().equals("DELETE")) {
                System.out.println("--request type: DELETE");
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
    }

    /**
     * adding report to database
     */
    public void handlePut(HttpExchange r, Connection conn) throws Exception {
        String body = Utils.convert(r.getRequestBody());
        JSONObject requestJSON = new JSONObject(body);
        JSONObject responseJSON = new JSONObject();
        HttpsExchange rs = (HttpsExchange) r;

        if (requestJSON.has("reviewID") && (requestJSON.has("accountInfo") && (requestJSON.has("sessionID")))){
            int reviewID = requestJSON.getInt("reviewID");
            String sessionID = requestJSON.getString("sessionID");
            String accountInfo = requestJSON.getString("accountInfo");

            System.out.println("--client send reviewID: "+reviewID);


            if (db.hasReport(conn, reviewID, accountInfo)){
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
                db.add_report(conn, reviewID, accountInfo, sessionID);

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

}
