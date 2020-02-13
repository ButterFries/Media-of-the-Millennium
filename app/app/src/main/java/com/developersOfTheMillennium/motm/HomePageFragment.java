package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class HomePageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){

        View v = in.inflate(R.layout.activity_app_home_page, container, false);

        final ImageButton MovieButton = v.findViewById(R.id.HomePageMovies);
        MovieButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TAKE TO MOVIE PAGE
                MoviePageFragment movieFragment = new MoviePageFragment();
                ((MainActivity)getActivity()).replaceFragment(movieFragment);
            }
        });

        final ImageButton TvButton = v.findViewById(R.id.HomePageTvSeries);
        TvButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TAKE TO TV PAGE
                TvPageFragment tvFragment = new TvPageFragment();
                ((MainActivity)getActivity()).replaceFragment(tvFragment);
            }
        });

        final ImageButton MusicButton = v.findViewById(R.id.HomePageMusic);
        MusicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TAKE TO MUSIC PAGE
                MusicPageFragment musicFragment = new MusicPageFragment();
                ((MainActivity)getActivity()).replaceFragment(musicFragment);
            }
        });

        final ImageButton GameButton = v.findViewById(R.id.HomePageGames);
        GameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TAKE TO GAMES PAGE
                GamePageFragment gameFragment = new GamePageFragment();
                ((MainActivity)getActivity()).replaceFragment(gameFragment);
            }
        });

        final ImageButton BookButton = v.findViewById(R.id.HomePageBooks);
        BookButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TAKE TO BOOKS PAGE
                BookPageFragment bookFragment = new BookPageFragment();
                ((MainActivity)getActivity()).replaceFragment(bookFragment);
            }
        });

        return v;
    }
}