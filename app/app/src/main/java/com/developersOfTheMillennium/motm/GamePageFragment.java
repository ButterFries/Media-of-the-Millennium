package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class GamePageFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){
        //NEED TO CHANGE ONCLICK LATER SO SENDS TO ACTUAL REVIEW FOR SPECIFIC
        //BUTTON/IMG BUTTON
        View v = in.inflate(R.layout.activity_games, container, false);

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

        //ADVENTURE START
        final ImageButton Adventure1 = v.findViewById(R.id.Adventure1);
        Adventure1.setOnClickListener(this);
        final ImageButton Adventure2 = v.findViewById(R.id.Adventure2);
        Adventure2.setOnClickListener(this);
        final ImageButton Adventure3 = v.findViewById(R.id.Adventure3);
        Adventure3.setOnClickListener(this);
        final ImageButton Adventure4 = v.findViewById(R.id.Adventure4);
        Adventure4.setOnClickListener(this);
        final ImageButton Adventure5 = v.findViewById(R.id.Adventure5);
        Adventure5.setOnClickListener(this);
        final ImageButton Adventure6 = v.findViewById(R.id.Adventure6);
        Adventure6.setOnClickListener(this);
        final ImageButton Adventure7 = v.findViewById(R.id.Adventure7);
        Adventure7.setOnClickListener(this);
        final ImageButton Adventure8 = v.findViewById(R.id.Adventure8);
        Adventure8.setOnClickListener(this);
        final ImageButton Adventure9 = v.findViewById(R.id.Adventure9);
        Adventure9.setOnClickListener(this);
        final ImageButton Adventure10 = v.findViewById(R.id.Adventure10);
        Adventure10.setOnClickListener(this);
        //END OF ADVENTURE
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //START RPG
        final ImageButton Rpg1 = v.findViewById(R.id.Rpg1);
        Rpg1.setOnClickListener(this);
        final ImageButton Rpg2 = v.findViewById(R.id.Rpg2);
        Rpg2.setOnClickListener(this);
        final ImageButton Rpg3 = v.findViewById(R.id.Rpg3);
        Rpg3.setOnClickListener(this);
        final ImageButton Rpg4 = v.findViewById(R.id.Rpg4);
        Rpg4.setOnClickListener(this);
        final ImageButton Rpg5 = v.findViewById(R.id.Rpg5);
        Rpg5.setOnClickListener(this);
        final ImageButton Rpg6 = v.findViewById(R.id.Rpg6);
        Rpg6.setOnClickListener(this);
        final ImageButton Rpg7 = v.findViewById(R.id.Rpg7);
        Rpg7.setOnClickListener(this);
        final ImageButton Rpg8 = v.findViewById(R.id.Rpg8);
        Rpg8.setOnClickListener(this);
        final ImageButton Rpg9 = v.findViewById(R.id.Rpg9);
        Rpg9.setOnClickListener(this);
        final ImageButton Rpg10 = v.findViewById(R.id.Rpg10);
        Rpg10.setOnClickListener(this);
        //END OF RPG
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //START FPS
        final ImageButton Fps1 = v.findViewById(R.id.Fps1);
        Fps1.setOnClickListener(this);
        final ImageButton Fps2 = v.findViewById(R.id.Fps2);
        Fps2.setOnClickListener(this);
        final ImageButton Fps3 = v.findViewById(R.id.Fps3);
        Fps3.setOnClickListener(this);
        final ImageButton Fps4 = v.findViewById(R.id.Fps4);
        Fps4.setOnClickListener(this);
        final ImageButton Fps5 = v.findViewById(R.id.Fps5);
        Fps5.setOnClickListener(this);
        final ImageButton Fps6 = v.findViewById(R.id.Fps6);
        Fps6.setOnClickListener(this);
        final ImageButton Fps7 = v.findViewById(R.id.Fps7);
        Fps7.setOnClickListener(this);
        final ImageButton Fps8 = v.findViewById(R.id.Fps8);
        Fps8.setOnClickListener(this);
        final ImageButton Fps9 = v.findViewById(R.id.Fps9);
        Fps9.setOnClickListener(this);
        final ImageButton Fps10 = v.findViewById(R.id.Fps10);
        Fps10.setOnClickListener(this);
        //END OF FPS
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //START SPORTS
        final ImageButton Sports1 = v.findViewById(R.id.Sports1);
        Sports1.setOnClickListener(this);
        final ImageButton Sports2 = v.findViewById(R.id.Sports2);
        Sports2.setOnClickListener(this);
        final ImageButton Sports3 = v.findViewById(R.id.Sports3);
        Sports3.setOnClickListener(this);
        final ImageButton Sports4 = v.findViewById(R.id.Sports4);
        Sports4.setOnClickListener(this);
        final ImageButton Sports5 = v.findViewById(R.id.Sports5);
        Sports5.setOnClickListener(this);
        final ImageButton Sports6 = v.findViewById(R.id.Sports6);
        Sports6.setOnClickListener(this);
        final ImageButton Sports7 = v.findViewById(R.id.Sports7);
        Sports7.setOnClickListener(this);
        final ImageButton Sports8 = v.findViewById(R.id.Sports8);
        Sports8.setOnClickListener(this);
        final ImageButton Sports9 = v.findViewById(R.id.Sports9);
        Sports9.setOnClickListener(this);
        final ImageButton Sports10 = v.findViewById(R.id.Sports10);
        Sports10.setOnClickListener(this);
        //END OF SPORTS
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //START STRATEGY
        final ImageButton Strategy1 = v.findViewById(R.id.Strategy1);
        Strategy1.setOnClickListener(this);
        final ImageButton Strategy2 = v.findViewById(R.id.Strategy2);
        Strategy2.setOnClickListener(this);
        final ImageButton Strategy3 = v.findViewById(R.id.Strategy3);
        Strategy3.setOnClickListener(this);
        final ImageButton Strategy4 = v.findViewById(R.id.Strategy4);
        Strategy4.setOnClickListener(this);
        final ImageButton Strategy5 = v.findViewById(R.id.Strategy5);
        Strategy5.setOnClickListener(this);
        final ImageButton Strategy6 = v.findViewById(R.id.Strategy6);
        Strategy6.setOnClickListener(this);
        final ImageButton Strategy7 = v.findViewById(R.id.Strategy7);
        Strategy7.setOnClickListener(this);
        final ImageButton Strategy8 = v.findViewById(R.id.Strategy8);
        Strategy8.setOnClickListener(this);
        final ImageButton Strategy9 = v.findViewById(R.id.Strategy9);
        Strategy9.setOnClickListener(this);
        final ImageButton Strategy10 = v.findViewById(R.id.Strategy10);
        Strategy10.setOnClickListener(this);
        //END OF STRATEGY
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