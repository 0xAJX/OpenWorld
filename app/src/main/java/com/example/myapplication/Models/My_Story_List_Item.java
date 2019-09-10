package com.example.myapplication.Models;

import android.graphics.Bitmap;

public class My_Story_List_Item {

    private String title;



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





    public My_Story_List_Item(String title, Bitmap image)
    {
        this.image = image;
        this.title = title;
        //this.information = information;
        //this.location = location;
    }
}
