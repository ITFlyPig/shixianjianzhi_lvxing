package com.SelfTourGuide.bangkok.assetsdb;

import android.database.Cursor;
import android.util.Log;

import com.SelfTourGuide.bangkok.model.HotelModel;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

/**
 * userinfo增删改查等功能的使用
 */
public class HotelDao {
	private static final String TAG = com.SelfTourGuide.bangkok.assetsdb.HotelDao.class.getSimpleName();

	public HotelDao() {

	}

	/**
	 * 查询预览数据
	 * @return
	 */
	public ArrayList<HotelModel> queryAll() {
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("beijingdata.db");
		ArrayList<HotelModel> list = new ArrayList<HotelModel>();
		String sql="select * from hotel";
		try {
			Cursor cursor = db.rawQuery(sql, null);//防止没有这个表，导致崩溃
			list = parseCursorToList(cursor);
		} catch (Exception e) {
			Log.i(TAG, "queryAll: "+e);
		}
		return list;
	}

	//根据语言查询景点数据
	public ArrayList<HotelModel> queryLanguage(String language) {
		Log.e("tag",language);

		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("beijingdata.db");
		ArrayList<HotelModel> list = new ArrayList<HotelModel>();
		String sql="select * from hotel where language='" + language +"'";
		try {
			Cursor cursor = db.rawQuery(sql, null);//防止没有这个表，导致崩溃
			list = parseCursorToList(cursor);
		} catch (Exception e) {
			Log.i(TAG, "queryAll: "+e);
		}
		return list;
	}

	//根据id语言查询数据
	public ArrayList<HotelModel> querybyIDLanguage(String language, String attractionid) {
		Log.e("hotel","hotel"+language+"--"+attractionid);
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("beijingdata.db");
		ArrayList<HotelModel> list = new ArrayList<HotelModel>();
		String sql = "select * from hotel" + " where hotelid='"+attractionid+"' and language='" + language + "'";
		Log.e("hotel",sql);
		try {
			Cursor cursor = db.rawQuery(sql, null);//防止没有这个表，导致崩溃
			list = parseCursorToList(cursor);
		} catch (Exception e) {
			Log.i(TAG, "queryAll: "+e);
		}
		return list;
	}

	public ArrayList<HotelModel> parseCursorToList(Cursor cursor) {
		ArrayList<HotelModel> list = new ArrayList<HotelModel>();
		while (cursor.moveToNext()) {
			HotelModel model = new HotelModel();
			if (cursor != null && cursor.getCount() != 0) {
				model.setHotelid(cursor.getString(cursor.getColumnIndex("hotelid")));
				model.setHotel_name(cursor.getString(cursor.getColumnIndex("hotel_name")));
				model.setStar_rating(cursor.getString(cursor.getColumnIndex("star_rating")));
				model.setLanguage(cursor.getString(cursor.getColumnIndex("language")));
				model.setAddress(cursor.getString(cursor.getColumnIndex("address")));
				model.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
				model.setInstroduction(cursor.getString(cursor.getColumnIndex("introduction")));
				model.setSubway(cursor.getString(cursor.getColumnIndex("subway")));
				model.setWebsite(cursor.getString(cursor.getColumnIndex("website")));
//				model.setPrice_range(cursor.getString(cursor.getColumnIndex("price_range")));
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
