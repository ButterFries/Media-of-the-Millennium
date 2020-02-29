package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class MusicPageFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){
        //NEED TO CHANGE ONCLICK LATER SO SENDS TO ACTUAL REVIEW FOR SPECIFIC
        //BUTTON/IMG BUTTON
        View v = in.inflate(R.layout.activity_music, container, false);

        final ImageButton Trending1 = v.findViewById(R.id.Trending1);
        Trending1.setOnClickListener(this);
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

        //HIP HOP START
        final ImageButton HipHop1 = v.findViewById(R.id.HipHop1);
        HipHop1.setOnClickListener(this);
        final ImageButton HipHop2 = v.findViewById(R.id.HipHop2);
        HipHop2.setOnClickListener(this);
        final ImageButton HipHop3 = v.findViewById(R.id.HipHop3);
        HipHop3.setOnClickListener(this);
        final ImageButton HipHop4 = v.findViewById(R.id.HipHop4);
        HipHop4.setOnClickListener(this);
        final ImageButton HipHop5 = v.findViewById(R.id.HipHop5);
        HipHop5.setOnClickListener(this);
        final ImageButton HipHop6 = v.findViewById(R.id.HipHop6);
        HipHop6.setOnClickListener(this);
        final ImageButton HipHop7 = v.findViewById(R.id.HipHop7);
        HipHop7.setOnClickListener(this);
        final ImageButton HipHop8 = v.findViewById(R.id.HipHop8);
        HipHop8.setOnClickListener(this);
        final ImageButton HipHop9 = v.findViewById(R.id.HipHop9);
        HipHop9.setOnClickListener(this);
        final ImageButton HipHop10 = v.findViewById(R.id.HipHop10);
        HipHop10.setOnClickListener(this);
        //END OF HIP HOP
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //POP START
        final ImageButton Pop1 = v.findViewById(R.id.Pop1);
        Pop1.setOnClickListener(this);
        final ImageButton Pop2 = v.findViewById(R.id.Pop2);
        Pop2.setOnClickListener(this);
        final ImageButton Pop3 = v.findViewById(R.id.Pop3);
        Pop3.setOnClickListener(this);
        final ImageButton Pop4 = v.findViewById(R.id.Pop4);
        Pop4.setOnClickListener(this);
        final ImageButton Pop5 = v.findViewById(R.id.Pop5);
        Pop5.setOnClickListener(this);
        final ImageButton Pop6 = v.findViewById(R.id.Pop6);
        Pop6.setOnClickListener(this);
        final ImageButton Pop7 = v.findViewById(R.id.Pop7);
        Pop7.setOnClickListener(this);
        final ImageButton Pop8 = v.findViewById(R.id.Pop8);
        Pop8.setOnClickListener(this);
        final ImageButton Pop9 = v.findViewById(R.id.Pop9);
        Pop9.setOnClickListener(this);
        final ImageButton Pop10 = v.findViewById(R.id.Pop10);
        Pop10.setOnClickListener(this);
        //END OF POP
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //START RAP
        final ImageButton Rap1 = v.findViewById(R.id.Rap1);
        Rap1.setOnClickListener(this);
        final ImageButton Rap2 = v.findViewById(R.id.Rap2);
        Rap2.setOnClickListener(this);
        final ImageButton Rap3 = v.findViewById(R.id.Rap3);
        Rap3.setOnClickListener(this);
        final ImageButton Rap4 = v.findViewById(R.id.Rap4);
        Rap4.setOnClickListener(this);
        final ImageButton Rap5 = v.findViewById(R.id.Rap5);
        Rap5.setOnClickListener(this);
        final ImageButton Rap6 = v.findViewById(R.id.Rap6);
        Rap6.setOnClickListener(this);
        final ImageButton Rap7 = v.findViewById(R.id.Rap7);
        Rap7.setOnClickListener(this);
        final ImageButton Rap8 = v.findViewById(R.id.Rap8);
        Rap8.setOnClickListener(this);
        final ImageButton Rap9 = v.findViewById(R.id.Rap9);
        Rap9.setOnClickListener(this);
        final ImageButton Rap10 = v.findViewById(R.id.Rap10);
        Rap10.setOnClickListener(this);
        //END OF RAP
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //START ROCK
        final ImageButton Rock1 = v.findViewById(R.id.Rock1);
        Rock1.setOnClickListener(this);
        final ImageButton Rock2 = v.findViewById(R.id.Rock2);
        Rock2.setOnClickListener(this);
        final ImageButton Rock3 = v.findViewById(R.id.Rock3);
        Rock3.setOnClickListener(this);
        final ImageButton Rock4 = v.findViewById(R.id.Rock4);
        Rock4.setOnClickListener(this);
        final ImageButton Rock5 = v.findViewById(R.id.Rock5);
        Rock5.setOnClickListener(this);
        final ImageButton Rock6 = v.findViewById(R.id.Rock6);
        Rock6.setOnClickListener(this);
        final ImageButton Rock7 = v.findViewById(R.id.Rock7);
        Rock7.setOnClickListener(this);
        final ImageButton Rock8 = v.findViewById(R.id.Rock8);
        Rock8.setOnClickListener(this);
        final ImageButton Rock9 = v.findViewById(R.id.Rock9);
        Rock9.setOnClickListener(this);
        final ImageButton Rock10 = v.findViewById(R.id.Rock10);
        Rock10.setOnClickListener(this);
        //END OF ROCK
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //START EDM
        final ImageButton Edm1 = v.findViewById(R.id.Edm1);
        Edm1.setOnClickListener(this);
        final ImageButton Edm2 = v.findViewById(R.id.Edm2);
        Edm2.setOnClickListener(this);
        final ImageButton Edm3 = v.findViewById(R.id.Edm3);
        Edm3.setOnClickListener(this);
        final ImageButton Edm4 = v.findViewById(R.id.Edm4);
        Edm4.setOnClickListener(this);
        final ImageButton Edm5 = v.findViewById(R.id.Edm5);
        Edm5.setOnClickListener(this);
        final ImageButton Edm6 = v.findViewById(R.id.Edm6);
        Edm6.setOnClickListener(this);
        final ImageButton Edm7 = v.findViewById(R.id.Edm7);
        Edm7.setOnClickListener(this);
        final ImageButton Edm8 = v.findViewById(R.id.Edm8);
        Edm8.setOnClickListener(this);
        final ImageButton Edm9 = v.findViewById(R.id.Edm9);
        Edm9.setOnClickListener(this);
        final ImageButton Edm10 = v.findViewById(R.id.Edm10);
        Edm10.setOnClickListener(this);
        //END OF EDM
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //START CLASSICAL
        final ImageButton Classical1 = v.findViewById(R.id.Classical1);
        Classical1.setOnClickListener(this);
        final ImageButton Classical2 = v.findViewById(R.id.Classical2);
        Classical2.setOnClickListener(this);
        final ImageButton Classical3 = v.findViewById(R.id.Classical3);
        Classical3.setOnClickListener(this);
        final ImageButton Classical4 = v.findViewById(R.id.Classical4);
        Classical4.setOnClickListener(this);
        final ImageButton Classical5 = v.findViewById(R.id.Classical5);
        Classical5.setOnClickListener(this);
        final ImageButton Classical6 = v.findViewById(R.id.Classical6);
        Classical6.setOnClickListener(this);
        final ImageButton Classical7 = v.findViewById(R.id.Classical7);
        Classical7.setOnClickListener(this);
        final ImageButton Classical8 = v.findViewById(R.id.Classical8);
        Classical8.setOnClickListener(this);
        final ImageButton Classical9 = v.findViewById(R.id.Classical9);
        Classical9.setOnClickListener(this);
        final ImageButton Classical10 = v.findViewById(R.id.Classical10);
        Classical10.setOnClickListener(this);
        //END OF CLASSICAL
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
