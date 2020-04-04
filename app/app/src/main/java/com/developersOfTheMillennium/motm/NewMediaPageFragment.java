package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.developersOfTheMillennium.motm.utils.RetrieveAndDisplay;

public class NewMediaPageFragment extends Fragment {

    private ImageButton[] novelButtons = new ImageButton[10];
    private ImageButton[] videogameButtons = new ImageButton[10];
    private ImageButton[] musicButtons = new ImageButton[10];
    private ImageButton[] tvButtons = new ImageButton[10];
    private ImageButton[] movieButtons = new ImageButton[10];

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance) {

        View v = in.inflate(R.layout.activity_new_media, container, false);

        //START MOVIES
        final ImageButton Movies1 = v.findViewById(R.id.Movies1);
        movieButtons[0] = Movies1;
        final ImageButton Movies2 = v.findViewById(R.id.Movies2);
        movieButtons[1] = Movies2;
        final ImageButton Movies3 = v.findViewById(R.id.Movies3);
        movieButtons[2] = Movies3;
        final ImageButton Movies4 = v.findViewById(R.id.Movies4);
        movieButtons[3] = Movies4;
        final ImageButton Movies5 = v.findViewById(R.id.Movies5);
        movieButtons[4] = Movies5;
        final ImageButton Movies6 = v.findViewById(R.id.Movies6);
        movieButtons[5] = Movies6;
        final ImageButton Movies7 = v.findViewById(R.id.Movies7);
        movieButtons[6] = Movies7;
        final ImageButton Movies8 = v.findViewById(R.id.Movies8);
        movieButtons[7] = Movies8;
        final ImageButton Movies9 = v.findViewById(R.id.Movies9);
        movieButtons[8] = Movies9;
        final ImageButton Movies10 = v.findViewById(R.id.Movies10);
        movieButtons[9] = Movies10;
        //END OF Movies

        //TVShows START
        final ImageButton TVShows1 = v.findViewById(R.id.TVShows1);
        tvButtons[0] = TVShows1;
        final ImageButton TVShows2 = v.findViewById(R.id.TVShows2);
        tvButtons[1] = TVShows2;
        final ImageButton TVShows3 = v.findViewById(R.id.TVShows3);
        tvButtons[2] = TVShows3;
        final ImageButton TVShows4 = v.findViewById(R.id.TVShows4);
        tvButtons[3] = TVShows4;
        final ImageButton TVShows5 = v.findViewById(R.id.TVShows5);
        tvButtons[4] = TVShows5;
        final ImageButton TVShows6 = v.findViewById(R.id.TVShows6);
        tvButtons[5] = TVShows6;
        final ImageButton TVShows7 = v.findViewById(R.id.TVShows7);
        tvButtons[6] = TVShows7;
        final ImageButton TVShows8 = v.findViewById(R.id.TVShows8);
        tvButtons[7] = TVShows8;
        final ImageButton TVShows9 = v.findViewById(R.id.TVShows9);
        tvButtons[8] = TVShows9;
        final ImageButton TVShows10 = v.findViewById(R.id.TVShows10);
        tvButtons[9] = TVShows10;
        //END OF TVShows

        //Music START
        final ImageButton Music1 = v.findViewById(R.id.Music1);
        musicButtons[0] = Music1;
        final ImageButton Music2 = v.findViewById(R.id.Music2);
        musicButtons[1] = Music2;
        final ImageButton Music3 = v.findViewById(R.id.Music3);
        musicButtons[2] = Music3;
        final ImageButton Music4 = v.findViewById(R.id.Music4);
        musicButtons[3] = Music4;
        final ImageButton Music5 = v.findViewById(R.id.Music5);
        musicButtons[4] = Music5;
        final ImageButton Music6 = v.findViewById(R.id.Music6);
        musicButtons[5] = Music6;
        final ImageButton Music7 = v.findViewById(R.id.Music7);
        musicButtons[6] = Music7;
        final ImageButton Music8 = v.findViewById(R.id.Music8);
        musicButtons[7] = Music8;
        final ImageButton Music9 = v.findViewById(R.id.Music9);
        musicButtons[8] = Music9;
        final ImageButton Music10 = v.findViewById(R.id.Music10);
        musicButtons[9] = Music10;
        //END OF Music

        //START VideoGames
        final ImageButton VideoGames1 = v.findViewById(R.id.VideoGames1);
        videogameButtons[0] = VideoGames1;
        final ImageButton VideoGames2 = v.findViewById(R.id.VideoGames2);
        videogameButtons[1] = VideoGames2;
        final ImageButton VideoGames3 = v.findViewById(R.id.VideoGames3);
        videogameButtons[2] = VideoGames3;
        final ImageButton VideoGames4 = v.findViewById(R.id.VideoGames4);
        videogameButtons[3] = VideoGames4;
        final ImageButton VideoGames5 = v.findViewById(R.id.VideoGames5);
        videogameButtons[4] = VideoGames5;
        final ImageButton VideoGames6 = v.findViewById(R.id.VideoGames6);
        videogameButtons[5] = VideoGames6;
        final ImageButton VideoGames7 = v.findViewById(R.id.VideoGames7);
        videogameButtons[6] = VideoGames7;
        final ImageButton VideoGames8 = v.findViewById(R.id.VideoGames8);
        videogameButtons[7] = VideoGames8;
        final ImageButton VideoGames9 = v.findViewById(R.id.VideoGames9);
        videogameButtons[8] = VideoGames9;
        final ImageButton VideoGames10 = v.findViewById(R.id.VideoGames10);
        videogameButtons[9] = VideoGames10;
        //END OF VideoGames

        //START Novels
        final ImageButton Novels1 = v.findViewById(R.id.Novels1);
        novelButtons[0] = Novels1;
        final ImageButton Novels2 = v.findViewById(R.id.Novels2);
        novelButtons[1] = Novels2;
        final ImageButton Novels3 = v.findViewById(R.id.Novels3);
        novelButtons[2] = Novels3;
        final ImageButton Novels4 = v.findViewById(R.id.Novels4);
        novelButtons[3] = Novels4;
        final ImageButton Novels5 = v.findViewById(R.id.Novels5);
        novelButtons[4] = Novels5;
        final ImageButton Novels6 = v.findViewById(R.id.Novels6);
        novelButtons[5] = Novels6;
        final ImageButton Novels7 = v.findViewById(R.id.Novels7);
        novelButtons[6] = Novels7;
        final ImageButton Novels8 = v.findViewById(R.id.Novels8);
        novelButtons[7] = Novels8;
        final ImageButton Novels9 = v.findViewById(R.id.Novels9);
        novelButtons[8] = Novels9;
        final ImageButton Novels10 = v.findViewById(R.id.Novels10);
        novelButtons[9] = Novels10;
        //END OF Novels

        return v;
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstance) {
        retrieveAndDisplay(movieButtons,"", "cinema", "getNewMedia");
        retrieveAndDisplay(tvButtons, "", "tvseries", "getNewMedia");
        retrieveAndDisplay(musicButtons, "", "music", "getNewMedia");
        retrieveAndDisplay(videogameButtons, "", "videogame", "getNewMedia");
        retrieveAndDisplay(novelButtons, "", "novel", "getNewMedia");
    }

    private void retrieveAndDisplay(ImageButton[] genreButtons, String genre, String mediaType, String requestType) {
        RetrieveAndDisplay retrieveAndDisplay = (RetrieveAndDisplay) new RetrieveAndDisplay((MainActivity) getActivity()).execute(genreButtons, genre, mediaType, requestType);
    }
}