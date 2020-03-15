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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.developersOfTheMillennium.motm.utils.ValidateAccount;

public class LoginPageFragment extends Fragment {

    int login_tries = 0;
    int max_tries  = 3;
    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){

        View v = in.inflate(R.layout.activity_login_page_fragment, container, false);

        final EditText username = v.findViewById(R.id.email_input);
        final TextView username_text = v.findViewById(R.id.email_text);

        final EditText password = v.findViewById(R.id.password_input);
        final TextView password_text = v.findViewById(R.id.password_text);
        //Max password attempts

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


        final Button loginButton = v.findViewById(R.id.ActivityLoginButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(login_tries < max_tries){
                    login(username.getText().toString(), password.getText().toString());
                    login_tries += 1;
                    String attempt = String.valueOf(login_tries);
                    Toast.makeText(getActivity().getApplicationContext(), "Login Attempt: " + attempt, Toast.LENGTH_LONG).show();
                } else if (login_tries >= 3){
                    Toast.makeText(getActivity().getApplicationContext(),"Number of login attempts exceeded", Toast.LENGTH_LONG).show();
                    login_tries += 1;
                }
            }
        });


        return v;


    }

    private void login(String usernameEmail, String password) {
        ValidateAccount loginRequest = (ValidateAccount) new ValidateAccount((MainActivity) getActivity()).execute(usernameEmail, password);
    }
}
