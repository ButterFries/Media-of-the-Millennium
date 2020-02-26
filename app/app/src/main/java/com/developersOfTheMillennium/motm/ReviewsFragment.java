package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.EventListener;

//import com.example.motm.R;

public class ReviewsFragment extends Fragment {
    private Button submit_review;
    private RatingBar review_bar;
    private EditText review_title;
    private EditText review;
    private float review_rating;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.reviews_page_fragment, container, false);
        submit_review = v.findViewById(R.id.button);
        review = v.findViewById(R.id.review_text);
        review_title = v.findViewById(R.id.review_title);
        review_bar = v.findViewById(R.id.ratingBar2);

        review.addTextChangedListener(review_watcher);
        review_title.addTextChangedListener(review_watcher);


        review_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                review_rating = review_bar.getRating();
                if(review_rating == 0.0){
                    submit_review.setEnabled(false);
                }else{
                    submit_review.setEnabled(true);
                }
//                System.out.println(review_rating);
            }
        });

        return v;
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
