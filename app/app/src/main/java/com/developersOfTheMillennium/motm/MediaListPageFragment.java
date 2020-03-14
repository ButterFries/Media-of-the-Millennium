package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class MediaListPageFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.media_profile_page_fragment, container, false);
        //code to be implemented in the next sprint

        View rootView = inflater.inflate(R.layout.activity_media_list_page_fragment, container, false);

        // Review Button

        // Rating Bar and rating button
        //final RatingBar ratingBar = rootView.findViewById(R.id.ratingBar);
        //Button ratingButton = rootView.findViewById(R.id.ratingButton);
        //ratingButton.setOnClickListener(this);

        return rootView;


    }

}
