package server.motm;
import server.motm.servlets.*;
import server.motm.database.AppDatabase;
import server.motm.utils.*;
import server.motm.session.*;

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


        /***   initialize session manager   ***/
        System.out.println("! Initializing SessionManager");
        SessionManager sm = new SessionManager(/*30*/); //if arg then arg-sec session duration, otherwise default 1hour
        System.out.println("--manager ready");


        /***   initialize db connection   ***/
        System.out.println("! Setting up connection via AppDatabase");
        AppDatabase db = new AppDatabase();
        System.out.println("--connection established");

        
        // the general procedure should be like
        /* AppDatabase db = new AppDatabase();
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
        server.createContext("/HelloWorld", new HelloWorld(db, sm));
        server.createContext("/registerAccount", new registerAccount(db, sm));
        server.createContext("/validateAccount", new validateAccount(db, sm));
        ////server.createContext("/x", new x(db, sm));



        /***   start server and listen for customers   ***/
        server.start();
        System.out.printf("Server started on port %d...\n", PORT);



        /***   shutdown server   ***/
        //System.out.println("Shutting down server");
        //driver.close();
    }
}
