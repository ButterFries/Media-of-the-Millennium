package com.developersOfTheMillennium.motm.utils.Reviews;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.developersOfTheMillennium.motm.HomePageFragment;
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

public class UserAlreadyReviewed extends AsyncTask<String, Void, Boolean> {

    private static MainActivity activity;
    private static SecureHTTPClient HTTPSClient;

    private String title = "", body = "";
    private int rating = 0;

    public UserAlreadyReviewed(MainActivity a) {
        activity = a;
        HTTPSClient = new SecureHTTPClient(activity.getResources().getString(R.string.server_address)
                +":"+activity.getResources().getString(R.string.server_port), activity);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String sessionID = params[0];
        String mediaID = params[1];
        String user = params[2];
        String userType = params[3];


        return review(sessionID, mediaID, user, userType);
    }

    private boolean review(String sessionID, String mediaID, String user, String userType) {

        //Loading Spinner On
        activity.enableLoadingAnimation();

        //Email
        JSONObject data = new JSONObject();

        try {
            data.put("mediaID", mediaID);
            data.put("sessionID", sessionID);
            data.put("user", user);
            data.put("userType", userType);

            Log.i("mediaID", mediaID);
            Log.i("sessionID", sessionID);
            Log.i("user", user);
            Log.i("userType", userType);

            JSONObject rtn = postRequest("getReview", data);
            int error_code = rtn.getInt("error_code");

            //Loading Spinner Off
            activity.disableLoadingAnimation();

            if (error_code == 0) {
                title = rtn.getString("reviewTitle");
                body = rtn.getString("reviewText");
                rating = (int)(rtn.getDouble("rating")*2);
                return true;
            }
            else {
                return false;
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

        //TAKE TO HOME PAGE
        if (result) {
            EditText title = activity.findViewById(R.id.review_title);
            EditText body = activity.findViewById(R.id.review_text);
            RatingBar rating = activity.findViewById(R.id.ratingBar2);
            Button submit_review = activity.findViewById(R.id.button);
            Button edit_review = activity.findViewById(R.id.edit_review);
            Button delete_review = activity.findViewById(R.id.delete_review);

            title.setText(this.title);
            body.setText(this.body);
            rating.setRating(this.rating);

            submit_review.setVisibility(View.INVISIBLE);
            edit_review.setVisibility(View.VISIBLE);
            delete_review.setVisibility(View.VISIBLE);

        }
    }
}
