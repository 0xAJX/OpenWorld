package com.example.myapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class User(val email: String, val username: String, val password: String) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

}