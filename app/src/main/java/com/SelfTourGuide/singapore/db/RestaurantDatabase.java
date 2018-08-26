package com.SelfTourGuide.singapore.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.SelfTourGuide.singapore.model.RestaurantModel;

import java.util.ArrayList;

/**
 * Created by ${张志珍} on 2016/12/27
 * Project_NameDemoMusic
 * Package_Namecom.example.amd.demomusic.db
 * 17:47.
 */

public class RestaurantDatabase {

    private static final String TAG = com.SelfTourGuide.singapore.db.RestaurantDatabase.class.getSimpleName();
    private DatabaseHelper dbHelper;

    public RestaurantDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * 添加数据
     */
    public void insert(RestaurantModel model) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //插入数据SQL语句
      
        String sql = "insert into restaurant(restaurant_name,categories,price_range,restaurantid,language,type)values(?,?,?,?,?,?)";
        Object[] args = {
                model.getRestaurant_name(),model.getCategories(),model.getPrice_range(),model.getRestaurantid(),model.getLanguage(),model.getType()
        };
        db.execSQL(sql, args);
        db.close();
    }




    /**
     * 删除表数据
     */
    public void removeTableData() {
        SQLiteDatabase db =null;
        try {
            db = dbHelper.getWritableDatabase();
            String sql = "delete from restaurant";
            db.execSQL(sql);
        }catch (Exception e){
            db.close();
        }finally {
            db.close();
            if(db!=null){
                //清除对象
                db=null;
            }
        }
    }

    /**
     * 删除数据
     *
     * @param language
     */
    public void deleteByUrl(String language,String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from  restaurant where restaurantid=? and language=?", new Object[] { id,language });
        db.close();
      /*  String sql = "delete from restaurant where language = ?";
        Object[] args = {language};
        db.execSQL(sql, args);
        db.close();*/
    }



    /**
     * 查询所有
     *
     * @return
     */
    public ArrayList<RestaurantModel> queryAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from restaurant";
        Cursor cursor = db.rawQuery(sql,null);
        ArrayList<RestaurantModel> music_tables = new ArrayList<RestaurantModel>();
        RestaurantModel model = null;
        while (cursor.moveToNext()) {
            model = new RestaurantModel();
            if (cursor != null && cursor.getCount() != 0) {
                model.setRestaurant_name(cursor.getString(cursor.getColumnIndex("restaurant_name")));
                model.setCategories(cursor.getString(cursor.getColumnIndex("categories")));
                model.setPrice_range(cursor.getString(cursor.getColumnIndex("price_range")));
                model.setRestaurantid(cursor.getString(cursor.getColumnIndex("restaurantid")));
                model.setLanguage(cursor.getString(cursor.getColumnIndex("language")));
                model.setType(cursor.getString(cursor.getColumnIndex("type")));
            }
            music_tables.add(model);
        }
        if (cursor != null) {
            cursor.close();
        }
        return music_tables;
    }

    //根据语言查询数据
    public ArrayList<RestaurantModel> queryByUrl(String language, String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<RestaurantModel> list = new ArrayList<RestaurantModel>();
//        String sql="select * from restaurant where language='" + language +"'";
        String sql = "select * from restaurant" + " where restaurantid='"+id+"' and language='" + language + "'";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<RestaurantModel> listdata = new ArrayList<RestaurantModel>();
        RestaurantModel model = null;
        while (cursor.moveToNext()) {
            model = new RestaurantModel();
            if (cursor != null && cursor.getCount() != 0) {
                model.setRestaurant_name(cursor.getString(cursor.getColumnIndex("restaurant_name")));
                model.setCategories(cursor.getString(cursor.getColumnIndex("categories")));
                model.setPrice_range(cursor.getString(cursor.getColumnIndex("price_range")));
                model.setRestaurantid(cursor.getString(cursor.getColumnIndex("restaurantid")));
                model.setLanguage(cursor.getString(cursor.getColumnIndex("language")));
                model.setType(cursor.getString(cursor.getColumnIndex("type")));
            }
            listdata.add(model);
        }
        if (cursor != null) {
            cursor.close();
        }
        return listdata;
    }

    //根据语言查询数据
    public ArrayList<RestaurantModel> queryByUrl(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<RestaurantModel> list = new ArrayList<RestaurantModel>();
//        String sql="select * from restaurant where language='" + language +"'";
        String sql = "select * from restaurant" + " where restaurantid='"+id+"' ";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<RestaurantModel> listdata = new ArrayList<RestaurantModel>();
        RestaurantModel model = null;
        while (cursor.moveToNext()) {
            model = new RestaurantModel();
            if (cursor != null && cursor.getCount() != 0) {
                model.setRestaurant_name(cursor.getString(cursor.getColumnIndex("restaurant_name")));
                model.setCategories(cursor.getString(cursor.getColumnIndex("categories")));
                model.setPrice_range(cursor.getString(cursor.getColumnIndex("price_range")));
                model.setRestaurantid(cursor.getString(cursor.getColumnIndex("restaurantid")));
                model.setLanguage(cursor.getString(cursor.getColumnIndex("language")));
                model.setType(cursor.getString(cursor.getColumnIndex("type")));
            }
            listdata.add(model);
        }
        if (cursor != null) {
            cursor.close();
        }
        return listdata;
    }


}
