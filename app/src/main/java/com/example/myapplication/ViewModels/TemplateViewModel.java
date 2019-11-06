package com.example.myapplication.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.Models.Template;
import com.example.myapplication.Repositories.TemplateRepository;

import java.util.List;

public class TemplateViewModel extends AndroidViewModel {

    private TemplateRepository repository;
    private LiveData<List<Template>> allTemplates;

    public TemplateViewModel(@NonNull Application application) {
        super(application);

        repository = new TemplateRepository(application);
        allTemplates = repository.getAllTemplates();
    }

    public void insert(Template template) {
        repository.insert(template);
    }

    public void update(Template template) {
        repository.update(template);
    }

    public void delete(Template template) {
        repository.delete(template);
    }

    public LiveData<List<Template>> getAllTemplatest() {
        return allTemplates;
    }

    public Template getTemplateById(int id) { return repository.getTemplateById(id); }
}
