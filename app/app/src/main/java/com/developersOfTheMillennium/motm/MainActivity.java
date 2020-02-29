package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import okhttp3.*;

public class MainActivity extends AppCompatActivity implements FragmentChangeListener {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");



    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Initializes the SDK and calls back a completion listener
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.i("Admob", "SDK successfully initialized");
            }
        });

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar()!=null)
            this.getSupportActionBar().hide();

        this.setContentView(R.layout.activity_main);

        // Loads ad to ad bar
        AdView adView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);

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
