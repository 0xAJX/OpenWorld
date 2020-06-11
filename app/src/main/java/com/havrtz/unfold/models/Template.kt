package com.havrtz.unfold.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "templates")
class Template(@field:PrimaryKey(autoGenerate = false) @field:ColumnInfo(name = "id") val id: Int, @field:ColumnInfo(name = "no_of_images") val no_of_images: Int, @field:ColumnInfo(name = "template_res") val template_res: String)