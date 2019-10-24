package com.example.myapplication.Models;

import android.graphics.Bitmap;

public class MyStoryListItem {

    private String title = "My Story";



    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    private Bitmap image;



    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String information;
    private String location;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

            this.title = title;
    }





    public MyStoryListItem(String title, Bitmap image)
    {
        this.image = image;
        this.title = title;
        //this.information = information;
        //this.location = location;
    }
}
