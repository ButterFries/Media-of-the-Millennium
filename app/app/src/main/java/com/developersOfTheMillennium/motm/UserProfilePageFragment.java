package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class UserProfilePageFragment extends Fragment implements View.OnClickListener{

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.user_profile_fragment, container, false);
        // Need to add button functioning here.

        final Button favoritesBtn = rootView.findViewById(R.id.favoritesButton);
        favoritesBtn.setOnClickListener(this);

        final Button bookmarksBtn = rootView.findViewById(R.id.bookmarksButton);
        bookmarksBtn.setOnClickListener(this);

        return rootView;
    }
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.favoritesButton:
                //Log.d("MediaProfileFrag","review button pressed");
                fragment = new FavoritesFragment();
                replaceFragment(fragment);
                break;
            case R.id.bookmarksButton:
                //Log.d("MediaProfileFrag","review button pressed");
                fragment = new BookmarksFragment();
                replaceFragment(fragment);
                break;
            //case R.id.ratingBar:
            //ratingScore = ratingBar.getRating();
            //String rating = "Rating is :" + ratingBar.getRating();

        }
    }
    public void replaceFragment(Fragment someFragment) {
        //FragmentTransaction transaction = getFragmentManager().beginTransaction();
        androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_view, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
