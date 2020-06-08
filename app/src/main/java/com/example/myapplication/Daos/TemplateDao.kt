package com.example.myapplication.Daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.Models.Template

@Dao
interface TemplateDao {
    @Insert
    fun insert(template: Template?)

    @Update
    fun update(template: Template?)

    @Delete
    fun delete(template: Template?)

    @Query("DELETE FROM templates")
    fun deleteAllTemplates()

    @Query("SELECT * FROM templates WHERE id = :id")
    fun getTemplateById(id: Int): Template?

    @get:Query("SELECT * FROM templates ORDER BY id ASC")
    val allTemplates: LiveData<List<Template?>?>?
}