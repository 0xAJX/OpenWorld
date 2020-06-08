package com.example.myapplication.repositories

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.myapplication.daos.TemplateDao
import com.example.myapplication.databases.CollageDatabase
import com.example.myapplication.models.Template
import java.util.concurrent.ExecutionException

class TemplateRepository(application: Application?) {
    private val templateDao: TemplateDao
    val allTemplates: LiveData<List<Template>>
    fun insert(template: Template?) {
        InsertTemplateAsyncTask(templateDao).execute(template)
    }

    fun update(template: Template?) {
        UpdateTemplateAsyncTask(templateDao).execute(template)
    }

    fun delete(template: Template?) {
        DeleteTemplateAsyncTask(templateDao).execute(template)
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    fun getTemplateById(id: Int): Template {
        return GetTemplateByIdAsyncTask(templateDao).execute(id).get()
    }

    private class InsertTemplateAsyncTask private constructor(private val templateDao: TemplateDao) : AsyncTask<Template?, Void?, Void?>() {
        protected override fun doInBackground(vararg templates: Template): Void? {
            templateDao.insert(templates[0])
            return null
        }

    }

    private class UpdateTemplateAsyncTask private constructor(private val templateDao: TemplateDao) : AsyncTask<Template?, Void?, Void?>() {
        protected override fun doInBackground(vararg templates: Template): Void? {
            templateDao.update(templates[0])
            return null
        }

    }

    private class DeleteTemplateAsyncTask private constructor(private val templateDao: TemplateDao) : AsyncTask<Template?, Void?, Void?>() {
        protected override fun doInBackground(vararg templates: Template): Void? {
            templateDao.delete(templates[0])
            return null
        }

    }

    private inner class GetTemplateByIdAsyncTask private constructor(private val templateDao: TemplateDao) : AsyncTask<Int?, Void?, Template>() {
        protected override fun doInBackground(vararg integers: Int): Template {
            return templateDao.getTemplateById(integers[0])
        }

        override fun onPostExecute(template: Template) {}

    }

    init {
        val database: CollageDatabase = CollageDatabase.getInstance(application)
        templateDao = database.templateDao()
        allTemplates = templateDao.allTemplates
    }
}