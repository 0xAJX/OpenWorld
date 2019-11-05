package com.example.myapplication.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.Models.Template;

import java.util.List;

@Dao
public interface TemplateDao {
    @Insert
    void insert(Template template);

    @Update
    void update(Template template);

    @Delete
    void delete(Template template);

    @Query("DELETE FROM templates")
    void deleteAllTemplates();

    @Query("SELECT no_of_images, template_res FROM templates WHERE id = :id")
    LiveData<List<Template>> getTemplateById(int id);

    @Query("SELECT * FROM templates ORDER BY id ASC")
    LiveData<List<Template>> getAllTemplates();
}
