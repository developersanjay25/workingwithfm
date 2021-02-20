package com.example.workingwithfm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class uploadsong {
    Bitmap img;
    String album;
    String artist;
    uploadsong()
    {
//        empty constructor needed
    }
    uploadsong(Bitmap img, String album, String artist)
    {
        this.img = img;
        this.album = album;
        this.artist = artist;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
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
}
