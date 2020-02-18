package server.motm;
import server.motm.servlets.*;
import server.motm.database.appDatabase;
import server.motm.utils.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class Server 
{
    static int PORT = 8080;
    static String ADDR = "0.0.0.0";

    public static void main(String[] args) throws IOException
    {

        /***   create http server   ***/
        System.out.println("! Setting up HTTP server on "+ADDR+":"+PORT);
        HttpServer server = HttpServer.create(new InetSocketAddress(ADDR, PORT), 0);
        System.out.println("--setup complete");



        /***   initialize db connection   ***/
        System.out.println("! Setting up connection via appDatabase");
        appDatabase db = new appDatabase();
        System.out.println("--connection established");
        // the general procedure should be like
        /* appDatabase db = new appDatabase();
         * Connection conn = db.connect(args)
         * try{
         *     ~~~do some work, like write_to_DB(conn, args)
         * }
         * catch (Exception e){
         *     System.out.println("# Servlet X.func :  ERROR ::  "+e);
         * }
         * db.disconnect(conn);
         */



        /***   setup server servlets/endpoints   ***/
        server.createContext("/HelloWorld", new HelloWorld(db));
        server.createContext("/registerAccount", new registerAccount(db));
        server.createContext("/validateAccount", new validateAccount(db));
        ////server.createContext("/x", new x(db));



        /***   start server and listen for customers   ***/
        server.start();
        System.out.printf("Server started on port %d...\n", PORT);



        /***   shutdown server   ***/
        //System.out.println("Shutting down server");
        //driver.close();
    }
}
