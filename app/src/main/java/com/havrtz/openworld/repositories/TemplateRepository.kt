package com.havrtz.openworld.repositories

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.havrtz.openworld.daos.TemplateDao
import com.havrtz.openworld.databases.CollageDatabase
import com.havrtz.openworld.models.Template
import java.util.concurrent.ExecutionException

class TemplateRepository(application: Application?) {
    lateinit var templateDao: TemplateDao
    val allTemplates: LiveData<List<Template?>?>?
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

    private class InsertTemplateAsyncTask(private val templateDao: TemplateDao) : AsyncTask<Template?, Void?, Void?>() {
        override fun doInBackground(vararg templates: Template?): Void? {
            templateDao.insert(templates[0])
            return null
        }

    }

    private class UpdateTemplateAsyncTask(private val templateDao: TemplateDao) : AsyncTask<Template?, Void?, Void?>() {
        override fun doInBackground(vararg templates: Template?): Void? {
            templateDao.update(templates[0])
            return null
        }

    }

    private class DeleteTemplateAsyncTask(private val templateDao: TemplateDao) : AsyncTask<Template?, Void?, Void?>() {
        override fun doInBackground(vararg templates: Template?): Void? {
            templateDao.delete(templates[0])
            return null
        }

    }

    private inner class GetTemplateByIdAsyncTask(private val templateDao: TemplateDao) : AsyncTask<Int?, Void?, Template>() {
        override fun onPostExecute(template: Template) {}
        override fun doInBackground(vararg integers: Int?): Template {
            return integers[0]?.let { templateDao.getTemplateById(it) }!!

        }

    }

    init {
        val database: CollageDatabase? = application?.let { CollageDatabase.getInstance(it) }
        if (database != null) {
            templateDao = database.templateDao()
        }
        allTemplates = templateDao.allTemplates
    }
}