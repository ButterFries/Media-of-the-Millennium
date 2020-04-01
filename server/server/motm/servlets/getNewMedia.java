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

public class getNewMedia implements HttpHandler
{
    private static AppDatabase db;
    private static SessionManager sm;
    private static Connection conn = null;

    public getNewMedia(AppDatabase appDB, SessionManager appSM) {
        db = appDB;
        sm = appSM;

    }

    /* Client sends a rating (and maybe a sessionID) and in return gets
     * a JSON containing {error_code: int, profile: {common: {~:~,,}, distinct: {~:~,,}} }
     *
     * Error Codes:
     *      0 --  successfully fetched profile
     *      1 --  rating invalid
     *      2 --  database is missing data and cannot be fetched (critical error)
     *      ~~
     */
    public void handle(HttpExchange r) {
        System.out.println("\n-Received request [getNewMedia]");
        HttpsExchange rs = (HttpsExchange) r;
        try {
            if (r.getRequestMethod().equals("POST")) {
                System.out.println("--request type: POST (GET)");
                conn = db.connect();
                handleReq(r, conn);
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

    public void handleReq(HttpExchange r, Connection conn) throws Exception {
        String body = Utils.convert(r.getRequestBody());
        JSONObject requestJSON = new JSONObject(body);
        JSONObject responseJSON = new JSONObject();
        HttpsExchange rs = (HttpsExchange) r;

        if (requestJSON.has("mediaType")){
            String mediaType = requestJSON.getString("mediaType");
            System.out.println("--client send mediaType: "+mediaType);
            JSONObject profile = null;
            try{
                responseJSON.put("mediaIDs", db.get_mediaIDs_by_NewMedia(conn, mediaType));
            } catch (SQLDataException data_ex){
                System.out.println("#  ERROR ::  "+ data_ex);
                responseJSON.put("error_code", 2);
                responseJSON.put("error_description", "critical error:  database is missing data; rating cannot be fetched");
                String response = responseJSON.toString() + "\n";
                rs.sendResponseHeaders(500, response.length());
                OutputStream os = rs.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            } catch (SQLException sql_ex){
                System.out.println("#  ERROR ::  "+ sql_ex);
                responseJSON.put("error_code", 1);
                responseJSON.put("error_description", "invalid mediaType");
                String response = responseJSON.toString() + "\n";
                rs.sendResponseHeaders(404, response.length());
                OutputStream os = rs.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            } catch (Exception ex){
                System.out.println("#  ERROR ::  "+ ex);
                rs.sendResponseHeaders(500, -1);
                return;
            }
            try {
                responseJSON.put("error_code", 0);
                responseJSON.put("error_description", "successfully fetched top rated media");
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