package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class BookPageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){
        //NEED TO CHANGE ONCLICK LATER SO SENDS TO ACTUAL REVIEW FOR SPECIFIC
        //BUTTON/IMG BUTTON
        View v = in.inflate(R.layout.activity_books, container, false);

        final ImageButton TrendingButton = v.findViewById(R.id.TrendingButton);
        TrendingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to Trending Review
                MediaProfilePageFragment trendingFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(trendingFrag);
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

        final ImageButton HorrorButton = v.findViewById(R.id.HorrorButton);
        HorrorButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to Horror Review
                MediaProfilePageFragment horrorFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(horrorFrag);
            }
        });


        final ImageButton AnimeButton = v.findViewById(R.id.AnimeButton);
        AnimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Take to Anime Review
                MediaProfilePageFragment animeFrag = new MediaProfilePageFragment();
                ((MainActivity)getActivity()).replaceFragment(animeFrag);
            }
        });
        return v;


    }
}