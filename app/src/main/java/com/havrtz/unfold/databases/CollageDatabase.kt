package com.havrtz.unfold.databases

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.havrtz.unfold.daos.StoryDao
import com.havrtz.unfold.daos.StoryElementDao
import com.havrtz.unfold.daos.TemplateDao
import com.havrtz.unfold.daos.UserDao
import com.havrtz.unfold.models.Story
import com.havrtz.unfold.models.StoryElement
import com.havrtz.unfold.models.Template
import com.havrtz.unfold.models.User

@Database(entities = [Story::class, StoryElement::class, Template::class, User::class], version = 1)
abstract class CollageDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao?
    abstract fun storyElementDao(): StoryElementDao?
    abstract fun templateDao(): TemplateDao
    abstract fun userDao(): UserDao?
    private class PopulateDbAsyncTask(collageDatabase: CollageDatabase?) : AsyncTask<Void?, Void?, Void?>() {
        private val templateDao: TemplateDao = collageDatabase!!.templateDao()

        override fun doInBackground(vararg voids: Void?): Void? {
            templateDao.insert(Template(1, 1, ""))
            templateDao.insert(Template(2, 2, ""))
            templateDao.insert(Template(3, 3, ""))
            return null
        }
    }

    companion object {
        private var instance: CollageDatabase? = null

        @Synchronized
        fun getInstance(context: Context): CollageDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        CollageDatabase::class.java, "collage")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
            }
            return instance
        }

        private val roomCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance).execute()
            }
        }
    }
}