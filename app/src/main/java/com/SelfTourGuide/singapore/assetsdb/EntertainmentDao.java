package com.SelfTourGuide.singapore.assetsdb;

import android.database.Cursor;
import android.util.Log;

import com.SelfTourGuide.singapore.model.EntertainmentModel;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

/**
 * userinfo增删改查等功能的使用
 */
public class EntertainmentDao {
	private static final String TAG = com.SelfTourGuide.singapore.assetsdb.EntertainmentDao.class.getSimpleName();

	public EntertainmentDao() {

	}

	/**
	 * 查询预览数据
	 * @return
	 */
	public ArrayList<EntertainmentModel> queryAll() {
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("beijingdata.db");
		ArrayList<EntertainmentModel> list = new ArrayList<EntertainmentModel>();
		String sql="select * from entertainment";
		try {
			Cursor cursor = db.rawQuery(sql, null);//防止没有这个表，导致崩溃
			list = parseCursorToList(cursor);
		} catch (Exception e) {
			Log.i(TAG, "queryAll: "+e);
		}
		return list;
	}

	//根据语言查询景点数据
	public ArrayList<EntertainmentModel> queryLanguage(String language) {
		Log.e("tag",language);

		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("beijingdata.db");
		ArrayList<EntertainmentModel> list = new ArrayList<EntertainmentModel>();
		String sql="select * from entertainment where language='" + language +"'";
		try {
			Cursor cursor = db.rawQuery(sql, null);//防止没有这个表，导致崩溃
			list = parseCursorToList(cursor);
		} catch (Exception e) {
			Log.i(TAG, "queryAll: "+e);
		}
		return list;
	}

	//根据id语言查询数据
	public ArrayList<EntertainmentModel> querybyIDLanguage(String language, String attractionid) {
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("beijingdata.db");
		ArrayList<EntertainmentModel> list = new ArrayList<EntertainmentModel>();
		String sql = "select * from entertainment" + " where entertainmentid='"+attractionid+"' and language='" + language + "'";
		try {
			Cursor cursor = db.rawQuery(sql, null);//防止没有这个表，导致崩溃
			list = parseCursorToList(cursor);
		} catch (Exception e) {
			Log.i(TAG, "queryAll: "+e);
		}
		return list;
	}


	public ArrayList<EntertainmentModel> parseCursorToList(Cursor cursor) {
		ArrayList<EntertainmentModel> list = new ArrayList<EntertainmentModel>();
		while (cursor.moveToNext()) {
			EntertainmentModel model = new EntertainmentModel();
			if (cursor != null && cursor.getCount() != 0) {
				model.setEntertainment_name(cursor.getString(cursor.getColumnIndex("entertainment_name")));
				model.setEntertainmentid(cursor.getString(cursor.getColumnIndex("entertainmentid")));
				model.setLanguage(cursor.getString(cursor.getColumnIndex("language")));
				model.setAddress(cursor.getString(cursor.getColumnIndex("address")));
				model.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
				model.setInstroduction(cursor.getString(cursor.getColumnIndex("introduction")));
				model.setSubway(cursor.getString(cursor.getColumnIndex("subway")));
				model.setWebsite(cursor.getString(cursor.getColumnIndex("website")));
				model.setCategories(cursor.getString(cursor.getColumnIndex("categories")));
				model.setOpentime(cursor.getString(cursor.getColumnIndex("opentime")));
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
