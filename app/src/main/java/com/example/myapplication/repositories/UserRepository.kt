package com.example.myapplication.repositories

import android.app.Application
import android.os.AsyncTask
import com.example.myapplication.daos.UserDao
import com.example.myapplication.databases.CollageDatabase
import com.example.myapplication.models.User

class UserRepository(application: Application?) {
    private val userDao: UserDao
    fun insert(user: User?) {
        InsertUserAsyncTask(userDao).execute(user)
    }

    fun update(user: User?) {
        UpdateUserAsyncTask(userDao).execute(user)
    }

    fun delete(user: User?) {
        DeleteUserAsyncTask(userDao).execute(user)
    }

    private class InsertUserAsyncTask private constructor(private val userDao: UserDao) : AsyncTask<User?, Void?, Void?>() {
        protected override fun doInBackground(vararg users: User): Void? {
            userDao.insert(users[0])
            return null
        }

    }

    private class UpdateUserAsyncTask private constructor(private val userDao: UserDao) : AsyncTask<User?, Void?, Void?>() {
        protected override fun doInBackground(vararg users: User): Void? {
            userDao.update(users[0])
            return null
        }

    }

    private class DeleteUserAsyncTask private constructor(private val userDao: UserDao) : AsyncTask<User?, Void?, Void?>() {
        protected override fun doInBackground(vararg users: User): Void? {
            userDao.delete(users[0])
            return null
        }

    }

    init {
        val database: CollageDatabase = CollageDatabase.getInstance(application)
        userDao = database.userDao()
    }
}