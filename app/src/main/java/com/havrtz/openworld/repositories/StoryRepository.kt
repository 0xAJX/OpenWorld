package com.havrtz.openworld.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.havrtz.openworld.daos.StoryDao
import com.havrtz.openworld.databases.CollageDatabase
import com.havrtz.openworld.models.Story
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StoryRepository @Inject constructor(application: Application?) {
    private lateinit var storyDao: StoryDao
    private val scope = CoroutineScope(Dispatchers.Default)

    val allStories: LiveData<PagedList<Story>>
    fun insert(story: Story?) {
        scope.launch {
            insertStory(story)
        }
    }

    fun update(story: Story?) {
        scope.launch {
            updateStory(story)
        }
    }

    fun delete(story: Story?) {
        scope.launch {
            deleteStory(story)
        }
    }

    fun deleteAll() {
        scope.launch {
            deleteAllStory()
        }
    }

    private suspend fun insertStory(story: Story?) {
        withContext(Dispatchers.Default) {
            storyDao.insert(story)
        }
    }

    private suspend fun updateStory(story: Story?) {
        withContext(Dispatchers.Default) {
            storyDao.update(story)
        }
    }

    private suspend fun deleteStory(story: Story?) {
        withContext(Dispatchers.Default) {
            storyDao.delete(story)
        }
    }

    private suspend fun deleteAllStory() {
        withContext(Dispatchers.Default) {
            storyDao.deleteAllStories()
        }
    }

    init {
        val database: CollageDatabase? = application?.let { CollageDatabase.getInstance(it) }
        if (database != null) {
            storyDao = database.storyDao()!!
        }
        allStories = storyDao.allStories.toLiveData(pageSize = 10)
    }
}