package com.developersOfTheMillennium.motm.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.developersOfTheMillennium.motm.MainActivity;
import com.developersOfTheMillennium.motm.R;
import com.developersOfTheMillennium.motm.ssl.SecureHTTPClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.developersOfTheMillennium.motm.MainActivity.JSON;

public class GetMediaIDs { //extends AsyncTask<Object, JSONArray, JSONObject> {

    private static MainActivity activity;
    private static SecureHTTPClient HTTPSClient;

    public GetMediaIDs(MainActivity act) {//, String mediaType, String requestType, String genre) {
    //public GetMediaIDs(MainActivity a) {
        activity = act;

        HTTPSClient = new SecureHTTPClient(activity.getResources().getString(R.string.server_address)
                +":"+activity.getResources().getString(R.string.server_port), activity);
        //String mediaType = (String) params[0];
        //String requestType = (String) params[1];
        //String genre = (String) params[2];
        //System.out.println("do in back");
        //getIds(mediaType, requestType, genre);
    }

    //@Override
//    protected JSONObject doInBackground(Object...params) {
//        String mediaType = (String) params[0];
//        String requestType = (String) params[1];
//        String genre = (String) params[2];
//        ImageButton[] genreButtons = (ImageButton[]) params[3];
//        return getIds(mediaType, requestType, genreButtons);
//    }

    protected JSONObject getIds(String mediaType, String requestType, String genre) {
        //Picture
        JSONObject data = new JSONObject();
        JSONObject returnJSON = new JSONObject();
        try {
            //MIGHT NEED TO CHECK SOMETHING ELSE FOR DIFF MEDIA TYPES
            switch(requestType) {
                case "getNewMedia":
                    data.put("mediaType", mediaType);
                    returnJSON = postRequest("getNewMedia", data);
                    break;
                case "getTopRatedMedia":
                    data.put("mediaType", mediaType);
                    returnJSON = postRequest("getTopRatedMedia", data);
                    break;
                case "getMediaByGenreAndType":
                    data.put("mediaType", mediaType);
                    data.put("genre", genre);
                    returnJSON = postRequest("getMediaByGenreAndType", data);
                    break;
                    //NOT SURE IF WE NEED MORE?
            }
            //int error_code = rtn.getInt("error_code");
            //String session_token = rtn.getString("session_token");
            return returnJSON;
            //if (error_code == 0) {
            //AppGlobals.userType = "email";
            //AppGlobals.user = usernameEmail;
            //AppGlobals.session = session_token;
            //Log.i("global user type",AppGlobals.userType);
            //Log.i("global username/email",AppGlobals.user);
            //Log.i("global session token",AppGlobals.session);
            //    return true;
            //}
        } catch (Exception e) {
            Log.e("ERROR GetMediaIds", "JSON Parsing: " + e);
            //error case need to figure out general error case
            return null;
        }

    }

    private JSONObject postRequest(String context, JSONObject data) {
        //private String postRequest(String context, JSONObject data) {
        JSONObject responseData = null;

        /***   create http client request  ***/
        Log.i("Get MediaIDs", "Creating "+context+" request");

        RequestBody requestBody = RequestBody.create(data.toString(), JSON);
        System.out.println("https://"+activity.getResources().getString(R.string.server_address)+":"+activity.getResources().getString(R.string.server_port)+"/"+context);
        Request request = new Request.Builder()//.url("https://"+"0.0.0.0"+":"+"8080"+"/"+context)
                .url("https://"+activity.getResources().getString(R.string.server_address)+
                        ":"+activity.getResources().getString(R.string.server_port)+"/"+context)
                .addHeader("User-Agent", "motm "+context+" request")  // add request headers
                .post(requestBody)
                .build();

        try (Response response = HTTPSClient.run(request)) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            responseData = new JSONObject (response.body().string());
            //String responseData = response.body().string();
            Log.i("postRequest", "--complete");

        } catch (Exception e) {
            Log.e("HTTPS Request Error", ""+e);
        }
        return responseData;
    }

    //    // the onPostexecute method receives the return type of doInBackGround()
//    @Override
//    protected void onPostExecute(Object[] rtn) {
//        // do something with the result, for example display the received Data in a ListView
//        // in this case, "result" would contain the "someLong" variable returned by doInBackground();
//
//        //"New Media" "Top Rated"
//        JSONObject returnJSON = (JSONObject) rtn[0];
//        JSONArray array = (JSONArray) rtn[1];
//        try {
//            JSONArray NewMedia = returnJSON.getJSONArray("New Media");
//            //array.put(NewMedia);
//            //String TopRated = (String) rtn.get("Top Rated");
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//    }
}
