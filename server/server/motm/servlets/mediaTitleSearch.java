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
 * Client sends { query: string (,  amount: int) } and gets 
 * { error_code: int (,  matches: [int mediaID, ...]) }
 * The client asks the server to find titles that contain some string 
 * and returns any matches it finds (which may be no matches and so returning
 * an empty array). 
 * 
 * 
 * Error Codes: 
 *      0 --  successfully found some matches
 *      1 --  found no matches
 *      2 --  query too short
 *      ~~
 */

public class mediaTitleSearch implements HttpHandler
{
    private static AppDatabase db;
    private static SessionManager sm;
    private static Connection conn = null;

    public mediaTitleSearch(AppDatabase appDB, SessionManager appSM) {
        db = appDB;
        sm = appSM;
        conn = db.connect();
    }

    public void handle(HttpExchange r) {
        System.out.println("\n-Received request [mediaTitleSearch]");
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
            System.out.println("# ERROR mediaTitleSearch.handle ::  " + e);
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

        if ( requestJSON.has("query") ) {
            String query = requestJSON.getString("query");
            //if (query.length() < 4 ) error_code 2, query too short

            int amount = 50; //default limit of up to 50 matches
            if ( requestJSON.has("amount") ) 
                amount = requestJSON.getInt("amount");

            /*  search the database for matches  */
            System.out.println("--searching the database for matching titles");
            ArrayList<Integer> matches = db.get_mediaIDs_by_search(conn, query, amount); //exception will be forwarded up to .handle
            System.out.println("----successfully completed search");
            System.out.println("----["+matches.size()+"] matches found");


            /*  send response  */
            try {
                if( matches.size() > 0){
                    responseJSON.put("error_code", 0);
                    responseJSON.put("error_description", "successfully found matches");
                    responseJSON.put("matches", new JSONArray(matches));
                }
                else {
                    responseJSON.put("error_code", 1);
                    responseJSON.put("error_description", "no matches found");
                    responseJSON.put("matches", new JSONArray()); //not really necessary
                }
                
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
