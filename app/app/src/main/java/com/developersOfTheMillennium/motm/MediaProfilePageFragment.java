package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.developersOfTheMillennium.motm.utils.Bookmarks.AddBookmark;
import com.developersOfTheMillennium.motm.utils.Favorites.AddFavorite;

import androidx.fragment.app.Fragment;

import com.developersOfTheMillennium.motm.utils.GetMediaProfile;
import com.developersOfTheMillennium.motm.utils.GetPicture;

import org.w3c.dom.Text;

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
        final TextView title = rootView.findViewById(R.id.textView);
        final TextView tags = rootView.findViewById(R.id.textView5);
        final TextView summary = rootView.findViewById(R.id.textView6);

        try {
            //get info should be first
            getMediaProfile(Integer.toString(mediaID), image, title, tags, summary);
            getPicture(Integer.toString(mediaID), image);
        } catch (Exception e) {
            //catch but never happens because getPicture never throws exception? might need to fix?
        //    picture.setImageResource(R.drawable.ic_cinema);
        }

        // Review Button
        final Button reviewBtn = rootView.findViewById(R.id.reviewButton);
        reviewBtn.setOnClickListener(this);

        //Add to favorites
        final Button addFavoritesBtn = rootView.findViewById(R.id.addToFavorites);
        addFavoritesBtn.setOnClickListener(this);

        //Add to bookmarks
//        final Button addBookmarksBtn = rootView.findViewById(R.id.addToBookmarks);
//        addBookmarksBtn.setOnClickListener(this);



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
            case R.id.addToFavorites:
                //TODO: CHANGE MEDIAID (1)
                //Media ID / account Info / accountType
                AddFavorite addRequest = (AddFavorite) new AddFavorite((MainActivity) getActivity(), view).execute("1", AppGlobals.user, AppGlobals.userType);
                break;
           // case R.id.addToBookmarks:

//                //TODO: CHANGE MEDIAID (1)
//                //Media ID / account Info / accountType
//                AddBookmark addRequest2 = (AddBookmark) new AddBookmark((MainActivity) getActivity(), view).execute("1", AppGlobals.user, AppGlobals.userType);
//                break;
        }
    }
    public void replaceFragment(Fragment someFragment) {
        //FragmentTransaction transaction = getFragmentManager().beginTransaction();
        androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_view, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void getMediaProfile(String mediaId, ImageView imgView, TextView titleView, TextView tagsView, TextView summaryView) throws Exception{
        try {
            GetMediaProfile mediaInfo = (GetMediaProfile) new GetMediaProfile((MainActivity) getActivity()).execute(mediaId, imgView, titleView, tagsView, summaryView);
        } catch (Exception e) {
            throw new Exception("(getMediaProfile) -- something went wrong when retrieving media profile");
        }
    }

    private void getPicture(String mediaId, ImageView imgView) throws Exception{
        try {
            GetPicture pic = (GetPicture) new GetPicture((MainActivity) getActivity()).execute(mediaId, imgView);
        } catch (Exception e) {
            throw new Exception("(getPicture) -- something went wrong when retrieving picture");
        }
    }

}
