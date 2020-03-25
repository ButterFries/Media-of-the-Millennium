package com.developersOfTheMillennium.motm.utils.Ratings;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.developersOfTheMillennium.motm.AppGlobals;
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

public class UpdateRating extends AsyncTask<String, Void, Boolean> {

    private static MainActivity activity;
    private static View activity_view;
    //String contextType;

    private static SecureHTTPClient HTTPSClient;
    //private JSONArray lstItems = null;

    public UpdateRating(MainActivity a, View v) {
        activity = a;
        activity_view = v;
        //contextType = c;


        HTTPSClient = new SecureHTTPClient(activity.getResources().getString(R.string.server_address)
                +":"+activity.getResources().getString(R.string.server_port), activity);
    }

    @Override
    protected Boolean doInBackground(String... params ) {
        int mediaID = Integer.parseInt(params[0]);
        float newRating =  Float.parseFloat(params[1]);
        //email/username in global
        //session in global

        return run(mediaID, newRating);
    }

    private boolean run(int mediaID, float newRatingVal) {

        JSONObject data = new JSONObject();

        try {
            data.put("mediaID", mediaID);
            data.put(AppGlobals.userType, AppGlobals.user);
            data.put("new_rating", newRatingVal);
            data.put("session_token", AppGlobals.session);

//            String context = "";
//            if (contextType.equals("favorites"))
//                context = "getFavorites";
//            else
//                context = "getBookmarks";

            JSONObject rtn = putRequest("updateUsersRating", data);
            int error_code = rtn.getInt("error_code");
            //String session_token = rtn.getString("session_token");
            if (error_code == 0) {
                return true;
            }
        } catch (Exception e) {
            Log.e("ERROR POST", "JSON Parsing: " + e);
        }
        return false;
    }


    private JSONObject putRequest(String context, JSONObject data) {
        JSONObject rtn = null;

        /***   create http client request  ***/
        Log.i("AddFavorite/Bookmark", "Creating "+context+" request");

        RequestBody requestBody = RequestBody.create(data.toString(), JSON);

        Request request = new Request.Builder()
                .url("https://"+activity.getResources().getString(R.string.server_address)
                        +":"+activity.getResources().getString(R.string.server_port)+"/"+context)
                .addHeader("User-Agent", "motm "+context+" request")  // add request headers
                .put(requestBody)
                .build();

        try (Response response = HTTPSClient.run(request)) {


            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            String responseData = response.body().string();

            try {
                rtn = new JSONObject(responseData);
                Log.i("putRequest", "--complete");
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

        //display toast if favorites was succesfully added
        if (result) {
            Context context = activity.getApplicationContext(); //might be another context function
            CharSequence text = "Rating updated";
//            if (contextType.equals("favorites"))
//                text = "Added to Favorites";
//            else
//                text = "Added to Bookmarks";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

}

