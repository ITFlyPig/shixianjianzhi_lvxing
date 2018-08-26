package com.SelfTourGuide.singapore.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.SelfTourGuide.singapore.model.EntertainmentModel;
import com.SelfTourGuide.singapore.util.LogUtil;

import java.util.ArrayList;


/**
 * Created by ${张志珍} on 2016/12/27
 * Project_NameDemoMusic
 * Package_Namecom.example.amd.demomusic.db
 * 17:47.
 */

public class EntertainmentDatabase {

    private static final String TAG = com.SelfTourGuide.singapore.db.EntertainmentDatabase.class.getSimpleName();
    private DatabaseHelper dbHelper;

    public EntertainmentDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * 添加数据
     */
    public void insert(EntertainmentModel model) {
        LogUtil.i("娱乐插入"+model);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //插入数据SQL语句
        String sql = "insert into entertainment(entertainment_name,categories,address,entertainmentid,language,type)values(?,?,?,?,?,?)";
        Object[] args = {
                model.getEntertainment_name(),model.getCategories(),model.getAddress(),model.getEntertainmentid(),model.getLanguage(),model.getType()
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
            String sql = "delete from entertainment";
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
        db.execSQL("delete from entertainment where entertainmentid=? and language=?", new Object[] { id,language });
        db.close();
      /*  String sql = "delete from entertainment where language = ?";
        Object[] args = {language};
        db.execSQL(sql, args);
        db.close();*/
    }



    /**
     * 查询所有
     *
     * @return
     */
    public ArrayList<EntertainmentModel> queryAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from entertainment";
        Cursor cursor = db.rawQuery(sql,null);
        ArrayList<EntertainmentModel> music_tables = new ArrayList<EntertainmentModel>();
        EntertainmentModel model = null;
        while (cursor.moveToNext()) {
            model = new EntertainmentModel();
            if (cursor != null && cursor.getCount() != 0) {
                model.setEntertainment_name(cursor.getString(cursor.getColumnIndex("entertainment_name")));
                model.setCategories(cursor.getString(cursor.getColumnIndex("categories")));
                model.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                model.setEntertainmentid(cursor.getString(cursor.getColumnIndex("entertainmentid")));
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
    public ArrayList<EntertainmentModel> queryByUrl(String language, String id) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<EntertainmentModel> list = new ArrayList<EntertainmentModel>();
//        String sql="select * from entertainment where language='" + language +"'";
        String sql = "select * from entertainment" + " where entertainmentid='"+id+"' and language='" + language + "'";
        LogUtil.i("娱乐查询"+sql);
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<EntertainmentModel> listdata = new ArrayList<EntertainmentModel>();
        EntertainmentModel model = null;
        while (cursor.moveToNext()) {
            model = new EntertainmentModel();
            if (cursor != null && cursor.getCount() != 0) {
                model.setEntertainment_name(cursor.getString(cursor.getColumnIndex("entertainment_name")));
                model.setCategories(cursor.getString(cursor.getColumnIndex("categories")));
                model.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                model.setEntertainmentid(cursor.getString(cursor.getColumnIndex("entertainmentid")));
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
    public ArrayList<EntertainmentModel> queryByid(String id) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<EntertainmentModel> list = new ArrayList<EntertainmentModel>();
//        String sql="select * from entertainment where language='" + language +"'";
        String sql = "select * from entertainment" + " where entertainmentid='"+id+"' ";
        LogUtil.i("娱乐查询"+sql);
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<EntertainmentModel> listdata = new ArrayList<EntertainmentModel>();
        EntertainmentModel model = null;
        while (cursor.moveToNext()) {
            model = new EntertainmentModel();
            if (cursor != null && cursor.getCount() != 0) {
                model.setEntertainment_name(cursor.getString(cursor.getColumnIndex("entertainment_name")));
                model.setCategories(cursor.getString(cursor.getColumnIndex("categories")));
                model.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                model.setEntertainmentid(cursor.getString(cursor.getColumnIndex("entertainmentid")));
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
