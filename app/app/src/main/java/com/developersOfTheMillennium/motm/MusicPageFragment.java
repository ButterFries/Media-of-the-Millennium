package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class MusicPageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){
        //NEED TO CHANGE ONCLICK LATER SO SENDS TO ACTUAL REVIEW FOR SPECIFIC
        //BUTTON/IMG BUTTON
        View v = in.inflate(R.layout.activity_music, container, false);

        final ImageButton TrendingButton = v.findViewById(R.id.TrendingButton);
        TrendingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to Trending Review
                MediaProfilePageFragment trendingFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(trendingFrag);
            }
        });

        final ImageButton HipHopButton = v.findViewById(R.id.HipHopButton);
        HipHopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to HipHop Review
                MediaProfilePageFragment hipHopFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(hipHopFrag);
            }
        });

        final ImageButton PopButton = v.findViewById(R.id.PopButton);
        PopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to Pop Review
                MediaProfilePageFragment popFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(popFrag);
            }
        });

        final ImageButton RapButton = v.findViewById(R.id.RapButton);
        RapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to Rap Review
                MediaProfilePageFragment rapFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(rapFrag);
            }
        });

        final ImageButton RockButton = v.findViewById(R.id.RockButton);
        RockButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to Rock Review
                MediaProfilePageFragment rockFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(rockFrag);
            }
        });

        final ImageButton EdmButton = v.findViewById(R.id.EdmButton);
        EdmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to Edm Review
                MediaProfilePageFragment sciFiFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(sciFiFrag);
            }
        });

        final ImageButton ClassicalButton = v.findViewById(R.id.ClassicalButton);
        ClassicalButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to Classical Review
                MediaProfilePageFragment classicalFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(classicalFrag);
            }
        });
        return v;
    }
}
