package com.example.myapplication.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.Models.Template;
import com.example.myapplication.Repositories.TemplateRepository;

import java.util.List;

public class TemplateViewModel extends AndroidViewModel {

    private TemplateRepository repository;
    private LiveData<List<Template>> allTemplates, template;

    public TemplateViewModel(@NonNull Application application) {
        super(application);

        repository = new TemplateRepository(application);
        allTemplates = repository.getAllTemplates();
    }

    public TemplateViewModel(@NonNull Application application, int templateId) {
        super(application);

        repository = new TemplateRepository(application);
        allTemplates = repository.getAllTemplates();
        template = repository.getTemplateById(templateId);
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

    public LiveData<List<Template>> getTemplateById() { return template; }
}
