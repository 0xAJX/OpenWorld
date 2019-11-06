package com.example.myapplication.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.renderscript.Sampler;

import androidx.lifecycle.LiveData;

import com.example.myapplication.Daos.TemplateDao;
import com.example.myapplication.Databases.CollageDatabase;
import com.example.myapplication.Models.Template;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TemplateRepository {
    private TemplateDao templateDao;
    private LiveData<List<Template>> allTemplates;

    public TemplateRepository(Application application) {
        CollageDatabase database = CollageDatabase.getInstance(application);
        templateDao = database.templateDao();
        allTemplates = templateDao.getAllTemplates();
    }

    public void insert(Template template) {
        new InsertTemplateAsyncTask(templateDao).execute(template);
    }

    public void update(Template template) {
        new UpdateTemplateAsyncTask(templateDao).execute(template);
    }

    public void delete(Template template) {
        new DeleteTemplateAsyncTask(templateDao).execute(template);
    }

    public Template getTemplateById(int id) throws ExecutionException, InterruptedException {

        return new GetTemplateByIdAsyncTask(templateDao).execute(id).get();
    }

    public LiveData<List<Template>> getAllTemplates() {
        return allTemplates;
    }

    private static class InsertTemplateAsyncTask extends AsyncTask<Template, Void, Void> {

        private TemplateDao templateDao;

        private InsertTemplateAsyncTask(TemplateDao templateDao) {
            this.templateDao = templateDao;
        }

        @Override
        protected Void doInBackground(Template... templates) {
            templateDao.insert(templates[0]);
            return null;
        }
    }

    private static class UpdateTemplateAsyncTask extends AsyncTask<Template, Void, Void> {

        private TemplateDao templateDao;

        private UpdateTemplateAsyncTask(TemplateDao templateDao) {
            this.templateDao = templateDao;
        }

        @Override
        protected Void doInBackground(Template... templates) {
            templateDao.update(templates[0]);
            return null;
        }
    }

    private static class DeleteTemplateAsyncTask extends AsyncTask<Template, Void, Void> {

        private TemplateDao templateDao;

        private DeleteTemplateAsyncTask(TemplateDao templateDao) {
            this.templateDao = templateDao;
        }

        @Override
        protected Void doInBackground(Template... templates) {
            templateDao.delete(templates[0]);
            return null;
        }
    }

    private class GetTemplateByIdAsyncTask extends AsyncTask<Integer, Void, Template> {

        private TemplateDao templateDao;

        private GetTemplateByIdAsyncTask(TemplateDao templateDao) {
            this.templateDao = templateDao;
        }

        @Override
        protected Template doInBackground(Integer... integers) {
            return templateDao.getTemplateById(integers[0]);
        }

        @Override
        protected void onPostExecute(Template template) {
        }
    }

}
