package com.aykuttasil.percentbarapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aykuttasil.percentbar.PercentBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            List<Pair<PercentBarView.BarField, String>> list = new ArrayList<>();
            list.add(new Pair<>(PercentBarView.BarField.RIGHT, "https://yt3.ggpht.com/-wh6msZtAzCU/AAAAAAAAAAI/AAAAAAAAAAA/ERwt1WSIOUA/s900-c-k-no-rj-c0xffffff/photo.jpg"));
            list.add(new Pair<>(PercentBarView.BarField.RIGHT, "https://pbs.twimg.com/profile_images/2658478840/b923df35a917b4acdce24d0e03c1a61a_400x400.png"));
            list.add(new Pair<>(PercentBarView.BarField.RIGHT, "https://yt3.ggpht.com/-wh6msZtAzCU/AAAAAAAAAAI/AAAAAAAAAAA/ERwt1WSIOUA/s900-c-k-no-rj-c0xffffff/photo.jpg"));
            list.add(new Pair<>(PercentBarView.BarField.RIGHT, "https://pbs.twimg.com/profile_images/2658478840/b923df35a917b4acdce24d0e03c1a61a_400x400.png"));
            list.add(new Pair<>(PercentBarView.BarField.RIGHT, "https://yt3.ggpht.com/-wh6msZtAzCU/AAAAAAAAAAI/AAAAAAAAAAA/ERwt1WSIOUA/s900-c-k-no-rj-c0xffffff/photo.jpg"));
            list.add(new Pair<>(PercentBarView.BarField.RIGHT, "https://pbs.twimg.com/profile_images/2658478840/b923df35a917b4acdce24d0e03c1a61a_400x400.png"));
            list.add(new Pair<>(PercentBarView.BarField.LEFT, "https://pbs.twimg.com/profile_images/2658478840/b923df35a917b4acdce24d0e03c1a61a_400x400.png"));
            list.add(new Pair<>(PercentBarView.BarField.LEFT, "https://yt3.ggpht.com/-wh6msZtAzCU/AAAAAAAAAAI/AAAAAAAAAAA/ERwt1WSIOUA/s900-c-k-no-rj-c0xffffff/photo.jpg"));


            View alphaView = findViewById(R.id.ImageViewSoruImage);
            PercentBarView percentBarView = (PercentBarView) findViewById(R.id.PercentBarView);
            percentBarView.addAlphaView(alphaView);
            percentBarView.setRightValue(70);
            percentBarView.setLeftValue(55);
            percentBarView.setImages(list);
            percentBarView.setTitleList("X List");
            percentBarView.setRightBarColor(Color.MAGENTA);
            percentBarView.showResult();


            View alphaView1 = findViewById(R.id.ImageViewSoruImage1);
            PercentBarView percentBarView1 = (PercentBarView) findViewById(R.id.PercentBarView1);
            percentBarView1.addAlphaView(alphaView1);
            percentBarView1.setRightValue(30);
            percentBarView1.setLeftValue(80);
            percentBarView1.setImages(list);
            percentBarView1.setTitleList("Y List");
            percentBarView1.setRightWidthBar(70);
            percentBarView1.setRightBarColor(Color.BLACK);
            percentBarView1.showResult();


            View alphaView2 = findViewById(R.id.ImageViewSoruImage2);
            PercentBarView percentBarView2 = (PercentBarView) findViewById(R.id.PercentBarView2);
            percentBarView2.addAlphaView(alphaView2);
            percentBarView2.setRightValue(25);
            percentBarView2.setLeftValue(42);
            percentBarView2.setImages(list);
            percentBarView2.setTitleList("Z List");
            percentBarView2.setLeftBarColor(Color.GRAY);
            percentBarView2.showResult();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
