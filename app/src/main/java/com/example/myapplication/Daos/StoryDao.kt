package com.example.myapplication.Daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.Models.Story

@Dao
interface StoryDao {
    @Insert
    fun insert(story: Story?)

    @Update
    fun update(story: Story?)

    @Delete
    fun delete(story: Story?)

    @Query("DELETE FROM stories")
    fun deleteAllStories()

    @get:Query("SELECT * FROM stories ORDER BY id DESC")
    val allStories: LiveData<List<Story?>?>?
}