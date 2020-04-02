package com.developersOfTheMillennium.motm;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.developersOfTheMillennium.motm.utils.Reviews.AddReview;
import com.developersOfTheMillennium.motm.utils.Reviews.DeleteReview;
import com.developersOfTheMillennium.motm.utils.Reviews.GetReviews;
import com.developersOfTheMillennium.motm.utils.Reviews.UserAlreadyReviewed;

import static com.developersOfTheMillennium.motm.AppGlobals.session;
import static com.developersOfTheMillennium.motm.AppGlobals.user;
import static com.developersOfTheMillennium.motm.AppGlobals.userType;

//import com.example.motm.R;

public class ReviewsListFragment extends Fragment {
    private RatingBar[] review_bar = new RatingBar[5];
    private TextView[] review_title = new TextView[5];
    private TextView[] review_author = new TextView[5];
    private TextView[] review = new TextView[5];
    private int mediaID = -1;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.reviews_list_fragment, container, false);

        Bundle args = getArguments();
        if(args != null) {
            mediaID = args.getInt("mediaID", -1);

            // Inflate the layout for this fragment
            Log.i("list", "heyoooooooo");
            review[0] = v.findViewById(R.id.review_text1);
            review[1] = v.findViewById(R.id.review_text2);
            review[2] = v.findViewById(R.id.review_text3);
            review[3] = v.findViewById(R.id.review_text4);
            review[4] = v.findViewById(R.id.review_text5);

            review_title[0] = v.findViewById(R.id.review_title1);
            review_title[1] = v.findViewById(R.id.review_title2);
            review_title[2] = v.findViewById(R.id.review_title3);
            review_title[3] = v.findViewById(R.id.review_title4);
            review_title[4] = v.findViewById(R.id.review_title5);

            review_author[0] = v.findViewById(R.id.review_author1);
            review_author[1] = v.findViewById(R.id.review_author2);
            review_author[2] = v.findViewById(R.id.review_author3);
            review_author[3] = v.findViewById(R.id.review_author4);
            review_author[4] = v.findViewById(R.id.review_author5);

            review_bar[0] = v.findViewById(R.id.ratingBarList1);
            review_bar[1] = v.findViewById(R.id.ratingBarList2);
            review_bar[2] = v.findViewById(R.id.ratingBarList3);
            review_bar[3] = v.findViewById(R.id.ratingBarList4);
            review_bar[4] = v.findViewById(R.id.ratingBarList5);

            getReviews(mediaID, review, review_title, review_bar);

        }

        return v;
    }

    private void getReviews(int mediaID, TextView[] review, TextView[] review_title, RatingBar[] review_bar) {
        GetReviews getReviewsRequest = (GetReviews) new GetReviews
                ((MainActivity) this.getActivity(), review, review_title, review_author, review_bar).execute(mediaID+"", "5");
    }
}