package com.developersOfTheMillennium.motm.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.developersOfTheMillennium.motm.AppGlobals;
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

public class saveMediaList extends AsyncTask<String, Void, Boolean> {

    private static MainActivity activity;
    private static SecureHTTPClient HTTPSClient;

    public saveMediaList(MainActivity a) {
        activity = a;
        HTTPSClient = new SecureHTTPClient(activity.getResources().getString(R.string.server_address)
                + ":" + activity.getResources().getString(R.string.server_port), activity);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String list_name = params[0];
        String mediaIds = params[1];
        System.out.println(list_name + mediaIds);
        Log.i("Info Passed", list_name + mediaIds);
        return save(list_name, mediaIds);
//            return false;
    }

    private boolean save(String list_name, String mediaIds) {

        //Loading Spinner On
        activity.enableLoadingAnimation();

        JSONObject data = new JSONObject();

        try {
            data.put("username", AppGlobals.user);
            data.put("list_name", list_name);
            data.put("list_items", mediaIds);

            JSONObject rtn = putRequest("saveMediaList", data);
            int error_code = rtn.getInt("error_code");
            String session_token = rtn.getString("session_token");
            System.out.println(session_token);
            //String session_token = rtn.getString("session_token");
            if (error_code == 0) {
                return true;
            }
        } catch (Exception e) {
            Log.e("ERROR POST", "JSON Parsing: " + e);
        }
        activity.disableLoadingAnimation();
        return false;
    }


    private JSONObject putRequest(String context, JSONObject data) {
        JSONObject rtn = null;

        /***   create http client request  ***/
        Log.i("saveMediaList", "Creating "+context+" request");

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

        //TAKE TO HOME PAGE
        if (result) {
            HomePageFragment homeFragment = new HomePageFragment();
            activity.replaceFragment(homeFragment);
        }
    }


}
