package com.developersOfTheMillennium.motm.utils;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageButton;

import com.developersOfTheMillennium.motm.MainActivity;
import com.developersOfTheMillennium.motm.R;
import com.developersOfTheMillennium.motm.ssl.SecureHTTPClient;

import org.json.JSONArray;
import org.json.JSONObject;

public class RetrieveAndDisplay  extends AsyncTask<Object, Void, Void> {

    private static MainActivity activity;
    private static SecureHTTPClient HTTPSClient;

    public RetrieveAndDisplay(MainActivity a) {
        activity = a;
        HTTPSClient = new SecureHTTPClient(activity.getResources().getString(R.string.server_address)
                +":"+activity.getResources().getString(R.string.server_port), activity);
    }

    @Override
    protected Void doInBackground(Object... params) {
        ImageButton[] genreButtons = (ImageButton[]) params[0];
        String genre = (String) params[1];
        String mediaType = (String) params[2];
        String requestType = (String) params[3];

        retrieveAndDisplay(genreButtons, genre, mediaType, requestType);
        return null;
    }


    private void retrieveAndDisplay(ImageButton[] genreButtons, String genre, String mediaType, String requestType) {
        //Retrieve mediaIds
        JSONArray genreArray = null;
        try {
            genreArray = getMediaIDs(mediaType, requestType, genre, genreButtons);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(genreArray != null) {
            //iterate through the list then start setting tags and getpicture
            for (int i = 0; i < genreArray.length(); i++) {
                try {
                    Integer mediaID = (Integer) genreArray.get(i);
                    genreButtons[i].setTag(mediaID);
                    getPicture(Integer.toString(mediaID), genreButtons[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            //ERROR WITH RETRIEVAL OF MEDIA IDS leaves button and transition blank
            Log.i("Retrieving media IDs", "--Error");
        }
    }

    private JSONArray getMediaIDs(String mediaType, String requestType, String genre,ImageButton[] imageButtons) throws Exception{
        try {
            //new GetMediaIDs(activity).execute(mediaType, requestType, genre);
            GetMediaIDs result = new GetMediaIDs(activity);//, mediaType, requestType, genre); //(activity).execute(mediaType, requestType, genre).get();
            JSONObject res = result.getIds(mediaType, requestType, genre);
            return res.getJSONArray("mediaIDs");
            //GetMediaIDs IDs = (GetMediaIDs) new GetMediaIDs((MainActivity) getActivity()).execute(mediaType, array).get();
        } catch (Exception e) {
            for(int i=0; i<10; i++) {
                getPicture(null, imageButtons[i]);
            }
            throw new Exception("(getMediaIDs) -- something went wrong when retrieving mediaIDs");
        }
    }

    private void getPicture(String mediaId, ImageButton imgButton) throws Exception{
        try {
            GetPicture pic = (GetPicture) new GetPicture(activity).execute(mediaId, imgButton);
        } catch (Exception e) {
            throw new Exception("(getPicture) -- something went wrong when retrieving picture");
        }
    }
}
