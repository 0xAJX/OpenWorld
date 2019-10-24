package com.example.myapplication.Models;

import android.graphics.Bitmap;

public class TemplateItem {


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

    public String getTemplate_res() {
        return template_res;
    }

    public void setTemplate_res(String template_res) {
        this.template_res = template_res;
    }

    private String template_res = "";

    public TemplateItem()
    {

    }
}
