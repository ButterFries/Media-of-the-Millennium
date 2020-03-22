package com.developersOfTheMillennium.motm.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.module.AppGlideModule;
import com.developersOfTheMillennium.motm.AppGlobals;
import com.developersOfTheMillennium.motm.MainActivity;
import com.developersOfTheMillennium.motm.R;
import com.developersOfTheMillennium.motm.ssl.SecureHTTPClient;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import jp.wasabeef.picasso.transformations.CropSquareTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.developersOfTheMillennium.motm.MainActivity.JSON;

public class GetPicture extends AsyncTask<Object, Void, Object[]> {

    private static MainActivity activity;
    private static SecureHTTPClient HTTPSClient;

    public GetPicture(MainActivity a) {
        activity = a;
        HTTPSClient = new SecureHTTPClient(activity.getResources().getString(R.string.server_address)
                +":"+activity.getResources().getString(R.string.server_port), activity);
    }

    @Override
    protected Object[] doInBackground(Object...params) {
        String mediaID = (String) params[0];
        //try {
        //    ImageButton image = (ImageButton) params[1];
        //    return getPic(mediaID, image);
        //} catch (ClassCastException e) {
            ImageView image = (ImageView) params[1];
            return getPic(mediaID, image);
        //}

    }

    //private Boolean getPic(String mediaId, ImageButton btn) {
    private Object[] getPic(String mediaId, ImageView btn) {
        //Picture
        JSONObject data = new JSONObject();
        byte[] image = null;
        try {
            data.put("mediaID", mediaId);
            Log.i("mediaID", mediaId);
            byte[] rtn = postRequest("getPicture", data);
            //String rtn = postRequest("getPicture", data);
            byte[] byteArray = rtn;
            Bitmap bmp = null;
            if(byteArray != null) {
                //int error_code = rtn.getInt("error_code");
                //String session_token = rtn.getString("session_token");
                bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                System.out.println("WIDTH: " + btn.getWidth() + "HEIGHT : " + btn.getHeight());
            }
            //Bitmap bm = Bitmap.createScaledBitmap(bmp, btn.getMeasuredWidth(), btn.getMeasuredHeight(), false);
            //btn.setImageBitmap(bm);
            //btn.setImageBitmap(Bitmap.createScaledBitmap(bmp, btn.getMeasuredWidth(), btn.getMeasuredHeight(), false));
            //btn.set(R.drawable.ic_cinema);
            Object[] fin = new Object[2];
            fin[0] = bmp;
            fin[1] = btn;
            return fin;
            //return true;
            //if (error_code == 0) {
                //AppGlobals.userType = "email";
                //AppGlobals.user = usernameEmail;
                //AppGlobals.session = session_token;
                //Log.i("global user type",AppGlobals.userType);
                //Log.i("global username/email",AppGlobals.user);
                //Log.i("global session token",AppGlobals.session);
            //    return true;
            //}
        } catch (Exception e) {
            Log.e("ERROR Getting Picture", "JSON Parsing: " + e);
            //error case just set to image of cinema (MAY CHANGE TO GENERAL IMAGE
            // FOR SOME REASON THIS BREAKS THE CODE???
            //btn.setImageResource(R.drawable.ic_cinema);
            return null;
        }

    }


    private byte[] postRequest(String context, JSONObject data) {
    //private String postRequest(String context, JSONObject data) {
        byte[] rtn = null;

        /***   create http client request  ***/
        Log.i("Get Picture", "Creating "+context+" request");

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
            byte[] responseData = response.body().bytes();
            //String responseData = response.body().string();
            //System.out.println("RESPONSE DAAATTA :" + responseData);
            rtn = responseData;
            Log.i("postRequest", "--complete");

        } catch (Exception e) {
            Log.e("HTTPS Request Error", ""+e);
        }
        return rtn;
    }

//    // the onPostexecute method receives the return type of doInBackGround()
    @Override
    protected void onPostExecute(Object[] rtn) {
        // do something with the result, for example display the received Data in a ListView
        // in this case, "result" would contain the "someLong" variable returned by doInBackground();
        Bitmap bmp = (Bitmap) rtn[0];
        ImageView btn = (ImageView) rtn[1];
        if(bmp == null) {
            btn.setImageResource(R.drawable.ic_cinema); //change image to general error case
            return;
        }
        btn.setImageBitmap(Bitmap.createScaledBitmap(bmp, btn.getMeasuredWidth(), btn.getMeasuredHeight(), false));
    }
}
