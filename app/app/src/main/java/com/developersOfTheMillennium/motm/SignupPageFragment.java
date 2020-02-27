package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupPageFragment extends Fragment {


    Pattern emailRegex = Pattern.compile("^[A-Za-z0-9_\\-.+!#'*=?^~$#]+" +
            "@[A-Za-z0-9_\\-.+!#'*=?^~$#]+" +
            "\\.[A-Za-z0-9_\\-.+!#'*=?^~$#]+$");

    Matcher m;

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){

        View v = in.inflate(R.layout.activity_signup_page_fragment, container, false);

        final EditText email = v.findViewById(R.id.email_input);
        final TextView email_text = v.findViewById(R.id.email_text);

        final EditText username = v.findViewById(R.id.username_input);
        final TextView username_text = v.findViewById(R.id.username_text);

        final EditText password = v.findViewById(R.id.password_input);
        final TextView password_text = v.findViewById(R.id.password_text);

        final EditText password_confirm = v.findViewById(R.id.password_input);
        final TextView password_confirm_text = v.findViewById(R.id.password_text);

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()!=0) {
                    username_text.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                else {
                    username_text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()!=0) {
                    email_text.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                else {
                    email_text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()!=0) {
                    password_text.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                else {
                    password_text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        password_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()!=0) {
                    password_confirm_text.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                else {
                    password_confirm_text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });


        final Button loginButton = v.findViewById(R.id.ActivityLoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (signup(email.getText().toString(), username.getText().toString(),
                        password.getText().toString(), password_confirm.getText().toString())) {
                    //TODO: take to main page
                }
            }
        });



        return v;


    }

    private boolean signup(String email, String username, String password, String passwordConfirm){

        m = emailRegex.matcher(email);
        boolean match = m.matches();

        if (password.length()!=0 && passwordConfirm.length()!=0 && username.length()!=0 && email.length()!=0 &&
                password.equals(passwordConfirm) && match) {
            Log.i("Signup", "Valid credentials");
            return true;
        }
        Log.i("Signup", "Invalid credentials");
        return false;
    }
}
