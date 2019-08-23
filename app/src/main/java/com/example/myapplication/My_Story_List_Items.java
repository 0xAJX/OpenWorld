package com.example.myapplication;

import android.graphics.Bitmap;
import android.media.Image;
import android.widget.ImageView;

public class My_Story_List_Items {

    private String title;
    private String description;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public My_Story_List_Items(String title, String description ,Bitmap image)
    {
        this.image = image;
        this.title = title;
        this.description = description;
        //this.information = information;
        //this.location = location;
    }
}
