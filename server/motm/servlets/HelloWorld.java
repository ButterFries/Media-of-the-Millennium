package server.motm.servlets;
import server.motm.database.*;
import server.motm.utils.*;


import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import java.io.IOException;
import java.io.OutputStream;

import org.json.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.sql.*;

public class HelloWorld implements HttpHandler
{
    private static appDatabase db;

    public HelloWorld(appDatabase appDB) {
        db = appDB;
    }

    public void handle(HttpExchange r) {
        System.out.println("\n-Received request [HelloWorld]");
        Connection conn = null;
        try {
            if (r.getRequestMethod().equals("GET")) {
                System.out.println("--request type: GET");
                conn = db.connect();
                handleReq(r, conn);
            } else if (r.getRequestMethod().equals("POST")) {
                System.out.println("--request type: POST");
                conn = db.connect();
                handleReq(r, conn);
            }
            else if (r.getRequestMethod().equals("PUT")) {
                System.out.println("--request type: PUT");
                conn = db.connect();
                handleReq(r, conn);
            }
            else {
                System.out.println("--request type unsupported: "+r.getRequestMethod());
                r.sendResponseHeaders(405, -1);
            }
        } 
        catch (Exception e) {
            System.out.println("# ERROR HelloWorld.handle ::  " + e);
            try{
                r.sendResponseHeaders(500, -1);
            }catch (Exception eH500) {
                System.out.println("# handled error sending h500 ::  "+eH500);
            }
        }
        finally {
            try { //this is to safely disconnect from the db if a connection was made
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

        if (requestJSON.has("hello"))
            System.out.println("--client said hello: "+requestJSON.get("hello").toString());

        System.out.println("--replying with {hello: client}");
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("hello", "client");

        try {
            String response = responseJSON.toString() + "\n";
            r.sendResponseHeaders(200, response.length());
            OutputStream os = r.getResponseBody();
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
}
