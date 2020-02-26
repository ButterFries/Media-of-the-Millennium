package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

//import com.example.motm.R;

public class ReviewsFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.reviews_page_fragment, container, false);
        final Button submit_review = v.findViewById(R.id.button);



        final EditText review = v.findViewById(R.id.review_text);
        final String review_checker = review.getText().toString();
        if(review_checker.isEmpty() == true){
            review.setError("No review has been made, can not submit");
        }else{
            submit_review.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Take to Trending Review

                    review.setText(review_checker);
                    submit_review.setError("Not an error lol ");
                }
            });
        }


        return v;
    }

}
