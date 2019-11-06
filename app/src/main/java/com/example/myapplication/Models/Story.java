package com.example.myapplication.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stories")
public class Story {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int template_id;
    private int user_id;
    private String title;
    private String image_location;

    public int getId() {
        return id;
    }

    public int getTemplate_id() {
        return template_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage_location() {
        return image_location;
    }

    public Story(int template_id, int user_id, String title, String image_location) {
        this.template_id = template_id;
        this.user_id = user_id;
        this.title = title;
        this.image_location = image_location;
    }
}
