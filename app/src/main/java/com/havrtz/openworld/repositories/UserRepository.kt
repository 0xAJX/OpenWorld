package com.havrtz.openworld.repositories

import android.app.Application
import android.os.AsyncTask
import com.havrtz.openworld.daos.UserDao
import com.havrtz.openworld.databases.CollageDatabase
import com.havrtz.openworld.models.User

class UserRepository(application: Application?) {
    lateinit var userDao: UserDao

    fun insert(user: User?) {
        InsertUserAsyncTask(userDao).execute(user)
    }

    fun update(user: User?) {
        UpdateUserAsyncTask(userDao).execute(user)
    }

    fun delete(user: User?) {
        DeleteUserAsyncTask(userDao).execute(user)
    }

    private class InsertUserAsyncTask(private val userDao: UserDao) : AsyncTask<User?, Void?, Void?>() {
        override fun doInBackground(vararg users: User?): Void? {
            userDao.insert(users[0])
            return null
        }
    }

    private class UpdateUserAsyncTask(private val userDao: UserDao) : AsyncTask<User?, Void?, Void?>() {
        override fun doInBackground(vararg users: User?): Void? {
            userDao.update(users[0])
            return null
        }

    }

    private class DeleteUserAsyncTask(private val userDao: UserDao) : AsyncTask<User?, Void?, Void?>() {
        override fun doInBackground(vararg users: User?): Void? {
            userDao.delete(users[0])
            return null
        }

    }

    init {
        val database: CollageDatabase? = application?.let { CollageDatabase.getInstance(it) }
        if (database != null) {
            userDao = database.userDao()!!
        }
    }
}