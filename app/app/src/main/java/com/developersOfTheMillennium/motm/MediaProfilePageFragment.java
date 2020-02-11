package com.developersOfTheMillennium.motm;

import android.content.Context;
import android.media.MediaMetadata;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

//import com.example.motm.R;  //TODO?
import androidx.appcompat.app.AppCompatActivity;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MediaProfilePageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MediaProfilePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MediaProfilePageFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    int ratingScore;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MediaProfilePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MediaProfilePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MediaProfilePageFragment newInstance(String param1, String param2) {
        MediaProfilePageFragment fragment = new MediaProfilePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //final RatingBar ratingBar = findViewById(R.id.ratingBar);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.media_profile_page_fragment, container, false);

        View rootView = inflater.inflate(R.layout.media_profile_page_fragment, container, false);

        // Review Button
        Button reviewBtn = rootView.findViewById(R.id.reviewButton);
        reviewBtn.setOnClickListener(this);

        // Rating Bar and rating button
        final RatingBar ratingBar = rootView.findViewById(R.id.ratingBar);
        Button ratingButton = rootView.findViewById(R.id.ratingButton);
        ratingButton.setOnClickListener(this);

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
            case R.id.ratingBar:
                //ratingScore = ratingBar.getRating();
                //String rating = "Rating is :" + ratingBar.getRating();

        }
    }
    public void replaceFragment(Fragment someFragment) {
        //FragmentTransaction transaction = getFragmentManager().beginTransaction();
        androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }






    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
