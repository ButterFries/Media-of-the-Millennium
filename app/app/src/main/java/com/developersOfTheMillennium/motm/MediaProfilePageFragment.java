package com.developersOfTheMillennium.motm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developersOfTheMillennium.motm.utils.Bookmarks.AddBookmark;
import com.developersOfTheMillennium.motm.utils.Favorites.AddFavorite;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.developersOfTheMillennium.motm.utils.GetMediaProfile;
import com.developersOfTheMillennium.motm.utils.GetPicture;
import com.developersOfTheMillennium.motm.utils.GetPictureNoClick;
import com.developersOfTheMillennium.motm.utils.Ratings.GetMediaRating;
import com.developersOfTheMillennium.motm.utils.Ratings.GetUserRating;
import com.developersOfTheMillennium.motm.utils.Ratings.UpdateRating;
import com.developersOfTheMillennium.motm.utils.Reviews.GetReviews;

import org.w3c.dom.Text;

//import com.example.motm.R;  //TODO?


public class MediaProfilePageFragment extends Fragment implements View.OnClickListener {

    private String mediaID = null;
    float userRating;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.media_profile_page_fragment, container, false);

        View rootView = inflater.inflate(R.layout.media_profile_page_fragment, container, false);

        //Picture setting
        final ImageView image = rootView.findViewById(R.id.imageView2);
        final TextView title = rootView.findViewById(R.id.textView);
        final TextView tags = rootView.findViewById(R.id.textView5);
        final TextView summary = rootView.findViewById(R.id.textView6);
        final TextView links = rootView.findViewById(R.id.linksView);

        Bundle args = getArguments();
        if(args != null) {
            mediaID = args.getInt("mediaID", -1)+"";
            System.out.println("MEDIA ID: " + mediaID);
            try {
                //get info should be first
                getMediaRating(mediaID, rootView);
                getUserRating(mediaID, rootView);

                getMediaProfile(mediaID, image, title, tags, summary, links);
                getPicture(mediaID, image);


                ReviewsListFragment fragment = new ReviewsListFragment();
                Bundle args2 = new Bundle();
                if(mediaID != null) {
                    args2.putInt("mediaID", Integer.parseInt(mediaID));
                    fragment.setArguments(args2);
                }
                // Begin the transaction
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.mediaReviewContainer, fragment);
                ft.commit();



            } catch (Exception e) {
                //catch but never happens because getPicture never throws exception? might need to fix?
                //    picture.setImageResource(R.drawable.ic_cinema);
            }
        } else {
            //ERROR CASE SO LEAVE BLANK
            Log.i("Cannot find mediaID", "--error retrieving mediaID");
        }

        // Review Button
        final Button reviewBtn = rootView.findViewById(R.id.reviewButton);
        reviewBtn.setOnClickListener(this);

        //Add to favorites
        final Button addFavoritesBtn = rootView.findViewById(R.id.addToFavorites);
        addFavoritesBtn.setOnClickListener(this);

        //Add to bookmarks
        final Button addBookmarksBtn = rootView.findViewById(R.id.addToBookmarks);
        addBookmarksBtn.setOnClickListener(this);

        //Rating button
        final Button ratingButton = rootView.findViewById(R.id.ratingButton);
        ratingButton.setOnClickListener(this);

        //Rating button
        final ImageView imageView = rootView.findViewById(R.id.imageView2);
        imageView.setOnClickListener(null);

        return rootView;


    }

    @SuppressLint("ClickableViewAccessibility")
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.reviewButton:

                if (AppGlobals.userType.equals("guest")) {
                    CharSequence text = "Please sign in to review";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getContext(), text, duration);
                    toast.show();
                }
                else {
                    Log.d("MediaProfileFrag", "review button pressed");
                    fragment = new ReviewsFragment();

                    Bundle args = new Bundle();
                    if (mediaID != null) {
                        args.putInt("mediaID", Integer.parseInt(mediaID));
                        fragment.setArguments(args);
                    }
                    replaceFragment(fragment);
                }
                break;
            case R.id.addToFavorites:
                //Media ID
                if (AppGlobals.userType.equals("guest")) {
                    CharSequence text = "Please sign in to favorite";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getContext(), text, duration);
                    toast.show();
                }
                else {
                    AddFavorite addRequest = (AddFavorite) new AddFavorite((MainActivity) getActivity(), view).execute(mediaID); }
                break;
             case R.id.addToBookmarks:
                 if (AppGlobals.userType.equals("guest")) {
                     CharSequence text = "Please sign in to bookmark";
                     int duration = Toast.LENGTH_SHORT;

                     Toast toast = Toast.makeText(getContext(), text, duration);
                     toast.show();
                 }
                 else {
                //TODO: CHANGE MEDIAID (1)
                AddBookmark addRequest2 = (AddBookmark) new AddBookmark((MainActivity) getActivity(), view).execute(mediaID); }
                break;
            case R.id.ratingButton:
                if (AppGlobals.userType.equals("guest")) {
                    CharSequence text = "Please sign in to rate";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getContext(), text, duration);
                    toast.show();
                }
                else {
                    final Dialog rankDialog = new Dialog(getContext(), R.style.FullHeightDialog);
                    rankDialog.setContentView(R.layout.rating_overlay);
                    rankDialog.setCancelable(true);
                    RatingBar ratingBar = (RatingBar) rankDialog.findViewById(R.id.dialog_ratingbar);
                    ratingBar.setRating(0); //set to users rating if exist
                    ratingBar.setStepSize((float) 0.1);
                    ratingBar.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            RatingBar bar = (RatingBar) view;
                            userRating = bar.getRating();
                            return false;
                        }
                    });

                    TextView text = (TextView) rankDialog.findViewById(R.id.rank_dialog_text1);
                    text.setText("Your Rating");

                    Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
                    updateButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UpdateRating update = (UpdateRating) new UpdateRating((MainActivity) getActivity(), v).execute(mediaID, Float.toString(userRating));

