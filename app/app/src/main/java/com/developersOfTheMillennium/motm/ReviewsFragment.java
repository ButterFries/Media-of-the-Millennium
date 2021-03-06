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

import androidx.fragment.app.Fragment;

import com.developersOfTheMillennium.motm.utils.Reviews.AddReview;
import com.developersOfTheMillennium.motm.utils.Reviews.DeleteReview;
import com.developersOfTheMillennium.motm.utils.Reviews.UserAlreadyReviewed;
import com.google.android.material.snackbar.Snackbar;

import java.util.EventListener;

import static com.developersOfTheMillennium.motm.AppGlobals.user;
import static com.developersOfTheMillennium.motm.AppGlobals.userType;
import static com.developersOfTheMillennium.motm.AppGlobals.session;

//import com.example.motm.R;

public class ReviewsFragment extends Fragment {
    private Button submit_review;
    private RatingBar review_bar;
    private EditText review_title;
    private EditText review;
    private Button edit;
    private Button delete_review;
    private float review_rating;
    private int mediaID = -1;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();
        if(args != null) {
            mediaID = args.getInt("mediaID", -1);
        }
// Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.reviews_page_fragment, container, false);
        submit_review = v.findViewById(R.id.button);
        review = v.findViewById(R.id.review_text);
        review_title = v.findViewById(R.id.review_title);
        review_bar = v.findViewById(R.id.ratingBar2);
        edit = v.findViewById(R.id.edit_review);
        delete_review = v.findViewById(R.id.delete_review);

        review.addTextChangedListener(review_watcher);
        review_title.addTextChangedListener(review_watcher);

        submit_review.setEnabled(false);
        review_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                review_rating = review_bar.getRating();
                if(review_rating == 0.0){
                    submit_review.setEnabled(false);
                }else{
                    submit_review.setEnabled(true);
                }
// System.out.println(review_rating);
            }
        });
        submit_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review_title.setFocusable(false);
                review_title.setBackgroundColor(Color.TRANSPARENT);
                review.setFocusable(false);
                review.setBackgroundColor(Color.TRANSPARENT);
                review_bar.setIsIndicator(true);
                submit_review.setVisibility(View.INVISIBLE);
                edit.setVisibility(View.VISIBLE);
                delete_review.setVisibility(View.VISIBLE);

                //send add request to server
                addReview(review_title.getText().toString(), review.getText().toString(), ""+review_bar.getRating(), ""+mediaID);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review_title.setFocusableInTouchMode(true);
                review.setFocusableInTouchMode(true);
                review_bar.setIsIndicator(false);
                submit_review.setText("Submit Edit");
                submit_review.setVisibility(View.VISIBLE);
                edit.setVisibility(View.INVISIBLE);
                delete_review.setVisibility(View.INVISIBLE);
            }
        });

        delete_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review_title.setText("");
                review_title.setFocusableInTouchMode(true);
                review.setFocusableInTouchMode(true);
                review.setText("");
                review_bar.setRating((float) 0.0);
                review_bar.setIsIndicator(false);
                submit_review.setText("Add Review");
                submit_review.setVisibility(View.VISIBLE);
                submit_review.setEnabled(true);
                edit.setVisibility(View.INVISIBLE);
                delete_review.setVisibility(View.INVISIBLE);

                //send delete request to server
                deleteReview(mediaID+"");

            }
        });


        UserAlreadyReviewed userAlreadyReviewed = (UserAlreadyReviewed)
                new UserAlreadyReviewed((MainActivity) this.getActivity()).execute(session, mediaID+"", user, userType);



        return v;
    }

    private void addReview(String title, String text, String rating, String mediaID) {
        AddReview addReviewRequest = (AddReview) new AddReview((MainActivity) getActivity()).execute
                (title, text, rating, session, mediaID, user, userType);
    }
    private void deleteReview(String mediaID) {
        DeleteReview deleteReviewRequest = (DeleteReview) new DeleteReview((MainActivity) getActivity()).execute
                (session, mediaID, user, userType);
    }

    private TextWatcher review_watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            submit_review.setEnabled(false);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String review_checker = review.getText().toString().trim();
            String review_title_check = review_title.getText().toString().trim();
            submit_review.setEnabled(!review_checker.isEmpty() && !review_title_check.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


}