package com.aykuttasil.percentbarapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aykuttasil.percentbar.PercentBarView;
import com.aykuttasil.percentbar.models.BarImageModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    PercentBarView percentBarView;
    PercentBarView percentBarView1;
    PercentBarView percentBarView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<BarImageModel> mList = new ArrayList<>();
        mList.add(new BarImageModel("https://s-media-cache-ak0.pinimg.com/564x/2b/74/04/2b74046c2e2e488fe1f2be60680671dd.jpg", "Abc", PercentBarView.BarField.LEFT));
        mList.add(new BarImageModel("https://s-media-cache-ak0.pinimg.com/564x/65/01/b5/6501b5ae42a0c1aa863b1ae33a746ce2.jpg", "Def", PercentBarView.BarField.RIGHT));
        mList.add(new BarImageModel("https://s-media-cache-ak0.pinimg.com/564x/5a/e9/50/5ae9501fc3b49810db7901873f77d6f7.jpg", "Lorem", PercentBarView.BarField.RIGHT));
        mList.add(new BarImageModel("https://s-media-cache-ak0.pinimg.com/564x/9d/1c/86/9d1c861252a4d0067def3f567467b175.jpg", "Qwerty", PercentBarView.BarField.LEFT));
        mList.add(new BarImageModel("https://s-media-cache-ak0.pinimg.com/564x/5e/bd/17/5ebd1769bf115773ad83803b29ea4bac.jpg", "WWW", PercentBarView.BarField.LEFT));
        mList.add(new BarImageModel("https://s-media-cache-ak0.pinimg.com/564x/67/51/c3/6751c3d814fdda7db7e4844c3007db7c.jpg", "Aykut", PercentBarView.BarField.LEFT));
        mList.add(new BarImageModel("https://pbs.twimg.com/profile_images/2658478840/b923df35a917b4acdce24d0e03c1a61a_400x400.png", "text 2", PercentBarView.BarField.RIGHT));
        mList.add(new BarImageModel("https://yt3.ggpht.com/-wh6msZtAzCU/AAAAAAAAAAI/AAAAAAAAAAA/ERwt1WSIOUA/s900-c-k-no-rj-c0xffffff/photo.jpg", "text 3", PercentBarView.BarField.RIGHT));
        mList.add(new BarImageModel("https://pbs.twimg.com/profile_images/2658478840/b923df35a917b4acdce24d0e03c1a61a_400x400.png", "text 4", PercentBarView.BarField.RIGHT));
        mList.add(new BarImageModel("https://yt3.ggpht.com/-wh6msZtAzCU/AAAAAAAAAAI/AAAAAAAAAAA/ERwt1WSIOUA/s900-c-k-no-rj-c0xffffff/photo.jpg", "text 5", PercentBarView.BarField.LEFT));
        mList.add(new BarImageModel("https://pbs.twimg.com/profile_images/2658478840/b923df35a917b4acdce24d0e03c1a61a_400x400.png", "text 6", PercentBarView.BarField.LEFT));
        mList.add(new BarImageModel("https://yt3.ggpht.com/-wh6msZtAzCU/AAAAAAAAAAI/AAAAAAAAAAA/ERwt1WSIOUA/s900-c-k-no-rj-c0xffffff/photo.jpg", "text 7", PercentBarView.BarField.LEFT));
        mList.add(new BarImageModel("https://pbs.twimg.com/profile_images/2658478840/b923df35a917b4acdce24d0e03c1a61a_400x400.png", "text 8", PercentBarView.BarField.LEFT));

        View alphaView = findViewById(R.id.ImageViewSoruImage);
        percentBarView = (PercentBarView) findViewById(R.id.PercentBarView);
        percentBarView.addAlphaView(alphaView);
        percentBarView.setRightBarValue(70);
        percentBarView.setLeftBarValue(30);
        percentBarView.setImages(mList);
        percentBarView.setImagesListTitle("X List");
        percentBarView.setRightBarColor(Color.MAGENTA);


        View alphaView1 = findViewById(R.id.ImageViewSoruImage1);
        percentBarView1 = (PercentBarView) findViewById(R.id.PercentBarView1);
        percentBarView1.addAlphaView(alphaView1);
        percentBarView1.setRightBarValue(30);
        percentBarView1.setLeftBarValue(80);
        percentBarView1.setImages(mList);
        percentBarView1.setImagesListTitle("Y List");
        percentBarView1.setImagesListItemSize(50);
        percentBarView1.setRightBarWidth(70);
        percentBarView1.setRightBarColor(Color.BLACK);


        View alphaView2 = findViewById(R.id.ImageViewSoruImage2);
        percentBarView2 = (PercentBarView) findViewById(R.id.PercentBarView2);
        percentBarView2.addAlphaView(alphaView2);
        percentBarView2.setRightBarValue(25);
        percentBarView2.setLeftBarValue(42);
        percentBarView2.setImages(mList);
        percentBarView2.setImagesListTitle("Z List");
        percentBarView2.setLeftBarColor(Color.GRAY);

    }

    public void ButtonShowClick(View view) {
        percentBarView.showResult();
        percentBarView1.showResult();
        percentBarView2.showResult();
    }
}
