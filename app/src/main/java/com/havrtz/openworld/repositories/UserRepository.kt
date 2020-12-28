package com.havrtz.openworld.repositories

import android.app.Application
import android.os.AsyncTask
import com.havrtz.openworld.daos.UserDao
import com.havrtz.openworld.databases.CollageDatabase
import com.havrtz.openworld.models.User
import com.squareup.okhttp.Dispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepository(application: Application?) {
    private lateinit var userDao: UserDao
    private val scope = CoroutineScope(Dispatchers.Default)

    fun insert(user: User?) {
        scope.launch {
            insertUser(user)
        }
    }

    fun update(user: User?) {
        scope.launch {
            updateUser(user)
        }
    }

    fun delete(user: User?) {
        scope.launch {
            deleteUser(user)
        }
    }

    private suspend fun insertUser(user: User?) {
        withContext(Dispatchers.Default) {
            userDao.delete(user)
        }
    }

    private suspend fun updateUser(user: User?) {
        withContext(Dispatchers.Default) {
            userDao.update(user)
        }
    }

    private suspend fun deleteUser(user: User?) {
        withContext(Dispatchers.Default) {
            userDao.delete(user)
        }
    }

    init {
        val database: CollageDatabase? = application?.let { CollageDatabase.getInstance(it) }
        if (database != null) {
            userDao = database.userDao()!!
        }
    }
}