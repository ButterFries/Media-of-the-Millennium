package com.developersOfTheMillennium.motm.utils;

import android.os.AsyncTask;
import android.util.Log;

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

public class SubmitReview extends AsyncTask<String, Void, Boolean>{

        private static MainActivity activity;
        private static SecureHTTPClient HTTPSClient;

        public SubmitReview(MainActivity a) {
            activity = a;
            HTTPSClient = new SecureHTTPClient(activity.getResources().getString(R.string.server_address)
                    +":"+activity.getResources().getString(R.string.server_port), activity);
        }
        @Override
        protected Boolean doInBackground(String... params) {
            String review_title = params[0];
            Float rating = Float.valueOf(params[1]);
            String review = params[2];
            return submit(review_title, rating, review);
        }

        private boolean submit(String review_title, float rating, String review) {

            //Loading Spinner On
            activity.enableLoadingAnimation();
            JSONObject review_data = new JSONObject();
            try {
                review_data.put("review_title",review_title);
                review_data.put("rating", rating);
                review_data.put("review", review);

                JSONObject rtn = putReview("submitReview", review_data);
                int error_code = rtn.getInt("error_code");
                String session_token = rtn.getString("session_token");


            } catch (Exception e) {
                Log.e("ERROR Login", "JSON Parsing: " + e);
            }

            //Loading Spinner Off
            activity.disableLoadingAnimation();
            return false;
        }


        private JSONObject putReview(String context, JSONObject data) {
            JSONObject rtn = null;

            /***   create http client request  ***/
            Log.i("addReview", "Creating "+context+" request");

            RequestBody requestBody = RequestBody.create(JSON, data.toString());

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

            //TAKE TO HOME PAGE
            if (result) {
                HomePageFragment homeFragment = new HomePageFragment();
                activity.replaceFragment(homeFragment);
            }
        }

    }



