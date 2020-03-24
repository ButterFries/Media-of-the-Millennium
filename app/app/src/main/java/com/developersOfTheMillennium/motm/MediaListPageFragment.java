package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MediaListPageFragment extends Fragment implements View.OnClickListener {
    private ImageButton add_media;
    private EditText review_title;
    private TextView username;
    private FloatingActionButton add;
    private SearchView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_media_list_page_fragment, container, false);
        username = v.findViewById(R.id.username_text);
        username.setText(AppGlobals.user);
        searchView = v.findViewById(R.id.search);
        add = v.findViewById(R.id.floatingActionButton2);
        add.setOnClickListener(this);

        return v;


    }
    public void onClick(View view) {
        searchView.setVisibility(View.VISIBLE);
        searchView.requestFocus();
    }

}
