import server.motm.database.*;
import server.motm.utils.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;




/* See the following link for more instructions:
 *   https://openjdk.java.net/groups/net/httpclient/recipes.html
 * Possibly use Async requests in order to speed up the loading time,
 *   instead using placeholders for information that is still being fetched
 *   (this may not be applicable on everything)
 * 
 * Additional tutorial:
 *   https://mkyong.com/java/java-11-httpclient-examples/
 * 
 * HTTP header fields:
 *   https://en.wikipedia.org/wiki/List_of_HTTP_header_fields
 */

public class java11_ex_Client
{
    static int PORT = 8080;
    static String ADDR = "0.0.0.0";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public static void main(String[] args) throws IOException
    {
        java11_ex_Client c = new java11_ex_Client();
        c.sendHelloWorld();
    }

    private void sendHelloWorld(){
        /***   create http client request  ***/
        System.out.println("Creating HelloWorld request");
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://"+ADDR+":"+PORT+"/HelloWorld"))
                .setHeader("User-Agent", "motm HelloWorld request")
                .build();
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("--complete");


        /***   send request and wait to receive response   ***/
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        

        /***   print response   ***/
        response.headers().map().forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }
}
