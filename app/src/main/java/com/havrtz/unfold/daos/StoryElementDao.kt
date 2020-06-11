package com.havrtz.unfold.daos

import androidx.room.*
import com.havrtz.unfold.models.StoryElement

@Dao
interface StoryElementDao {
    @Insert
    fun insert(storyElement: StoryElement?)

    @Update
    fun update(storyElement: StoryElement?)

    @Delete
    fun delete(storyElement: StoryElement?)

    @Query("DELETE FROM story_elements")
    fun deleteAllStoryElements()
}