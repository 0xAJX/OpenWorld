package com.example.myapplication.repositories

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.myapplication.daos.StoryElementDao
import com.example.myapplication.databases.CollageDatabase
import com.example.myapplication.models.StoryElement

class StoryElementRepository(application: Application?) {
    lateinit var storyElementDao: StoryElementDao
    private val allStoryELements: LiveData<List<StoryElement>>? = null

    fun insert(storyElement: StoryElement?) {
        InsertStoryElementAsyncTask(storyElementDao).execute(storyElement)
    }

    fun update(storyElement: StoryElement?) {
        UpdateStoryElementAsyncTask(storyElementDao).execute(storyElement)
    }

    fun delete(storyElement: StoryElement?) {
        DeleteStoryElementAsyncTask(storyElementDao).execute(storyElement)
    }

    private class InsertStoryElementAsyncTask(private val storyElementDao: StoryElementDao) : AsyncTask<StoryElement?, Void?, Void?>() {

        override fun doInBackground(vararg storyElements: StoryElement?): Void? {
            storyElementDao.insert(storyElements[0])
            return null
        }

    }

    private class UpdateStoryElementAsyncTask(private val storyElementDao: StoryElementDao) : AsyncTask<StoryElement?, Void?, Void?>() {

        override fun doInBackground(vararg storyElements: StoryElement?): Void? {
            storyElementDao.update(storyElements[0])
            return null
        }

    }

    private class DeleteStoryElementAsyncTask(private val storyElementDao: StoryElementDao) : AsyncTask<StoryElement?, Void?, Void?>() {
        override fun doInBackground(vararg storyElements: StoryElement?): Void? {
            storyElementDao.delete(storyElements[0])
            return null
        }

    }

    init {
        val database: CollageDatabase? = application?.let { CollageDatabase.getInstance(it) }
        if (database != null) {
            storyElementDao = database.storyElementDao()!!
        }
    }
}