package com.example.myapplication.model;

public class Image_Item {

    private int imageID;

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getTemplateID() {
        return templateID;
    }

    public void setTemplateID(int templateID) {
        this.templateID = templateID;
    }

    public int getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(int imageLocation) {
        this.imageLocation = imageLocation;
    }

    private int templateID;
    private int imageLocation;

    public Image_Item()
    {

    }
}
