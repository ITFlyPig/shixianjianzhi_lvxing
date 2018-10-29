package com.SelfTourGuide.bangkok.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ${张志珍} on 2016/12/27
 * Project_NameDemoMusic
 * Package_Namecom.example.amd.demomusic.db
 * 17:39.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //数据库名字
    private static final String DB_NAME = "collection.db";

    //本版号
    private static final int DB_VERSION = 1;



    //创建景点
    public static final String CREATE_TABLE_ATTRACTION = "create table attraction ("

            + "_id integer primary key autoincrement, attraction_name text,ticket_price text,address text,attractionid text,language text,type text)";
    //创建娱乐
    public static final String CREATE_TABLE_ENTERTAINMENT = "create table entertainment ("

            + "_id integer primary key autoincrement, entertainment_name text,categories text,address text,entertainmentid text,language text,type text)";
    //创建酒店
    public static final String CREATE_TABLE_HOTEL = "create table hotel ("

            + "_id integer primary key autoincrement, hotel_name text,star_rating text,address text,hotelid text,language text,type text)";
    //创建表餐厅
    public static final String CREATE_TABLE_RESTAURANT= "create table restaurant ("

            + "_id integer primary key autoincrement, restaurant_name text,categories text,price_range text,restaurantid text,language text,type text)";

    //创建表购物
    public static final String CREATE_TABLE_SHOPPING= "create table shopping ("

            + "_id integer primary key autoincrement, shopping_name text,categories text,address text,shoppingid text,language text,type text)";
    //创建表
    public static final String CREATE_TABLE_ALL = "create table alltable ("

            + "_id integer primary key autoincrement, allname text,type1 text,type2 text,typeid text,language text,type text)";

    //删除表
    private static final String DROP_TABLE_NOTE = "drop table if exists alltable";




    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQLiteDatabase 用于操作数据库的工具类


        db.execSQL(CREATE_TABLE_ATTRACTION);
        db.execSQL(CREATE_TABLE_ALL);
        db.execSQL(CREATE_TABLE_ENTERTAINMENT);
        db.execSQL(CREATE_TABLE_HOTEL);
        db.execSQL(CREATE_TABLE_RESTAURANT);
        db.execSQL(CREATE_TABLE_SHOPPING);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_NOTE);
        db.execSQL(CREATE_TABLE_ALL);
    }

}
