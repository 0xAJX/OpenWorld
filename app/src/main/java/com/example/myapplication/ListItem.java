package com.example.myapplication;

public class ListItem {

    private String title;
    private String description;

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



    public ListItem(String title, String description, String information, String location)
    {
        this.title = title;
        this.description = description;
        this.information = information;
        this.location = location;
    }
}
