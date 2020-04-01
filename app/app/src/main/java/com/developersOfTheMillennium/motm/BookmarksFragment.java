package com.developersOfTheMillennium.motm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developersOfTheMillennium.motm.utils.Bookmarks.DisplayBookmarks;
import com.developersOfTheMillennium.motm.utils.Favorites.DisplayFavorites;


public class BookmarksFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_bookmarks, container, false);

        //accountInfo / accountType
        DisplayBookmarks displayBookmarkRequest = (DisplayBookmarks) new DisplayBookmarks((MainActivity) getActivity(), v).execute();
        return v;

    }
}
