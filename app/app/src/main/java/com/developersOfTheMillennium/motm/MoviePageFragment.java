package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class MoviePageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){
        //NEED TO CHANGE ONCLICK LATER SO SENDS TO ACTUAL REVIEW FOR SPECIFIC
        //BUTTON/IMG BUTTON
        View v = in.inflate(R.layout.activity_movies, container, false);

        final ImageButton TrendingButton = v.findViewById(R.id.TrendingButton);
        TrendingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to Trending Review
                MediaProfilePageFragment trendingFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(trendingFrag);
            }
        });

        final ImageButton ComedyButton = v.findViewById(R.id.ComedyButton);
        ComedyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to Comedy Review
                MediaProfilePageFragment comedyFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(comedyFrag);
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

        final ImageButton DramaButton = v.findViewById(R.id.DramaButton);
        DramaButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to Drama Review
                MediaProfilePageFragment dramaFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(dramaFrag);
            }
        });

        final ImageButton RomanceButton = v.findViewById(R.id.RomanceButton);
        RomanceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to Romance Review
                MediaProfilePageFragment romanceFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(romanceFrag);
            }
        });

        final ImageButton SciFiButton = v.findViewById(R.id.SciFiButton);
        SciFiButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to SciFi Review
                MediaProfilePageFragment sciFiFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(sciFiFrag);
            }
        });

        final ImageButton HistButton = v.findViewById(R.id.HistButton);
        HistButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to History Review
                MediaProfilePageFragment histFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(histFrag);
            }
        });
        return v;
    }
}