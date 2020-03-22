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

/* (NOT USED YET)
 * Client sends {} and gets {}
 * 
 * Error Codes: 
 *      0 --  
 *      1 --  
 *      2 --  
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
        System.out.println("\n-Received request [addMediaProfile]");
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
    }

    public void handleReq(HttpExchange r, Connection conn) throws Exception {
        String body = Utils.convert(r.getRequestBody());
        JSONObject requestJSON = new JSONObject(body);

        HttpsExchange rs = (HttpsExchange) r;

        if (requestJSON.has("title") && requestJSON.has("mediaType") && requestJSON.has("summary") && requestJSON.has("genres")
            && requestJSON.has("tags") && requestJSON.has("links") && requestJSON.has("image")) {
            String title = requestJSON.getString("title");
            String mediaType = requestJSON.getString("mediaType");
            String summary = requestJSON.getString("summary");
            String genres = requestJSON.getString("genres");
            String tags = requestJSON.getString("tags");
            String links = requestJSON.getString("links");
            String img = requestJSON.getString("image"); 
            //THIS IS ONLY FOR LOCAL IMAGES SO WHEN WE ADD MEDIA PROFILE WE SHOULD HAVE
            //THE IMAGE DOWNLOADED

            JSONObject responseJSON = new JSONObject();

            /*  register the title to db  */
            System.out.println("--adding media to database");
            db.add_media_title(conn, title, mediaType, summary, genres, tags, links, img); //exception will be forwarded up to .handle
            System.out.println("----successfully added media to database");

            /*  send response  */
            try {
                responseJSON.put("error_code", 0);
                responseJSON.put("error_description", "successfully added media to database");
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
