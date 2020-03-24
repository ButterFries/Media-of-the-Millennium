package com.developersOfTheMillennium.motm.utils;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

public class AddReport extends AsyncTask<String, Void, Boolean> {

    private static MainActivity activity;
    private static View activity_view;

    private static SecureHTTPClient HTTPSClient;

    public AddReport(MainActivity a, View v) {
        activity = a;
        activity_view = v;



        HTTPSClient = new SecureHTTPClient(activity.getResources().getString(R.string.server_address)
                +":"+activity.getResources().getString(R.string.server_port), activity);
    }

    @Override
    protected Boolean doInBackground(String... params ) {
        int reviewID = Integer.parseInt(params[0]);
        String accountInfo = params[1];
        String sessionID = params[2];
        return run(reviewID, accountInfo, sessionID);
    }

    private boolean run(int reviewID, String accountInfo, String sessionID) {

        JSONObject data = new JSONObject();

        try {
            data.put("reviewID", reviewID);
            data.put("accountInfo", accountInfo);
            data.put("sessionID",sessionID);


            JSONObject rtn = putRequest("getReports", data);
            int error_code = rtn.getInt("error_code");
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
        Log.i("AddReport", "Creating "+context+" request");

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

        //display toast if report was succesfully added
        if (result) {
            Context context = activity.getApplicationContext(); //might be another context function
            CharSequence text = "Reported!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

}