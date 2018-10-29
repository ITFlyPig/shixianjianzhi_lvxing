package com.SelfTourGuide.bangkok.assetsdb;

import android.database.Cursor;
import android.util.Log;

import com.SelfTourGuide.bangkok.model.OverviewModel;

import java.util.ArrayList;

/**
 * userinfo增删改查等功能的使用
 */
public class OverViewDao {
	private static final String TAG = com.SelfTourGuide.bangkok.assetsdb.OverViewDao.class.getSimpleName();

	public OverViewDao() {

	}

	/**
	 * 查询预览数据
	 * @return
	 */
	public ArrayList<OverviewModel> queryAll() {
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		net.sqlcipher.database.SQLiteDatabase db = assetsDatabaseManager.getDatabase("beijingdata.db");
		ArrayList<OverviewModel> list = new ArrayList<OverviewModel>();
		String sql="select * from overview";
		try {
			Cursor cursor = db.rawQuery(sql, null);//防止没有这个表，导致崩溃
			list = parseSubwayCursorToList(cursor);
		} catch (Exception e) {
			Log.i(TAG, "queryAll: "+e);
		}
		return list;
	}

	//根据语言查询预览数据
	public ArrayList<OverviewModel> queryLanguage(String language) {
		Log.e("tag",language);

		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		net.sqlcipher.database.SQLiteDatabase db = assetsDatabaseManager.getDatabase("beijingdata.db");
		ArrayList<OverviewModel> list = new ArrayList<OverviewModel>();
		String sql="select * from overview where language='" + language +"'";
		try {
			Cursor cursor = db.rawQuery(sql, null);//防止没有这个表，导致崩溃
			list = parseSubwayCursorToList(cursor);
		} catch (Exception e) {
			Log.i(TAG, "queryAll: "+e);
		}
		return list;

	}

	public ArrayList<OverviewModel> parseSubwayCursorToList(Cursor cursor) {
		ArrayList<OverviewModel> list = new ArrayList<OverviewModel>();
		while (cursor.moveToNext()) {
			OverviewModel overviewModel = new OverviewModel();
			if (cursor != null && cursor.getCount() != 0) {
				overviewModel.setLanguage(cursor.getString(cursor.getColumnIndex("language")));
				overviewModel.setDescription(cursor.getString(cursor.getColumnIndex("description")));
				list.add(overviewModel);
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
