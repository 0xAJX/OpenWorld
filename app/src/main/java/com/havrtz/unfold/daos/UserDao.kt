package com.havrtz.unfold.daos

import androidx.room.*
import com.havrtz.unfold.models.User

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