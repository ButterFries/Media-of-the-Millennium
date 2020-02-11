package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

public class MediaHomePage extends AppCompatActivity {

    ViewFlipper viewFlipper;
    Button left1;
    Button right1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvseries);

        //IMPLEMENT A VIEWFLIPPER FOR EACH CATEGORYPIC

        //viewFlipper = findViewById(R.id.viewFlipper1);
        right1 = findViewById(R.id.RightComedy);
        left1 = findViewById(R.id.LeftComedy);
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

