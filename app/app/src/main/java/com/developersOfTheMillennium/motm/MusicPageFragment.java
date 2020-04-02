package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.developersOfTheMillennium.motm.utils.RetrieveAndDisplay;

public class MusicPageFragment extends Fragment {

    private ImageButton[] TrendingButtons = new ImageButton[10];
    private ImageButton[] HipHopButtons = new ImageButton[10];
    private ImageButton[] PopButtons = new ImageButton[10];
    private ImageButton[] RapButtons = new ImageButton[10];
    private ImageButton[] RockButtons = new ImageButton[10];
    private ImageButton[] EdmButtons = new ImageButton[10];
    private ImageButton[] ClassicalButtons = new ImageButton[10];

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){

        View v = in.inflate(R.layout.activity_music, container, false);

        final ImageButton Trending1 = v.findViewById(R.id.Trending1);
        TrendingButtons[0] = Trending1;
        final ImageButton Trending2 = v.findViewById(R.id.Trending2);
        TrendingButtons[1] = Trending2;
        final ImageButton Trending3 = v.findViewById(R.id.Trending3);
        TrendingButtons[2] = Trending3;
        final ImageButton Trending4 = v.findViewById(R.id.Trending4);
        TrendingButtons[3] = Trending4;
        final ImageButton Trending5 = v.findViewById(R.id.Trending5);
        TrendingButtons[4] = Trending5;
        final ImageButton Trending6 = v.findViewById(R.id.Trending6);
        TrendingButtons[5] = Trending6;
        final ImageButton Trending7 = v.findViewById(R.id.Trending7);
        TrendingButtons[6] = Trending7;
        final ImageButton Trending8 = v.findViewById(R.id.Trending8);
        TrendingButtons[7] = Trending8;
        final ImageButton Trending9 = v.findViewById(R.id.Trending9);
        TrendingButtons[8] = Trending9;
        final ImageButton Trending10 = v.findViewById(R.id.Trending10);
        TrendingButtons[9] = Trending10;
        //END OF TRENDING

        //HIP HOP START
        final ImageButton HipHop1 = v.findViewById(R.id.HipHop1);
        HipHopButtons[0] = HipHop1;
        final ImageButton HipHop2 = v.findViewById(R.id.HipHop2);
        HipHopButtons[1] = HipHop2;
        final ImageButton HipHop3 = v.findViewById(R.id.HipHop3);
        HipHopButtons[2] = HipHop3;
        final ImageButton HipHop4 = v.findViewById(R.id.HipHop4);
        HipHopButtons[3] = HipHop4;
        final ImageButton HipHop5 = v.findViewById(R.id.HipHop5);
        HipHopButtons[4] = HipHop5;
        final ImageButton HipHop6 = v.findViewById(R.id.HipHop6);
        HipHopButtons[5] = HipHop6;
        final ImageButton HipHop7 = v.findViewById(R.id.HipHop7);
        HipHopButtons[6] = HipHop7;
        final ImageButton HipHop8 = v.findViewById(R.id.HipHop8);
        HipHopButtons[7] = HipHop8;
        final ImageButton HipHop9 = v.findViewById(R.id.HipHop9);
        HipHopButtons[8] = HipHop9;
        final ImageButton HipHop10 = v.findViewById(R.id.HipHop10);
        HipHopButtons[9] = HipHop10;
        //END OF HIP HOP

        //POP START
        final ImageButton Pop1 = v.findViewById(R.id.Pop1);
        PopButtons[0] = Pop1;
        final ImageButton Pop2 = v.findViewById(R.id.Pop2);
        PopButtons[1] = Pop2;
        final ImageButton Pop3 = v.findViewById(R.id.Pop3);
        PopButtons[2] = Pop3;
        final ImageButton Pop4 = v.findViewById(R.id.Pop4);
        PopButtons[3] = Pop4;
        final ImageButton Pop5 = v.findViewById(R.id.Pop5);
        PopButtons[4] = Pop5;
        final ImageButton Pop6 = v.findViewById(R.id.Pop6);
        PopButtons[5] = Pop6;
        final ImageButton Pop7 = v.findViewById(R.id.Pop7);
        PopButtons[6] = Pop7;
        final ImageButton Pop8 = v.findViewById(R.id.Pop8);
        PopButtons[7] = Pop8;
        final ImageButton Pop9 = v.findViewById(R.id.Pop9);
        PopButtons[8] = Pop9;
        final ImageButton Pop10 = v.findViewById(R.id.Pop10);
        PopButtons[9] = Pop10;
        //END OF POP

        //RAP START
        final ImageButton Rap1 = v.findViewById(R.id.Rap1);
        RapButtons[0] = Rap1;
        final ImageButton Rap2 = v.findViewById(R.id.Rap2);
        RapButtons[1] = Rap2;
        final ImageButton Rap3 = v.findViewById(R.id.Rap3);
        RapButtons[2] = Rap3;
        final ImageButton Rap4 = v.findViewById(R.id.Rap4);
        RapButtons[3] = Rap4;
        final ImageButton Rap5 = v.findViewById(R.id.Rap5);
        RapButtons[4] = Rap5;
        final ImageButton Rap6 = v.findViewById(R.id.Rap6);
        RapButtons[5] = Rap6;
        final ImageButton Rap7 = v.findViewById(R.id.Rap7);
        RapButtons[6] = Rap7;
        final ImageButton Rap8 = v.findViewById(R.id.Rap8);
        RapButtons[7] = Rap8;
        final ImageButton Rap9 = v.findViewById(R.id.Rap9);
        RapButtons[8] = Rap9;
        final ImageButton Rap10 = v.findViewById(R.id.Rap10);
        RapButtons[9] = Rap10;
        //END OF RAP

        //START ROCK
        final ImageButton Rock1 = v.findViewById(R.id.Rock1);
        RockButtons[0] = Rock1;
        final ImageButton Rock2 = v.findViewById(R.id.Rock2);
        RockButtons[1] = Rock2;
        final ImageButton Rock3 = v.findViewById(R.id.Rock3);
        RockButtons[2] = Rock3;
        final ImageButton Rock4 = v.findViewById(R.id.Rock4);
        RockButtons[3] = Rock4;
        final ImageButton Rock5 = v.findViewById(R.id.Rock5);
        RockButtons[4] = Rock5;
        final ImageButton Rock6 = v.findViewById(R.id.Rock6);
        RockButtons[5] = Rock6;
        final ImageButton Rock7 = v.findViewById(R.id.Rock7);
        RockButtons[6] = Rock7;
        final ImageButton Rock8 = v.findViewById(R.id.Rock8);
        RockButtons[7] = Rock8;
        final ImageButton Rock9 = v.findViewById(R.id.Rock9);
        RockButtons[8] = Rock9;
        final ImageButton Rock10 = v.findViewById(R.id.Rock10);
        RockButtons[9] = Rock10;
        //END OF ROCK

        //EDM START
        final ImageButton Edm1 = v.findViewById(R.id.Edm1);
        EdmButtons[0] = Edm1;
        final ImageButton Edm2 = v.findViewById(R.id.Edm2);
        EdmButtons[1] = Edm2;
        final ImageButton Edm3 = v.findViewById(R.id.Edm3);
        EdmButtons[2] = Edm3;
        final ImageButton Edm4 = v.findViewById(R.id.Edm4);
        EdmButtons[3] = Edm4;
        final ImageButton Edm5 = v.findViewById(R.id.Edm5);
        EdmButtons[4] = Edm5;
        final ImageButton Edm6 = v.findViewById(R.id.Edm6);
        EdmButtons[5] = Edm6;
        final ImageButton Edm7 = v.findViewById(R.id.Edm7);
        EdmButtons[6] = Edm7;
        final ImageButton Edm8 = v.findViewById(R.id.Edm8);
        EdmButtons[7] = Edm8;
        final ImageButton Edm9 = v.findViewById(R.id.Edm9);
        EdmButtons[8] = Edm9;
        final ImageButton Edm10 = v.findViewById(R.id.Edm10);
        EdmButtons[9] = Edm10;
        //END OF EDM

        //CLASSICAL START
        final ImageButton Classical1 = v.findViewById(R.id.Classical1);
        ClassicalButtons[0] = Classical1;
        final ImageButton Classical2 = v.findViewById(R.id.Classical2);
        ClassicalButtons[1] = Classical2;
        final ImageButton Classical3 = v.findViewById(R.id.Classical3);
        ClassicalButtons[2] = Classical3;
        final ImageButton Classical4 = v.findViewById(R.id.Classical4);
        ClassicalButtons[3] = Classical4;
        final ImageButton Classical5 = v.findViewById(R.id.Classical5);
        ClassicalButtons[4] = Classical5;
        final ImageButton Classical6 = v.findViewById(R.id.Classical6);
        ClassicalButtons[5] = Classical6;
        final ImageButton Classical7 = v.findViewById(R.id.Classical7);
        ClassicalButtons[6] = Classical7;
        final ImageButton Classical8 = v.findViewById(R.id.Classical8);
        ClassicalButtons[7] = Classical8;
        final ImageButton Classical9 = v.findViewById(R.id.Classical9);
        ClassicalButtons[8] = Classical9;
        final ImageButton Classical10 = v.findViewById(R.id.Classical10);
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

    private void retrieveAndDisplay(ImageButton[] genreButtons, String genre, String mediaType, String requestType) {
        RetrieveAndDisplay retrieveAndDisplay = (RetrieveAndDisplay) new RetrieveAndDisplay((MainActivity) getActivity()).execute(genreButtons, genre, mediaType, requestType);
    }
}
