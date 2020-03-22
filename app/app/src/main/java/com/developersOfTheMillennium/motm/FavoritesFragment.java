package com.developersOfTheMillennium.motm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developersOfTheMillennium.motm.utils.Favorites.DisplayFavorites;


public class FavoritesFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_favorites, container, false);

        //accountInfo / accountType
        DisplayFavorites displayFavRequest = (DisplayFavorites) new DisplayFavorites((MainActivity) getActivity(), v).execute(AppGlobals.user, AppGlobals.userType);
        return v;

    }
}
