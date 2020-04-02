package com.developersOfTheMillennium.motm.utils;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageButton;

import com.developersOfTheMillennium.motm.MainActivity;
import com.developersOfTheMillennium.motm.R;
import com.developersOfTheMillennium.motm.ssl.SecureHTTPClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.developersOfTheMillennium.motm.MainActivity.JSON;

public class RetrieveList extends AsyncTask<Object, Void, Void>{

    private static MainActivity activity;
    private static SecureHTTPClient HTTPSClient;

    public RetrieveList(MainActivity a) {
        activity = a;
        HTTPSClient = new SecureHTTPClient(activity.getResources().getString(R.string.server_address)
                +":"+activity.getResources().getString(R.string.server_port), activity);
    }
    @Override
    protected Void doInBackground(Object... params) {
//        ImageButton[] genreButtons = (ImageButton[]) params[0];
//        String genre = (String) params[1];
        String username = (String) params[0];
        String list_name = (String) params[1];
        JSONObject return_list = (JSONObject) params[2];

        retrieveListFromDb(username,list_name,return_list);
        return null;
    }
    private void retrieveListFromDb(String username, String list_name,JSONObject return_list) {
        //Retrieve mediaIds
        JSONObject data = new JSONObject();
        JSONObject reponse = new JSONObject();

        try {
            data.put("username", username);
            data.put("list_name", list_name);
            Log.i("Getting List",list_name);
            reponse = getRequest("getMediaList", data);
            return_list = reponse;

            //String rtn = postRequest("getPicture", data);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR Getting Picture", "JSON Parsing: " + e);
            //error case just set to image of cinema (MAY CHANGE TO GENERAL IMAGE
            // FOR SOME REASON THIS BREAKS THE CODE???
            //btn.setImageResource(R.drawable.ic_cinema);
        }
    }
//        if(genreArray != null) {
//            //iterate through the list then start setting tags and getpicture
//            for (int i = 0; i < genreArray.length(); i++) {
//                try {
//                    Integer mediaID = (Integer) genreArray.get(i);
//                    genreButtons[i].setTag(mediaID);
//                    getPicture(Integer.toString(mediaID), genreButtons[i]);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        } else {
//            //ERROR WITH RETRIEVAL OF MEDIA IDS leaves button and transition blank
//            Log.i("Retrieving media IDs", "--Error");
//        }

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


//        private JSONArray getMediaIDs(String mediaType, String requestType, String genre,ImageButton[] imageButtons) throws Exception{
//            try {
//                //new GetMediaIDs(activity).execute(mediaType, requestType, genre);
//                GetMediaIDs result = new GetMediaIDs(activity);//, mediaType, requestType, genre); //(activity).execute(mediaType, requestType, genre).get();
//                JSONObject res = result.getIds(mediaType, requestType, genre);
//                return res.getJSONArray("mediaIDs");
//                //GetMediaIDs IDs = (GetMediaIDs) new GetMediaIDs((MainActivity) getActivity()).execute(mediaType, array).get();
//            } catch (Exception e) {
//                for(int i=0; i<10; i++) {
//                    getPicture(null, imageButtons[i]);
//                }
//                throw new Exception("(getMediaIDs) -- something went wrong when retrieving mediaIDs");
//            }
//        }
//
//        private void getPicture(String mediaId, ImageButton imgButton) throws Exception{
//            try {
//                GetPicture pic = (GetPicture) new GetPicture(activity).execute(mediaId, imgButton);
//            } catch (Exception e) {
//                throw new Exception("(getPicture) -- something went wrong when retrieving picture");
//            }
//        }
}


