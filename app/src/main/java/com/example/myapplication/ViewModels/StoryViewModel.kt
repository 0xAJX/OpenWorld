package com.example.myapplication.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.Models.Story
import com.example.myapplication.Repositories.StoryRepository

class StoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: StoryRepository
    val allStories: LiveData<List<Story>>
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
        repository = StoryRepository(application)
        allStories = repository.allStories
    }
}