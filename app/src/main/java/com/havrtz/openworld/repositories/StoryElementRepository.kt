package com.havrtz.openworld.repositories

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.havrtz.openworld.daos.StoryElementDao
import com.havrtz.openworld.databases.CollageDatabase
import com.havrtz.openworld.models.StoryElement
import com.havrtz.openworld.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class StoryElementRepository(application: Application?, override val coroutineContext: CoroutineContext) : CoroutineScope{
    lateinit var storyElementDao: StoryElementDao
    private val allStoryELements: LiveData<List<StoryElement>>? = null
    private val scope = CoroutineScope(Dispatchers.Default)


    fun insert(storyElement: StoryElement?) {
        scope.launch {
            insertStoryElement(storyElement)
        }
    }

    fun update(storyElement: StoryElement?) {
        scope.launch {
            updateStoryElement(storyElement)
        }
    }

    fun delete(storyElement: StoryElement?) {
        scope.launch {
            deleteStoryElement(storyElement)
        }
    }

    private suspend fun insertStoryElement(storyElement: StoryElement?) {
        withContext(Dispatchers.Default) {
            storyElementDao.insert(storyElement)
        }
    }

    private suspend fun updateStoryElement(storyElement: StoryElement?) {
        withContext(Dispatchers.Default) {
            storyElementDao.update(storyElement)
        }
    }

    private suspend fun deleteStoryElement(storyElement: StoryElement?) {
        withContext(Dispatchers.Default) {
            storyElementDao.delete(storyElement)
        }
    }

    init {
        val database: CollageDatabase? = application?.let { CollageDatabase.getInstance(it) }
        if (database != null) {
            storyElementDao = database.storyElementDao()!!
        }
    }
}