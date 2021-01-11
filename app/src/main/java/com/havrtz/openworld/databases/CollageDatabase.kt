package com.havrtz.openworld.databases

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.havrtz.openworld.daos.StoryDao
import com.havrtz.openworld.daos.StoryElementDao
import com.havrtz.openworld.daos.TemplateDao
import com.havrtz.openworld.daos.UserDao
import com.havrtz.openworld.models.Story
import com.havrtz.openworld.models.StoryElement
import com.havrtz.openworld.models.Template
import com.havrtz.openworld.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Database(entities = [Story::class, StoryElement::class, Template::class, User::class], version = 1)
abstract class CollageDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao?
    abstract fun storyElementDao(): StoryElementDao?
    abstract fun templateDao(): TemplateDao
    abstract fun userDao(): UserDao?

    companion object {
        private var instance: CollageDatabase? = null
        private val scope = CoroutineScope(Dispatchers.Default)

//        val migration_1_2: Migration = object: Migration(1,2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                val db = "INSERT INTO TEMPLATES" + " (" + "id" + ", " + "no_of_images" + ", " + "template_res" + ") " + "Values (1, 1, '1080x1920');" +
//                        "INSERT INTO TEMPLATES" + " (" + "id" + ", " + "no_of_images" + ", " + "template_res" + ") " + "Values (2, 2, '1080x1920');" +
//                        "INSERT INTO TEMPLATES" + " (" + "id" + ", " + "no_of_images" + ", " + "template_res" + ") " + "Values (3, 3, '1080x1920');"
//                database.execSQL(db)
//            }
//        }

        @Synchronized
        fun getInstance(context: Context): CollageDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        CollageDatabase::class.java, "collage")
                        //.addMigrations(migration_1_2)
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
            }
            return instance
        }

        private val roomCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                scope.launch {
                    populateDb(instance)
                }
            }
        }

        private suspend fun populateDb(collageDatabase: CollageDatabase?) {

            val templateDao: TemplateDao = collageDatabase!!.templateDao()

            withContext(Dispatchers.Default) {
                templateDao.insert(Template(1, 1, ""))
                templateDao.insert(Template(2, 2, ""))
                templateDao.insert(Template(3, 3, ""))
            }
        }
    }
}