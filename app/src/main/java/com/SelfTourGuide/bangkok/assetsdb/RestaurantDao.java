package com.SelfTourGuide.bangkok.assetsdb;

import android.database.Cursor;
import android.util.Log;

import com.SelfTourGuide.bangkok.model.RestaurantModel;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

/**
 * userinfo增删改查等功能的使用
 */
public class RestaurantDao {
	private static final String TAG = com.SelfTourGuide.bangkok.assetsdb.RestaurantDao.class.getSimpleName();

	public RestaurantDao() {

	}

	/**
	 * 查询预览数据
	 * @return
	 */
	public ArrayList<RestaurantModel> queryAll() {
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("beijingdata.db");
		ArrayList<RestaurantModel> list = new ArrayList<RestaurantModel>();
		String sql="select * from restaurant";
		try {
			Cursor cursor = db.rawQuery(sql, null);//防止没有这个表，导致崩溃
			list = parseCursorToList(cursor);
		} catch (Exception e) {
			Log.i(TAG, "queryAll: "+e);
		}
		return list;
	}

	//根据语言查询景点数据
	public ArrayList<RestaurantModel> queryLanguage(String language) {
		Log.e("tag",language);

		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("beijingdata.db");
		ArrayList<RestaurantModel> list = new ArrayList<RestaurantModel>();
		String sql="select * from restaurant where language='" + language +"'";
		try {
			Cursor cursor = db.rawQuery(sql, null);//防止没有这个表，导致崩溃
			list = parseCursorToList(cursor);
		} catch (Exception e) {
			Log.i(TAG, "queryAll: "+e);
		}
		return list;
	}

	//根据id语言查询数据
	public ArrayList<RestaurantModel> querybyIDLanguage(String language, String attractionid) {
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("beijingdata.db");
		ArrayList<RestaurantModel> list = new ArrayList<RestaurantModel>();
		String sql = "select * from restaurant" + " where restaurantid='"+attractionid+"' and language='" + language + "'";
		Log.e("tag","查询餐厅："+sql);
		try {
			Cursor cursor = db.rawQuery(sql, null);//防止没有这个表，导致崩溃
			list = parseCursorToList(cursor);
		} catch (Exception e) {
			Log.i(TAG, "queryAll: "+e);
		}
		return list;
	}


	public ArrayList<RestaurantModel> parseCursorToList(Cursor cursor) {
		ArrayList<RestaurantModel> list = new ArrayList<RestaurantModel>();
		while (cursor.moveToNext()) {
			RestaurantModel model = new RestaurantModel();
			if (cursor != null && cursor.getCount() != 0) {
				model.setRestaurantid(cursor.getString(cursor.getColumnIndex("restaurantid")));
				model.setRestaurant_name(cursor.getString(cursor.getColumnIndex("restaurant_name")));
				model.setCategories(cursor.getString(cursor.getColumnIndex("categories")));
				model.setLanguage(cursor.getString(cursor.getColumnIndex("language")));
				model.setOpentime(cursor.getString(cursor.getColumnIndex("opentime")));
				model.setAddress(cursor.getString(cursor.getColumnIndex("address")));
				model.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
				model.setInstroduction(cursor.getString(cursor.getColumnIndex("introduction")));
				model.setSubway(cursor.getString(cursor.getColumnIndex("subway")));
				model.setWebsite(cursor.getString(cursor.getColumnIndex("website")));
				model.setPrice_range(cursor.getString(cursor.getColumnIndex("price_range")));
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
