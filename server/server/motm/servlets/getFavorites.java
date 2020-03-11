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

public class getFavorites implements HttpHandler
{
    private static AppDatabase db;
    private static SessionManager sm;
    private static Connection conn = null;

    public getFavorites(AppDatabase appDB, SessionManager appSM) {
        db = appDB;
        sm = appSM;
        conn = db.connect();
    }

    /* Client sends there userID and mediaID (and maybe a sessionID) and in return gets
     * a JSON containing {error_code: int, profile: {common: {~:~,,}, distinct: {~:~,,}} }
     *
     * Error Codes:
     *      0 --  successfully fetched/added/removed user favourites
     *      1 --  mediaID invalid
     *      2 --  accountID invalid
     *      3 --  user already has favorite in DB -- can't add
     *      4 --  user doesnt have favorite in DB -- can't delete
     *      ~~
     */
    public void handle(HttpExchange r) {
        System.out.println("\n-Received request [getFavorites.java]");
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
            else if (r.getRequestMethod().equals("POST")) {
                System.out.println("--request type: POST (GET)");
                handlePost(r, conn);
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
     * adding media profile to user favourites
     */
    public void handlePut(HttpExchange r, Connection conn) throws Exception {
        String body = Utils.convert(r.getRequestBody());
        JSONObject requestJSON = new JSONObject(body);
        JSONObject responseJSON = new JSONObject();
        HttpsExchange rs = (HttpsExchange) r;

        if (requestJSON.has("mediaID") && requestJSON.has("accountID")){
            int mediaID = requestJSON.getInt("mediaID");
            int accountID = requestJSON.getInt("accountID");
            System.out.println("--client send mediaID: "+mediaID);
            System.out.println("--client send accountID: "+accountID);

            /* check if user already has existing mediaID in favorites */

            if (db.hasFavorite(conn, mediaID, accountID)){
                System.out.println("--favorited ID already exists");
                responseJSON.put("error_code", 3);
                responseJSON.put("error_description", "error: user already has exisiting favorites in DB");
                String response = responseJSON.toString() + "\n";
                rs.sendResponseHeaders(200, response.length());
                OutputStream os = rs.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            }
            /* add mediaID to user favorites */
            System.out.println("User favorites does not contain mediaID yet");
            try {
                db.add_titleToFavorites(conn, mediaID, accountID);

                responseJSON.put("error_code", 0);
                responseJSON.put("error_description", "successfully added media profile to favorites");
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
     * removing a media profile (ID) from user favorites
     */
    public void handleDelete(HttpExchange r, Connection conn) throws Exception {
        String body = Utils.convert(r.getRequestBody());
        JSONObject requestJSON = new JSONObject(body);
        JSONObject responseJSON = new JSONObject();
        HttpsExchange rs = (HttpsExchange) r;

        if (requestJSON.has("mediaID") && requestJSON.has("accountID")){
            int mediaID = requestJSON.getInt("mediaID");
            int accountID = requestJSON.getInt("accountID");
            System.out.println("--client send mediaID: "+mediaID);
            System.out.println("--client send accountID: "+accountID);

            /* check if user does NOT have existing mediaID in favorites */
            if (!db.hasFavorite(conn, mediaID, accountID)){
                responseJSON.put("error_code", 3);
                responseJSON.put("error_description", "error: cant delete, user does not have ID as a favorite");
                String response = responseJSON.toString() + "\n";
                rs.sendResponseHeaders(200, response.length());
                OutputStream os = rs.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            }

            System.out.println("User favorites has contain mediaID yet.. attempting to delete");
            try {
                db.remove_favorite(conn, mediaID, accountID);

                responseJSON.put("error_code", 0);
                responseJSON.put("error_description", "successfully removed media profile from favorites");
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
    /**
     * retrieving all favourites, for displaying on screen
     */
    public void handlePost(HttpExchange r, Connection conn) throws Exception {
        String body = Utils.convert(r.getRequestBody());
        JSONObject requestJSON = new JSONObject(body);
        JSONObject responseJSON = new JSONObject();
        HttpsExchange rs = (HttpsExchange) r;

        if (requestJSON.has("accountID")){
            //int mediaID = requestJSON.getInt("mediaID");
            int accountID = requestJSON.getInt("accountID");
            //String mID = Integer.toString(mediaID);
            String uID = Integer.toString(accountID);

            //System.out.println("--client send mediaID: "+mediaID);
            System.out.println("--client send accountID: "+accountID);
            try {
                String fav_string = db.retrieve_favorites(conn, accountID);
                String[] fav_list = fav_string.split(",");
                JSONArray favs = new JSONArray();

                for (String f : fav_list){
                    try {
                        String title = db.retrieve_title(conn, f);
                        JSONObject title_and_id = new JSONObject();
                        title_and_id.put("mediaId", f);
                        title_and_id.put("title", title);
                        favs.put(title_and_id);
                    } catch (SQLException ex) {
                        throw new SQLException("Error while retrieving title");
                    }
                }

                System.out.println("Succesfully added all Id: title to favs (JSONArray)");

                //responseJSON.put("error_code", 0);
                //responseJSON.put("error_description", "successfully sent media ID and title array");
                //sending jsonarray
                String response = favs.toString() + "\n";
                rs.sendResponseHeaders(200, response.length());
                OutputStream os = rs.getResponseBody();
                os.write(response.getBytes());
                os.close();
                System.out.println("--response :   "+response.trim());
                System.out.println("--request fufilled");

            } catch (Exception e){
                System.out.println("## ERROR ::  " + e);
                throw new Exception("(handlePost) -- something went wrong when sending response");
            }
        }
        else {
            rs.sendResponseHeaders(400, -1);
        }

    }
}
