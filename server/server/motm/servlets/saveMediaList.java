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

public class saveMediaList implements HttpHandler
{
    private static AppDatabase db;
    private static SessionManager sm;
    private static Connection conn = null;

    public saveMediaList(AppDatabase appDB, SessionManager appSM) {
        db = appDB;
        sm = appSM;
        conn = db.connect();
    }

    public void handle(HttpExchange r) {
        System.out.println("\n-Received request [saveMediaList.java]");
        HttpsExchange rs = (HttpsExchange) r;
        try {
            if (r.getRequestMethod().equals("PUT")) {
                System.out.println("--request type: PUT");
                conn = db.connect();
                handlePut(r, conn);
            }else if(r.getRequestMethod().equals("POST")){
                System.out.println("--request type: POST(GET)");
                conn = db.connect();
                handleGET(r, conn);
            }else {
                System.out.println("--request type unsupported: " + r.getRequestMethod());
                rs.sendResponseHeaders(405, -1);
            }
        }catch (Exception e) {
            System.out.println("# ERROR ::  " + e);
            if (r.getResponseCode() < 0 ){ //header hasnt been sent yet
                try{
                    rs.sendResponseHeaders(500, -1);
                }catch (Exception eH500) {
                    System.out.println("# error sending h500 ::  "+eH500);
                }
            }
        }finally {
            try { //this is to safely disconnect from the db if a connection was made
                if (conn != null){
                    db.disconnect(conn);
                }

            }
            catch (Exception eDisconnect){
                System.out.println("# handled error disconnecting :: "+eDisconnect);
            }
        }
//            else if (r.getRequestMethod().equals("DELETE")) {
//                System.out.println("--request type: DELETE");
//                handleDelete(r, conn);
//            }
//            else if (r.getRequestMethod().equals("POST")) {
//                System.out.println("--request type: POST (GET)");
//                handlePost(r, conn);

}

    public void handlePut(HttpExchange r, Connection conn) throws Exception {
        String body = Utils.convert(r.getRequestBody());
        JSONObject requestJSON = new JSONObject(body);
        JSONObject responseJSON = new JSONObject();
        HttpsExchange rs = (HttpsExchange) r;

        if (requestJSON.has("username") && requestJSON.has("list_name") && requestJSON.has("list_items")){
            String username = requestJSON.getString("username");
            String list_name = requestJSON.getString("list_name");
            String items = requestJSON.getString("list_items");

            System.out.println("--client send username: "+username);
//            System.out.println("--client send accountInfo: "+accountInfo);

            /* check if user already has existing mediaID in favorites */

            if (db.list_exists(username, list_name,conn)){
                db.update_list(username,list_name,items,conn);
                responseJSON.put("error_code", 0);
                responseJSON.put("error_description", "error: user already has exisiting favorites in DB");
                String response = responseJSON.toString() + "\n";
                rs.sendResponseHeaders(200, response.length());
                OutputStream os = rs.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                try {

                    db.insert_media_item(conn, username, list_name, items);
//                db.add_titleToFavorites(conn, mediaID, accountInfo, accountType);

                    responseJSON.put("error_code", 0);
                    responseJSON.put("error_description", "successfully added media list");
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
            /* add mediaID to user favorites */
//            System.out.println("User favorites does not contain mediaID yet");

        } else {
            rs.sendResponseHeaders(400, -1);
        }
    }
    public void handleGET(HttpExchange r, Connection conn) throws Exception {
        String body = Utils.convert(r.getRequestBody());
        JSONObject requestJSON = new JSONObject(body);
        JSONObject responseJSON = new JSONObject();
        JSONArray list_names = new JSONArray();
        HttpsExchange rs = (HttpsExchange) r;

        if (requestJSON.has("username")){
            String username = requestJSON.getString("username");

            System.out.println("--client send username: "+username);
//            System.out.println("--client send accountInfo: "+accountInfo);

            try {
                list_names= db.get_user_listname(username,conn);
//                db.add_titleToFavorites(conn, mediaID, accountInfo, accountType);
                responseJSON.put("list_name",list_names);
                responseJSON.put("error_code", 0);
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
                throw new Exception("(handlePut) -- something went wrong when sending response");
            }
        }else {
            rs.sendResponseHeaders(400, -1);
        }
    }
}
