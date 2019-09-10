package com.example.myapplication.model;

import android.graphics.Bitmap;

public class Template_Item {



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

    public int getNo_of_images() {
        return no_of_images;
    }

    public void setNo_of_images(int no_of_images) {
        this.no_of_images = no_of_images;
    }

    private int no_of_images;

    public Template_Item(String id, int no_of_images ,Bitmap image)
    {
        this.image = image;
        this.id = id;
        this.no_of_images = no_of_images;
    }
}
