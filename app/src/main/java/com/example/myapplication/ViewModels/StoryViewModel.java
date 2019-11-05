package com.example.myapplication.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.Models.Story;
import com.example.myapplication.Repositories.StoryRepository;

import java.util.List;

public class StoryViewModel extends AndroidViewModel {

    private StoryRepository repository;
    private LiveData<List<Story>> allStories;

    public StoryViewModel(@NonNull Application application) {
        super(application);

        repository = new StoryRepository(application);
        allStories = repository.getAllStories();
    }

    public void insert(Story story) {
        repository.insert(story);
    }

    public void update(Story story) {
        repository.update(story);
    }

    public void delete(Story story) {
        repository.delete(story);
    }

    public void deleteAllStories() {
        repository.deleteAllStories();
    }

    public LiveData<List<Story>> getAllStories() {
        return allStories;
    }

}
