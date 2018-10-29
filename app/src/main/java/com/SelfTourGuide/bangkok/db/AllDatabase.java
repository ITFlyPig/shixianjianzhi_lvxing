package com.SelfTourGuide.bangkok.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.SelfTourGuide.bangkok.model.AllModel;
import com.SelfTourGuide.bangkok.util.LogUtil;

import java.util.ArrayList;


/**
 * Created by ${张志珍} on 2016/12/27
 * Project_NameDemoMusic
 * Package_Namecom.example.amd.demomusic.db
 * 17:47.
 */

public class AllDatabase {

    private static final String TAG = com.SelfTourGuide.bangkok.db.AllDatabase.class.getSimpleName();
    private DatabaseHelper dbHelper;

    public AllDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * 添加数据
     */
    public void insert(AllModel model) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        LogUtil.i("insert"+model.toString());
        //插入数据SQL语句
        String sql = "insert into alltable(allname,type1,type2,typeid,language,type)values(?,?,?,?,?,?)";
        Object[] args = {
                model.getAllname(),model.getType1(),model.getType2(),model.getTypeid(),model.getLanguage(),model.getType()
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
            String sql = "delete from alltable";
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
  /*  public void deleteByUrl(String language,String type,String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
       *//* String sql = "delete from alltable where language = ?";
        Object[] args = {language};
        db.execSQL(sql, args);*//*
        db.execSQL("delete from  alltable where typeid=? and language=? and type=?", new Object[] { id,language,type });
        db.close();
    }*/

    /**
     * 删除数据
     *
     */
    public void deleteByUrl(String type,String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
       /* String sql = "delete from alltable where language = ?";
        Object[] args = {language};
        db.execSQL(sql, args);*/
        db.execSQL("delete from  alltable where  typeid=? and type=?", new Object[] { id,type });
        db.close();
    }


    /**
     * 查询所有
     *
     * @return
     */
    public ArrayList<AllModel> queryAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from alltable";
        Cursor cursor = db.rawQuery(sql,null);
        ArrayList<AllModel> music_tables = new ArrayList<AllModel>();
        AllModel model = null;
        while (cursor.moveToNext()) {
            model = new AllModel();
            if (cursor != null && cursor.getCount() != 0) {
                model.setAllname(cursor.getString(cursor.getColumnIndex("allname")));
                model.setType1(cursor.getString(cursor.getColumnIndex("type1")));
                model.setType2(cursor.getString(cursor.getColumnIndex("type2")));
                model.setTypeid(cursor.getString(cursor.getColumnIndex("typeid")));
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
    public ArrayList<AllModel> queryByUrl(String language, String id, String type) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<AllModel> list = new ArrayList<AllModel>();
//        String sql="select * from alltable where language='" + language +"'";
        String sql = "select * from alltable" + " where typeid='"+id+"' and language='" + language + "'" + "  and type='" + type + "'";
        Log.i(TAG, "queryByUrl: "+sql);
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<AllModel> listdata = new ArrayList<AllModel>();
        AllModel model = null;
        while (cursor.moveToNext()) {
            model = new AllModel();
            if (cursor != null && cursor.getCount() != 0) {
                model.setAllname(cursor.getString(cursor.getColumnIndex("allname")));
                model.setType1(cursor.getString(cursor.getColumnIndex("type1")));
                model.setType2(cursor.getString(cursor.getColumnIndex("type2")));
                model.setTypeid(cursor.getString(cursor.getColumnIndex("typeid")));
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
