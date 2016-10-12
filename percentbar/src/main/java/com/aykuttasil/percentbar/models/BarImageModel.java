package com.aykuttasil.percentbar.models;

import com.aykuttasil.percentbar.PercentBarView;

/**
 * Created by aykutasil on 12.10.2016.
 */

public class BarImageModel {

    private String imageUrl;
    private String barText;
    private PercentBarView.BarField barValue;


    public BarImageModel() {
    }

    public BarImageModel(String imageUrl, String text, PercentBarView.BarField value) {
        this.imageUrl = imageUrl;
        this.barText = text;
        this.barValue = value;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBarText() {
        return barText;
    }

    public void setBarText(String barText) {
        this.barText = barText;
    }

    public PercentBarView.BarField getValue() {
        return barValue;
    }

    public void setValue(PercentBarView.BarField value) {
        this.barValue = value;
    }
}
