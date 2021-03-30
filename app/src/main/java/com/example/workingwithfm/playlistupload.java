package com.example.workingwithfm;

public class playlistupload {
    String link;
    String img;
    String name ;
playlistupload(String link,String name,String img)
{
    this.link = link;
    this.img = img;
    this.name = name;
}
    public String getLink() {
        return link;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
