package com.developersOfTheMillennium.motm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class MoviePageFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){
        //NEED TO CHANGE ONCLICK LATER SO SENDS TO ACTUAL REVIEW FOR SPECIFIC
        //BUTTON/IMG BUTTON
        PictureHandler handler = new PictureHandler();
        View v = in.inflate(R.layout.activity_movies, container, false);

        final ImageButton Trending1 = v.findViewById(R.id.Trending1);
        Trending1.setOnClickListener(this);
        //TESTING TO SEE IF BYTE[] gets outputted properly still haven't tested yet
        //byte[] byteArray = handler.getByteArrayImage("https://www.bigstockphoto.com/images/homepage/module-6.jpg");
        //Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        //Trending1.setImageBitmap(Bitmap.createScaledBitmap(bmp, Trending1.getWidth(), Trending1.getHeight(), false));

        //ACTUAL CODE to test
        //String bytesString = get_picture_bytes(conn, mediaId);
        //byte[] bytes = bytesString.getBytes();
        //Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        //Trending1.setImageBitmap(Bitmap.createScaledBitmap(bmp, Trending1.getWidth(), Trending1.getHeight(), false));

        final ImageButton Trending2 = v.findViewById(R.id.Trending2);
        Trending2.setOnClickListener(this);
        final ImageButton Trending3 = v.findViewById(R.id.Trending3);
        Trending3.setOnClickListener(this);
        final ImageButton Trending4 = v.findViewById(R.id.Trending4);
        Trending4.setOnClickListener(this);
        final ImageButton Trending5 = v.findViewById(R.id.Trending5);
        Trending5.setOnClickListener(this);
        final ImageButton Trending6 = v.findViewById(R.id.Trending6);
        Trending6.setOnClickListener(this);
        final ImageButton Trending7 = v.findViewById(R.id.Trending7);
        Trending7.setOnClickListener(this);
        final ImageButton Trending8 = v.findViewById(R.id.Trending8);
        Trending8.setOnClickListener(this);
        final ImageButton Trending9 = v.findViewById(R.id.Trending9);
        Trending9.setOnClickListener(this);
        final ImageButton Trending10 = v.findViewById(R.id.Trending10);
        Trending10.setOnClickListener(this);
        //END OF TRENDING
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //COMEDY START
        final ImageButton Comedy1 = v.findViewById(R.id.Comedy1);
        Comedy1.setOnClickListener(this);
        final ImageButton Comedy2 = v.findViewById(R.id.Comedy2);
        Comedy2.setOnClickListener(this);
        final ImageButton Comedy3 = v.findViewById(R.id.Comedy3);
        Comedy3.setOnClickListener(this);
        final ImageButton Comedy4 = v.findViewById(R.id.Comedy4);
        Comedy4.setOnClickListener(this);
        final ImageButton Comedy5 = v.findViewById(R.id.Comedy5);
        Comedy5.setOnClickListener(this);
        final ImageButton Comedy6 = v.findViewById(R.id.Comedy6);
        Comedy6.setOnClickListener(this);
        final ImageButton Comedy7 = v.findViewById(R.id.Comedy7);
        Comedy7.setOnClickListener(this);
        final ImageButton Comedy8 = v.findViewById(R.id.Comedy8);
        Comedy8.setOnClickListener(this);
        final ImageButton Comedy9 = v.findViewById(R.id.Comedy9);
        Comedy9.setOnClickListener(this);
        final ImageButton Comedy10 = v.findViewById(R.id.Comedy10);
        Comedy10.setOnClickListener(this);
        //END OF COMEDY
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //ACTION START
        final ImageButton Action1 = v.findViewById(R.id.Action1);
        Action1.setOnClickListener(this);
        final ImageButton Action2 = v.findViewById(R.id.Action2);
        Action2.setOnClickListener(this);
        final ImageButton Action3 = v.findViewById(R.id.Action3);
        Action3.setOnClickListener(this);
        final ImageButton Action4 = v.findViewById(R.id.Action4);
        Action4.setOnClickListener(this);
        final ImageButton Action5 = v.findViewById(R.id.Action5);
        Action5.setOnClickListener(this);
        final ImageButton Action6 = v.findViewById(R.id.Action6);
        Action6.setOnClickListener(this);
        final ImageButton Action7 = v.findViewById(R.id.Action7);
        Action7.setOnClickListener(this);
        final ImageButton Action8 = v.findViewById(R.id.Action8);
        Action8.setOnClickListener(this);
        final ImageButton Action9 = v.findViewById(R.id.Action9);
        Action9.setOnClickListener(this);
        final ImageButton Action10 = v.findViewById(R.id.Action10);
        Action10.setOnClickListener(this);
        //END OF ACTION
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //START DRAMA
        final ImageButton Drama1 = v.findViewById(R.id.Drama1);
        Drama1.setOnClickListener(this);
        final ImageButton Drama2 = v.findViewById(R.id.Drama2);
        Drama2.setOnClickListener(this);
        final ImageButton Drama3 = v.findViewById(R.id.Drama3);
        Drama3.setOnClickListener(this);
        final ImageButton Drama4 = v.findViewById(R.id.Drama4);
        Drama4.setOnClickListener(this);
        final ImageButton Drama5 = v.findViewById(R.id.Drama5);
        Drama5.setOnClickListener(this);
        final ImageButton Drama6 = v.findViewById(R.id.Drama6);
        Drama6.setOnClickListener(this);
        final ImageButton Drama7 = v.findViewById(R.id.Drama7);
        Drama7.setOnClickListener(this);
        final ImageButton Drama8 = v.findViewById(R.id.Drama8);
        Drama8.setOnClickListener(this);
        final ImageButton Drama9 = v.findViewById(R.id.Drama9);
        Drama9.setOnClickListener(this);
        final ImageButton Drama10 = v.findViewById(R.id.Drama10);
        Drama10.setOnClickListener(this);
        //END OF DRAMA
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //START ROMANCE
        final ImageButton Romance1 = v.findViewById(R.id.Romance1);
        Romance1.setOnClickListener(this);
        final ImageButton Romance2 = v.findViewById(R.id.Romance2);
        Romance2.setOnClickListener(this);
        final ImageButton Romance3 = v.findViewById(R.id.Romance3);
        Romance3.setOnClickListener(this);
        final ImageButton Romance4 = v.findViewById(R.id.Romance4);
        Romance4.setOnClickListener(this);
        final ImageButton Romance5 = v.findViewById(R.id.Romance5);
        Romance5.setOnClickListener(this);
        final ImageButton Romance6 = v.findViewById(R.id.Romance6);
        Romance6.setOnClickListener(this);
        final ImageButton Romance7 = v.findViewById(R.id.Romance7);
        Romance7.setOnClickListener(this);
        final ImageButton Romance8 = v.findViewById(R.id.Romance8);
        Romance8.setOnClickListener(this);
        final ImageButton Romance9 = v.findViewById(R.id.Romance9);
        Romance9.setOnClickListener(this);
        final ImageButton Romance10 = v.findViewById(R.id.Romance10);
        Romance10.setOnClickListener(this);
        //END OF ROMANCE
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //START SCIFI
        final ImageButton SciFi1 = v.findViewById(R.id.SciFi1);
        SciFi1.setOnClickListener(this);
        final ImageButton SciFi2 = v.findViewById(R.id.SciFi2);
        SciFi2.setOnClickListener(this);
        final ImageButton SciFi3 = v.findViewById(R.id.SciFi3);
        SciFi3.setOnClickListener(this);
        final ImageButton SciFi4 = v.findViewById(R.id.SciFi4);
        SciFi4.setOnClickListener(this);
        final ImageButton SciFi5 = v.findViewById(R.id.SciFi5);
        SciFi5.setOnClickListener(this);
        final ImageButton SciFi6 = v.findViewById(R.id.SciFi6);
        SciFi6.setOnClickListener(this);
        final ImageButton SciFi7 = v.findViewById(R.id.SciFi7);
        SciFi7.setOnClickListener(this);
        final ImageButton SciFi8 = v.findViewById(R.id.SciFi8);
        SciFi8.setOnClickListener(this);
        final ImageButton SciFi9 = v.findViewById(R.id.SciFi9);
        SciFi9.setOnClickListener(this);
        final ImageButton SciFi10 = v.findViewById(R.id.SciFi10);
        SciFi10.setOnClickListener(this);
        //END OF SCIFI
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //START HISTORICAL
        final ImageButton Historical1 = v.findViewById(R.id.Historical1);
        Historical1.setOnClickListener(this);
        final ImageButton Historical2 = v.findViewById(R.id.Historical2);
        Historical2.setOnClickListener(this);
        final ImageButton Historical3 = v.findViewById(R.id.Historical3);
        Historical3.setOnClickListener(this);
        final ImageButton Historical4 = v.findViewById(R.id.Historical4);
        Historical4.setOnClickListener(this);
        final ImageButton Historical5 = v.findViewById(R.id.Historical5);
        Historical5.setOnClickListener(this);
        final ImageButton Historical6 = v.findViewById(R.id.Historical6);
        Historical6.setOnClickListener(this);
        final ImageButton Historical7 = v.findViewById(R.id.Historical7);
        Historical7.setOnClickListener(this);
        final ImageButton Historical8 = v.findViewById(R.id.Historical8);
        Historical8.setOnClickListener(this);
        final ImageButton Historical9 = v.findViewById(R.id.Historical9);
        Historical9.setOnClickListener(this);
        final ImageButton Historical10 = v.findViewById(R.id.Historical10);
        Historical10.setOnClickListener(this);
        //END OF HISTORICAL
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        return v;
    }

    @Override
    public void onClick(View v) {
        //Take to the Proper Review
        //MIGHT NEED TO ADD CASE SWITCH FOR EACH BUTTON CREATE FRAG DEPENDING ON WHAT ITS ID IS
        //Integer id = v.getId();
        MediaProfilePageFragment Frag = new MediaProfilePageFragment();
        ((MainActivity)getActivity()).replaceFragment(Frag);
    }
}