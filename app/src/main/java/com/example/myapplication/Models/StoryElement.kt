package com.example.myapplication.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story_elements")
class StoryElement(val story_id: Int, val image_id: Int, val image_location: String) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

}