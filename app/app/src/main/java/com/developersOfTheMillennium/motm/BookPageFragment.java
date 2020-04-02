package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import com.developersOfTheMillennium.motm.utils.RetrieveAndDisplay;


public class BookPageFragment extends Fragment {

    private ImageButton[] TrendingButtons = new ImageButton[10];
    private ImageButton[] SciFiButtons = new ImageButton[10];
    private ImageButton[] ActionButtons = new ImageButton[10];
    private ImageButton[] DramaButtons = new ImageButton[10];
    private ImageButton[] RomanceButtons = new ImageButton[10];
    private ImageButton[] HorrorButtons = new ImageButton[10];
    private ImageButton[] AnimeButtons = new ImageButton[10];

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){
        View v = in.inflate(R.layout.activity_books, container, false);

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

        //START SCIFI
        final ImageButton SciFi1 = v.findViewById(R.id.SciFi1);
        SciFiButtons[0] = SciFi1;
        final ImageButton SciFi2 = v.findViewById(R.id.SciFi2);
        SciFiButtons[1] = SciFi2;
        final ImageButton SciFi3 = v.findViewById(R.id.SciFi3);
        SciFiButtons[2] = SciFi3;
        final ImageButton SciFi4 = v.findViewById(R.id.SciFi4);
        SciFiButtons[3] = SciFi4;
        final ImageButton SciFi5 = v.findViewById(R.id.SciFi5);
        SciFiButtons[4] = SciFi5;
        final ImageButton SciFi6 = v.findViewById(R.id.SciFi6);
        SciFiButtons[5] = SciFi6;
        final ImageButton SciFi7 = v.findViewById(R.id.SciFi7);
        SciFiButtons[6] = SciFi7;
        final ImageButton SciFi8 = v.findViewById(R.id.SciFi8);
        SciFiButtons[7] = SciFi8;
        final ImageButton SciFi9 = v.findViewById(R.id.SciFi9);
        SciFiButtons[8] = SciFi9;
        final ImageButton SciFi10 = v.findViewById(R.id.SciFi10);
        SciFiButtons[9] = SciFi10;
        //END OF SCIFI

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

        //START DRAMA
        final ImageButton Drama1 = v.findViewById(R.id.Drama1);
        DramaButtons[0] = Drama1;
        final ImageButton Drama2 = v.findViewById(R.id.Drama2);
        DramaButtons[1] = Drama2;
        final ImageButton Drama3 = v.findViewById(R.id.Drama3);
        DramaButtons[2] = Drama3;
        final ImageButton Drama4 = v.findViewById(R.id.Drama4);
        DramaButtons[3] = Drama4;
        final ImageButton Drama5 = v.findViewById(R.id.Drama5);
        DramaButtons[4] = Drama5;
        final ImageButton Drama6 = v.findViewById(R.id.Drama6);
        DramaButtons[5] = Drama6;
        final ImageButton Drama7 = v.findViewById(R.id.Drama7);
        DramaButtons[6] = Drama7;
        final ImageButton Drama8 = v.findViewById(R.id.Drama8);
        DramaButtons[7] = Drama8;
        final ImageButton Drama9 = v.findViewById(R.id.Drama9);
        DramaButtons[8] = Drama9;
        final ImageButton Drama10 = v.findViewById(R.id.Drama10);
        DramaButtons[9] = Drama10;
        //END OF DRAMA

        //START ROMANCE
        final ImageButton Romance1 = v.findViewById(R.id.Romance1);
        RomanceButtons[0] = Romance1;
        final ImageButton Romance2 = v.findViewById(R.id.Romance2);
        RomanceButtons[1] = Romance2;
        final ImageButton Romance3 = v.findViewById(R.id.Romance3);
        RomanceButtons[2] = Romance3;
        final ImageButton Romance4 = v.findViewById(R.id.Romance4);
        RomanceButtons[3] = Romance4;
        final ImageButton Romance5 = v.findViewById(R.id.Romance5);
        RomanceButtons[4] = Romance5;
        final ImageButton Romance6 = v.findViewById(R.id.Romance6);
        RomanceButtons[5] = Romance6;
        final ImageButton Romance7 = v.findViewById(R.id.Romance7);
        RomanceButtons[6] = Romance7;
        final ImageButton Romance8 = v.findViewById(R.id.Romance8);
        RomanceButtons[7] = Romance8;
        final ImageButton Romance9 = v.findViewById(R.id.Romance9);
        RomanceButtons[8] = Romance9;
        final ImageButton Romance10 = v.findViewById(R.id.Romance10);
        RomanceButtons[9] = Romance10;
        //END OF ROMANCE

        //HORROR START
        final ImageButton Horror1 = v.findViewById(R.id.Horror1);
        HorrorButtons[0] = Horror1;
        final ImageButton Horror2 = v.findViewById(R.id.Horror2);
        HorrorButtons[1] = Horror2;
        final ImageButton Horror3 = v.findViewById(R.id.Horror3);
        HorrorButtons[2] = Horror3;
        final ImageButton Horror4 = v.findViewById(R.id.Horror4);
        HorrorButtons[3] = Horror4;
        final ImageButton Horror5 = v.findViewById(R.id.Horror5);
        HorrorButtons[4] = Horror5;
        final ImageButton Horror6 = v.findViewById(R.id.Horror6);
        HorrorButtons[5] = Horror6;
        final ImageButton Horror7 = v.findViewById(R.id.Horror7);
        HorrorButtons[6] = Horror7;
        final ImageButton Horror8 = v.findViewById(R.id.Horror8);
        HorrorButtons[7] = Horror8;
        final ImageButton Horror9 = v.findViewById(R.id.Horror9);
        HorrorButtons[8] = Horror9;
        final ImageButton Horror10 = v.findViewById(R.id.Horror10);
        HorrorButtons[9] = Horror10;
        //END OF HORROR

        //START ANIME
        final ImageButton Anime1 = v.findViewById(R.id.Anime1);
        AnimeButtons[0] = Anime1;
        final ImageButton Anime2 = v.findViewById(R.id.Anime2);
        AnimeButtons[1] = Anime2;
        final ImageButton Anime3 = v.findViewById(R.id.Anime3);
        AnimeButtons[2] = Anime3;
        final ImageButton Anime4 = v.findViewById(R.id.Anime4);
        AnimeButtons[3] = Anime4;
        final ImageButton Anime5 = v.findViewById(R.id.Anime5);
        AnimeButtons[4] = Anime5;
        final ImageButton Anime6 = v.findViewById(R.id.Anime6);
        AnimeButtons[5] = Anime6;
        final ImageButton Anime7 = v.findViewById(R.id.Anime7);
        AnimeButtons[6] = Anime7;
        final ImageButton Anime8 = v.findViewById(R.id.Anime8);
        AnimeButtons[7] = Anime8;
        final ImageButton Anime9 = v.findViewById(R.id.Anime9);
        AnimeButtons[8] = Anime9;
        final ImageButton Anime10 = v.findViewById(R.id.Anime10);
        AnimeButtons[9] = Anime10;
        //END OF ANIME

        return v;
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstance) {
        retrieveAndDisplay(TrendingButtons, "trending", "novel", "getTopRatedMedia");
        retrieveAndDisplay(SciFiButtons, "scifi", "novel", "getMediaByGenreAndType");
        retrieveAndDisplay(ActionButtons, "action", "novel", "getMediaByGenreAndType");
        retrieveAndDisplay(DramaButtons, "drama", "novel", "getMediaByGenreAndType");
        retrieveAndDisplay(RomanceButtons, "romance", "novel", "getMediaByGenreAndType");
        retrieveAndDisplay(HorrorButtons, "horror", "novel", "getMediaByGenreAndType");
        retrieveAndDisplay(AnimeButtons, "anime", "novel", "getMediaByGenreAndType");
    }

    private void retrieveAndDisplay(ImageButton[] genreButtons, String genre, String mediaType, String requestType) {
        RetrieveAndDisplay retrieveAndDisplay = (RetrieveAndDisplay) new RetrieveAndDisplay((MainActivity) getActivity()).execute(genreButtons, genre, mediaType, requestType);
    }
}