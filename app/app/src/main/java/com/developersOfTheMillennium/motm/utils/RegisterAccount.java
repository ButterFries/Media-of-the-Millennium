package com.developersOfTheMillennium.motm.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.developersOfTheMillennium.motm.HomePageFragment;
import com.developersOfTheMillennium.motm.MainActivity;
import com.developersOfTheMillennium.motm.ssl.SecureHTTPClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.developersOfTheMillennium.motm.MainActivity.ADDR;
import static com.developersOfTheMillennium.motm.MainActivity.JSON;
import static com.developersOfTheMillennium.motm.MainActivity.PORT;

public class RegisterAccount extends AsyncTask<String, Void, Boolean> {

    private static MainActivity activity;

    private static SecureHTTPClient HTTPSClient = new SecureHTTPClient(ADDR+":"+PORT);

    public RegisterAccount(MainActivity a) {
        activity = a;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String username = params[0];
        String email = params[0];
        String password = params[1];

        return register(username, email, password);
    }

    private boolean register(String username, String email, String password) {

        JSONObject data = new JSONObject();

        try {
            data.put("email", username);
            data.put("u_name", email);
            data.put("pw", password);

            JSONObject rtn = putRequest("registerAccount", data);
            int error_code = rtn.getInt("error_code");

            if (error_code == 0) {
                return true;
            }
        } catch (Exception e) {
            Log.e("ERROR Login", "JSON Parsing: " + e);
        }

        return false;
    }


    private JSONObject putRequest(String context, JSONObject data) {
        JSONObject rtn = null;

        /***   create http client request  ***/
        System.out.println("Creating "+context+" request");

        RequestBody requestBody = RequestBody.create(JSON, data.toString());

        Request request = new Request.Builder()
                .url("https://"+ADDR+":"+PORT+"/"+context)
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
