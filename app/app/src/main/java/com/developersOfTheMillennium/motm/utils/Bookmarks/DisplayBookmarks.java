package com.developersOfTheMillennium.motm.utils.Bookmarks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.developersOfTheMillennium.motm.AppGlobals;
import com.developersOfTheMillennium.motm.BookmarksAdapter;
import com.developersOfTheMillennium.motm.FavouritesAdapter;
import com.developersOfTheMillennium.motm.MainActivity;
import com.developersOfTheMillennium.motm.R;
import com.developersOfTheMillennium.motm.ssl.SecureHTTPClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.developersOfTheMillennium.motm.MainActivity.JSON;

public class DisplayBookmarks extends AsyncTask<String, Void, Boolean> {

    private static MainActivity activity;
    private static View activity_view;
    //String contextType;

    private static SecureHTTPClient HTTPSClient;
    private JSONArray lstItems = null;

    public DisplayBookmarks(MainActivity a, View v) {
        activity = a;
        activity_view = v;
        //contextType = c;

        HTTPSClient = new SecureHTTPClient(activity.getResources().getString(R.string.server_address)
                +":"+activity.getResources().getString(R.string.server_port), activity);
    }

    @Override
    protected Boolean doInBackground(String... params ) {
        lstItems = run();
        if (lstItems != null) {
            System.out.println("temp wasnt null");
            return true;
        }
        return false;
    }

    private JSONArray run() {

        JSONObject data = new JSONObject();

        try {
            data.put("accountInfo", AppGlobals.user);
            data.put("accountType", AppGlobals.userType);

            JSONArray rtn = postRequest("getBookmarks", data);

            return rtn;

        } catch (Exception e) {
            Log.e("ERROR POST", "JSON Parsing: " + e);
        }
        return null; //BAD
    }


    private JSONArray postRequest(String context, JSONObject data) {
        JSONArray rtn = null;

        /***   create http client request  ***/
        Log.i("DisplayBookmarks", "Creating "+context+" request");

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
                rtn = new JSONArray(responseData);
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

        //Displays each favorite (JSON object in JSONArray lstItems)
        if (result) {
            ListView bkList;
            bkList = (ListView) activity_view.findViewById(R.id.list_view_2);
            BookmarksAdapter allBookmarksAdapter = new BookmarksAdapter(activity.getBaseContext(), lstItems);
            bkList.setAdapter(allBookmarksAdapter);



        }
    }

}
