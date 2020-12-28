package com.havrtz.openworld.repositories

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.havrtz.openworld.daos.TemplateDao
import com.havrtz.openworld.databases.CollageDatabase
import com.havrtz.openworld.models.Story
import com.havrtz.openworld.models.StoryElement
import com.havrtz.openworld.models.Template
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutionException

class TemplateRepository(application: Application?) {
    lateinit var templateDao: TemplateDao
    val allTemplates: LiveData<List<Template?>?>?
    private val scope = CoroutineScope(Dispatchers.Default)

    fun insert(template: Template?) {
        scope.launch {
            insertTemplate(template)
        }
    }

    fun update(template: Template?) {
        scope.launch {
            updateTemplate(template)
        }
    }

    fun delete(template: Template?) {
        scope.launch {
            deleteTemplate(template)
        }
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    fun getTemplateById(id: Int): Template {
        return GetTemplateByIdAsyncTask(templateDao).execute(id).get()
    }

    private suspend fun insertTemplate(template: Template?) {
        withContext(Dispatchers.Default) {
            templateDao.insert(template)
        }
    }

    private suspend fun updateTemplate(template: Template?) {
        withContext(Dispatchers.Default) {
            templateDao.update(template)
        }
    }

    private suspend fun deleteTemplate(template: Template?) {
        withContext(Dispatchers.Default) {
            templateDao.delete(template)
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