package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import com.developersOfTheMillennium.motm.utils.RetrieveAndDisplay;

public class GamePageFragment extends Fragment {

    private ImageButton[] TrendingButtons = new ImageButton[10];
    private ImageButton[] ActionButtons = new ImageButton[10];
    private ImageButton[] AdventureButtons = new ImageButton[10];
    private ImageButton[] RpgButtons = new ImageButton[10];
    private ImageButton[] FpsButtons = new ImageButton[10];
    private ImageButton[] SportsButtons = new ImageButton[10];
    private ImageButton[] StrategyButtons = new ImageButton[10];

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){
        View v = in.inflate(R.layout.activity_games, container, false);

        final ImageButton Trending1 = v.findViewById(R.id.Trending1);
        TrendingButtons[0] = Trending1;
        final ImageButton Trending2 = v.findViewById(R.id.Trending2);
        TrendingButtons[1] = Trending2;
        final ImageButton Trending3 = v.findViewById(R.id.Trending3);
        TrendingButtons[2] = Trending3;
        final ImageButton Trending4 = v.findViewById(R.id.Trending4);
        TrendingButtons[3] = Trending4;
        final ImageButton Trending5 = v.findViewById(R.id.Trending5);
        TrendingButtons[4] = Trending5;
        final ImageButton Trending6 = v.findViewById(R.id.Trending6);
        TrendingButtons[5] = Trending6;
        final ImageButton Trending7 = v.findViewById(R.id.Trending7);
        TrendingButtons[6] = Trending7;
        final ImageButton Trending8 = v.findViewById(R.id.Trending8);
        TrendingButtons[7] = Trending8;
        final ImageButton Trending9 = v.findViewById(R.id.Trending9);
        TrendingButtons[8] = Trending9;
        final ImageButton Trending10 = v.findViewById(R.id.Trending10);
        TrendingButtons[9] = Trending10;
        //END OF TRENDING

        //ACTION START
        final ImageButton Action1 = v.findViewById(R.id.Action1);
        ActionButtons[0] = Action1;
        final ImageButton Action2 = v.findViewById(R.id.Action2);
        ActionButtons[1] = Action2;
        final ImageButton Action3 = v.findViewById(R.id.Action3);
        ActionButtons[2] = Action3;
        final ImageButton Action4 = v.findViewById(R.id.Action4);
        ActionButtons[3] = Action4;
        final ImageButton Action5 = v.findViewById(R.id.Action5);
        ActionButtons[4] = Action5;
        final ImageButton Action6 = v.findViewById(R.id.Action6);
        ActionButtons[5] = Action6;
        final ImageButton Action7 = v.findViewById(R.id.Action7);
        ActionButtons[6] = Action7;
        final ImageButton Action8 = v.findViewById(R.id.Action8);
        ActionButtons[7] = Action8;
        final ImageButton Action9 = v.findViewById(R.id.Action9);
        ActionButtons[8] = Action9;
        final ImageButton Action10 = v.findViewById(R.id.Action10);
        ActionButtons[9] = Action10;
        //END OF ACTION

        //START ADVENTURE
        final ImageButton Adventure1 = v.findViewById(R.id.Adventure1);
        AdventureButtons[0] = Adventure1;
        final ImageButton Adventure2 = v.findViewById(R.id.Adventure2);
        AdventureButtons[1] = Adventure2;
        final ImageButton Adventure3 = v.findViewById(R.id.Adventure3);
        AdventureButtons[2] = Adventure3;
        final ImageButton Adventure4 = v.findViewById(R.id.Adventure4);
        AdventureButtons[3] = Adventure4;
        final ImageButton Adventure5 = v.findViewById(R.id.Adventure5);
        AdventureButtons[4] = Adventure5;
        final ImageButton Adventure6 = v.findViewById(R.id.Adventure6);
        AdventureButtons[5] = Adventure6;
        final ImageButton Adventure7 = v.findViewById(R.id.Adventure7);
        AdventureButtons[6] = Adventure7;
        final ImageButton Adventure8 = v.findViewById(R.id.Adventure8);
        AdventureButtons[7] = Adventure8;
        final ImageButton Adventure9 = v.findViewById(R.id.Adventure9);
        AdventureButtons[8] = Adventure9;
        final ImageButton Adventure10 = v.findViewById(R.id.Adventure10);
        AdventureButtons[9] = Adventure10;
        //END OF ADVENTURE

        //START RPG
        final ImageButton Rpg1 = v.findViewById(R.id.Rpg1);
        RpgButtons[0] = Rpg1;
        final ImageButton Rpg2 = v.findViewById(R.id.Rpg2);
        RpgButtons[1] = Rpg2;
        final ImageButton Rpg3 = v.findViewById(R.id.Rpg3);
        RpgButtons[2] = Rpg3;
        final ImageButton Rpg4 = v.findViewById(R.id.Rpg4);
        RpgButtons[3] = Rpg4;
        final ImageButton Rpg5 = v.findViewById(R.id.Rpg5);
        RpgButtons[4] = Rpg5;
        final ImageButton Rpg6 = v.findViewById(R.id.Rpg6);
        RpgButtons[5] = Rpg6;
        final ImageButton Rpg7 = v.findViewById(R.id.Rpg7);
        RpgButtons[6] = Rpg7;
        final ImageButton Rpg8 = v.findViewById(R.id.Rpg8);
        RpgButtons[7] = Rpg8;
        final ImageButton Rpg9 = v.findViewById(R.id.Rpg9);
        RpgButtons[8] = Rpg9;
        final ImageButton Rpg10 = v.findViewById(R.id.Rpg10);
        RpgButtons[9] = Rpg10;
        //END OF RPG

        //START FPS
        final ImageButton Fps1 = v.findViewById(R.id.Fps1);
        FpsButtons[0] = Fps1;
        final ImageButton Fps2 = v.findViewById(R.id.Fps2);
        FpsButtons[1] = Fps2;
        final ImageButton Fps3 = v.findViewById(R.id.Fps3);
        FpsButtons[2] = Fps3;
        final ImageButton Fps4 = v.findViewById(R.id.Fps4);
        FpsButtons[3] = Fps4;
        final ImageButton Fps5 = v.findViewById(R.id.Fps5);
        FpsButtons[4] = Fps5;
        final ImageButton Fps6 = v.findViewById(R.id.Fps6);
        FpsButtons[5] = Fps6;
        final ImageButton Fps7 = v.findViewById(R.id.Fps7);
        FpsButtons[6] = Fps7;
        final ImageButton Fps8 = v.findViewById(R.id.Fps8);
        FpsButtons[7] = Fps8;
        final ImageButton Fps9 = v.findViewById(R.id.Fps9);
        FpsButtons[8] = Fps9;
        final ImageButton Fps10 = v.findViewById(R.id.Fps10);
        FpsButtons[9] = Fps10;
        //END OF FPS

        //START SPORTS
        final ImageButton Sports1 = v.findViewById(R.id.Sports1);
        SportsButtons[0] = Sports1;
        final ImageButton Sports2 = v.findViewById(R.id.Sports2);
        SportsButtons[1] = Sports2;
        final ImageButton Sports3 = v.findViewById(R.id.Sports3);
        SportsButtons[2] = Sports3;
        final ImageButton Sports4 = v.findViewById(R.id.Sports4);
        SportsButtons[3] = Sports4;
        final ImageButton Sports5 = v.findViewById(R.id.Sports5);
        SportsButtons[4] = Sports5;
        final ImageButton Sports6 = v.findViewById(R.id.Sports6);
        SportsButtons[5] = Sports6;
        final ImageButton Sports7 = v.findViewById(R.id.Sports7);
        SportsButtons[6] = Sports7;
        final ImageButton Sports8 = v.findViewById(R.id.Sports8);
        SportsButtons[7] = Sports8;
        final ImageButton Sports9 = v.findViewById(R.id.Sports9);
        SportsButtons[8] = Sports9;
        final ImageButton Sports10 = v.findViewById(R.id.Sports10);
        SportsButtons[9] = Sports10;
        //END OF SPORTS

        //START STRATEGY
        final ImageButton Strategy1 = v.findViewById(R.id.Strategy1);
        StrategyButtons[0] = Strategy1;
        final ImageButton Strategy2 = v.findViewById(R.id.Strategy2);
        StrategyButtons[1] = Strategy2;
        final ImageButton Strategy3 = v.findViewById(R.id.Strategy3);
        StrategyButtons[2] = Strategy3;
        final ImageButton Strategy4 = v.findViewById(R.id.Strategy4);
        StrategyButtons[3] = Strategy4;
        final ImageButton Strategy5 = v.findViewById(R.id.Strategy5);
        StrategyButtons[4] = Strategy5;
        final ImageButton Strategy6 = v.findViewById(R.id.Strategy6);
        StrategyButtons[5] = Strategy6;
        final ImageButton Strategy7 = v.findViewById(R.id.Strategy7);
        StrategyButtons[6] = Strategy7;
        final ImageButton Strategy8 = v.findViewById(R.id.Strategy8);
        StrategyButtons[7] = Strategy8;
        final ImageButton Strategy9 = v.findViewById(R.id.Strategy9);
        StrategyButtons[8] = Strategy9;
        final ImageButton Strategy10 = v.findViewById(R.id.Strategy10);
        StrategyButtons[9] = Strategy10;
        //END OF STRATEGY
        return v;
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstance) {
        retrieveAndDisplay(TrendingButtons, "trending", "videogame", "getTopRatedMedia");
        retrieveAndDisplay(ActionButtons, "action", "videogame", "getMediaByGenreAndType");
        retrieveAndDisplay(AdventureButtons, "adventure", "videogame", "getMediaByGenreAndType");
        retrieveAndDisplay(RpgButtons, "rpg", "videogame", "getMediaByGenreAndType");
        retrieveAndDisplay(FpsButtons, "fps", "videogame", "getMediaByGenreAndType");
        retrieveAndDisplay(SportsButtons, "sports", "videogame", "getMediaByGenreAndType");
        retrieveAndDisplay(StrategyButtons, "strategy", "videogame", "getMediaByGenreAndType");
    }

    private void retrieveAndDisplay(ImageButton[] genreButtons, String genre, String mediaType, String requestType) {
        RetrieveAndDisplay retrieveAndDisplay = (RetrieveAndDisplay) new RetrieveAndDisplay((MainActivity) getActivity()).execute(genreButtons, genre, mediaType, requestType);
    }
}