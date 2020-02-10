package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

public class MediaHomePage extends AppCompatActivity {

    ViewFlipper viewFlipper;
    Button leftArrow;
    Button rightArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvseries);
        //viewFlipper = findViewById(R.id.viewFlipper);
        //rightArrow = findViewById(R.id.RightArrow);
        //leftArrow = findViewById(R.id.LeftArrow);
        //leftArrow.setOnClickListener(new View.OnClickListener() {
        //    public void onClick(View v) {
                //Previous pic
        //        viewFlipper.showPrevious();
        //    }
        //});
        //rightArrow.setOnClickListener(new View.OnClickListener() {
        //    public void onClick(View v) {
                //Next Pic
        //        viewFlipper.showNext();
        //    }
        //});
    }
}

