package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class LoginPageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){

        View v = in.inflate(R.layout.activity_login_page_fragment, container, false);

        return v;


    }
}