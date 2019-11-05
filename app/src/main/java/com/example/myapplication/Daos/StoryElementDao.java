package com.example.myapplication.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.Models.StoryElement;

@Dao
public interface StoryElementDao {

    @Insert
    void insert(StoryElement storyElement);

    @Update
    void update(StoryElement storyElement);

    @Delete
    void delete(StoryElement storyElement);

    @Query("DELETE FROM story_elements")
    void deleteAllStoryElements();

}
