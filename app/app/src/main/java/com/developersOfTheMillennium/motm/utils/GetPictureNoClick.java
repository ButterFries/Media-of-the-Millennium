package com.developersOfTheMillennium.motm.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.developersOfTheMillennium.motm.MainActivity;
import com.developersOfTheMillennium.motm.MediaProfilePageFragment;
import com.developersOfTheMillennium.motm.R;
import com.developersOfTheMillennium.motm.ssl.SecureHTTPClient;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.developersOfTheMillennium.motm.MainActivity.JSON;

public class GetPictureNoClick extends GetPicture implements View.OnClickListener {

    public GetPictureNoClick(MainActivity a) {
        super(a);
    }

//    // the onPostexecute method receives the return type of doInBackGround()
    @Override
    protected void onPostExecute(Object[] rtn) {
        // do something with the result, for example display the received Data in a ListView
        // in this case, "result" would contain the "someLong" variable returned by doInBackground();
        Bitmap bmp = (Bitmap) rtn[0];
        ImageView btn = (ImageView) rtn[1];
        if(bmp == null) {
            //btn.setOnClickListener(null);
            btn.setImageResource(R.mipmap.error_pic); //change image to general error case
            return;
        }
        btn.setImageBitmap(Bitmap.createScaledBitmap(bmp, btn.getMeasuredWidth(), btn.getMeasuredHeight(), false));
    }
}
