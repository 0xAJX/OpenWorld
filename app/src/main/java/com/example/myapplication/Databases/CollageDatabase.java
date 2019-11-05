package com.example.myapplication.Databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.Daos.StoryDao;
import com.example.myapplication.Daos.StoryElementDao;
import com.example.myapplication.Daos.TemplateDao;
import com.example.myapplication.Daos.UserDao;
import com.example.myapplication.Models.Story;
import com.example.myapplication.Models.StoryElement;
import com.example.myapplication.Models.Template;
import com.example.myapplication.Models.User;

@Database(entities = {Story.class, StoryElement.class, Template.class, User.class}, version = 1)
public abstract class CollageDatabase extends RoomDatabase {
    private static CollageDatabase instance;

    public abstract StoryDao storyDao();
    public abstract StoryElementDao storyElementDao();
    public abstract TemplateDao templateDao();
    public abstract UserDao userDao();

    public static synchronized CollageDatabase getInstance(Context context) {
        if (instance == null) {
           instance = Room.databaseBuilder(context.getApplicationContext(),
                   CollageDatabase.class, "collage")
                   .fallbackToDestructiveMigration()
                   .addCallback(roomCallback)
                   .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private TemplateDao templateDao;

        private PopulateDbAsyncTask(CollageDatabase collageDatabase) {
            templateDao = collageDatabase.templateDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            templateDao.insert(new Template(1,1, ""));
            templateDao.insert(new Template(2,2, ""));
            templateDao.insert(new Template(3,3, ""));
            return null;
        }
    }
}
