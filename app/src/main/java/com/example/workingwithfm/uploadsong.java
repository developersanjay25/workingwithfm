package com.example.workingwithfm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class uploadsong {
    String img;
    String album;
    String artist;
    String song,key;
    uploadsong()
    {

    }
    uploadsong(String img, String album, String artist,String song)
    {
        this.img = img;
        this.album = album;
        this.artist = artist;
        this.song = song;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
    public  void setsongurl(String song)
    {
        this.song = song;
    }

    public String getsong()
    {
        return song;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
