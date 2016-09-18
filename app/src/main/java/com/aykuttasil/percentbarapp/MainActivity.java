package com.aykuttasil.percentbarapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aykuttasil.percentbar.PercentBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            View alphaView = findViewById(R.id.ImageViewSoruImage);
            PercentBarView percentBarView = (PercentBarView) findViewById(R.id.PercentBarView);
            percentBarView.addAlphaView(alphaView);
            percentBarView.setAValue(70);
            percentBarView.setBValue(55);
            percentBarView.showResult();


            View alphaView1 = findViewById(R.id.ImageViewSoruImage1);
            PercentBarView percentBarView1 = (PercentBarView) findViewById(R.id.PercentBarView1);
            percentBarView1.addAlphaView(alphaView1);
            percentBarView1.setAValue(30);
            percentBarView1.setBValue(80);
            percentBarView1.showResult();


            View alphaView2 = findViewById(R.id.ImageViewSoruImage2);
            PercentBarView percentBarView2 = (PercentBarView) findViewById(R.id.PercentBarView2);
            percentBarView2.addAlphaView(alphaView2);
            percentBarView2.setAValue(25);
            percentBarView2.setBValue(42);
            percentBarView2.showResult();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
