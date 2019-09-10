package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.myapplication.Models.Image_Item;
import com.example.myapplication.Util.Constants;

import java.util.ArrayList;
import java.util.List;



public class DatabaseHandler extends SQLiteOpenHelper {

    //information of database




    //SQLiteDatabase database;

    private Context ctx;
    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {

        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Constants.TABLE_NAME + "(" + Constants.TEMPLATE_ID + " INTEGER PRIMARY KEY ,"  + Constants.NO_OF_IMAGES + " INTEGER" + ")");
        db.execSQL("CREATE TABLE " + Constants.TABLE_NAME_2 + "(" + Constants.COLLAGE_CREATED_ID + " INTEGER PRIMARY KEY ," + Constants.TEMPLATE_ID + ")");
        db.execSQL("CREATE TABLE " + Constants.TABLE_NAME_3 + "(" + Constants.COLLAGE_CREATED_ID + " INTEGER," + Constants.IMAGE_ID + " INTEGER," + Constants.IMAGE_LOCATION + "TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_2);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_3);

        onCreate(db);
    }

    public void addImages(Image_Item image)
    {

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
