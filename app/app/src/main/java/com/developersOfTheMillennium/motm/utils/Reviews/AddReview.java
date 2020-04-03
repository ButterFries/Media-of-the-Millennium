package com.developersOfTheMillennium.motm.utils.Reviews;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.os.SystemClock.*;
import android.view.inputmethod.InputMethodManager;

import com.developersOfTheMillennium.motm.AppGlobals;
import com.developersOfTheMillennium.motm.HomePageFragment;
import com.developersOfTheMillennium.motm.MainActivity;
import com.developersOfTheMillennium.motm.MediaProfilePageFragment;
import com.developersOfTheMillennium.motm.R;
import com.developersOfTheMillennium.motm.ssl.SecureHTTPClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.os.SystemClock.sleep;
import static com.developersOfTheMillennium.motm.MainActivity.JSON;

public class AddReview extends AsyncTask<String, Void, Boolean> {

    private static MainActivity activity;
    private static SecureHTTPClient HTTPSClient;

    public AddReview(MainActivity a) {
        activity = a;
        HTTPSClient = new SecureHTTPClient(activity.getResources().getString(R.string.server_address)
                +":"+activity.getResources().getString(R.string.server_port), activity);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String title = params[0];
        String text = params[1];
        float rating = Float.parseFloat(params[2]);
        String sessionID = params[3];
        String mediaID = params[4];
        String user = params[5];
        String userType = params[6];


        return review(title, text, rating, sessionID, mediaID, user, userType);
    }

    private boolean review(String title, String text, float rating, String sessionID,
                           String mediaID, String user, String userType) {

        //Loading Spinner On
        activity.enableLoadingAnimation();

        //Email
        JSONObject data = new JSONObject();

        try {
            data.put("mediaID", mediaID);
            data.put("sessionID", sessionID);
            data.put("rating", rating);
            data.put("user", user);
            data.put("userType", userType);
            data.put("reviewText", text);
            data.put("reviewTitle", title);

            Log.i("mediaID", mediaID);
            Log.i("sessionID", sessionID);
            Log.i("rating", ""+rating);
            Log.i("user", user);
            Log.i("userType", userType);
            Log.i("reviewText", text);
            Log.i("reviewTitle", title);

            JSONObject rtn = postRequest("addReview", data);
            int error_code = rtn.getInt("error_code");

            if (error_code == 0) {
                //Loading Spinner Off
                activity.disableLoadingAnimation();
                return true;
            }
        } catch (Exception e) {
            Log.e("ERROR Login", "JSON Parsing: " + e);
        }

        //Loading Spinner Off
        activity.disableLoadingAnimation();

        return false;
    }


    private JSONObject postRequest(String context, JSONObject data) {
        JSONObject rtn = null;

        /***   create http client request  ***/
        Log.i("ValidateAccount", "Creating "+context+" request");

        RequestBody requestBody = RequestBody.create(JSON, data.toString());

        Request request = new Request.Builder()
                .url("https://"+activity.getResources().getString(R.string.server_address)+
                        ":"+activity.getResources().getString(R.string.server_port)+"/"+context)
                .addHeader("User-Agent", "motm "+context+" request")  // add request headers
                .post(requestBody)
                .build();

        try (Response response = HTTPSClient.run(request)) {


            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            String responseData = response.body().string();

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

        return rtn;
    }

    // the onPostexecute method receives the return type of doInBackGround()
    protected void onPostExecute(Boolean result) {
        // do something with the result, for example display the received Data in a ListView
        // in this case, "result" would contain the "someLong" variable returned by doInBackground();

        //Take back to media page
        activity.getFragmentManager().popBackStackImmediate();
    }
}
