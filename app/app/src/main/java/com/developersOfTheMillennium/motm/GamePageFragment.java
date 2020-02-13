package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class GamePageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){
        //NEED TO CHANGE ONCLICK LATER SO SENDS TO ACTUAL REVIEW FOR SPECIFIC
        //BUTTON/IMG BUTTON
        View v = in.inflate(R.layout.activity_games, container, false);

        final ImageButton TrendingButton = v.findViewById(R.id.TrendingButton);
        TrendingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to Trending Review
                MediaProfilePageFragment trendingFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(trendingFrag);
            }
        });

        final ImageButton ActionButton = v.findViewById(R.id.ActionButton);
        ActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to Action Review
                MediaProfilePageFragment actionFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(actionFrag);
            }
        });

        final ImageButton AdventureButton = v.findViewById(R.id.AdventureButton);
        AdventureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to Adventure Review
                MediaProfilePageFragment adventureFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(adventureFrag);
            }
        });

        final ImageButton RpgButton = v.findViewById(R.id.RpgButton);
        RpgButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to Rpg Review
                MediaProfilePageFragment rpgFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(rpgFrag);
            }
        });

        final ImageButton FpsButton = v.findViewById(R.id.FpsButton);
        FpsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to Fps Review
                MediaProfilePageFragment fpsFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(fpsFrag);
            }
        });

        final ImageButton SportsButton = v.findViewById(R.id.SportsButton);
        SportsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to Sports Review
                MediaProfilePageFragment sportsFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(sportsFrag);
            }
        });

        final ImageButton StrategyButton = v.findViewById(R.id.StrategyButton);
        StrategyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to Strategy Review
                MediaProfilePageFragment stratFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(stratFrag);
            }
        });

        return v;


    }
}