package com.example.myapplication.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.Models.Story;

import java.util.List;

@Dao
public interface StoryDao {

    @Insert
    void insert(Story story);

    @Update
    void update(Story story);

    @Delete
    void delete(Story story);

    @Query("DELETE FROM stories")
    void deleteAllStories();

    @Query("SELECT * FROM stories ORDER BY id DESC")
    LiveData<List<Story>> getAllStories();
}
