package com.havrtz.openworld.repositories

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.havrtz.openworld.daos.StoryDao
import com.havrtz.openworld.databases.CollageDatabase
import com.havrtz.openworld.models.Story

class StoryRepository(application: Application?) {
    private lateinit var storyDao: StoryDao

    val allStories: LiveData<PagedList<Story>>
    fun insert(story: Story?) {
        InsertStoryAsyncTask(storyDao).execute(story)
    }

    fun update(story: Story?) {
        UpdateStoryAsyncTask(storyDao).execute(story)
    }

    fun delete(story: Story?) {
        DeleteStoryAsyncTask(storyDao).execute(story)
    }

    fun deleteAllStories() {
        DeleteAllStoryAsyncTask(storyDao).execute()
    }

    private class InsertStoryAsyncTask(private val storyDao: StoryDao) : AsyncTask<Story?, Void?, Void?>() {
        override fun doInBackground(vararg stories: Story?): Void? {
            storyDao.insert(stories[0])
            return null
        }

    }

    private class UpdateStoryAsyncTask(private val storyDao: StoryDao) : AsyncTask<Story?, Void?, Void?>() {
        override fun doInBackground(vararg stories: Story?): Void? {
            storyDao.update(stories[0])
            return null
        }

    }

    private class DeleteStoryAsyncTask(private val storyDao: StoryDao) : AsyncTask<Story?, Void?, Void?>() {
        override fun doInBackground(vararg stories: Story?): Void? {
            storyDao.delete(stories[0])
            return null
        }

    }

    private class DeleteAllStoryAsyncTask(private val storyDao: StoryDao) : AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            storyDao.deleteAllStories()
            return null
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