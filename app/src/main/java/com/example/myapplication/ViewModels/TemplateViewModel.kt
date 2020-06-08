package com.example.myapplication.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.Models.Template
import com.example.myapplication.Repositories.TemplateRepository
import java.util.concurrent.ExecutionException

class TemplateViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TemplateRepository
    val allTemplatest: LiveData<List<Template>>
    fun insert(template: Template?) {
        repository.insert(template)
    }

    fun update(template: Template?) {
        repository.update(template)
    }

    fun delete(template: Template?) {
        repository.delete(template)
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    fun getTemplateById(id: Int): Template {
        return repository.getTemplateById(id)
    }

    init {
        repository = TemplateRepository(application)
        allTemplatest = repository.allTemplates
    }
}