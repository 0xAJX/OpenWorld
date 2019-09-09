package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.myapplication.model.Image_Item;

public class Database extends SQLiteOpenHelper {

    //information of database

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "COLLAGE";

    public static final String TABLE_NAME = "TEMPLATES";
    public static final String TABLE_NAME_2 = "IMAGES";

    public static final String USER_ID = "USER_ID";
    public static final String IMAGE_ID = "IMAGE_ID";
    public static final String IMAGE_LOCATION = "";

    public static final String TEMPLATE_ID = "TEMPLATE_ID";


    SQLiteDatabase database;

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //database.execSQL("CREATE TABLE " + TABLE_NAME + "(" + USER_ID + " INTEGER PRIMARY KEY ,"  + TEMPLATE_ID + " INTEGER" + ")");
        database.execSQL("CREATE TABLE " + TABLE_NAME_2 + "(" + TEMPLATE_ID + " INTEGER PRIMARY KEY ," + IMAGE_ID + " INTEGER," + IMAGE_LOCATION + "TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String loadImage() {
        String result = "";
        String query = "Select * FROM " + TABLE_NAME_2;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0) + " " + result_1 + System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;

    }

    public void addImage(Image_Item image) {
        ContentValues values = new ContentValues();

        values.put(IMAGE_ID, image.getImageID());
        values.put(TEMPLATE_ID, image.getTemplateID());
        values.put(IMAGE_LOCATION, image.getImageLocation());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME_2, null, values);
        db.close();

    }

}
