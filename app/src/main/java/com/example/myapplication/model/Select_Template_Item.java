package com.example.myapplication.model;

import android.graphics.Bitmap;

public class Select_Template_Item {



    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    private Bitmap image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;



    public Select_Template_Item(String id, Bitmap image)
    {
        this.image = image;
        this.id = id;
    }
}
