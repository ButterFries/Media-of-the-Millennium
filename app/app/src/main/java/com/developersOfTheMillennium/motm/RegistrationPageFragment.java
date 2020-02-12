package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.content.Intent;

public class RegistrationPageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){
        View v = in.inflate(R.layout.activity_registration_page_fragment, container, false);

        final Button loginButton = v.findViewById(R.id.ActivityRegistrationButtonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TAKE TO LOGIN PAGE
                LoginPageFragment loginFragment = new LoginPageFragment();
                ((MainActivity)getActivity()).replaceFragment(loginFragment);
            }
        });
        final Button signupButton = v.findViewById(R.id.ActivityRegistrationButtonSignup);
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TAKE TO SIGNUP PAGE
            }
        });
        final Button guestButton = v.findViewById(R.id.ActivityRegistrationButtonGuest);
        guestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TAKE TO HOME PAGE AS GUEST
                startActivity(new Intent((MainActivity)getActivity(), MediaHomePage.class));
            }
        });


        return v;


    }
}
