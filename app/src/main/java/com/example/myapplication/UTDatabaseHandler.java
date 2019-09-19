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
import com.example.myapplication.Models.Template_Item;
import com.example.myapplication.Models.User_Template_Item;
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

            Log.d("QUEry","query");
            db.execSQL("CREATE TABLE " + Constants.TABLE_NAME + " (" + Constants.TEMPLATE_ID + " INTEGER Primary Key AUTOINCREMENT, " + Constants.NO_OF_IMAGES + " INTEGER, " + Constants.TEMPLATE_RES + " TEXT" + ")");
            Log.d("QUEry","CREATE TABLE " + Constants.TABLE_NAME + " (" + Constants.TEMPLATE_ID + " INTEGER Primary Key AUTOINCREMENT, " + Constants.NO_OF_IMAGES + " INTEGER, " + Constants.TEMPLATE_RES + " TEXT" + ")");
            db.execSQL("CREATE TABLE " + Constants.TABLE_NAME_2 + " (" + Constants.USER_ID + " INTEGER PRIMARY KEY , " + Constants.USERNAME + " TEXT," + Constants.PASSWORD + " TEXT" + ")");
            db.execSQL("CREATE TABLE " + Constants.TABLE_NAME_3 + " (" + Constants.USER_TEMPLATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Constants.TEMPLATE_ID + " INTEGER," + Constants.USER_ID + " INTEGER, " + Constants.STORY_TITLE + " TEXT, " + Constants.USER_TEMPLATE_LOCATION + " TEXT" +")");
            db.execSQL("CREATE TABLE " + Constants.TABLE_NAME_4 + " (" + Constants.USER_TEMPLATE_ID + " INTEGER, " + Constants.IMAGE_ID + " INTEGER, " + Constants.IMAGE_LOCATION + " TEXT" + ")");


        }
        catch (Exception e)
        {
            Log.d("QUERY ERROR", "QUERY ERROR");
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NO_OF_IMAGES, 1);
        contentValues.put(Constants.TEMPLATE_RES, "");
        db.insert(Constants.TABLE_NAME, null, contentValues);
        contentValues.put(Constants.NO_OF_IMAGES, 2);
        contentValues.put(Constants.TEMPLATE_RES, "");
        db.insert(Constants.TABLE_NAME, null, contentValues);
        contentValues.put(Constants.NO_OF_IMAGES, 3);
        contentValues.put(Constants.TEMPLATE_RES, "");
        db.insert(Constants.TABLE_NAME, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_2);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_3);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_4);

        onCreate(db);
    }

    /*public boolean addTemplate()
    {
        SQLiteDatabase db = this.getWritableDatabase();



        long result = -1;

        for(int i = 1; i <=3 ; i++)
        {
            result =
        }

        Log.d("result" , Long.toString(result));

        db.close();

        if(result == -1)
            return false;
        else
            return true;

    }*/

    public int getNoOfImages(String tid)
    {
        String query = "SELECT no_of_images FROM " + Constants.TABLE_NAME + " WHERE " + Constants.TEMPLATE_ID + "="+tid;
        SQLiteDatabase db = this.getWritableDatabase();

        try
        {
            Cursor cursor = db.rawQuery(query, null);

            while (cursor.moveToNext())
            {
                Log.d("query",cursor.getString(cursor.getColumnIndex("no_of_images")));
                return Integer.parseInt(cursor.getString(cursor.getColumnIndex("no_of_images")));
            }
        }
        catch (Exception e)
        {

        }

        db.close();

        return 1;
    }

    public List<Template_Item> getTemplates()
    {
        String query = "SELECT * FROM " + Constants.TABLE_NAME;
        List<Template_Item> template_items = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        //try {

            Cursor cursor = db.rawQuery(query, null);

            while (cursor.moveToNext())
            {
                Template_Item item = new Template_Item();
                item.setId(cursor.getString(cursor.getColumnIndex("template_id")));
                Log.d("id", item.getId());
                item.setNo_of_images(Integer.parseInt(cursor.getString(cursor.getColumnIndex("no_of_images"))));
                Log.d("nos", Integer.toString(item.getNo_of_images()));
                //item.setTemplate_res(cursor.getString(cursor.getColumnIndex("template_res")));
                //Log.d("res", item.getTemplate_res());

                template_items.add(item);
            }

            db.close();
            return template_items;

        //}
       // catch (Exception e)
        //{

        //}






    }

    public String addUserTemplate(String tid, String user_id, String story_title, String utLocation)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //contentValues1.put(Constants.USER_TEMPLATE_ID, ut_id);
        contentValues.put(Constants.TEMPLATE_ID, tid);
        contentValues.put(Constants.USER_ID,"1" /*TODO: create user id*/);
        contentValues.put(Constants.STORY_TITLE, story_title);
        contentValues.put(Constants.USER_TEMPLATE_LOCATION, utLocation);

        long result = db.insert(Constants.TABLE_NAME_3, null, contentValues);

        //"SELECT max(user_template_id) FROM " + Constants.TABLE_NAME_3

        Cursor cursor = db.rawQuery("SELECT last_insert_rowid();",null);

        cursor.moveToFirst();

        Log.d("cursor ", DatabaseUtils.dumpCursorToString(cursor));

        if(cursor.getCount() > 0) {

            String utid = cursor.getString(0);

            Log.d("utid", utid);

            db.close();

            return utid;

        }

        db.close();
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

        db.close();

        if(result == -1)
            return false;
        else
            return true;

    }

    public boolean UpdateUserTemplate(String utid, String story_title, String utLocation)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.USER_ID,"1" /*TODO: create user id*/);
        contentValues.put(Constants.STORY_TITLE, story_title);
        contentValues.put(Constants.USER_TEMPLATE_LOCATION, utLocation);

        long result = db.update(Constants.TABLE_NAME_3, contentValues, "user_template_id="+utid, null);

        db.close();

        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean UpdateImages(String utid, List<Image_Item> images)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        long result = 0;

        for(int i = 0 ; i < images.size() ; i++)
        {
            contentValues.put(Constants.IMAGE_ID, images.get(i).getImageID());
            contentValues.put(Constants.IMAGE_LOCATION, images.get(i).getImageLocation());

            result = db.update(Constants.TABLE_NAME_4, contentValues, "user_template_id="+ utid, null);
        }

        db.close();

        if(result == -1)
            return false;
        else
            return true;

    }



    public List<User_Template_Item> loadUserTemplates() {
        List<User_Template_Item> templateItems = new ArrayList<>();

        String query = "Select * FROM " + Constants.TABLE_NAME_3;

        SQLiteDatabase db = this.getWritableDatabase();

        try
        {
            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {

               User_Template_Item item = new User_Template_Item();
               item.setStory_title(cursor.getString(cursor.getColumnIndex("story_title")));
               item.setTemplate_id(cursor.getString(cursor.getColumnIndex("template_id")));
               item.setUser_template_id(cursor.getString(cursor.getColumnIndex("user_template_id")));
               item.setUser_template_location(cursor.getString(cursor.getColumnIndex("user_template_location")));


               templateItems.add(item);
            }

            cursor.close();
        }
        catch (Exception e)
        {

        }

        db.close();
        return templateItems;

    }

   public List<Image_Item> loadImages(String utid)
   {
       List<Image_Item> image_items = new ArrayList<>();
       String query = "Select * FROM " + Constants.TABLE_NAME_4 + " WHERE " + Constants.USER_TEMPLATE_ID+ "=" + utid;
       Log.d("query load", query);
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = db.rawQuery(query, null);
       while (cursor.moveToNext())
       {
           Image_Item item = new Image_Item();
           item.setUserTemplateID(utid);
           item.setImageID(Integer.parseInt(cursor.getString(cursor.getColumnIndex("image_id"))));
           item.setImageLocation(cursor.getString(cursor.getColumnIndex("image_location")));

           image_items.add(item);

           Log.d("query",cursor.getString(cursor.getColumnIndex("image_location")));
       }

       db.close();
       return image_items;
   }

   public boolean deleteUserTemplate(String utid)
   {
       SQLiteDatabase db = this.getWritableDatabase();
       int result = db.delete(Constants.TABLE_NAME_3,"user_template_id="+utid, null);

       deleteImages(utid);

       db.close();

       if(result == -1)
           return false;
       else
           return true;
   }

    public boolean deleteImages(String utid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(Constants.TABLE_NAME_4,"user_template_id="+utid, null);

        db.close();

        if(result == -1)
            return false;
        else
            return true;
    }

}
