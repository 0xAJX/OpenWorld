package com.example.myapplication.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "story_elements")
public class StoryElement {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int story_id;
    private int image_id;
    private String image_location;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getStory_id() {
        return story_id;
    }

    public int getImage_id() {
        return image_id;
    }

    public String getImage_location() {
        return image_location;
    }

    public StoryElement(int story_id, int image_id, String image_location) {
        this.story_id = story_id;
        this.image_id = image_id;
        this.image_location = image_location;
    }
}
