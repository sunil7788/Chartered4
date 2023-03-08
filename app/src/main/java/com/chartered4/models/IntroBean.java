package com.chartered4.models;

import android.graphics.drawable.Drawable;

public class IntroBean {

    Drawable img ;
    String title;

    public IntroBean(Drawable img, String title) {
        this.img = img;
        this.title = title;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
