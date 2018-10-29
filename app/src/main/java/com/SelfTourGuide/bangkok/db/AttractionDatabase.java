package com.SelfTourGuide.bangkok.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.SelfTourGuide.bangkok.model.AttractionModel;
import com.SelfTourGuide.bangkok.util.LogUtil;

import java.util.ArrayList;


/**
 * Created by ${张志珍} on 2016/12/27
 * Project_NameDemoMusic
 * Package_Namecom.example.amd.demomusic.db
 * 17:47.
 */

public class AttractionDatabase {

    private static final String TAG = com.SelfTourGuide.bangkok.db.AttractionDatabase.class.getSimpleName();
    private DatabaseHelper dbHelper;

    public AttractionDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * 添加数据
     */
    public void insert(AttractionModel model) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //插入数据SQL语句

        String sql = "insert into attraction(attraction_name,ticket_price,address,attractionid,language,type)values(?,?,?,?,?,?)";
        Object[] args = {
                model.getAttraction_name(),model.getTicket_price(),model.getAddress(),model.getAttractionid(),model.getLanguage(),model.getType()
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
            String sql = "delete from attraction";
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
    public void deleteByUrl(String language,String id ) {
        LogUtil.i("景点删除字段"+language+" - "+id);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("delete from attraction where attractionid=? and language=?", new Object[] { id,language });
        db.close();
      /*  String sql = "delete from attraction" + " where attractionid='"+id+"' and language='" + language + "'";
        LogUtil.i("sql"+sql);
        Object[] args = {language};
        db.execSQL(sql, args);
        db.close();*/
    }



    /**
     * 查询所有
     *
     * @return
     */
    public ArrayList<AttractionModel> queryAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from attraction";
        Cursor cursor = db.rawQuery(sql,null);
        ArrayList<AttractionModel> music_tables = new ArrayList<AttractionModel>();
        AttractionModel model = null;
        while (cursor.moveToNext()) {
            model = new AttractionModel();
            if (cursor != null && cursor.getCount() != 0) {
                model.setAttraction_name(cursor.getString(cursor.getColumnIndex("attraction_name")));
                model.setTicket_price(cursor.getString(cursor.getColumnIndex("ticket_price")));
                model.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                model.setAttractionid(cursor.getString(cursor.getColumnIndex("attractionid")));
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

    //根据id查询数据
    public ArrayList<AttractionModel> queryByUrl(String language, String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<AttractionModel> list = new ArrayList<AttractionModel>();
//        String sql="select * from attraction where language='" + language +"'";
        String sql = "select * from attraction" + " where attractionid='"+id+"' and language='" + language + "'";
        Log.i(TAG, "根据id和语言查询: "+sql);
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<AttractionModel> listdata = new ArrayList<AttractionModel>();
        AttractionModel model = null;
        while (cursor.moveToNext()) {
            model = new AttractionModel();
            if (cursor != null && cursor.getCount() != 0) {
                model.setAttraction_name(cursor.getString(cursor.getColumnIndex("attraction_name")));
                model.setTicket_price(cursor.getString(cursor.getColumnIndex("ticket_price")));
                model.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                model.setAttractionid(cursor.getString(cursor.getColumnIndex("attractionid")));
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

    //根据id查询数据
    public ArrayList<AttractionModel> queryByid(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<AttractionModel> list = new ArrayList<AttractionModel>();
//        String sql="select * from attraction where language='" + language +"'";
        String sql = "select * from attraction" + " where attractionid='"+id+"' ";
        Log.i(TAG, "根据id和语言查询: "+sql);
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<AttractionModel> listdata = new ArrayList<AttractionModel>();
        AttractionModel model = null;
        while (cursor.moveToNext()) {
            model = new AttractionModel();
            if (cursor != null && cursor.getCount() != 0) {
                model.setAttraction_name(cursor.getString(cursor.getColumnIndex("attraction_name")));
                model.setTicket_price(cursor.getString(cursor.getColumnIndex("ticket_price")));
                model.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                model.setAttractionid(cursor.getString(cursor.getColumnIndex("attractionid")));
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
