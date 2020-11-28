package com.havrtz.openworld.viewmodels

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.havrtz.openworld.databases.CollageDatabase
import com.havrtz.openworld.models.Story
import com.havrtz.openworld.repositories.StoryRepository

class StoryViewModel @ViewModelInject constructor(private val repository: StoryRepository, @Assisted private val savedState: SavedStateHandle) : ViewModel() {
    val allStories: LiveData<PagedList<Story>> = repository.allStories

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
}