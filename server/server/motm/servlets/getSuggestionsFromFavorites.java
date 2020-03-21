package server.motm.servlets;
import server.motm.database.*;
import server.motm.utils.*;
import server.motm.session.*;


import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections; 

import java.io.IOException;
import java.io.OutputStream;

import org.json.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;


import java.sql.*;

import com.sun.net.httpserver.HttpsExchange;

/* 
 * Client sends { username/email: string user, session_token: string } and gets 
 * { error_code: int (,  suggestions: [int mediaID, ...]) }
 * The client asks the server to find titles that contain some the same genre
 * as the titles in the users favorites and returns any matches it finds 
 * (which may be no matches and so returning an empty array). 
 * If there are no favorites then it will choose at random.
 * Up to 20 mediaID suggestions will be returned.
 * 
 * 
 * Error Codes: 
 *      0 --  successfully found some favorites and matches
 *      1 --  found some favorites but no matches (no other titles with fav genre)
 *      2 --  found no favorties but some matches (fav list empty)
 *      3 --  found no favorites and no matches (database empty)
 *      4 --  user doesn't exist
 *      5 --  email doesn't exist
 *      6 --  invalid session
 *      7 --  no genres in favorites
 *      ~~
 */
public class getSuggestionsFromFavorites implements HttpHandler
{
    private static AppDatabase db;
    private static SessionManager sm;
    private static Connection conn = null;

    public getSuggestionsFromFavorites(AppDatabase appDB, SessionManager appSM) {
        db = appDB;
        sm = appSM;
        conn = db.connect();
    }

    public void handle(HttpExchange r) {
        System.out.println("\n-Received request [getSuggestionsFromFavorites]");
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
            System.out.println("# ERROR getSuggestionsFromFavorites.handle ::  " + e);
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


        if ( requestJSON.has("session_token") && (requestJSON.has("username") || requestJSON.has("email")) ) {
            String sessionID = requestJSON.getString("session_token");
            

            /*  check if user exists  */
            AppDatabase.accountInfo acc = null;
            String user = null;
            String userType = null;
            if ( requestJSON.has("username") ) {
                String username = requestJSON.getString("username");
                user = username;
                userType = "username";
                System.out.println("----username: "+username);
                if ( !db.usernameExists(conn, username) ){ //exception will be forwarded up to .handle
                    response_err(r, responseJSON, 200, "username doesn't exist",4);
                    return;
                }
                acc = db.get_user_from_name(conn, username);
            }
            else { //(  requestJSON.has("email") 
                String email = requestJSON.getString("email");
                System.out.println("----email: "+email);
                user = email;
                userType = "email";
                if ( !db.emailExists(conn, email) ){ //exception will be forwarded up to .handle
                    response_err(r, responseJSON, 200, "email doesn't exist",5);
                    return;
                }
                acc = db.get_user_from_email(conn, email);
            }
            int userID = acc.get_ID();


            /*  check if session is valid  */
            if ( !sm.isValidSession(userID+"", sessionID) ){
                response_err(r, responseJSON, 200, "invalid session for user ["+user+"] with session ID ["+sessionID+"]", 6);
                return;
            }


            /*  fetching favorites  */
            String favorites_str = db.retrieve_favorites(conn, user, userType);
            String[] favorites = favorites_str.split(",");


            if (favorites.length < 1){
                List<Integer> select_suggestions = db.get_random_media(conn, 20);
                if (select_suggestions.size() < 1){
                    response_err(r, responseJSON, 200, "found no favorites and no matches", 3);
                    return;
                }
                responseJSON.put("error_code", 2);
                responseJSON.put("error_description", "found no favorites but some matches");
                responseJSON.put("suggestions", new JSONArray(select_suggestions));
            }
            else{
                /*  get genres from favorites  */
                System.out.println("--compiling a list of genres from favorites ["+favorites_str+"]");
                ArrayList<String> all_genres = db.get_genres_from_favorites(conn, favorites);
                System.out.println("----all unique genres ["+all_genres+"]");
                /* select some (5) genres */
                Collections.shuffle(all_genres); 
                List<String> select_genres = all_genres.subList( 0, Integer.min(all_genres.size(), 5) ); 


                /*  get mediaIDs from those genres  */
                List<String> already_used_IDs = Arrays.asList(favorites);
                List<String> all_suggestions = new ArrayList<String>();
                for (String genre : select_genres){  //select 10 mediaIDs from that genre
                    ArrayList<String> genre_suggestions = new ArrayList<String>();
                    for ( Integer mediaID : db.get_mediaIDs_by_genre(conn, genre, already_used_IDs.toArray(new String[0]), 10))
                        genre_suggestions.add(String.valueOf(mediaID));
                    System.out.println("----got for ["+genre+"] genre the titles ["+genre_suggestions.toString()+"]");
                    all_suggestions.addAll(genre_suggestions);
                    already_used_IDs.addAll(genre_suggestions);
                }
                if (all_suggestions.size() < 1){
                    response_err(r, responseJSON, 200, "found some favorites but no matches", 1);
                    return;
                }
                System.out.println("----all suggestions ["+all_suggestions.toString()+"]"); //max size 50
                /* select up to 20 from the list of all mediaID suggestions */
                Collections.shuffle(all_suggestions); 
                List<String> select_suggestions = all_suggestions.subList( 0, Integer.min(all_suggestions.size(), 20) ); 
                responseJSON.put("error_code", 0);
                responseJSON.put("error_description", "successfully found suggestions");
                responseJSON.put("suggestions", new JSONArray(select_suggestions));
            }


            
                

            /*  send response  */
            try {
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

    private void response_err(HttpExchange r, JSONObject responseJSON, int header_code, String err, int err_code) throws Exception {
        System.out.println("----"+err);
        responseJSON.put("error_code", err_code);
        responseJSON.put("error_description", err);
        String response = responseJSON.toString() + "\n";
        r.sendResponseHeaders(header_code, response.length());
        OutputStream os = r.getResponseBody();
        os.write(response.getBytes());
        os.close();
        System.out.println("--responese :   "+response.trim());
        System.out.println("--request fufilled");
    }
}
