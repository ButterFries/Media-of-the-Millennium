package com.developersOfTheMillennium.motm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import okhttp3.*;

public class MainActivity extends AppCompatActivity implements FragmentChangeListener {

    public static final int PORT = 8080;
    public static final String ADDR = "192.168.50.253";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");



    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        RegistrationPageFragment rpf = new RegistrationPageFragment();
        fragmentTransaction.add(R.id.fragment_view, rpf);
        fragmentTransaction.commit();
    }


    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_view, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }


}
