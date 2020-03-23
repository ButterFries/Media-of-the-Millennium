package com.developersOfTheMillennium.motm.utils.Ratings;


import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.developersOfTheMillennium.motm.FavouritesAdapter;
import com.developersOfTheMillennium.motm.MainActivity;
import com.developersOfTheMillennium.motm.R;
import com.developersOfTheMillennium.motm.ssl.SecureHTTPClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.developersOfTheMillennium.motm.MainActivity.JSON;

public class GetMediaRating extends AsyncTask<String, Void, Boolean> {

    private static MainActivity activity;
    private static View activity_view;

    private static SecureHTTPClient HTTPSClient;
    private JSONObject rating = null;

    public GetMediaRating(MainActivity a, View v) {
        activity = a;
        activity_view = v;

        HTTPSClient = new SecureHTTPClient(activity.getResources().getString(R.string.server_address)
                +":"+activity.getResources().getString(R.string.server_port), activity);
    }

    @Override
    protected Boolean doInBackground(String... params ) {
        int mediaID = Integer.parseInt(params[0]);
        // String accountInfo = params[0];

        rating = run(mediaID);
        if (rating != null) {
            System.out.println("rating wasnt null");
            return true;
        }
        return false;
    }

    private JSONObject run(int mediaID) {

        JSONObject data = new JSONObject();

        try {
            data.put("mediaID", mediaID);


            JSONObject rtn = postRequest("getMediaRating", data);

            return rtn;
        } catch (Exception e) {
            Log.e("ERROR POST", "JSON Parsing: " + e);
        }
        return null; //BAD
    }


    private JSONObject postRequest(String context, JSONObject data) {
        JSONObject rtn = null;

        /***   create http client request  ***/
        Log.i("DisplayFavorites", "Creating "+context+" request");

        RequestBody requestBody = RequestBody.create(data.toString(), JSON);

        Request request = new Request.Builder()
                .url("https://"+activity.getResources().getString(R.string.server_address)
                        +":"+activity.getResources().getString(R.string.server_port)+"/"+context)
                .addHeader("User-Agent", "motm "+context+" request")  // add request headers
                .post(requestBody)
                .build();

        try (Response response = HTTPSClient.run(request)) {


            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            String responseData = response.body().string(); //[{"mediaId":"1","title":"example"},{"mediaId":"2","title":"Pog"},{"mediaId":"3","title":"Pepe"}]"

            try {
                rtn = new JSONObject(responseData);
                Log.i("postRequest", "--complete");
            }catch (JSONException e) {
                Log.e("ERROR postRequest "+context, "String to Json Parse Error");
                throw new JSONException("String to Json Parse Error");
            }

        } catch (Exception e) {
            Log.e("HTTPS Request Error", ""+e);
        }
        /***   send request and wait to receive response   ***/
        return rtn;
    }

    // the onPostexecute method receives the return type of doInBackGround()
    protected void onPostExecute(Boolean result) {
        // do something with the result, for example display the received Data in a ListView
        // in this case, "result" would contain the "someLong" variable returned by doInBackground();

        if (result) {
            TextView publicRating = activity_view.findViewById(R.id.publicRating);
            try {
                publicRating.setText(Double.toString(rating.getDouble("rating")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
