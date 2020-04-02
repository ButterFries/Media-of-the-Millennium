package com.developersOfTheMillennium.motm.utils;

import android.os.AsyncTask;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.developersOfTheMillennium.motm.MainActivity;
import com.developersOfTheMillennium.motm.R;
import com.developersOfTheMillennium.motm.ssl.SecureHTTPClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.developersOfTheMillennium.motm.MainActivity.JSON;

public class getList extends AsyncTask<Object, Void, Void> implements View.OnClickListener{
    private static MainActivity activity;
    private static SecureHTTPClient HTTPSClient;

    public getList(MainActivity a) {
        activity = a;
        HTTPSClient = new SecureHTTPClient(activity.getResources().getString(R.string.server_address)
                +":"+activity.getResources().getString(R.string.server_port), activity);
    }

    @Override
    protected Void doInBackground(Object...params) {
        String username = (String) params[0];
        List<MenuItem> button = (List<MenuItem>) params[1];
        System.out.println(button);
        getListName(username,button);
        return null;
    }

    //private Boolean getPic(String mediaId, ImageButton btn) {
    private void getListName(String username,List<MenuItem> btns) {
        //Picture
        JSONObject data = new JSONObject();
        JSONObject return_data = new JSONObject();
        JSONObject name_holder = null;
        JSONArray names =null;

//        if(mediaId == null) {
//            //Empty image
//            fin[0] = null;
//            fin[1] = btn;
//            return fin;
//        }
        try {
            data.put("username", username);
            Log.i("username", username);
            return_data = getRequest("saveMediaList", data);


            name_holder = new JSONObject(return_data.get("data").toString());
            names = new JSONArray(name_holder.get("list_name").toString());
            for(int i= 0;i<names.length();i++){

                btns.get(i).setVisible(true);
                btns.get(i).setTitle(names.get(i).toString());
                System.out.println(btns.get(i));
            }
            //String rtn = postRequest("getPicture", data);


        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR Getting Picture", "JSON Parsing: " + e);
            //error case just set to image of cinema (MAY CHANGE TO GENERAL IMAGE
            // FOR SOME REASON THIS BREAKS THE CODE???
            //btn.setImageResource(R.drawable.ic_cinema);
        }

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
            //String responseData = response.body().string();
            //System.out.println("RESPONSE DAAATTA :" + responseData);
//            names = responseData;
            Log.i("getRequest", "--complete");

        } catch (Exception e) {
            Log.e("HTTPS Request Error", ""+e);
        }
        return return_data;
    }

//    protected void onPostExecute(List<MenuItem> rtn) {
//        // do something with the result, for example display the received Data in a ListView
//        // in this case, "result" would contain the "someLong" variable returned by doInBackground();
//
//
//    }
    @Override
    public void onClick(View v) {

    }
}
