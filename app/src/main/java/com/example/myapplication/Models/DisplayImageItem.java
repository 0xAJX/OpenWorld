package com.example.myapplication.Models;

public class DisplayImageItem {

    private int imageID;

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getUserTemplateID() {
        return userTemplateID;
    }

    public void setUserTemplateID(String userTemplateID) {
        this.userTemplateID = userTemplateID;
    }

    private String userTemplateID;

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    private String imageLocation;

    public DisplayImageItem()
    {

    }
}
