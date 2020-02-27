package com.developersOfTheMillennium.motm.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.developersOfTheMillennium.motm.HomePageFragment;
import com.developersOfTheMillennium.motm.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.developersOfTheMillennium.motm.MainActivity.ADDR;
import static com.developersOfTheMillennium.motm.MainActivity.JSON;
import static com.developersOfTheMillennium.motm.MainActivity.PORT;
import static com.developersOfTheMillennium.motm.MainActivity.httpClient;

public class PostServer extends AsyncTask<String, Void, Boolean> {

    private static applyHash ap = new applyHash();
    private static MainActivity activity;

    public PostServer(MainActivity a) {
        activity = a;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String usernameEmail = params[0];
        String password = params[1];

        return login(usernameEmail, password);
    }

    private boolean login(String usernameEmail, String password) {

        //STAGE 1 - Email
        JSONObject data = new JSONObject();
        boolean emailFound = false;
        boolean usernameFound = false;
        String hash = "";

        try {
            data.put("email", usernameEmail);

            JSONObject rtn = postRequest("validateAccount", data);
            int error_code = rtn.getInt("error_code");

            if (error_code == 0) {
                emailFound = true;
                hash = rtn.getString("hash");
            }
        } catch (Exception e) {
            Log.e("ERROR Login Stage 1", "JSON Parsing: " + e);
        }

        //STAGE 1 - Username
        if (!emailFound) {
            try {
                data.put("u_name", usernameEmail);
                JSONObject rtn = postRequest("validateAccount", data);
                int error_code = rtn.getInt("error_code");
                if (error_code == 0) {
                    usernameFound = true;
                    hash = rtn.getString("hash");
                }
            } catch (Exception e) {
                Log.e("ERROR Login Stage 1", "JSON Parsing");
            }
        }

        if (!usernameFound && !emailFound) {
            return false;
        }
        Log.i("HASH", hash);

        //STAGE 2
        String sPassword = "";
        try {
            sPassword = ap.hashPassword(password, hash);
        } catch (Exception e) {
            Log.e("ERROR Salt", e.toString());
        }


        //STAGE 2 - Email
        if (emailFound) {
            try {
                data.put("email", usernameEmail);
                data.put("s_pw", sPassword);

                JSONObject rtn = postRequest("validateAccount", data);
                int error_code = rtn.getInt("error_code");

                //Stored password matches hashed password
                if (error_code == 0) {
                    return true;
                }
            } catch (Exception e) {
                Log.e("ERROR Login Stage 1", "JSON Parsing");
            }
        }

        //STAGE 2 - Username
        else if (usernameFound) {
            try {
                data.put("u_name", usernameEmail);
                data.put("s_pw", sPassword);

                JSONObject rtn = postRequest("validateAccount", data);
                int error_code = rtn.getInt("error_code");

                //Stored password matches hashed password
                if (error_code == 0) {
                    return true;
                }
            } catch (Exception e) {
                Log.e("ERROR Login Stage 2", "JSON Parsing");
            }
        }



        return false;
    }


    private JSONObject postRequest(String context, JSONObject data) throws IOException, JSONException {
        JSONObject rtn = null;

        /***   create http client request  ***/
        System.out.println("Creating "+context+" request");

        RequestBody requestBody = RequestBody.create(JSON, data.toString());

        Request request = new Request.Builder()
                .url("http://"+ADDR+":"+PORT+"/"+context)
                .addHeader("User-Agent", "motm "+context+" request")  // add request headers
                .post(requestBody)
                .build();

        /***   send request and wait to receive response   ***/
        try (Response response = httpClient.newCall(request).execute()) {

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
        }

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
