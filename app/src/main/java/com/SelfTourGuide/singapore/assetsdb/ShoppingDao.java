package com.SelfTourGuide.singapore.assetsdb;

import android.database.Cursor;
import android.util.Log;

import com.SelfTourGuide.singapore.model.ShoppingModel;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

/**
 * userinfo增删改查等功能的使用
 */
public class ShoppingDao {
	private static final String TAG = com.SelfTourGuide.singapore.assetsdb.ShoppingDao.class.getSimpleName();

	public ShoppingDao() {

	}

	/**
	 * 查询预览数据
	 * @return
	 */
	public ArrayList<ShoppingModel> queryAll() {
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("beijingdata.db");
		ArrayList<ShoppingModel> list = new ArrayList<ShoppingModel>();
		String sql="select * from shopping";
		try {
			Cursor cursor = db.rawQuery(sql, null);//防止没有这个表，导致崩溃
			list = parseCursorToList(cursor);
		} catch (Exception e) {
			Log.i(TAG, "queryAll: "+e);
		}
		return list;
	}

	//根据语言查询景点数据
	public ArrayList<ShoppingModel> queryLanguage(String language) {
		// 通过管理对象获取数据库
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("beijingdata.db");
		ArrayList<ShoppingModel> list = new ArrayList<ShoppingModel>();
		String sql="select * from shopping where language='" + language +"'";
		Log.e("tag",sql);
		try {
			Cursor cursor = db.rawQuery(sql, null);//防止没有这个表，导致崩溃
			list = parseCursorToList(cursor);
		} catch (Exception e) {
			Log.i(TAG, "queryAll: "+e);
		}
		return list;
	}

	//根据id语言查询数据
	public ArrayList<ShoppingModel> querybyIDLanguage(String language, String attractionid) {
		Log.e("tag",language);
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("beijingdata.db");
		ArrayList<ShoppingModel> list = new ArrayList<ShoppingModel>();
		String sql = "select * from shopping" + " where shoppingid ='"+attractionid+"' and language='" + language + "'";
		try {
			Cursor cursor = db.rawQuery(sql, null);//防止没有这个表，导致崩溃
			list = parseCursorToList(cursor);
		} catch (Exception e) {
			Log.i(TAG, "queryAll: "+e);
		}
		return list;
	}


	public ArrayList<ShoppingModel> parseCursorToList(Cursor cursor) {
		ArrayList<ShoppingModel> list = new ArrayList<ShoppingModel>();
		while (cursor.moveToNext()) {
			ShoppingModel model = new ShoppingModel();
			if (cursor != null && cursor.getCount() != 0) {
				model.setShopping_name(cursor.getString(cursor.getColumnIndex("shopping_name")));
				model.setShoppingid(cursor.getString(cursor.getColumnIndex("shoppingid")));
				model.setOpentime(cursor.getString(cursor.getColumnIndex("opentime")));
				model.setAddress(cursor.getString(cursor.getColumnIndex("address")));
				model.setSubway(cursor.getString(cursor.getColumnIndex("subway")));
				model.setInstroduction(cursor.getString(cursor.getColumnIndex("introduction")));
				model.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
				model.setWebsite(cursor.getString(cursor.getColumnIndex("website")));
				model.setLanguage(cursor.getString(cursor.getColumnIndex("language")));
				model.setCategories(cursor.getString(cursor.getColumnIndex("categories")));
				list.add(model);
			}
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}



	/** 关闭数据库 */
	public synchronized void closeDB(AssetsDatabaseManager mg) {
		// TODO Auto-generated method stub
		if (mg != null) {
			mg.closeAllDatabase();
		}
	}

}
