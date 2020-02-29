package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class BookPageFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){
        //NEED TO CHANGE ONCLICK LATER SO SENDS TO ACTUAL REVIEW FOR SPECIFIC
        //BUTTON/IMG BUTTON
        View v = in.inflate(R.layout.activity_books, container, false);

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

        //START SCIFI
        final ImageButton SciFi1 = v.findViewById(R.id.SciFi1);
        SciFi1.setOnClickListener(this);
        final ImageButton SciFi2 = v.findViewById(R.id.SciFi2);
        SciFi2.setOnClickListener(this);
        final ImageButton SciFi3 = v.findViewById(R.id.SciFi3);
        SciFi3.setOnClickListener(this);
        final ImageButton SciFi4 = v.findViewById(R.id.SciFi4);
        SciFi4.setOnClickListener(this);
        final ImageButton SciFi5 = v.findViewById(R.id.SciFi5);
        SciFi5.setOnClickListener(this);
        final ImageButton SciFi6 = v.findViewById(R.id.SciFi6);
        SciFi6.setOnClickListener(this);
        final ImageButton SciFi7 = v.findViewById(R.id.SciFi7);
        SciFi7.setOnClickListener(this);
        final ImageButton SciFi8 = v.findViewById(R.id.SciFi8);
        SciFi8.setOnClickListener(this);
        final ImageButton SciFi9 = v.findViewById(R.id.SciFi9);
        SciFi9.setOnClickListener(this);
        final ImageButton SciFi10 = v.findViewById(R.id.SciFi10);
        SciFi10.setOnClickListener(this);
        //END OF SCIFI
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

        //START DRAMA
        final ImageButton Drama1 = v.findViewById(R.id.Drama1);
        Drama1.setOnClickListener(this);
        final ImageButton Drama2 = v.findViewById(R.id.Drama2);
        Drama2.setOnClickListener(this);
        final ImageButton Drama3 = v.findViewById(R.id.Drama3);
        Drama3.setOnClickListener(this);
        final ImageButton Drama4 = v.findViewById(R.id.Drama4);
        Drama4.setOnClickListener(this);
        final ImageButton Drama5 = v.findViewById(R.id.Drama5);
        Drama5.setOnClickListener(this);
        final ImageButton Drama6 = v.findViewById(R.id.Drama6);
        Drama6.setOnClickListener(this);
        final ImageButton Drama7 = v.findViewById(R.id.Drama7);
        Drama7.setOnClickListener(this);
        final ImageButton Drama8 = v.findViewById(R.id.Drama8);
        Drama8.setOnClickListener(this);
        final ImageButton Drama9 = v.findViewById(R.id.Drama9);
        Drama9.setOnClickListener(this);
        final ImageButton Drama10 = v.findViewById(R.id.Drama10);
        Drama10.setOnClickListener(this);
        //END OF DRAMA
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //START ROMANCE
        final ImageButton Romance1 = v.findViewById(R.id.Romance1);
        Romance1.setOnClickListener(this);
        final ImageButton Romance2 = v.findViewById(R.id.Romance2);
        Romance2.setOnClickListener(this);
        final ImageButton Romance3 = v.findViewById(R.id.Romance3);
        Romance3.setOnClickListener(this);
        final ImageButton Romance4 = v.findViewById(R.id.Romance4);
        Romance4.setOnClickListener(this);
        final ImageButton Romance5 = v.findViewById(R.id.Romance5);
        Romance5.setOnClickListener(this);
        final ImageButton Romance6 = v.findViewById(R.id.Romance6);
        Romance6.setOnClickListener(this);
        final ImageButton Romance7 = v.findViewById(R.id.Romance7);
        Romance7.setOnClickListener(this);
        final ImageButton Romance8 = v.findViewById(R.id.Romance8);
        Romance8.setOnClickListener(this);
        final ImageButton Romance9 = v.findViewById(R.id.Romance9);
        Romance9.setOnClickListener(this);
        final ImageButton Romance10 = v.findViewById(R.id.Romance10);
        Romance10.setOnClickListener(this);
        //END OF ROMANCE
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //HORROR START
        final ImageButton Horror1 = v.findViewById(R.id.Horror1);
        Horror1.setOnClickListener(this);
        final ImageButton Horror2 = v.findViewById(R.id.Horror2);
        Horror2.setOnClickListener(this);
        final ImageButton Horror3 = v.findViewById(R.id.Horror3);
        Horror3.setOnClickListener(this);
        final ImageButton Horror4 = v.findViewById(R.id.Horror4);
        Horror4.setOnClickListener(this);
        final ImageButton Horror5 = v.findViewById(R.id.Horror5);
        Horror5.setOnClickListener(this);
        final ImageButton Horror6 = v.findViewById(R.id.Horror6);
        Horror6.setOnClickListener(this);
        final ImageButton Horror7 = v.findViewById(R.id.Horror7);
        Horror7.setOnClickListener(this);
        final ImageButton Horror8 = v.findViewById(R.id.Horror8);
        Horror8.setOnClickListener(this);
        final ImageButton Horror9 = v.findViewById(R.id.Horror9);
        Horror9.setOnClickListener(this);
        final ImageButton Horror10 = v.findViewById(R.id.Horror10);
        Horror10.setOnClickListener(this);
        //END OF HORROR
        //EXPAND LATER? CHANGE THE MEDIAFRAG

        //START ANIME
        final ImageButton Anime1 = v.findViewById(R.id.Anime1);
        Anime1.setOnClickListener(this);
        final ImageButton Anime2 = v.findViewById(R.id.Anime2);
        Anime2.setOnClickListener(this);
        final ImageButton Anime3 = v.findViewById(R.id.Anime3);
        Anime3.setOnClickListener(this);
        final ImageButton Anime4 = v.findViewById(R.id.Anime4);
        Anime4.setOnClickListener(this);
        final ImageButton Anime5 = v.findViewById(R.id.Anime5);
        Anime5.setOnClickListener(this);
        final ImageButton Anime6 = v.findViewById(R.id.Anime6);
        Anime6.setOnClickListener(this);
        final ImageButton Anime7 = v.findViewById(R.id.Anime7);
        Anime7.setOnClickListener(this);
        final ImageButton Anime8 = v.findViewById(R.id.Anime8);
        Anime8.setOnClickListener(this);
        final ImageButton Anime9 = v.findViewById(R.id.Anime9);
        Anime9.setOnClickListener(this);
        final ImageButton Anime10 = v.findViewById(R.id.Anime10);
        Anime10.setOnClickListener(this);
        //END OF ANIME
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