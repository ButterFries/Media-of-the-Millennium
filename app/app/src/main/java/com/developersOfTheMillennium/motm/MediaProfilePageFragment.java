package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.developersOfTheMillennium.motm.utils.GetPicture;

//import com.example.motm.R;  //TODO?

public class MediaProfilePageFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.media_profile_page_fragment, container, false);

        View rootView = inflater.inflate(R.layout.media_profile_page_fragment, container, false);

        //Picture setting

        Bundle args = getArguments();
        int mediaID = args.getInt("mediaID", 0);
        System.out.println("MEDIA IDbrul: " + mediaID);
        final ImageView image = rootView.findViewById(R.id.imageView2);
        try {
            //get info should be first
            //getInfo(Integer.toString(mediaID));
            getPicture(Integer.toString(mediaID), image);
        } catch (Exception e) {
            //catch but never happens because getPicture never throws exception? might need to fix?
        //    picture.setImageResource(R.drawable.ic_cinema);
        }

        // Review Button
        final Button reviewBtn = rootView.findViewById(R.id.reviewButton);
        reviewBtn.setOnClickListener(this);

        // Rating Bar and rating button
        //final RatingBar ratingBar = rootView.findViewById(R.id.ratingBar);
        //Button ratingButton = rootView.findViewById(R.id.ratingButton);
        //ratingButton.setOnClickListener(this);

        return rootView;


    }

    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.reviewButton:
                Log.d("MediaProfileFrag","review button pressed");
                fragment = new ReviewsFragment();
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

    private void getPicture(String mediaId, ImageView imgView) throws Exception{
        try {
            GetPicture pic = (GetPicture) new GetPicture((MainActivity) getActivity()).execute(mediaId, imgView);
        } catch (Exception e) {
            throw new Exception("(getPicture) -- something went wrong when retrieving picture");
        }
    }

}
