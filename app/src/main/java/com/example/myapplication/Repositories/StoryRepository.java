package com.example.myapplication.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.Daos.StoryDao;
import com.example.myapplication.Databases.CollageDatabase;
import com.example.myapplication.Models.Story;

import java.util.List;

public class StoryRepository {
    private StoryDao storyDao;
    private LiveData<List<Story>> allStories;

    public StoryRepository(Application application) {
        CollageDatabase database = CollageDatabase.getInstance(application);
        storyDao = database.storyDao();
        allStories = storyDao.getAllStories();
    }

    public void insert(Story story) {
        new InsertStoryAsyncTask(storyDao).execute(story);
    }

    public void update(Story story) {
        new UpdateStoryAsyncTask(storyDao).execute(story);
    }

    public void delete(Story story) {
        new DeleteStoryAsyncTask(storyDao).execute(story);
    }

    public void deleteAllStories() {
        new DeleteAllStoryAsyncTask(storyDao).execute();
    }

    public LiveData<List<Story>> getAllStories() {
        return allStories;
    }

    private static class InsertStoryAsyncTask extends AsyncTask<Story, Void, Void> {

        private StoryDao storyDao;

        private InsertStoryAsyncTask(StoryDao storyDao) {
            this.storyDao = storyDao;
        }

        @Override
        protected Void doInBackground(Story... stories) {
            storyDao.insert(stories[0]);
            return null;
        }
    }

    private static class UpdateStoryAsyncTask extends AsyncTask<Story, Void, Void> {

        private StoryDao storyDao;

        private UpdateStoryAsyncTask(StoryDao storyDao) {
            this.storyDao = storyDao;
        }

        @Override
        protected Void doInBackground(Story... stories) {
            storyDao.update(stories[0]);
            return null;
        }
    }

    private static class DeleteStoryAsyncTask extends AsyncTask<Story, Void, Void> {

        private StoryDao storyDao;

        private DeleteStoryAsyncTask(StoryDao storyDao) {
            this.storyDao = storyDao;
        }

        @Override
        protected Void doInBackground(Story... stories) {
            storyDao.delete(stories[0]);
            return null;
        }
    }

    private static class DeleteAllStoryAsyncTask extends AsyncTask<Void, Void, Void> {

        private StoryDao storyDao;

        private DeleteAllStoryAsyncTask(StoryDao storyDao) {
            this.storyDao = storyDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            storyDao.deleteAllStories();
            return null;
        }
    }
}
