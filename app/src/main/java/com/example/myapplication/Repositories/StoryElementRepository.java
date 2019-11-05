package com.example.myapplication.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.Daos.StoryElementDao;
import com.example.myapplication.Databases.CollageDatabase;
import com.example.myapplication.Models.StoryElement;

import java.util.List;

public class StoryElementRepository {
    private StoryElementDao storyElementDao;
    private LiveData<List<StoryElement>> allStoryELements;

    public StoryElementRepository(Application application) {
        CollageDatabase database = CollageDatabase.getInstance(application);
        storyElementDao = database.storyElementDao();
    }

    public void insert(StoryElement storyElement) {
        new InsertStoryElementAsyncTask(storyElementDao).execute(storyElement);
    }

    public void update(StoryElement storyElement) {
        new UpdateStoryElementAsyncTask(storyElementDao).execute(storyElement);
    }

    public void delete(StoryElement storyElement) {
        new DeleteStoryElementAsyncTask(storyElementDao).execute(storyElement);
    }

    private static class InsertStoryElementAsyncTask extends AsyncTask<StoryElement, Void, Void> {

        private StoryElementDao storyElementDao;

        private InsertStoryElementAsyncTask(StoryElementDao storyElementDao) {
            this.storyElementDao = storyElementDao;
        }

        @Override
        protected Void doInBackground(StoryElement... storyElements) {
            storyElementDao.insert(storyElements[0]);
            return null;
        }
    }

    private static class UpdateStoryElementAsyncTask extends AsyncTask<StoryElement, Void, Void> {

        private StoryElementDao storyElementDao;

        private UpdateStoryElementAsyncTask(StoryElementDao storyElementDao) {
            this.storyElementDao = storyElementDao;
        }

        @Override
        protected Void doInBackground(StoryElement... storyElements) {
            storyElementDao.update(storyElements[0]);
            return null;
        }
    }

    private static class DeleteStoryElementAsyncTask extends AsyncTask<StoryElement, Void, Void> {

        private StoryElementDao storyElementDao;

        private DeleteStoryElementAsyncTask(StoryElementDao storyElementDao) {
            this.storyElementDao = storyElementDao;
        }

        @Override
        protected Void doInBackground(StoryElement... storyElements) {
            storyElementDao.delete(storyElements[0]);
            return null;
        }
    }

}
