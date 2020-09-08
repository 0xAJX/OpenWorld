package com.havrtz.unfold.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.havrtz.unfold.databases.CollageDatabase
import com.havrtz.unfold.models.Story
import com.havrtz.unfold.repositories.StoryRepository

class StoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: StoryRepository = StoryRepository(application)
    val allStories: LiveData<PagedList<Story>>

    fun insert(story: Story?) {
        repository.insert(story)
    }

    fun update(story: Story?) {
        repository.update(story)
    }

    fun delete(story: Story?) {
        repository.delete(story)
    }

    fun deleteAllStories() {
        repository.deleteAllStories()
    }

    init {
        val factory: DataSource.Factory<Int, Story> =
                CollageDatabase.getInstance(getApplication())?.storyDao()!!.allStories

        allStories = repository.allStories
    }
}