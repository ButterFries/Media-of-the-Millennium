package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.developersOfTheMillennium.motm.utils.ValidateAccount;

public class LoginPageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater in, ViewGroup container, Bundle savedInstance){

        View v = in.inflate(R.layout.activity_login_page_fragment, container, false);

        final EditText username = v.findViewById(R.id.email_input);
        final TextView username_text = v.findViewById(R.id.email_text);

        final EditText password = v.findViewById(R.id.password_input);
        final TextView password_text = v.findViewById(R.id.password_text);

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
                login(username.getText().toString(), password.getText().toString());
            }
        });


        return v;


    }

    private void login(String usernameEmail, String password){
        ValidateAccount loginRequest = (ValidateAccount) new ValidateAccount((MainActivity) getActivity()).execute(usernameEmail, password);
    }
}
