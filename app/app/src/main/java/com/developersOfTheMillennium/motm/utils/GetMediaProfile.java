package com.developersOfTheMillennium.motm.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developersOfTheMillennium.motm.MainActivity;
import com.developersOfTheMillennium.motm.R;
import com.developersOfTheMillennium.motm.ssl.SecureHTTPClient;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.developersOfTheMillennium.motm.MainActivity.JSON;

public class GetMediaProfile extends AsyncTask<Object, Void, Object[]> {

    private static MainActivity activity;
    private static SecureHTTPClient HTTPSClient;

    public GetMediaProfile(MainActivity a) {
        activity = a;
        HTTPSClient = new SecureHTTPClient(activity.getResources().getString(R.string.server_address)
                +":"+activity.getResources().getString(R.string.server_port), activity);
    }

    @Override
    protected Object[] doInBackground(Object...params) {
        String mediaID = (String) params[0];
        ImageView image = (ImageView) params[1];
        TextView title = (TextView) params[2];
        TextView tags = (TextView) params[3];
        TextView summary = (TextView) params[4];
        TextView links = (TextView) params[5];
        return getInfo(mediaID, image, title, tags, summary, links);
    }

    private Object[] getInfo(String mediaId, ImageView imgView, TextView titleView, TextView genresView, TextView summaryView, TextView linksView) {
        //Picture
        JSONObject data = new JSONObject();
        JSONObject returnJSON = new JSONObject();
        try {
            data.put("mediaID", mediaId);
            Log.i("mediaID", mediaId);
            returnJSON = postRequest("getMediaProfile", data);

            //int error_code = rtn.getInt("error_code");
            //String session_token = rtn.getString("session_token");

            Object[] fin = new Object[6]; //Might need to add genres and links?
            fin[0] = returnJSON;
            fin[1] = imgView;
            fin[2] = titleView;
            fin[3] = genresView;
            fin[4] = summaryView;
            fin[5] = linksView;
            //MIGHT NEED TO ADD MORE LIKE GENRES LINKS
            return fin;
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
            Log.e("ERROR Get MediaProfile", "JSON Parsing: " + e);
            //error case need to figure out general error case
            return null;
        }

    }

    private JSONObject postRequest(String context, JSONObject data) {
        //private String postRequest(String context, JSONObject data) {
        JSONObject responseData = null;

        /***   create http client request  ***/
        Log.i("Get MediaProfile", "Creating "+context+" request");

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
            responseData = new JSONObject (response.body().string());
            //String responseData = response.body().string();
            System.out.println("RESPONSE DATA :" + responseData);
            Log.i("postRequest", "--complete");

        } catch (Exception e) {
            Log.e("HTTPS Request Error", ""+e);
        }
        return responseData;
    }

    //    // the onPostexecute method receives the return type of doInBackGround()
    @Override
    protected void onPostExecute(Object[] rtn) {
        // do something with the result, for example display the received Data in a ListView
        // in this case, "result" would contain the "someLong" variable returned by doInBackground();
        JSONObject returnJSON = (JSONObject) rtn[0];
        ImageView imgView = (ImageView) rtn[1];
        //System.out.println("IMAGE VIEW " + imgView.toString());
        TextView titleView = (TextView) rtn[2];
        TextView genresView = (TextView) rtn[3];
        TextView summaryView = (TextView) rtn[4];
        TextView linksView = (TextView) rtn[5];

        //byte[] byteArray = null;
        String title = "";
        String genres = "";
        String summary = "";
        String links = "";
        try {
            //String byteString = (String) returnJSON.get("image");
            title = (String) returnJSON.get("title");
            genres = (String) returnJSON.get("genres"); //MIGHT want to separate by , display differently?
            summary = (String) returnJSON.get("summary");
            links = (String) returnJSON.get("links");

//            titleView.setText("Title: " + title);
//            genresView.setText("Genres: " + genres);
//            summaryView.setText("Summary: " + summary);

            titleView.setText(title);
            genresView.setText(genres);
            summaryView.setText(summary);

            String[] linksArray = links.split(",");
            linksView.setText("Links: \n");
            for(int i=0; i<linksArray.length; i++) {
                linksView.append(linksArray[i]+"\n");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
