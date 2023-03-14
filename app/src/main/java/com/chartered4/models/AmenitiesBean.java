package com.chartered4.models;


public class AmenitiesBean {

    String title;
    String value;
    private boolean isChecked;

    public AmenitiesBean(String title, String value, boolean isChecked) {
        this.title = title;
        this.value = value;
        this.isChecked = isChecked;
    }

    public AmenitiesBean(String title, boolean isChecked) {
        this.title = title;
        this.isChecked = isChecked;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