//


                            rankDialog.dismiss();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(MediaProfilePageFragment.this).attach(MediaProfilePageFragment.this).commit();
                        }
                    });

                    //now that the dialog is set up, it's time to show it
                    rankDialog.show();

                }

                break;

        }
    }
    public void replaceFragment(Fragment someFragment) {
        //FragmentTransaction transaction = getFragmentManager().beginTransaction();
        androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_view, someFragment, "mpp_frag");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void getMediaProfile(String mediaId, ImageView imgView, TextView titleView, TextView tagsView, TextView summaryView, TextView linksView) throws Exception{
        try {
            GetMediaProfile mediaInfo = (GetMediaProfile) new GetMediaProfile((MainActivity) getActivity()).execute(mediaId, imgView, titleView, tagsView, summaryView, linksView);
        } catch (Exception e) {
            throw new Exception("(getMediaProfile) -- something went wrong when retrieving media profile");
        }
    }

    private void getPicture(String mediaId, ImageView imgView) throws Exception{
        try {
            GetPictureNoClick pic = (GetPictureNoClick) new GetPictureNoClick((MainActivity) getActivity()).execute(mediaId, imgView);
        } catch (Exception e) {
            throw new Exception("(getPicture) -- something went wrong when retrieving picture");
        }
    }

    private void getMediaRating(String mediaId, View v) throws Exception{
        try {
            GetMediaRating publicRate = (GetMediaRating) new GetMediaRating((MainActivity) getActivity(), v).execute(mediaId);
        } catch (Exception e) {
            throw new Exception("(getMediaRating) -- something went wrong when retrieving picture");
        }
    }
    private void getUserRating(String mediaId, View v) throws Exception{
        try {
            GetUserRating userRate = (GetUserRating) new GetUserRating((MainActivity) getActivity(), v).execute(mediaId);
        } catch (Exception e) {
            throw new Exception("(getMediaRating) -- something went wrong when retrieving picture");
        }
    }

}
