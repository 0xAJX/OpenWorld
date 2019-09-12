package com.example.myapplication.Models;

public class Image_Item {

    private int imageID;

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getUserTemplateID() {
        return userTemplateID;
    }

    public void setUserTemplateID(int userTemplateID) {
        this.userTemplateID = userTemplateID;
    }

    private int userTemplateID;

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    private String imageLocation;

    public Image_Item()
    {

    }
}
