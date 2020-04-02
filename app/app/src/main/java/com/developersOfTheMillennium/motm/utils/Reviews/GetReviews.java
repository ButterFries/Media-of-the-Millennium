package com.developersOfTheMillennium.motm.utils.Reviews;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.developersOfTheMillennium.motm.MainActivity;
import com.developersOfTheMillennium.motm.R;
import com.developersOfTheMillennium.motm.ssl.SecureHTTPClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.developersOfTheMillennium.motm.MainActivity.JSON;

public class GetReviews extends AsyncTask<String, Void, Boolean> {

    private static MainActivity activity;
    private static SecureHTTPClient HTTPSClient;

    private JSONArray reviews;

    private TextView[] review_text, review_title, review_author;
    private RatingBar[] review_rating;

    public GetReviews(MainActivity a, TextView[] review_text, TextView[] review_title, TextView[] review_author, RatingBar[] review_rating) {
        activity = a;
        HTTPSClient = new SecureHTTPClient(activity.getResources().getString(R.string.server_address)
                +":"+activity.getResources().getString(R.string.server_port), activity);

        this.review_text = review_text;
        this.review_title = review_title;
        this.review_rating = review_rating;
        this.review_author = review_author;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String mediaID = params[0];
        int limit = Integer.parseInt(params[1]);

        return review(mediaID, limit);
    }

    private boolean review(String mediaID, int limit) {

        //Email
        JSONObject data = new JSONObject();

        try {
            data.put("mediaID", mediaID);
            data.put("limit", limit);

            Log.i("mediaID", mediaID);
            Log.i("limit", limit+"");


            JSONObject rtn = postRequest("getReviews", data);
            int error_code = rtn.getInt("error_code");

            if (error_code == 0) {
                reviews = rtn.getJSONArray("reviews");
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            Log.e("ERROR Login", "JSON Parsing: " + e);
        }

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
            Log.i("reviews", reviews.toString());

            for (int i = 0; i < reviews.length(); i++) {
                try {
                    review_text[i].setText(((JSONObject)reviews.get(i)).getString("reviewText"));
                    review_title[i].setText(((JSONObject)reviews.get(i)).getString("reviewTitle"));
                    review_author[i].setText(((JSONObject)reviews.get(i)).getString("username"));
                    review_rating[i].setRating(((JSONObject)reviews.get(i)).getInt("rating"));

                    review_text[i].setVisibility(View.VISIBLE);
                    review_title[i].setVisibility(View.VISIBLE);
                    review_author[i].setVisibility(View.VISIBLE);
                    review_rating[i].setVisibility(View.VISIBLE);

                    ((LinearLayout)review_text[i].getParent()).setBackgroundResource(R.drawable.custom_border);
                }
                catch (Exception e) {
                    Log.i("review", "Couldn't find review");
                }
            }

        }
    }
}
