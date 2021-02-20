package com.example.workingwithfm;

import com.google.firebase.database.Exclude;

public class upload2 {
    private String img;
    private String key, text;

    upload2() {

    }

    upload2(String img, String text) {
        this.img = img;
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public String getText() {
        return text;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    public void setImg(String img) {
        this.img = img;
    }
}