package com.developersOfTheMillennium.motm.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.developersOfTheMillennium.motm.HomePageFragment;
import com.developersOfTheMillennium.motm.MainActivity;
import com.developersOfTheMillennium.motm.MediaListPageFragment;
import com.developersOfTheMillennium.motm.R;
import com.developersOfTheMillennium.motm.ssl.SecureHTTPClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.developersOfTheMillennium.motm.MainActivity.JSON;

public class RetrieveList extends AsyncTask<Object, Void, JSONObject>{

    private static MainActivity activity;
    private static SecureHTTPClient HTTPSClient;

    public RetrieveList(MainActivity a) {
        activity = a;
        HTTPSClient = new SecureHTTPClient(activity.getResources().getString(R.string.server_address)
                +":"+activity.getResources().getString(R.string.server_port), activity);
    }
    @Override
    protected JSONObject doInBackground(Object... params) {
//        ImageButton[] genreButtons = (ImageButton[]) params[0];
//        String genre = (String) params[1];
        String username = (String) params[0];
        String list_name = (String) params[1];
        JSONObject return_list = (JSONObject) params[2];

        return retrieveListFromDb(username,list_name,return_list);

    }
    private JSONObject retrieveListFromDb(String username, String list_name, JSONObject return_list) {
        //Retrieve mediaIds
        JSONObject data = new JSONObject();
        JSONObject reponse = null;

        JSONObject return_data = null;
        JSONObject info = new JSONObject();
        JSONArray test = null;
        try {
            data.put("username", username);
            data.put("list_name", list_name);
            Log.i("Getting List",list_name);
            reponse = getRequest("getMediaList", data);
            return_data = new JSONObject(reponse.get("data").toString());
            test = new JSONArray(return_data.get("list_items").toString().replace("[" + "]",""));
            info.put("list_name",list_name);
            info.put("list_items",test);


        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR Getting Picture", "JSON Parsing: " + e);
            //error case just set to image of cinema (MAY CHANGE TO GENERAL IMAGE
            // FOR SOME REASON THIS BREAKS THE CODE???
            //btn.setImageResource(R.drawable.ic_cinema);
        }
        return info;
    }

    private JSONObject getRequest(String context, JSONObject data) {
        //private String postRequest(String context, JSONObject data) {
        JSONObject return_data = new JSONObject();

        /***   create http client request  ***/
        Log.i("Get List Names(Only 3)", "Creating "+context+" request");

        RequestBody requestBody = RequestBody.create(data.toString(), JSON);
        System.out.println("https://"+activity.getResources().getString(R.string.server_address)+":"+activity.getResources().getString(R.string.server_port)+"/"+context);
        Request request = new Request.Builder()//.url("https://"+"0.0.0.0"+":"+"8080"+"/"+context)
                .url("https://"+activity.getResources().getString(R.string.server_address)+
                        ":"+activity.getResources().getString(R.string.server_port)+"/"+context)
                .addHeader("User-Agent", "motm "+context+" request")  // add request headers
                .post(requestBody)
                .build();

        try (Response response = HTTPSClient.run(request)) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body

            return_data.put("data",response.body().string());

            Log.i("getRequest", "--complete");

        } catch (Exception e) {
            Log.e("HTTPS Request Error", ""+e);
        }
        return return_data;
    }

    private void getPicture(String mediaId, ImageButton imgButton) throws Exception{
        try {
            GetPicture pic = (GetPicture) new GetPicture(activity).execute(mediaId, imgButton);
        } catch (Exception e) {
            throw new Exception("(getPicture) -- something went wrong when retrieving picture");
        }
    }

    protected void onPostExecute(JSONObject list_info) {
        // do something with the result, for example display the received Data in a ListView
        // in this case, "result" would contain the "someLong" variable returned by doInBackground();


        try {
            Bundle args = new Bundle();
            args.putString("list_name",list_info.get("list_name").toString());
            JSONArray load_pics = list_info.getJSONArray("list_items");
            MediaListPageFragment mediaList = new MediaListPageFragment();
            mediaList.setAdd_media(load_pics);
            mediaList.setArguments(args);
            activity.replaceFragment(mediaList);
            System.out.println(mediaList.getAdd_media());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}


