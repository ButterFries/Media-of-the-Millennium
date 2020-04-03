package server.motm;
import server.motm.servlets.*;
import server.motm.database.AppDatabase;
import server.motm.utils.*;
import server.motm.session.*;

import java.io.IOException;
//import java.io.*;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
//import java.lang.*;

import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpsExchange;

//import com.sun.net.httpserver.*;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;

import java.net.URL;
import java.net.InetAddress;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;





public class Server 
{
    static int PORT = 8080;
//    static String ADDR = "192.168.50.253";
    static String ADDR = "192.168.0.18";
    //keytool -genkeypair -keyalg RSA -alias selfsigned -keystore motm_key.jks -storepass developers_of_the_millenium_password_to_motm -validity 360 -keysize 2048

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, CertificateException, UnrecoverableKeyException
    {

        /***   create http server   ***/
        System.out.println("! Setting up HTTP server on "+ADDR+":"+PORT);
        //HttpServer server = HttpServer.create(new InetSocketAddress(ADDR, PORT), 0);

        /* sourced@:  https://stackoverflow.com/questions/2308479/simple-java-https-server */
        // initialise the HTTPS server
        HttpsServer httpsServer = HttpsServer.create(new InetSocketAddress(ADDR, PORT), 0);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        // initialise the keystore
        String jks = "motm_key.jks";
        String jks_pw = "developers_of_the_millenium_password_to_motm";
        char[] cert_password = jks_pw.toCharArray();
        KeyStore ks = KeyStore.getInstance("JKS");
        FileInputStream fis = new FileInputStream(jks);
        ks.load(fis, cert_password);
        // setup the key manager factory
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, cert_password);
        // setup the trust manager factory
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ks);
        // setup the HTTPS context and parameters
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        httpsServer.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
            public void configure(HttpsParameters params) {
                try {
                    // initialise the SSL context
                    SSLContext context = getSSLContext();
                    SSLEngine engine = context.createSSLEngine();
                    params.setNeedClientAuth(false);
                    params.setCipherSuites(engine.getEnabledCipherSuites());
                    params.setProtocols(engine.getEnabledProtocols());
                    // Set the SSL parameters
                    SSLParameters sslParameters = context.getSupportedSSLParameters();
                    params.setSSLParameters(sslParameters);
                } catch (Exception ex) {
                    System.out.println("Failed to create HTTPS port");
                }
            }
        });
        //System.setProperty("javax.net.ssl.trustStore",jks);
        //System.setProperty("javax.net.ssl.trustStorePassword",jks_pw);
        System.out.println("--setup complete");


        /***   initialize session manager   ***/
        System.out.println("! Initializing SessionManager");
        SessionManager sm = new SessionManager(); //if arg then arg-sec session duration, otherwise default 1hour
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
        /* //http server code (unused)
        server.createContext("/HelloWorld", new HelloWorld(db, sm));
        server.createContext("/registerAccount", new registerAccount(db, sm));
        server.createContext("/validateAccount", new validateAccount(db, sm));
        //server.createContext("/x", new x(db, sm));
        */
        httpsServer.createContext("/HelloWorld", new HelloWorld(db, sm));
        httpsServer.createContext("/registerAccount", new registerAccount(db, sm));
        httpsServer.createContext("/validateAccount", new validateAccount(db, sm));
        httpsServer.createContext("/getMediaProfile", new getMediaProfile(db, sm));
        httpsServer.createContext("/getFavorites", new getFavorites(db, sm));
        httpsServer.createContext("/getBookmarks", new getBookmarks(db, sm));

        httpsServer.createContext("/getMediaRating", new getMediaRating(db, sm));
        httpsServer.createContext("/getUsersRating", new getUsersRating(db, sm));
        httpsServer.createContext("/updateUsersRating", new updateUsersRating(db, sm));

        //httpsServer.createContext("/addMediaProfile", new addMediaProfile(db, sm));
        httpsServer.createContext("/getReports", new getReports(db, sm));
        httpsServer.createContext("/addMediaProfile", new addMediaProfile(db, sm));
        httpsServer.createContext("/getPicture", new getPicture(db, sm));
        httpsServer.createContext("/addReview", new addReview(db, sm));
        httpsServer.createContext("/deleteReview", new deleteReview(db, sm));
        httpsServer.createContext("/getReview", new getReview(db, sm));
        httpsServer.createContext("/getReviews", new getReviews(db, sm));
        httpsServer.createContext("/mediaTitleSearch", new mediaTitleSearch(db, sm));
        
        httpsServer.createContext("/getNewMedia", new getNewMedia(db, sm));
        httpsServer.createContext("/getTopRatedMedia", new getTopRatedMedia(db, sm));
        httpsServer.createContext("/saveMediaList", new saveMediaList(db, sm));
        httpsServer.createContext("/getMediaByGenreAndType", new getMediaByGenreAndType(db, sm));
        httpsServer.createContext("/getMediaList", new getMediaList(db, sm));
        //httpsServer.createContext("/x", new x(db, sm));



        /***   start server and listen for customers   ***/
        httpsServer.setExecutor(null); // creates a default executor
        httpsServer.start();
        //server.start();
        System.out.printf("Server started on port %d...\n", PORT);



        /***   shutdown server   ***/
        //System.out.println("Shutting down server");
        //driver.close();
    }
}
