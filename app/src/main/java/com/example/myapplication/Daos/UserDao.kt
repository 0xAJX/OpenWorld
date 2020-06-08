package com.example.myapplication.Daos

import androidx.room.*
import com.example.myapplication.Models.User

@Dao
interface UserDao {
    @Insert
    fun insert(user: User?)

    @Update
    fun update(user: User?)

    @Delete
    fun delete(user: User?)

    @Query("DELETE FROM users")
    fun deleteAllStoryElements()
}