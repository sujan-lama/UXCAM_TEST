package com.sujan.uxcam.model;

import android.graphics.drawable.Drawable;

public class DetailModel {
    String title;
    String subTitle;
    int icon;

    public DetailModel(String title, String subTitle, int icon) {
        this.title = title;
        this.subTitle = subTitle;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }


    public String getSubTitle() {
        return subTitle;
    }


    public int getIcon() {
        return icon;
    }


}
