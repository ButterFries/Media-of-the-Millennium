package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.developersOfTheMillennium.motm.utils.GetMediaIDs;
import com.developersOfTheMillennium.motm.utils.GetPicture;

import org.json.JSONArray;
import org.json.JSONObject;

public class MusicPageFragment extends Fragment implements View.OnClickListener, FragmentChangeListener {

    private FragmentManager fragmentManager = getFragmentManager();
    private ImageButton[] TrendingButtons = new ImageButton[10];
    private ImageButton[] HipHopButtons = new ImageButton[10];
    private ImageButton[] PopButtons = new ImageButton[10];
    private ImageButton[] RapButtons = new ImageButton[10];
    private ImageButton[] RockButtons = new ImageButton[10];
    private ImageButton[] EdmButtons = new ImageButton[10];
    private ImageButton[] ClassicalButtons = new ImageButton[10];

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){
        //NEED TO CHANGE ONCLICK LATER SO SENDS TO ACTUAL REVIEW FOR SPECIFIC
        //BUTTON/IMG BUTTON
        View v = in.inflate(R.layout.activity_music, container, false);

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

        //HIP HOP START
        final ImageButton HipHop1 = v.findViewById(R.id.HipHop1);
        HipHop1.setOnClickListener(this);
        HipHopButtons[0] = HipHop1;
        final ImageButton HipHop2 = v.findViewById(R.id.HipHop2);
        HipHop2.setOnClickListener(this);
        HipHopButtons[1] = HipHop2;
        final ImageButton HipHop3 = v.findViewById(R.id.HipHop3);
        HipHop3.setOnClickListener(this);
        HipHopButtons[2] = HipHop3;
        final ImageButton HipHop4 = v.findViewById(R.id.HipHop4);
        HipHop4.setOnClickListener(this);
        HipHopButtons[3] = HipHop4;
        final ImageButton HipHop5 = v.findViewById(R.id.HipHop5);
        HipHop5.setOnClickListener(this);
        HipHopButtons[4] = HipHop5;
        final ImageButton HipHop6 = v.findViewById(R.id.HipHop6);
        HipHop6.setOnClickListener(this);
        HipHopButtons[5] = HipHop6;
        final ImageButton HipHop7 = v.findViewById(R.id.HipHop7);
        HipHop7.setOnClickListener(this);
        HipHopButtons[6] = HipHop7;
        final ImageButton HipHop8 = v.findViewById(R.id.HipHop8);
        HipHop8.setOnClickListener(this);
        HipHopButtons[7] = HipHop8;
        final ImageButton HipHop9 = v.findViewById(R.id.HipHop9);
        HipHop9.setOnClickListener(this);
        HipHopButtons[8] = HipHop9;
        final ImageButton HipHop10 = v.findViewById(R.id.HipHop10);
        HipHop10.setOnClickListener(this);
        HipHopButtons[9] = HipHop10;
        //END OF HIP HOP

        //POP START
        final ImageButton Pop1 = v.findViewById(R.id.Pop1);
        Pop1.setOnClickListener(this);
        PopButtons[0] = Pop1;
        final ImageButton Pop2 = v.findViewById(R.id.Pop2);
        Pop2.setOnClickListener(this);
        PopButtons[1] = Pop2;
        final ImageButton Pop3 = v.findViewById(R.id.Pop3);
        Pop3.setOnClickListener(this);
        PopButtons[2] = Pop3;
        final ImageButton Pop4 = v.findViewById(R.id.Pop4);
        Pop4.setOnClickListener(this);
        PopButtons[3] = Pop4;
        final ImageButton Pop5 = v.findViewById(R.id.Pop5);
        Pop5.setOnClickListener(this);
        PopButtons[4] = Pop5;
        final ImageButton Pop6 = v.findViewById(R.id.Pop6);
        Pop6.setOnClickListener(this);
        PopButtons[5] = Pop6;
        final ImageButton Pop7 = v.findViewById(R.id.Pop7);
        Pop7.setOnClickListener(this);
        PopButtons[6] = Pop7;
        final ImageButton Pop8 = v.findViewById(R.id.Pop8);
        Pop8.setOnClickListener(this);
        PopButtons[7] = Pop8;
        final ImageButton Pop9 = v.findViewById(R.id.Pop9);
        Pop9.setOnClickListener(this);
        PopButtons[8] = Pop9;
        final ImageButton Pop10 = v.findViewById(R.id.Pop10);
        Pop10.setOnClickListener(this);
        PopButtons[9] = Pop10;
        //END OF POP

        //RAP START
        final ImageButton Rap1 = v.findViewById(R.id.Rap1);
        Rap1.setOnClickListener(this);
        RapButtons[0] = Rap1;
        final ImageButton Rap2 = v.findViewById(R.id.Rap2);
        Rap2.setOnClickListener(this);
        RapButtons[1] = Rap2;
        final ImageButton Rap3 = v.findViewById(R.id.Rap3);
        Rap3.setOnClickListener(this);
        RapButtons[2] = Rap3;
        final ImageButton Rap4 = v.findViewById(R.id.Rap4);
        Rap4.setOnClickListener(this);
        RapButtons[3] = Rap4;
        final ImageButton Rap5 = v.findViewById(R.id.Rap5);
        Rap5.setOnClickListener(this);
        RapButtons[4] = Rap5;
        final ImageButton Rap6 = v.findViewById(R.id.Rap6);
        Rap6.setOnClickListener(this);
        RapButtons[5] = Rap6;
        final ImageButton Rap7 = v.findViewById(R.id.Rap7);
        Rap7.setOnClickListener(this);
        RapButtons[6] = Rap7;
        final ImageButton Rap8 = v.findViewById(R.id.Rap8);
        Rap8.setOnClickListener(this);
        RapButtons[7] = Rap8;
        final ImageButton Rap9 = v.findViewById(R.id.Rap9);
        Rap9.setOnClickListener(this);
        RapButtons[8] = Rap9;
        final ImageButton Rap10 = v.findViewById(R.id.Rap10);
        Rap10.setOnClickListener(this);
        RapButtons[9] = Rap10;
        //END OF RAP

        //START ROCK
        final ImageButton Rock1 = v.findViewById(R.id.Rock1);
        Rock1.setOnClickListener(this);
        RockButtons[0] = Rock1;
        final ImageButton Rock2 = v.findViewById(R.id.Rock2);
        Rock2.setOnClickListener(this);
        RockButtons[1] = Rock2;
        final ImageButton Rock3 = v.findViewById(R.id.Rock3);
        Rock3.setOnClickListener(this);
        RockButtons[2] = Rock3;
        final ImageButton Rock4 = v.findViewById(R.id.Rock4);
        Rock4.setOnClickListener(this);
        RockButtons[3] = Rock4;
        final ImageButton Rock5 = v.findViewById(R.id.Rock5);
        Rock5.setOnClickListener(this);
        RockButtons[4] = Rock5;
        final ImageButton Rock6 = v.findViewById(R.id.Rock6);
        Rock6.setOnClickListener(this);
        RockButtons[5] = Rock6;
        final ImageButton Rock7 = v.findViewById(R.id.Rock7);
        Rock7.setOnClickListener(this);
        RockButtons[6] = Rock7;
        final ImageButton Rock8 = v.findViewById(R.id.Rock8);
        Rock8.setOnClickListener(this);
        RockButtons[7] = Rock8;
        final ImageButton Rock9 = v.findViewById(R.id.Rock9);
        Rock9.setOnClickListener(this);
        RockButtons[8] = Rock9;
        final ImageButton Rock10 = v.findViewById(R.id.Rock10);
        Rock10.setOnClickListener(this);
        RockButtons[9] = Rock10;
        //END OF ROCK

        //EDM START
        final ImageButton Edm1 = v.findViewById(R.id.Edm1);
        Edm1.setOnClickListener(this);
        EdmButtons[0] = Edm1;
        final ImageButton Edm2 = v.findViewById(R.id.Edm2);
        Edm2.setOnClickListener(this);
        EdmButtons[1] = Edm2;
        final ImageButton Edm3 = v.findViewById(R.id.Edm3);
        Edm3.setOnClickListener(this);
        EdmButtons[2] = Edm3;
        final ImageButton Edm4 = v.findViewById(R.id.Edm4);
        Edm4.setOnClickListener(this);
        EdmButtons[3] = Edm4;
        final ImageButton Edm5 = v.findViewById(R.id.Edm5);
        Edm5.setOnClickListener(this);
        EdmButtons[4] = Edm5;
        final ImageButton Edm6 = v.findViewById(R.id.Edm6);
        Edm6.setOnClickListener(this);
        EdmButtons[5] = Edm6;
        final ImageButton Edm7 = v.findViewById(R.id.Edm7);
        Edm7.setOnClickListener(this);
        EdmButtons[6] = Edm7;
        final ImageButton Edm8 = v.findViewById(R.id.Edm8);
        Edm8.setOnClickListener(this);
        EdmButtons[7] = Edm8;
        final ImageButton Edm9 = v.findViewById(R.id.Edm9);
        Edm9.setOnClickListener(this);
        EdmButtons[8] = Edm9;
        final ImageButton Edm10 = v.findViewById(R.id.Edm10);
        Edm10.setOnClickListener(this);
        EdmButtons[9] = Edm10;
        //END OF EDM

        //CLASSICAL START
        final ImageButton Classical1 = v.findViewById(R.id.Classical1);
        Classical1.setOnClickListener(this);
        ClassicalButtons[0] = Classical1;
        final ImageButton Classical2 = v.findViewById(R.id.Classical2);
        Classical2.setOnClickListener(this);
        ClassicalButtons[1] = Classical2;
        final ImageButton Classical3 = v.findViewById(R.id.Classical3);
        Classical3.setOnClickListener(this);
        ClassicalButtons[2] = Classical3;
        final ImageButton Classical4 = v.findViewById(R.id.Classical4);
        Classical4.setOnClickListener(this);
        ClassicalButtons[3] = Classical4;
        final ImageButton Classical5 = v.findViewById(R.id.Classical5);
        Classical5.setOnClickListener(this);
        ClassicalButtons[4] = Classical5;
        final ImageButton Classical6 = v.findViewById(R.id.Classical6);
        Classical6.setOnClickListener(this);
        ClassicalButtons[5] = Classical6;
        final ImageButton Classical7 = v.findViewById(R.id.Classical7);
        Classical7.setOnClickListener(this);
        ClassicalButtons[6] = Classical7;
        final ImageButton Classical8 = v.findViewById(R.id.Classical8);
        Classical8.setOnClickListener(this);
        ClassicalButtons[7] = Classical8;
        final ImageButton Classical9 = v.findViewById(R.id.Classical9);
        Classical9.setOnClickListener(this);
        ClassicalButtons[8] = Classical9;
        final ImageButton Classical10 = v.findViewById(R.id.Classical10);
        Classical10.setOnClickListener(this);
        ClassicalButtons[9] = Classical10;
        //END OF CLASSICAL

        return v;
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstance) {
        //Currently freezes and doesn't load until all retrieve and display is done
        retrieveAndDisplay(TrendingButtons, "trending", "music", "getTopRatedMedia");
        retrieveAndDisplay(HipHopButtons, "hiphop", "music", "getMediaByGenreAndType");
        retrieveAndDisplay(PopButtons, "pop", "music", "getMediaByGenreAndType");
        retrieveAndDisplay(RockButtons, "rock", "music", "getMediaByGenreAndType");
        retrieveAndDisplay(RapButtons, "rap", "music", "getMediaByGenreAndType");
        retrieveAndDisplay(EdmButtons, "edm", "music", "getMediaByGenreAndType");
        retrieveAndDisplay(ClassicalButtons, "classical", "music", "getMediaByGenreAndType");
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_view, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
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
}
