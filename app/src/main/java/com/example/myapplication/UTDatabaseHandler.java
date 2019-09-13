package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.myapplication.Models.Image_Item;
import com.example.myapplication.Util.Constants;

import java.util.ArrayList;
import java.util.List;



public class UTDatabaseHandler extends SQLiteOpenHelper {

    //information of database




    //SQLiteDatabase database;

    private Context ctx;
    public UTDatabaseHandler(@Nullable Context context) {

        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);

        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {

            Log.d("query 1", "CREATE TABLE " + Constants.TABLE_NAME + " (" + Constants.TEMPLATE_ID + " INTEGER primary key auto_increment , " + Constants.NO_OF_IMAGES + " INTEGER" + ");");
            db.execSQL("CREATE TABLE " + Constants.TABLE_NAME + " (" + Constants.TEMPLATE_ID + " INTEGER Primary Key AUTOINCREMENT, " + Constants.NO_OF_IMAGES + " INTEGER" + ")");

            Log.d("query 2", "CREATE TABLE " + Constants.TABLE_NAME_2 + " (" + Constants.USER_ID + " INTEGER PRIMARY KEY , " + Constants.USERNAME + " TEXT," + Constants.PASSWORD + " TEXT" + ")");
            db.execSQL("CREATE TABLE " + Constants.TABLE_NAME_2 + " (" + Constants.USER_ID + " INTEGER PRIMARY KEY , " + Constants.USERNAME + " TEXT," + Constants.PASSWORD + " TEXT" + ")");

            Log.d("query 3", "CREATE TABLE " + Constants.TABLE_NAME_3 + " (" + Constants.USER_TEMPLATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Constants.TEMPLATE_ID + " INTEGER," + Constants.USER_ID + " INTEGER, " + Constants.STORY_TITLE + " TEXT" + ")");
            db.execSQL("CREATE TABLE " + Constants.TABLE_NAME_3 + " (" + Constants.USER_TEMPLATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Constants.TEMPLATE_ID + " INTEGER," + Constants.USER_ID + " INTEGER, " + Constants.STORY_TITLE + " TEXT, " + Constants.USER_TEMPLATE_LOCATION + " TEXT" +")");

            Log.d("query 4",  "CREATE TABLE " + Constants.TABLE_NAME_4 + " (" + Constants.USER_TEMPLATE_ID + " INTEGER, " + Constants.IMAGE_ID + " INTEGER, " + Constants.IMAGE_LOCATION + " TEXT" + ")");
            db.execSQL("CREATE TABLE " + Constants.TABLE_NAME_4 + " (" + Constants.USER_TEMPLATE_ID + " INTEGER, " + Constants.IMAGE_ID + " INTEGER, " + Constants.IMAGE_LOCATION + " TEXT" + ")");
        }
        catch (Exception e)
        {
            Log.d("QUERY ERROR", "QUERY ERROR");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_2);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_3);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_4);*/

        onCreate(db);
    }

    public String addUserTemplate(String tid, String user_id, String story_title, String utLocation)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();

        //contentValues1.put(Constants.USER_TEMPLATE_ID, ut_id);
        contentValues1.put(Constants.TEMPLATE_ID, tid);
        contentValues1.put(Constants.USER_ID,"1" /*TODO: create user id*/);
        contentValues1.put(Constants.STORY_TITLE, story_title);
        contentValues1.put(Constants.USER_TEMPLATE_LOCATION, utLocation);

        long result = db.insert(Constants.TABLE_NAME_3, null, contentValues1);

        //"SELECT max(user_template_id) FROM " + Constants.TABLE_NAME_3

        Cursor cursor = db.rawQuery("SELECT last_insert_rowid();",null);

        cursor.moveToFirst();

        Log.d("cursor ", DatabaseUtils.dumpCursorToString(cursor));

        if(cursor.getCount() > 0) {

            String utid = cursor.getString(0);

            Log.d("utid", utid);
            return utid;

        }

        return "";
        /*if(result == -1)
            return false;
        else
            return true;*/
    }

    public boolean addImages(List<Image_Item> images)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        long result = 0;

        for(int i = 0 ; i < images.size() ; i++)
        {
            contentValues.put(Constants.USER_TEMPLATE_ID, images.get(i).getUserTemplateID());
            contentValues.put(Constants.IMAGE_ID, images.get(i).getImageID());
            contentValues.put(Constants.IMAGE_LOCATION, images.get(i).getImageLocation());

            result = db.insert(Constants.TABLE_NAME_4, null, contentValues);

        }

        if(result == -1)
            return false;
        else
            return true;

    }



    public List<String> loadImages(int templateID) {
        List<String> result = new ArrayList<>();

        String query = "Select * FROM " + Constants.TABLE_NAME_2 + "WHERE TEMPLATE_ID=" + templateID;

        SQLiteDatabase db = this.getWritableDatabase();

        try
        {
            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {

                int imageID = cursor.getInt(0);
                String location = cursor.getString(1);

                result.add(imageID, location);
            }
            cursor.close();
        }
        catch (Exception e)
        {

        }

        db.close();
        return result;

    }

    /*public void addImage(Image_Item image) {
        ContentValues values = new ContentValues();

        values.put(Constants.IMAGE_ID, image.getImageID());
        values.put(Constants.TEMPLATE_ID, image.getTemplateID());
        values.put(Constants.IMAGE_LOCATION, image.getImageLocation());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(Constants.TABLE_NAME_2, null, values);
        db.close();

    }*/

}