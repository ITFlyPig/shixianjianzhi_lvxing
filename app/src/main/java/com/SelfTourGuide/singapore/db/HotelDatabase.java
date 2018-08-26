package com.SelfTourGuide.singapore.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.SelfTourGuide.singapore.model.HotelModel;
import com.SelfTourGuide.singapore.util.LogUtil;

import java.util.ArrayList;


/**
 * Created by ${张志珍} on 2016/12/27
 * Project_NameDemoMusic
 * Package_Namecom.example.amd.demomusic.db
 * 17:47.
 */

public class HotelDatabase {

    private static final String TAG = com.SelfTourGuide.singapore.db.HotelDatabase.class.getSimpleName();
    private DatabaseHelper dbHelper;

    public HotelDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * 添加数据
     */
    public void insert(HotelModel model) {

        LogUtil.i("酒店"+model.toString());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //插入数据SQL语句
        String sql = "insert into hotel(hotel_name,star_rating,address,hotelid,language,type)values(?,?,?,?,?,?)";
        Object[] args = {
                model.getHotel_name(),model.getStar_rating(),model.getAddress(),model.getHotelid(),model.getLanguage(),model.getType()
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
            String sql = "delete from hotel";
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
        db.execSQL("delete from hotel where hotelid=? and language=?", new Object[] { id,language });
        db.close();
       /* String sql = "delete from hotel where language = ?";
        Object[] args = {language};
        db.execSQL(sql, args);
        db.close();*/
    }

    /**
     * 删除数据
     *
     */
    public void deleteByUrl(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from hotel where hotelid=? ", new Object[] { id });
        db.close();
       /* String sql = "delete from hotel where language = ?";
        Object[] args = {language};
        db.execSQL(sql, args);
        db.close();*/
    }


    /**
     * 查询所有
     *
     * @return
     */
    public ArrayList<HotelModel> queryAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from hotel";
        Cursor cursor = db.rawQuery(sql,null);
        ArrayList<HotelModel> music_tables = new ArrayList<HotelModel>();
        HotelModel model = null;
        while (cursor.moveToNext()) {
            model = new HotelModel();
            if (cursor != null && cursor.getCount() != 0) {
                model.setHotel_name(cursor.getString(cursor.getColumnIndex("hotel_name")));
                model.setStar_rating(cursor.getString(cursor.getColumnIndex("star_rating")));
                model.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                model.setHotelid(cursor.getString(cursor.getColumnIndex("hotelid")));
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
    public ArrayList<HotelModel> queryByUrl(String language, String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<HotelModel> list = new ArrayList<HotelModel>();
//        String sql="select * from hotel where language='" + language +"'";
        String sql = "select * from hotel" + " where hotelid = '"+id+"' and language='" + language + "'";

        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<HotelModel> listdata = new ArrayList<HotelModel>();
        HotelModel model = null;
        while (cursor.moveToNext()) {
            model = new HotelModel();
            if (cursor != null && cursor.getCount() != 0) {
                model.setHotel_name(cursor.getString(cursor.getColumnIndex("hotel_name")));
                model.setStar_rating(cursor.getString(cursor.getColumnIndex("star_rating")));
                model.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                model.setHotelid(cursor.getString(cursor.getColumnIndex("hotelid")));
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
    public ArrayList<HotelModel> queryByUrl(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<HotelModel> list = new ArrayList<HotelModel>();
//        String sql="select * from hotel where language='" + language +"'";
        String sql = "select * from hotel" + " where hotelid = '"+id+"' ";

        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<HotelModel> listdata = new ArrayList<HotelModel>();
        HotelModel model = null;
        while (cursor.moveToNext()) {
            model = new HotelModel();
            if (cursor != null && cursor.getCount() != 0) {
                model.setHotel_name(cursor.getString(cursor.getColumnIndex("hotel_name")));
                model.setStar_rating(cursor.getString(cursor.getColumnIndex("star_rating")));
                model.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                model.setHotelid(cursor.getString(cursor.getColumnIndex("hotelid")));
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
