package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class NewMediaPageFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){
        //NEED TO CHANGE ONCLICK LATER SO SENDS TO ACTUAL REVIEW FOR SPECIFIC
        //BUTTON/IMG BUTTON
        View v = in.inflate(R.layout.activity_new_media, container, false);

        final ImageButton Movies1 = v.findViewById(R.id.Movies1);
        Movies1.setOnClickListener(this);
        final ImageButton Movies2 = v.findViewById(R.id.Movies2);
        Movies2.setOnClickListener(this);
        final ImageButton Movies3 = v.findViewById(R.id.Movies3);
        Movies3.setOnClickListener(this);
        final ImageButton Movies4 = v.findViewById(R.id.Movies4);
        Movies4.setOnClickListener(this);
        final ImageButton Movies5 = v.findViewById(R.id.Movies5);
        Movies5.setOnClickListener(this);
        final ImageButton Movies6 = v.findViewById(R.id.Movies6);
        Movies6.setOnClickListener(this);
        final ImageButton Movies7 = v.findViewById(R.id.Movies7);
        Movies7.setOnClickListener(this);
        final ImageButton Movies8 = v.findViewById(R.id.Movies8);
        Movies8.setOnClickListener(this);
        final ImageButton Movies9 = v.findViewById(R.id.Movies9);
        Movies9.setOnClickListener(this);
        final ImageButton Movies10 = v.findViewById(R.id.Movies10);
        Movies10.setOnClickListener(this);
        //END OF Movies
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //TVShows START
        final ImageButton TVShows1 = v.findViewById(R.id.TVShows1);
        TVShows1.setOnClickListener(this);
        final ImageButton TVShows2 = v.findViewById(R.id.TVShows2);
        TVShows2.setOnClickListener(this);
        final ImageButton TVShows3 = v.findViewById(R.id.TVShows3);
        TVShows3.setOnClickListener(this);
        final ImageButton TVShows4 = v.findViewById(R.id.TVShows4);
        TVShows4.setOnClickListener(this);
        final ImageButton TVShows5 = v.findViewById(R.id.TVShows5);
        TVShows5.setOnClickListener(this);
        final ImageButton TVShows6 = v.findViewById(R.id.TVShows6);
        TVShows6.setOnClickListener(this);
        final ImageButton TVShows7 = v.findViewById(R.id.TVShows7);
        TVShows7.setOnClickListener(this);
        final ImageButton TVShows8 = v.findViewById(R.id.TVShows8);
        TVShows8.setOnClickListener(this);
        final ImageButton TVShows9 = v.findViewById(R.id.TVShows9);
        TVShows9.setOnClickListener(this);
        final ImageButton TVShows10 = v.findViewById(R.id.TVShows10);
        TVShows10.setOnClickListener(this);
        //END OF TVShows
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //Music START
        final ImageButton Music1 = v.findViewById(R.id.Music1);
        Music1.setOnClickListener(this);
        final ImageButton Music2 = v.findViewById(R.id.Music2);
        Music2.setOnClickListener(this);
        final ImageButton Music3 = v.findViewById(R.id.Music3);
        Music3.setOnClickListener(this);
        final ImageButton Music4 = v.findViewById(R.id.Music4);
        Music4.setOnClickListener(this);
        final ImageButton Music5 = v.findViewById(R.id.Music5);
        Music5.setOnClickListener(this);
        final ImageButton Music6 = v.findViewById(R.id.Music6);
        Music6.setOnClickListener(this);
        final ImageButton Music7 = v.findViewById(R.id.Music7);
        Music7.setOnClickListener(this);
        final ImageButton Music8 = v.findViewById(R.id.Music8);
        Music8.setOnClickListener(this);
        final ImageButton Music9 = v.findViewById(R.id.Music9);
        Music9.setOnClickListener(this);
        final ImageButton Music10 = v.findViewById(R.id.Music10);
        Music10.setOnClickListener(this);
        //END OF Music
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //START VideoGames
        final ImageButton VideoGames1 = v.findViewById(R.id.VideoGames1);
        VideoGames1.setOnClickListener(this);
        final ImageButton VideoGames2 = v.findViewById(R.id.VideoGames2);
        VideoGames2.setOnClickListener(this);
        final ImageButton VideoGames3 = v.findViewById(R.id.VideoGames3);
        VideoGames3.setOnClickListener(this);
        final ImageButton VideoGames4 = v.findViewById(R.id.VideoGames4);
        VideoGames4.setOnClickListener(this);
        final ImageButton VideoGames5 = v.findViewById(R.id.VideoGames5);
        VideoGames5.setOnClickListener(this);
        final ImageButton VideoGames6 = v.findViewById(R.id.VideoGames6);
        VideoGames6.setOnClickListener(this);
        final ImageButton VideoGames7 = v.findViewById(R.id.VideoGames7);
        VideoGames7.setOnClickListener(this);
        final ImageButton VideoGames8 = v.findViewById(R.id.VideoGames8);
        VideoGames8.setOnClickListener(this);
        final ImageButton VideoGames9 = v.findViewById(R.id.VideoGames9);
        VideoGames9.setOnClickListener(this);
        final ImageButton VideoGames10 = v.findViewById(R.id.VideoGames10);
        VideoGames10.setOnClickListener(this);
        //END OF VideoGames
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //START Novels
        final ImageButton Novels1 = v.findViewById(R.id.Novels1);
        Novels1.setOnClickListener(this);
        final ImageButton Novels2 = v.findViewById(R.id.Novels2);
        Novels2.setOnClickListener(this);
        final ImageButton Novels3 = v.findViewById(R.id.Novels3);
        Novels3.setOnClickListener(this);
        final ImageButton Novels4 = v.findViewById(R.id.Novels4);
        Novels4.setOnClickListener(this);
        final ImageButton Novels5 = v.findViewById(R.id.Novels5);
        Novels5.setOnClickListener(this);
        final ImageButton Novels6 = v.findViewById(R.id.Novels6);
        Novels6.setOnClickListener(this);
        final ImageButton Novels7 = v.findViewById(R.id.Novels7);
        Novels7.setOnClickListener(this);
        final ImageButton Novels8 = v.findViewById(R.id.Novels8);
        Novels8.setOnClickListener(this);
        final ImageButton Novels9 = v.findViewById(R.id.Novels9);
        Novels9.setOnClickListener(this);
        final ImageButton Novels10 = v.findViewById(R.id.Novels10);
        Novels10.setOnClickListener(this);
        //END OF Novels
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