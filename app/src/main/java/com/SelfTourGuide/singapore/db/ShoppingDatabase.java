package com.SelfTourGuide.singapore.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.SelfTourGuide.singapore.model.ShoppingModel;
import com.SelfTourGuide.singapore.util.LogUtil;

import java.util.ArrayList;


/**
 * Created by ${张志珍} on 2016/12/27
 * Project_NameDemoMusic
 * Package_Namecom.example.amd.demomusic.db
 * 17:47.
 */

public class ShoppingDatabase {

    private static final String TAG = com.SelfTourGuide.singapore.db.ShoppingDatabase.class.getSimpleName();
    private DatabaseHelper dbHelper;

    public ShoppingDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * 添加数据
     */
    public void insert(ShoppingModel model) {
        LogUtil.i("购物"+model.toString());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //插入数据SQL语句
      
        String sql = "insert into shopping(shopping_name,categories,address,shoppingid,language,type)values(?,?,?,?,?,?)";
        Object[] args = {
                model.getShopping_name(),model.getCategories(),model.getAddress(),model.getShoppingid(),model.getLanguage(),model.getType()
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
            String sql = "delete from shopping";
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
        db.execSQL("delete from  shopping where shoppingid=? and language=?", new Object[] { id,language });
        db.close();
        /*String sql = "delete from shopping where language = ?";
        Object[] args = {language};
        db.execSQL(sql, args);
        db.close();*/
    }



    /**
     * 查询所有
     *
     * @return
     */
    public ArrayList<ShoppingModel> queryAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from shopping";
        Cursor cursor = db.rawQuery(sql,null);
        ArrayList<ShoppingModel> music_tables = new ArrayList<ShoppingModel>();
        ShoppingModel model = null;
        while (cursor.moveToNext()) {
            model = new ShoppingModel();
            if (cursor != null && cursor.getCount() != 0) {

                model.setShopping_name(cursor.getString(cursor.getColumnIndex("shopping_name")));
                model.setCategories(cursor.getString(cursor.getColumnIndex("categories")));
                model.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                model.setShoppingid(cursor.getString(cursor.getColumnIndex("shoppingid")));
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
    public ArrayList<ShoppingModel> queryByUrl(String language, String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<ShoppingModel> list = new ArrayList<ShoppingModel>();
//        String sql="select * from shopping where language='" + language +"'";
        String sql = "select * from shopping" + " where shoppingid='"+id+"' and language='" + language + "'";
        LogUtil.i("购物查询"+sql);
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<ShoppingModel> listdata = new ArrayList<ShoppingModel>();
        ShoppingModel model = null;
        while (cursor.moveToNext()) {
            model = new ShoppingModel();
            if (cursor != null && cursor.getCount() != 0) {
                model.setShopping_name(cursor.getString(cursor.getColumnIndex("shopping_name")));
                model.setCategories(cursor.getString(cursor.getColumnIndex("categories")));
                model.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                model.setShoppingid(cursor.getString(cursor.getColumnIndex("shoppingid")));
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
    public ArrayList<ShoppingModel> queryByUrl(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<ShoppingModel> list = new ArrayList<ShoppingModel>();
//        String sql="select * from shopping where language='" + language +"'";
        String sql = "select * from shopping" + " where shoppingid='"+id+"' ";
        LogUtil.i("购物查询"+sql);
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<ShoppingModel> listdata = new ArrayList<ShoppingModel>();
        ShoppingModel model = null;
        while (cursor.moveToNext()) {
            model = new ShoppingModel();
            if (cursor != null && cursor.getCount() != 0) {
                model.setShopping_name(cursor.getString(cursor.getColumnIndex("shopping_name")));
                model.setCategories(cursor.getString(cursor.getColumnIndex("categories")));
                model.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                model.setShoppingid(cursor.getString(cursor.getColumnIndex("shoppingid")));
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
