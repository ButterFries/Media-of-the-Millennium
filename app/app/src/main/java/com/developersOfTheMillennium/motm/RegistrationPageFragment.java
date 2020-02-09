package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationPageFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button loginButton = (Button) findViewById(R.id.ActivityRegistrationButtonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TAKE TO LOGIN PAGE
            }
        });
        Button signupButton = (Button) findViewById(R.id.ActivityRegistrationButtonSignup);
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TAKE TO SIGNUP PAGE
            }
        });
        Button guestButton = (Button) findViewById(R.id.ActivityRegistrationButtonGuest);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TAKE TO HOME PAGE AS GUEST
            }
        });


        setContentView(R.layout.activity_registration_page_fragment);
    }

}
