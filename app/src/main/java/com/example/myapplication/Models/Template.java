package com.example.myapplication.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "templates")
public class Template {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    private int id;

    @ColumnInfo(name = "no_of_images")
    private int no_of_images;

    @ColumnInfo(name = "template_res")
    private String template_res;

    public int getId() {
        return id;
    }

    public int getNo_of_images() {
        return no_of_images;
    }

    public String getTemplate_res() {
        return template_res;
    }

    public Template(int id, int no_of_images, String template_res) {
        this.id = id;
        this.no_of_images = no_of_images;
        this.template_res = template_res;
    }
}
