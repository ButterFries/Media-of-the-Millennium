package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.developersOfTheMillennium.motm.utils.GetMediaIDs;
import com.developersOfTheMillennium.motm.utils.GetPicture;

import org.json.JSONArray;
import org.json.JSONObject;

public class TopRatedMediaPageFragment extends Fragment implements View.OnClickListener {

    private ImageButton[] novelButtons = new ImageButton[10];
    private ImageButton[] videogameButtons = new ImageButton[10];
    private ImageButton[] musicButtons = new ImageButton[10];
    private ImageButton[] tvButtons = new ImageButton[10];
    private ImageButton[] movieButtons = new ImageButton[10];

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){
        //NEED TO CHANGE ONCLICK LATER SO SENDS TO ACTUAL REVIEW FOR SPECIFIC
        //BUTTON/IMG BUTTON
        View v = in.inflate(R.layout.activity_top_rated_media, container, false);

        final ImageButton Movies1 = v.findViewById(R.id.Movies1);
        Movies1.setOnClickListener(this);
        movieButtons[0] = Movies1;
        final ImageButton Movies2 = v.findViewById(R.id.Movies2);
        Movies2.setOnClickListener(this);
        movieButtons[1] = Movies2;
        final ImageButton Movies3 = v.findViewById(R.id.Movies3);
        Movies3.setOnClickListener(this);
        movieButtons[2] = Movies3;
        final ImageButton Movies4 = v.findViewById(R.id.Movies4);
        Movies4.setOnClickListener(this);
        movieButtons[3] = Movies4;
        final ImageButton Movies5 = v.findViewById(R.id.Movies5);
        Movies5.setOnClickListener(this);
        movieButtons[4] = Movies5;
        final ImageButton Movies6 = v.findViewById(R.id.Movies6);
        Movies6.setOnClickListener(this);
        movieButtons[5] = Movies6;
        final ImageButton Movies7 = v.findViewById(R.id.Movies7);
        Movies7.setOnClickListener(this);
        movieButtons[6] = Movies7;
        final ImageButton Movies8 = v.findViewById(R.id.Movies8);
        Movies8.setOnClickListener(this);
        movieButtons[7] = Movies8;
        final ImageButton Movies9 = v.findViewById(R.id.Movies9);
        Movies9.setOnClickListener(this);
        movieButtons[8] = Movies9;
        final ImageButton Movies10 = v.findViewById(R.id.Movies10);
        Movies10.setOnClickListener(this);
        movieButtons[9] = Movies10;
        //END OF Movies

        //TVShows START
        final ImageButton TVShows1 = v.findViewById(R.id.TVShows1);
        TVShows1.setOnClickListener(this);
        tvButtons[0] = TVShows1;
        final ImageButton TVShows2 = v.findViewById(R.id.TVShows2);
        TVShows2.setOnClickListener(this);
        tvButtons[1] = TVShows2;
        final ImageButton TVShows3 = v.findViewById(R.id.TVShows3);
        TVShows3.setOnClickListener(this);
        tvButtons[2] = TVShows3;
        final ImageButton TVShows4 = v.findViewById(R.id.TVShows4);
        TVShows4.setOnClickListener(this);
        tvButtons[3] = TVShows4;
        final ImageButton TVShows5 = v.findViewById(R.id.TVShows5);
        TVShows5.setOnClickListener(this);
        tvButtons[4] = TVShows5;
        final ImageButton TVShows6 = v.findViewById(R.id.TVShows6);
        TVShows6.setOnClickListener(this);
        tvButtons[5] = TVShows6;
        final ImageButton TVShows7 = v.findViewById(R.id.TVShows7);
        TVShows7.setOnClickListener(this);
        tvButtons[6] = TVShows7;
        final ImageButton TVShows8 = v.findViewById(R.id.TVShows8);
        TVShows8.setOnClickListener(this);
        tvButtons[7] = TVShows8;
        final ImageButton TVShows9 = v.findViewById(R.id.TVShows9);
        TVShows9.setOnClickListener(this);
        tvButtons[8] = TVShows9;
        final ImageButton TVShows10 = v.findViewById(R.id.TVShows10);
        TVShows10.setOnClickListener(this);
        tvButtons[9] = TVShows10;
        //END OF TVShows

        //Music START
        final ImageButton Music1 = v.findViewById(R.id.Music1);
        Music1.setOnClickListener(this);
        musicButtons[0] = Music1;
        final ImageButton Music2 = v.findViewById(R.id.Music2);
        Music2.setOnClickListener(this);
        musicButtons[1] = Music2;
        final ImageButton Music3 = v.findViewById(R.id.Music3);
        Music3.setOnClickListener(this);
        musicButtons[2] = Music3;
        final ImageButton Music4 = v.findViewById(R.id.Music4);
        Music4.setOnClickListener(this);
        musicButtons[3] = Music4;
        final ImageButton Music5 = v.findViewById(R.id.Music5);
        Music5.setOnClickListener(this);
        musicButtons[4] = Music5;
        final ImageButton Music6 = v.findViewById(R.id.Music6);
        Music6.setOnClickListener(this);
        musicButtons[5] = Music6;
        final ImageButton Music7 = v.findViewById(R.id.Music7);
        Music7.setOnClickListener(this);
        musicButtons[6] = Music7;
        final ImageButton Music8 = v.findViewById(R.id.Music8);
        Music8.setOnClickListener(this);
        musicButtons[7] = Music8;
        final ImageButton Music9 = v.findViewById(R.id.Music9);
        Music9.setOnClickListener(this);
        musicButtons[8] = Music9;
        final ImageButton Music10 = v.findViewById(R.id.Music10);
        Music10.setOnClickListener(this);
        musicButtons[9] = Music10;
        //END OF Music

        //START VideoGames
        final ImageButton VideoGames1 = v.findViewById(R.id.VideoGames1);
        VideoGames1.setOnClickListener(this);
        videogameButtons[0] = VideoGames1;
        final ImageButton VideoGames2 = v.findViewById(R.id.VideoGames2);
        VideoGames2.setOnClickListener(this);
        videogameButtons[1] = VideoGames2;
        final ImageButton VideoGames3 = v.findViewById(R.id.VideoGames3);
        VideoGames3.setOnClickListener(this);
        videogameButtons[2] = VideoGames3;
        final ImageButton VideoGames4 = v.findViewById(R.id.VideoGames4);
        VideoGames4.setOnClickListener(this);
        videogameButtons[3] = VideoGames4;
        final ImageButton VideoGames5 = v.findViewById(R.id.VideoGames5);
        VideoGames5.setOnClickListener(this);
        videogameButtons[4] = VideoGames5;
        final ImageButton VideoGames6 = v.findViewById(R.id.VideoGames6);
        VideoGames6.setOnClickListener(this);
        videogameButtons[5] = VideoGames6;
        final ImageButton VideoGames7 = v.findViewById(R.id.VideoGames7);
        VideoGames7.setOnClickListener(this);
        videogameButtons[6] = VideoGames7;
        final ImageButton VideoGames8 = v.findViewById(R.id.VideoGames8);
        VideoGames8.setOnClickListener(this);
        videogameButtons[7] = VideoGames8;
        final ImageButton VideoGames9 = v.findViewById(R.id.VideoGames9);
        VideoGames9.setOnClickListener(this);
        videogameButtons[8] = VideoGames9;
        final ImageButton VideoGames10 = v.findViewById(R.id.VideoGames10);
        VideoGames10.setOnClickListener(this);
        videogameButtons[9] = VideoGames10;
        //END OF VideoGames

        //START Novels
        final ImageButton Novels1 = v.findViewById(R.id.Novels1);
        Novels1.setOnClickListener(this);
        novelButtons[0] = Novels1;
        final ImageButton Novels2 = v.findViewById(R.id.Novels2);
        Novels2.setOnClickListener(this);
        novelButtons[1] = Novels2;
        final ImageButton Novels3 = v.findViewById(R.id.Novels3);
        Novels3.setOnClickListener(this);
        novelButtons[2] = Novels3;
        final ImageButton Novels4 = v.findViewById(R.id.Novels4);
        Novels4.setOnClickListener(this);
        novelButtons[3] = Novels4;
        final ImageButton Novels5 = v.findViewById(R.id.Novels5);
        Novels5.setOnClickListener(this);
        novelButtons[4] = Novels5;
        final ImageButton Novels6 = v.findViewById(R.id.Novels6);
        Novels6.setOnClickListener(this);
        novelButtons[5] = Novels6;
        final ImageButton Novels7 = v.findViewById(R.id.Novels7);
        Novels7.setOnClickListener(this);
        novelButtons[6] = Novels7;
        final ImageButton Novels8 = v.findViewById(R.id.Novels8);
        Novels8.setOnClickListener(this);
        novelButtons[7] = Novels8;
        final ImageButton Novels9 = v.findViewById(R.id.Novels9);
        Novels9.setOnClickListener(this);
        novelButtons[8] = Novels9;
        final ImageButton Novels10 = v.findViewById(R.id.Novels10);
        Novels10.setOnClickListener(this);
        novelButtons[9] = Novels10;
        //END OF Novels

        return v;
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstance) {
        //Currently freezes and doesn't load until all retrieve and display is done
        retrieveAndDisplay(movieButtons,"cinema");
        retrieveAndDisplay(tvButtons,  "tvseries");
        retrieveAndDisplay(musicButtons,  "music");
        retrieveAndDisplay(videogameButtons,  "videogame");
        retrieveAndDisplay(novelButtons, "novel");
        return;
    }

    private void retrieveAndDisplay(ImageButton[] genreButtons, String mediaType) {
        //Retrieve mediaIds
        JSONArray genreArray = null;
        try {
            genreArray = getMediaIDs(mediaType, "getTopRatedMedia", "");
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

    private JSONArray getMediaIDs(String mediaType, String requestType, String genre) throws Exception{
        try {
            JSONObject result = new GetMediaIDs((MainActivity) getActivity()).execute(mediaType, requestType, "").get();
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

}