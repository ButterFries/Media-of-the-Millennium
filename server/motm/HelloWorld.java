package server.motm;
import server.motm.database.appDatabase;


import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import java.io.IOException;
import java.io.OutputStream;

import org.json.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HelloWorld implements HttpHandler
{
    private static appDatabase db;

    public HelloWorld(appDatabase appDB) {
        db = appDB;
    }

    public void handle(HttpExchange r) {
        System.out.println("\n-Received request [HelloWorld]");
        try {
            if (r.getRequestMethod().equals("GET")) {
                System.out.println("--request type: GET");
                handleReq(r);
            } else if (r.getRequestMethod().equals("POST")) {
                System.out.println("--request type: POST");
                handleReq(r);
            }
            else if (r.getRequestMethod().equals("PUT")) {
                System.out.println("--request type: PUT");
                handleReq(r);
            }
            else {
                System.out.println("--request type unsupported: "+r.getRequestMethod());
            }
        } catch (Exception e) {
            System.out.println("# ERROR HelloWorld.handle ::  " + e);
        }
    }

    public void handleReq(HttpExchange r) throws Exception {
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
