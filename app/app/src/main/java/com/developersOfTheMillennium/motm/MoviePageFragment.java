package com.developersOfTheMillennium.motm;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.developersOfTheMillennium.motm.utils.GetMediaIDs;
import com.developersOfTheMillennium.motm.utils.GetPicture;
import com.developersOfTheMillennium.motm.utils.ValidateAccount;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class MoviePageFragment extends Fragment implements View.OnClickListener, FragmentChangeListener {

    private static MainActivity activity;
    private FragmentManager fragmentManager = getFragmentManager();

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){

        View v = in.inflate(R.layout.activity_movies, container, false);

        //NOT SURE WHAT WE DOING FOR TRENDING
        ImageButton[] TrendingButtons = new ImageButton[10];
        final ImageButton Trending1 = v.findViewById(R.id.Trending1);
        Trending1.setOnClickListener(this);
        TrendingButtons[0] = Trending1;
        final ImageButton Trending2 = v.findViewById(R.id.Trending2);
        Trending2.setOnClickListener(this);
        TrendingButtons[1] = Trending2;
        final ImageButton Trending3 = v.findViewById(R.id.Trending3);
        Trending3.setOnClickListener(this);
        TrendingButtons[2] = Trending3;
        final ImageButton Trending4 = v.findViewById(R.id.Trending4);
        Trending4.setOnClickListener(this);
        TrendingButtons[3] = Trending4;
        final ImageButton Trending5 = v.findViewById(R.id.Trending5);
        Trending5.setOnClickListener(this);
        TrendingButtons[4] = Trending5;
        final ImageButton Trending6 = v.findViewById(R.id.Trending6);
        Trending6.setOnClickListener(this);
        TrendingButtons[5] = Trending6;
        final ImageButton Trending7 = v.findViewById(R.id.Trending7);
        Trending7.setOnClickListener(this);
        TrendingButtons[6] = Trending7;
        final ImageButton Trending8 = v.findViewById(R.id.Trending8);
        Trending8.setOnClickListener(this);
        TrendingButtons[7] = Trending8;
        final ImageButton Trending9 = v.findViewById(R.id.Trending9);
        Trending9.setOnClickListener(this);
        TrendingButtons[8] = Trending9;
        final ImageButton Trending10 = v.findViewById(R.id.Trending10);
        Trending10.setOnClickListener(this);
        TrendingButtons[9] = Trending10;
        //END OF TRENDING

        //COMEDY START
        ImageButton[] ComedyButtons = new ImageButton[10];
        final ImageButton Comedy1 = v.findViewById(R.id.Comedy1);
        Comedy1.setOnClickListener(this);
        ComedyButtons[0] = Comedy1;
        final ImageButton Comedy2 = v.findViewById(R.id.Comedy2);
        Comedy2.setOnClickListener(this);
        ComedyButtons[1] = Comedy2;
        final ImageButton Comedy3 = v.findViewById(R.id.Comedy3);
        Comedy3.setOnClickListener(this);
        ComedyButtons[2] = Comedy3;
        final ImageButton Comedy4 = v.findViewById(R.id.Comedy4);
        Comedy4.setOnClickListener(this);
        ComedyButtons[3] = Comedy4;
        final ImageButton Comedy5 = v.findViewById(R.id.Comedy5);
        Comedy5.setOnClickListener(this);
        ComedyButtons[4] = Comedy5;
        final ImageButton Comedy6 = v.findViewById(R.id.Comedy6);
        Comedy6.setOnClickListener(this);
        ComedyButtons[5] = Comedy6;
        final ImageButton Comedy7 = v.findViewById(R.id.Comedy7);
        Comedy7.setOnClickListener(this);
        ComedyButtons[6] = Comedy7;
        final ImageButton Comedy8 = v.findViewById(R.id.Comedy8);
        Comedy8.setOnClickListener(this);
        ComedyButtons[7] = Comedy8;
        final ImageButton Comedy9 = v.findViewById(R.id.Comedy9);
        Comedy9.setOnClickListener(this);
        ComedyButtons[8] = Comedy9;
        final ImageButton Comedy10 = v.findViewById(R.id.Comedy10);
        Comedy10.setOnClickListener(this);
        ComedyButtons[9] = Comedy10;
        //END OF COMEDY

        //ACTION START
        ImageButton[] ActionButtons = new ImageButton[10];
        final ImageButton Action1 = v.findViewById(R.id.Action1);
        Action1.setOnClickListener(this);
        ActionButtons[0] = Action1;
        final ImageButton Action2 = v.findViewById(R.id.Action2);
        Action2.setOnClickListener(this);
        ActionButtons[1] = Action2;
        final ImageButton Action3 = v.findViewById(R.id.Action3);
        Action3.setOnClickListener(this);
        ActionButtons[2] = Action3;
        final ImageButton Action4 = v.findViewById(R.id.Action4);
        Action4.setOnClickListener(this);
        ActionButtons[3] = Action4;
        final ImageButton Action5 = v.findViewById(R.id.Action5);
        Action5.setOnClickListener(this);
        ActionButtons[4] = Action5;
        final ImageButton Action6 = v.findViewById(R.id.Action6);
        Action6.setOnClickListener(this);
        ActionButtons[5] = Action6;
        final ImageButton Action7 = v.findViewById(R.id.Action7);
        Action7.setOnClickListener(this);
        ComedyButtons[6] = Action7;
        final ImageButton Action8 = v.findViewById(R.id.Action8);
        Action8.setOnClickListener(this);
        ComedyButtons[7] = Action8;
        final ImageButton Action9 = v.findViewById(R.id.Action9);
        Action9.setOnClickListener(this);
        ActionButtons[8] = Action9;
        final ImageButton Action10 = v.findViewById(R.id.Action10);
        Action10.setOnClickListener(this);
        ActionButtons[9] = Action10;
        //END OF ACTION

        //START DRAMA
        ImageButton[] DramaButtons = new ImageButton[10];
        final ImageButton Drama1 = v.findViewById(R.id.Drama1);
        Drama1.setOnClickListener(this);
        DramaButtons[0] = Drama1;
        final ImageButton Drama2 = v.findViewById(R.id.Drama2);
        Drama2.setOnClickListener(this);
        DramaButtons[1] = Drama2;
        final ImageButton Drama3 = v.findViewById(R.id.Drama3);
        Drama3.setOnClickListener(this);
        DramaButtons[2] = Drama3;
        final ImageButton Drama4 = v.findViewById(R.id.Drama4);
        Drama4.setOnClickListener(this);
        DramaButtons[3] = Drama4;
        final ImageButton Drama5 = v.findViewById(R.id.Drama5);
        Drama5.setOnClickListener(this);
        DramaButtons[4] = Drama5;
        final ImageButton Drama6 = v.findViewById(R.id.Drama6);
        Drama6.setOnClickListener(this);
        DramaButtons[5] = Drama6;
        final ImageButton Drama7 = v.findViewById(R.id.Drama7);
        Drama7.setOnClickListener(this);
        DramaButtons[6] = Drama7;
        final ImageButton Drama8 = v.findViewById(R.id.Drama8);
        Drama8.setOnClickListener(this);
        DramaButtons[7] = Drama8;
        final ImageButton Drama9 = v.findViewById(R.id.Drama9);
        Drama9.setOnClickListener(this);
        DramaButtons[8] = Drama9;
        final ImageButton Drama10 = v.findViewById(R.id.Drama10);
        Drama10.setOnClickListener(this);
        DramaButtons[9] = Drama10;
        //END OF DRAMA

        //START ROMANCE
        ImageButton[] RomanceButtons = new ImageButton[10];
        final ImageButton Romance1 = v.findViewById(R.id.Romance1);
        Romance1.setOnClickListener(this);
        RomanceButtons[0] = Romance1;
        final ImageButton Romance2 = v.findViewById(R.id.Romance2);
        Romance2.setOnClickListener(this);
        RomanceButtons[1] = Romance2;
        final ImageButton Romance3 = v.findViewById(R.id.Romance3);
        Romance3.setOnClickListener(this);
        RomanceButtons[2] = Romance3;
        final ImageButton Romance4 = v.findViewById(R.id.Romance4);
        Romance4.setOnClickListener(this);
        RomanceButtons[3] = Romance4;
        final ImageButton Romance5 = v.findViewById(R.id.Romance5);
        Romance5.setOnClickListener(this);
        RomanceButtons[4] = Romance5;
        final ImageButton Romance6 = v.findViewById(R.id.Romance6);
        Romance6.setOnClickListener(this);
        RomanceButtons[5] = Romance6;
        final ImageButton Romance7 = v.findViewById(R.id.Romance7);
        Romance7.setOnClickListener(this);
        RomanceButtons[6] = Romance7;
        final ImageButton Romance8 = v.findViewById(R.id.Romance8);
        Romance8.setOnClickListener(this);
        RomanceButtons[7] = Romance8;
        final ImageButton Romance9 = v.findViewById(R.id.Romance9);
        Romance9.setOnClickListener(this);
        RomanceButtons[8] = Romance9;
        final ImageButton Romance10 = v.findViewById(R.id.Romance10);
        Romance10.setOnClickListener(this);
        RomanceButtons[9] = Romance10;
        //END OF ROMANCE

        //START SCIFI
        ImageButton[] SciFiButtons = new ImageButton[10];
        final ImageButton SciFi1 = v.findViewById(R.id.SciFi1);
        SciFi1.setOnClickListener(this);
        SciFiButtons[0] = SciFi1;
        final ImageButton SciFi2 = v.findViewById(R.id.SciFi2);
        SciFi2.setOnClickListener(this);
        SciFiButtons[1] = SciFi2;
        final ImageButton SciFi3 = v.findViewById(R.id.SciFi3);
        SciFi3.setOnClickListener(this);
        SciFiButtons[2] = SciFi3;
        final ImageButton SciFi4 = v.findViewById(R.id.SciFi4);
        SciFi4.setOnClickListener(this);
        SciFiButtons[3] = SciFi4;
        final ImageButton SciFi5 = v.findViewById(R.id.SciFi5);
        SciFi5.setOnClickListener(this);
        SciFiButtons[4] = SciFi5;
        final ImageButton SciFi6 = v.findViewById(R.id.SciFi6);
        SciFi6.setOnClickListener(this);
        SciFiButtons[5] = SciFi6;
        final ImageButton SciFi7 = v.findViewById(R.id.SciFi7);
        SciFi7.setOnClickListener(this);
        SciFiButtons[6] = SciFi7;
        final ImageButton SciFi8 = v.findViewById(R.id.SciFi8);
        SciFi8.setOnClickListener(this);
        SciFiButtons[7] = SciFi8;
        final ImageButton SciFi9 = v.findViewById(R.id.SciFi9);
        SciFi9.setOnClickListener(this);
        SciFiButtons[8] = SciFi9;
        final ImageButton SciFi10 = v.findViewById(R.id.SciFi10);
        SciFi10.setOnClickListener(this);
        SciFiButtons[9] = SciFi10;
        //END OF SCIFI

        //START HISTORICAL
        ImageButton[] HistoricalButtons = new ImageButton[10];
        final ImageButton Historical1 = v.findViewById(R.id.Historical1);
        Historical1.setOnClickListener(this);
        HistoricalButtons[0] = Historical1;
        final ImageButton Historical2 = v.findViewById(R.id.Historical2);
        Historical2.setOnClickListener(this);
        HistoricalButtons[1] = Historical2;
        final ImageButton Historical3 = v.findViewById(R.id.Historical3);
        Historical3.setOnClickListener(this);
        HistoricalButtons[2] = Historical3;
        final ImageButton Historical4 = v.findViewById(R.id.Historical4);
        Historical4.setOnClickListener(this);
        HistoricalButtons[3] = Historical4;
        final ImageButton Historical5 = v.findViewById(R.id.Historical5);
        Historical5.setOnClickListener(this);
        HistoricalButtons[4] = Historical5;
        final ImageButton Historical6 = v.findViewById(R.id.Historical6);
        Historical6.setOnClickListener(this);
        HistoricalButtons[5] = Historical6;
        final ImageButton Historical7 = v.findViewById(R.id.Historical7);
        Historical7.setOnClickListener(this);
        HistoricalButtons[6] = Historical7;
        final ImageButton Historical8 = v.findViewById(R.id.Historical8);
        Historical8.setOnClickListener(this);
        HistoricalButtons[7] = Historical8;
        final ImageButton Historical9 = v.findViewById(R.id.Historical9);
        Historical9.setOnClickListener(this);
        HistoricalButtons[8] = Historical9;
        final ImageButton Historical10 = v.findViewById(R.id.Historical10);
        Historical10.setOnClickListener(this);
        HistoricalButtons[9] = Historical10;
        //END OF HISTORICAL

        //CHANGE TRENDING
        retrieveAndDisplay(TrendingButtons, "trending", "cinema","getTopRatedMedia");
        retrieveAndDisplay(ComedyButtons, "comedy", "cinema", "getMediaByGenreAndType");
        retrieveAndDisplay(ActionButtons, "action", "cinema", "getMediaByGenreAndType");
        retrieveAndDisplay(DramaButtons, "drama", "cinema", "getMediaByGenreAndType");
        retrieveAndDisplay(RomanceButtons, "romance", "cinema", "getMediaByGenreAndType");
        retrieveAndDisplay(SciFiButtons, "scifi", "cinema", "getMediaByGenreAndType");
        retrieveAndDisplay(HistoricalButtons, "historical", "cinema", "getMediaByGenreAndType");

        return v;
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_view, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        //Take to the Proper Review
        //MIGHT NEED TO ADD CASE SWITCH FOR EACH BUTTON CREATE FRAG DEPENDING ON WHAT ITS ID IS
        Object id = v.getTag();
        Bundle args = new Bundle();
        MediaProfilePageFragment Frag = new MediaProfilePageFragment();
        if(id != null) {
            args.putInt("mediaID", (Integer) id);
            Frag.setArguments(args);
        }
        ((MainActivity)getActivity()).replaceFragment(Frag);
    }

    private void retrieveAndDisplay(ImageButton[] genreButtons, String genre, String mediaType, String requestType) {
        //Retrieve mediaIds
        JSONArray genreArray = null;
        try {
            genreArray = getMediaIDs(mediaType, requestType, genre);
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

    private JSONArray getMediaIDs(String mediaType, String requestType, String genre) throws Exception{
        try {
            JSONObject result = new GetMediaIDs((MainActivity) getActivity()).execute(mediaType, requestType, genre).get();
            return result.getJSONArray("mediaIDs");
            //GetMediaIDs IDs = (GetMediaIDs) new GetMediaIDs((MainActivity) getActivity()).execute(mediaType, array).get();
        } catch (Exception e) {
            throw new Exception("(getMediaIDs) -- something went wrong when retrieving mediaIDs");
        }
    }

    private void getPicture(String mediaId, ImageButton imgButton) throws Exception{
        try {
            GetPicture pic = (GetPicture) new GetPicture((MainActivity) getActivity()).execute(mediaId, imgButton);
        } catch (Exception e) {
            throw new Exception("(getPicture) -- something went wrong when retrieving picture");
        }
    }
}