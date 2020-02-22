package motm.example;

import motm.utils.*;
import motm.ssl.*;

import okhttp3.*;
import org.json.*;

import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.util.Arrays;




/* It seems appropriate to use "OkHttp 4.4.0" which works 
 *   on Android 5.0+ (API level 21+) and on Java 8+.
 * See the following link for more instructions:
 *   https://square.github.io/okhttp/
 * 
 * Additional tutorials:
 *   https://mkyong.com/java/how-to-send-http-request-getpost-in-java/
 *   https://mkyong.com/java/okhttp-how-to-send-http-requests/
 * 
 * HTTP header fields:
 *   https://en.wikipedia.org/wiki/List_of_HTTP_header_fields
 * 
 * OKHTTP3 timeouts:
 *   https://www.baeldung.com/okhttp-timeouts
 * 
 * OKHTP3 client:
 *   https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/
 */

public class Client 
{
    static int PORT = 8080;
    static String ADDR = "0.0.0.0";
    static String http_type = "https"; //"http";

    /* Note: ADDR should be changed from "localhost"/"0.0.0.0" to your local IP address when testing through the android emulator
     *  https://stackoverflow.com/questions/42904584/how-to-connect-to-localhost-from-android-studio-emulator 
     */

    private SecureHTTPClient secureHttpClient = new SecureHTTPClient(ADDR);
    /* //use  `Response response = secureHttpClient.run(request)` instead
    private final CertificatePinner certPinner = new CertificatePinner.Builder()
            .add(ADDR, "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=")
            .build();
    private final OkHttpClient httpClient = new OkHttpClient().newBuilder()
        .connectTimeout(3, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS))
        .certificatePinner(certPinner)
        .build();
    */
    



    public static void main(String[] args) throws Exception
    {
        System.out.println("Client (exmaple) starting up");
        if (args.length < 1){
            System.out.println("  `ex_Client 0` to send helloWorld");
            System.out.println("  `ex_Client 1` to register a fixed account");
            System.out.println("  `ex_Client 2` to validate an existing username and correct password");
            System.out.println("  `ex_Client 3` to validate an existing email and correct password");
            System.out.println("  `ex_Client 4` to validate an account with wrong password");
            System.out.println("  `ex_Client 5` to validate an account with incorrect username");
            System.exit(0);
        }
        else{
            Client c = new Client();
            String arg = args[0];
            System.out.println("-- arg mode :  "+arg);
            try{
                
                if (arg.equals("0"))
                    c.sendHelloWorld();
                else if (arg.equals("1"))
                    c.sendRegistration("testClient", "test@example", "testPassword1");
                else if (arg.equals("2"))
                    c.sendValidation(0, "testClient", "testPassword1");
                else if (arg.equals("3"))
                    c.sendValidation(1, "test@example", "testPassword1");
                else if (arg.equals("4"))
                    c.sendValidation(0, "testClient", "testPasswordA");
                else if (arg.equals("5"))
                    c.sendValidation(0, "TESTCLIENT", "testPassword1");
                else 
                    System.out.println("--arg mode not valid :  "+arg);
            } catch (Exception eHW){
                //eHW.printStackTrace();
                System.out.println(eHW);
            }
            c.close();

            System.exit(0); //force close, not sure of implications yet
        }
    }
    private void close() throws IOException{
        System.out.println("--closing");
        //httpClient.dispatcher().executorService().shutdown();
        //httpClient.connectionPool().evictAll();
        ////httpClient.cache().close();
    }

    private void sendHelloWorld(){
        /***   create http client request  ***/
        System.out.println("Creating HelloWorld request");
        String url = http_type+"://"+ADDR+":"+PORT+"/HelloWorld";
        try {
            JSONObject json = new JSONObject();
            json.put("hello", "from example client");
            RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json; charset=utf-8"));

            Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "motm HelloWorld request")
                .addHeader("Accept", "application/json")
                .post(body)
                //.method("GET", body)
                .build();
            /* OKHTTP will not allow a requestbody for GET
             * therefore we must use PUT/POST for all requests with a body
             * so use POST instead of GET
             */


            /***   send request and wait to receive response   ***/
            //Response response = httpClient.newCall(request).execute();
            Response response = secureHttpClient.run(request);

            
            /***   print response   ***/
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            System.out.println(response.toString());
            System.out.println(response.code());
            System.out.println(response.body().string().trim());
            response.close();
        } 
        catch (Exception e){
            //e.printStackTrace();
            System.out.println(e); 
            System.exit(1);
        }
        System.out.println("--complete");
        
    }


    private void sendRegistration(String username, String email, String password){
        System.out.println("Creating registerAccount request");
        String url = http_type+"://"+ADDR+":"+PORT+"/registerAccount";
        try {
            
            String secured_password = generatePasswordHash.generateStrongPasswordHash(password);
            JSONObject json = new JSONObject();
            json.put("u_name", username);
            json.put("email", email);
            json.put("s_pw", secured_password);
            RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "motm registerAccount request")
                .addHeader("Accept", "application/json")
                .put(body)
                .build();
            //Response response = httpClient.newCall(request).execute();
            Response response = secureHttpClient.run(request);
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            System.out.println(response.toString());
            System.out.println(response.body().string().trim());
            response.close();
        } 
        catch (Exception e){
            System.out.println(e);
            System.exit(1);
        }
        System.out.println("--complete");
    }

    private void sendValidation(int mode, String user, String password) throws Exception{
        System.out.println("Creating validateAccount request");
        String url = http_type+"://"+ADDR+":"+PORT+"/validateAccount";
        try {
            JSONObject json = new JSONObject();
            if (mode == 0)
                json.put("u_name", user);
            else
                json.put("email", user);
            RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "motm registerAccount initial request")
                .addHeader("Accept", "application/json")
                .post(body)
                .build();
            //Response response = httpClient.newCall(request).execute();
            Response response = secureHttpClient.run(request);
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            System.out.println(response.toString());
            String res_body = response.body().string();
            System.out.println(res_body.trim());
            response.close();

            JSONObject responseJSON = new JSONObject(res_body);

            int err_code = responseJSON.getInt("error_code");
            String err_msg = responseJSON.getString("error_description");
            if (err_code != 0)
                throw new Exception("error code"+err_code+" ::  "+err_msg);

            String stored_hash = responseJSON.getString("hash");
            System.out.println("stored_hash::  '"+stored_hash+"'");
            String hashed_password = applyHash.hashPassword(password, stored_hash);
            System.out.println("hashed_password::  '"+hashed_password+"'");

            json = new JSONObject();
            if (mode == 0)
                json.put("u_name", user);
            else
                json.put("email", user);
            json.put("s_pw", hashed_password);
            body = RequestBody.create(json.toString(), MediaType.parse("application/json; charset=utf-8"));
            request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "motm registerAccount final request")
                .addHeader("Accept", "application/json")
                .post(body)
                .build();
            //response = httpClient.newCall(request).execute();
            response = secureHttpClient.run(request);
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            System.out.println(response.toString());
            System.out.println(response.body().string().trim());
            response.close();
        } 
        catch (Exception e){
            System.out.println(e);
            System.exit(1);
        }
        System.out.println("--complete");
    }
}
