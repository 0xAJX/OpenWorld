package com.havrtz.unfold.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.havrtz.unfold.models.Story
import com.havrtz.unfold.repositories.StoryRepository

class StoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: StoryRepository = StoryRepository(application)
    val allStories: LiveData<List<Story?>?>?
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
        allStories = repository.allStories
    }
}