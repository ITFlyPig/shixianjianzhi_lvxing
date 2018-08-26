package com.SelfTourGuide.singapore.assetsdb;

import android.database.Cursor;
import android.util.Log;

import com.SelfTourGuide.singapore.model.AttractionModel;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

/**
 * userinfo增删改查等功能的使用
 */
public class AttractionsDao {
	private static final String TAG = com.SelfTourGuide.singapore.assetsdb.AttractionsDao.class.getSimpleName();

	public AttractionsDao() {

	}

	/**
	 * 查询预览数据
	 * @return
	 */
	public ArrayList<AttractionModel> queryAll() {
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("beijingdata.db");
		ArrayList<AttractionModel> list = new ArrayList<AttractionModel>();
		String sql="select * from attraction";
		try {
			Cursor cursor = db.rawQuery(sql, null);//防止没有这个表，导致崩溃
			list = parseCursorToList(cursor);
		} catch (Exception e) {
			Log.i(TAG, "queryAll: "+e);
		}
		return list;
	}

	//根据语言查询景点数据
	public ArrayList<AttractionModel> queryLanguage(String language) {
		Log.e("tag",language);
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("beijingdata.db");
		ArrayList<AttractionModel> list = new ArrayList<AttractionModel>();
		String sql="select * from attraction where language='" + language +"'";
		try {
			Cursor cursor = db.rawQuery(sql, null);//防止没有这个表，导致崩溃
			list = parseCursorToList(cursor);
		} catch (Exception e) {
			Log.i(TAG, "queryAll: "+e);
		}
		return list;
	}


	//根据id语言查询数据
	public ArrayList<AttractionModel> querybyIDLanguage(String language, String attractionid) {
		Log.e("tag",language);
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("beijingdata.db");
		ArrayList<AttractionModel> list = new ArrayList<AttractionModel>();
		String sql = "select * from attraction" + " where attractionid='"+attractionid+"' and language='" + language + "'";
		Log.i("tag", "getData: "+sql);
		try {
			Cursor cursor = db.rawQuery(sql, null);//防止没有这个表，导致崩溃
			list = parseCursorToList(cursor);
		} catch (Exception e) {
			Log.i(TAG, "queryAll: "+e);
		}
		return list;
	}

	public ArrayList<AttractionModel> parseCursorToList(Cursor cursor) {
		ArrayList<AttractionModel> list = new ArrayList<AttractionModel>();
		while (cursor.moveToNext()) {
			AttractionModel model = new AttractionModel();
			if (cursor != null && cursor.getCount() != 0) {
				model.setAttractionid(cursor.getString(cursor.getColumnIndex("attractionid")));
				model.setAttraction_name(cursor.getString(cursor.getColumnIndex("attraction_name")));
				model.setLanguage(cursor.getString(cursor.getColumnIndex("language")));
				model.setOpentime(cursor.getString(cursor.getColumnIndex("opentime")));
				model.setHowlong(cursor.getString(cursor.getColumnIndex("howlong")));
				model.setTicket_price(cursor.getString(cursor.getColumnIndex("ticket_price")));
				model.setAddress(cursor.getString(cursor.getColumnIndex("address")));
				model.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
				model.setInstroduction(cursor.getString(cursor.getColumnIndex("introduction")));
				model.setSubwag(cursor.getString(cursor.getColumnIndex("subway")));
				model.setWebsite(cursor.getString(cursor.getColumnIndex("website")));
				model.setLogoid(cursor.getString(cursor.getColumnIndex("logoid")));
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
