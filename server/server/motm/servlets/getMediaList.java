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

public class getMediaList implements HttpHandler {
    private static AppDatabase db;
    private static SessionManager sm;
    private static Connection conn = null;

    public getMediaList(AppDatabase appDB, SessionManager appSM) {
        db = appDB;
        sm = appSM;
        conn = db.connect();
    }

    public void handle(HttpExchange r) {
        System.out.println("\n-Received request [getMediaList.java]");
        HttpsExchange rs = (HttpsExchange) r;
        try {
           if(r.getRequestMethod().equals("POST")){
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
                if (conn != null) {
                    db.disconnect(conn);
                }
            }
            catch (Exception eDisconnect){
                System.out.println("# handled error disconnecting :: "+eDisconnect);
            }
        }
    }

    public void handleGET(HttpExchange r, Connection conn) throws Exception {
        String body = Utils.convert(r.getRequestBody());
        JSONObject requestJSON = new JSONObject(body);
        JSONObject responseJSON = new JSONObject();

        HttpsExchange rs = (HttpsExchange) r;

        if (requestJSON.has("username") && requestJSON.has("list_name")){
            String username = requestJSON.getString("username");
            String list_name = requestJSON.getString("list_name");

            System.out.println("--client send username: "+username);
//            System.out.println("--client send accountInfo: "+accountInfo);

            try {
                responseJSON= db.get_user_lists(username,list_name,conn);
//                db.add_titleToFavorites(conn, mediaID, accountInfo, accountType);
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