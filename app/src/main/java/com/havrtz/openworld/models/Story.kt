package com.havrtz.openworld.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stories")
class Story(val template_id: Int, val user_id: Int, val title: String, val image_location: String) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

}