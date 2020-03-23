package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.developersOfTheMillennium.motm.utils.GetMediaIDs;
import com.developersOfTheMillennium.motm.utils.GetPicture;

import org.json.JSONArray;
import org.json.JSONObject;

public class GamePageFragment extends Fragment implements View.OnClickListener, FragmentChangeListener {

    private FragmentManager fragmentManager = getFragmentManager();
    private ImageButton[] TrendingButtons = new ImageButton[10];
    private ImageButton[] ActionButtons = new ImageButton[10];
    private ImageButton[] AdventureButtons = new ImageButton[10];
    private ImageButton[] RpgButtons = new ImageButton[10];
    private ImageButton[] FpsButtons = new ImageButton[10];
    private ImageButton[] SportsButtons = new ImageButton[10];
    private ImageButton[] StrategyButtons = new ImageButton[10];

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){
        //NEED TO CHANGE ONCLICK LATER SO SENDS TO ACTUAL REVIEW FOR SPECIFIC
        //BUTTON/IMG BUTTON
        View v = in.inflate(R.layout.activity_games, container, false);

        final ImageButton Trending1 = v.findViewById(R.id.Trending1);
        Trending1.setOnClickListener(this);
        TrendingButtons[0] = Trending1;
        final ImageButton Trending2 = v.findViewById(R.id.Trending2);
        Trending2.setOnClickListener(this);
        TrendingButtons[1] = Trending2;
        final ImageButton Trending3 = v.findViewById(R.id.Trending3);
        Trending3.setOnClickListener(this);
        TrendingButtons[2] = Trending3;
        final ImageButton Trending4 = v.findViewById(R.id.Trending4);
        Trending4.setOnClickListener(this);
        TrendingButtons[3] = Trending4;
        final ImageButton Trending5 = v.findViewById(R.id.Trending5);
        Trending5.setOnClickListener(this);
        TrendingButtons[4] = Trending5;
        final ImageButton Trending6 = v.findViewById(R.id.Trending6);
        Trending6.setOnClickListener(this);
        TrendingButtons[5] = Trending6;
        final ImageButton Trending7 = v.findViewById(R.id.Trending7);
        Trending7.setOnClickListener(this);
        TrendingButtons[6] = Trending7;
        final ImageButton Trending8 = v.findViewById(R.id.Trending8);
        Trending8.setOnClickListener(this);
        TrendingButtons[7] = Trending8;
        final ImageButton Trending9 = v.findViewById(R.id.Trending9);
        Trending9.setOnClickListener(this);
        TrendingButtons[8] = Trending9;
        final ImageButton Trending10 = v.findViewById(R.id.Trending10);
        Trending10.setOnClickListener(this);
        TrendingButtons[9] = Trending10;
        //END OF TRENDING

        //ACTION START
        final ImageButton Action1 = v.findViewById(R.id.Action1);
        Action1.setOnClickListener(this);
        ActionButtons[0] = Action1;
        final ImageButton Action2 = v.findViewById(R.id.Action2);
        Action2.setOnClickListener(this);
        ActionButtons[1] = Action2;
        final ImageButton Action3 = v.findViewById(R.id.Action3);
        Action3.setOnClickListener(this);
        ActionButtons[2] = Action3;
        final ImageButton Action4 = v.findViewById(R.id.Action4);
        Action4.setOnClickListener(this);
        ActionButtons[3] = Action4;
        final ImageButton Action5 = v.findViewById(R.id.Action5);
        Action5.setOnClickListener(this);
        ActionButtons[4] = Action5;
        final ImageButton Action6 = v.findViewById(R.id.Action6);
        Action6.setOnClickListener(this);
        ActionButtons[5] = Action6;
        final ImageButton Action7 = v.findViewById(R.id.Action7);
        Action7.setOnClickListener(this);
        ActionButtons[6] = Action7;
        final ImageButton Action8 = v.findViewById(R.id.Action8);
        Action8.setOnClickListener(this);
        ActionButtons[7] = Action8;
        final ImageButton Action9 = v.findViewById(R.id.Action9);
        Action9.setOnClickListener(this);
        ActionButtons[8] = Action9;
        final ImageButton Action10 = v.findViewById(R.id.Action10);
        Action10.setOnClickListener(this);
        ActionButtons[9] = Action10;
        //END OF ACTION

        //START ADVENTURE
        final ImageButton Adventure1 = v.findViewById(R.id.Adventure1);
        Adventure1.setOnClickListener(this);
        AdventureButtons[0] = Adventure1;
        final ImageButton Adventure2 = v.findViewById(R.id.Adventure2);
        Adventure2.setOnClickListener(this);
        AdventureButtons[1] = Adventure2;
        final ImageButton Adventure3 = v.findViewById(R.id.Adventure3);
        Adventure3.setOnClickListener(this);
        AdventureButtons[2] = Adventure3;
        final ImageButton Adventure4 = v.findViewById(R.id.Adventure4);
        Adventure4.setOnClickListener(this);
        AdventureButtons[3] = Adventure4;
        final ImageButton Adventure5 = v.findViewById(R.id.Adventure5);
        Adventure5.setOnClickListener(this);
        AdventureButtons[4] = Adventure5;
        final ImageButton Adventure6 = v.findViewById(R.id.Adventure6);
        Adventure6.setOnClickListener(this);
        AdventureButtons[5] = Adventure6;
        final ImageButton Adventure7 = v.findViewById(R.id.Adventure7);
        Adventure7.setOnClickListener(this);
        AdventureButtons[6] = Adventure7;
        final ImageButton Adventure8 = v.findViewById(R.id.Adventure8);
        Adventure8.setOnClickListener(this);
        AdventureButtons[7] = Adventure8;
        final ImageButton Adventure9 = v.findViewById(R.id.Adventure9);
        Adventure9.setOnClickListener(this);
        AdventureButtons[8] = Adventure9;
        final ImageButton Adventure10 = v.findViewById(R.id.Adventure10);
        Adventure10.setOnClickListener(this);
        AdventureButtons[9] = Adventure10;
        //END OF ADVENTURE

        //START RPG
        final ImageButton Rpg1 = v.findViewById(R.id.Rpg1);
        Rpg1.setOnClickListener(this);
        RpgButtons[0] = Rpg1;
        final ImageButton Rpg2 = v.findViewById(R.id.Rpg2);
        Rpg2.setOnClickListener(this);
        RpgButtons[1] = Rpg2;
        final ImageButton Rpg3 = v.findViewById(R.id.Rpg3);
        Rpg3.setOnClickListener(this);
        RpgButtons[2] = Rpg3;
        final ImageButton Rpg4 = v.findViewById(R.id.Rpg4);
        Rpg4.setOnClickListener(this);
        RpgButtons[3] = Rpg4;
        final ImageButton Rpg5 = v.findViewById(R.id.Rpg5);
        Rpg5.setOnClickListener(this);
        RpgButtons[4] = Rpg5;
        final ImageButton Rpg6 = v.findViewById(R.id.Rpg6);
        Rpg6.setOnClickListener(this);
        RpgButtons[5] = Rpg6;
        final ImageButton Rpg7 = v.findViewById(R.id.Rpg7);
        Rpg7.setOnClickListener(this);
        RpgButtons[6] = Rpg7;
        final ImageButton Rpg8 = v.findViewById(R.id.Rpg8);
        Rpg8.setOnClickListener(this);
        RpgButtons[7] = Rpg8;
        final ImageButton Rpg9 = v.findViewById(R.id.Rpg9);
        Rpg9.setOnClickListener(this);
        RpgButtons[8] = Rpg9;
        final ImageButton Rpg10 = v.findViewById(R.id.Rpg10);
        Rpg10.setOnClickListener(this);
        RpgButtons[9] = Rpg10;
        //END OF RPG

        //START FPS
        final ImageButton Fps1 = v.findViewById(R.id.Fps1);
        Fps1.setOnClickListener(this);
        FpsButtons[0] = Fps1;
        final ImageButton Fps2 = v.findViewById(R.id.Fps2);
        Fps2.setOnClickListener(this);
        FpsButtons[1] = Fps2;
        final ImageButton Fps3 = v.findViewById(R.id.Fps3);
        Fps3.setOnClickListener(this);
        FpsButtons[2] = Fps3;
        final ImageButton Fps4 = v.findViewById(R.id.Fps4);
        Fps4.setOnClickListener(this);
        FpsButtons[3] = Fps4;
        final ImageButton Fps5 = v.findViewById(R.id.Fps5);
        Fps5.setOnClickListener(this);
        FpsButtons[4] = Fps5;
        final ImageButton Fps6 = v.findViewById(R.id.Fps6);
        Fps6.setOnClickListener(this);
        FpsButtons[5] = Fps6;
        final ImageButton Fps7 = v.findViewById(R.id.Fps7);
        Fps7.setOnClickListener(this);
        FpsButtons[6] = Fps7;
        final ImageButton Fps8 = v.findViewById(R.id.Fps8);
        Fps8.setOnClickListener(this);
        FpsButtons[7] = Fps8;
        final ImageButton Fps9 = v.findViewById(R.id.Fps9);
        Fps9.setOnClickListener(this);
        FpsButtons[8] = Fps9;
        final ImageButton Fps10 = v.findViewById(R.id.Fps10);
        Fps10.setOnClickListener(this);
        FpsButtons[9] = Fps10;
        //END OF FPS

        //START SPORTS
        final ImageButton Sports1 = v.findViewById(R.id.Sports1);
        Sports1.setOnClickListener(this);
        SportsButtons[0] = Sports1;
        final ImageButton Sports2 = v.findViewById(R.id.Sports2);
        Sports2.setOnClickListener(this);
        SportsButtons[1] = Sports2;
        final ImageButton Sports3 = v.findViewById(R.id.Sports3);
        Sports3.setOnClickListener(this);
        SportsButtons[2] = Sports3;
        final ImageButton Sports4 = v.findViewById(R.id.Sports4);
        Sports4.setOnClickListener(this);
        SportsButtons[3] = Sports4;
        final ImageButton Sports5 = v.findViewById(R.id.Sports5);
        Sports5.setOnClickListener(this);
        SportsButtons[4] = Sports5;
        final ImageButton Sports6 = v.findViewById(R.id.Sports6);
        Sports6.setOnClickListener(this);
        SportsButtons[5] = Sports6;
        final ImageButton Sports7 = v.findViewById(R.id.Sports7);
        Sports7.setOnClickListener(this);
        SportsButtons[6] = Sports7;
        final ImageButton Sports8 = v.findViewById(R.id.Sports8);
        Sports8.setOnClickListener(this);
        SportsButtons[7] = Sports8;
        final ImageButton Sports9 = v.findViewById(R.id.Sports9);
        Sports9.setOnClickListener(this);
        SportsButtons[8] = Sports9;
        final ImageButton Sports10 = v.findViewById(R.id.Sports10);
        Sports10.setOnClickListener(this);
        SportsButtons[9] = Sports10;
        //END OF SPORTS

        //START STRATEGY
        final ImageButton Strategy1 = v.findViewById(R.id.Strategy1);
        Strategy1.setOnClickListener(this);
        StrategyButtons[0] = Strategy1;
        final ImageButton Strategy2 = v.findViewById(R.id.Strategy2);
        Strategy2.setOnClickListener(this);
        StrategyButtons[1] = Strategy2;
        final ImageButton Strategy3 = v.findViewById(R.id.Strategy3);
        Strategy3.setOnClickListener(this);
        StrategyButtons[2] = Strategy3;
        final ImageButton Strategy4 = v.findViewById(R.id.Strategy4);
        Strategy4.setOnClickListener(this);
        StrategyButtons[3] = Strategy4;
        final ImageButton Strategy5 = v.findViewById(R.id.Strategy5);
        Strategy5.setOnClickListener(this);
        StrategyButtons[4] = Strategy5;
        final ImageButton Strategy6 = v.findViewById(R.id.Strategy6);
        Strategy6.setOnClickListener(this);
        StrategyButtons[5] = Strategy6;
        final ImageButton Strategy7 = v.findViewById(R.id.Strategy7);
        Strategy7.setOnClickListener(this);
        StrategyButtons[6] = Strategy7;
        final ImageButton Strategy8 = v.findViewById(R.id.Strategy8);
        Strategy8.setOnClickListener(this);
        StrategyButtons[7] = Strategy8;
        final ImageButton Strategy9 = v.findViewById(R.id.Strategy9);
        Strategy9.setOnClickListener(this);
        StrategyButtons[8] = Strategy9;
        final ImageButton Strategy10 = v.findViewById(R.id.Strategy10);
        Strategy10.setOnClickListener(this);
        StrategyButtons[9] = Strategy10;
        //END OF STRATEGY
        return v;
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstance) {
        //Currently freezes and doesn't load until all retrieve and display is done
        retrieveAndDisplay(TrendingButtons, "trending", "videogame", "getTopRatedMedia");
        retrieveAndDisplay(ActionButtons, "action", "videogame", "getMediaByGenreAndType");
        retrieveAndDisplay(AdventureButtons, "adventure", "videogame", "getMediaByGenreAndType");
        retrieveAndDisplay(RpgButtons, "rpg", "videogame", "getMediaByGenreAndType");
        retrieveAndDisplay(FpsButtons, "fps", "videogame", "getMediaByGenreAndType");
        retrieveAndDisplay(SportsButtons, "sports", "videogame", "getMediaByGenreAndType");
        retrieveAndDisplay(StrategyButtons, "strategy", "videogame", "getMediaByGenreAndType");
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_view, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

    private void retrieveAndDisplay(ImageButton[] genreButtons, String genre, String mediaType, String requestType) {
        //Retrieve mediaIds
        JSONArray genreArray = null;
        try {
            genreArray = getMediaIDs(mediaType, requestType, genre);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(genreArray != null) {
            //iterate through the list then start setting tags and getpicture
            for (int i = 0; i < genreArray.length(); i++) {
                try {
                    Integer mediaID = (Integer) genreArray.get(i);
                    genreButtons[i].setTag(mediaID);
                    getPicture(Integer.toString(mediaID), genreButtons[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            //ERROR WITH RETRIEVAL OF MEDIA IDS leaves button and transition blank
            Log.i("Retrieving media IDs", "--Error");
        }
    }

    private JSONArray getMediaIDs(String mediaType, String requestType, String genre) throws Exception{
        try {
            JSONObject result = new GetMediaIDs((MainActivity) getActivity()).execute(mediaType, requestType, genre).get();
            return result.getJSONArray("mediaIDs");
            //GetMediaIDs IDs = (GetMediaIDs) new GetMediaIDs((MainActivity) getActivity()).execute(mediaType, array).get();
        } catch (Exception e) {
            throw new Exception("(getMediaIDs) -- something went wrong when retrieving mediaIDs");
        }
    }

    private void getPicture(String mediaId, ImageButton imgButton) throws Exception{
        try {
            GetPicture pic = (GetPicture) new GetPicture((MainActivity) getActivity()).execute(mediaId, imgButton);
        } catch (Exception e) {
            throw new Exception("(getPicture) -- something went wrong when retrieving picture");
        }
    }

    @Override
    public void onClick(View v) {
        //Take to the Proper Review
        //MIGHT NEED TO ADD CASE SWITCH FOR EACH BUTTON CREATE FRAG DEPENDING ON WHAT ITS ID IS
        Object id = v.getTag();
        Bundle args = new Bundle();
        MediaProfilePageFragment Frag = new MediaProfilePageFragment();
        if(id != null) {
            args.putInt("mediaID", (Integer) id);
            Frag.setArguments(args);
        }
        ((MainActivity)getActivity()).replaceFragment(Frag);
    }
}