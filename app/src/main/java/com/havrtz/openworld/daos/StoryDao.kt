package com.havrtz.openworld.daos

import androidx.paging.DataSource
import androidx.room.*
import com.havrtz.openworld.models.Story

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
    val allStories: DataSource.Factory<Int, Story>
}