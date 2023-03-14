package com.chartered4.models;


import android.graphics.drawable.Drawable;

public class ListingTypeBean {

    String id;
    String title;
    Drawable icon;
    private boolean isChecked;

    public ListingTypeBean(String id, String title, Drawable icon, boolean isChecked) {
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.isChecked = isChecked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
