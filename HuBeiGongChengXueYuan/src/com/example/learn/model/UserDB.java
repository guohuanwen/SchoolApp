package com.example.learn.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.learn.db.UserOpenHelper;

public class UserDB {
	private static final String DBName = "User";
	private static final int Verson = 1;
	private static UserDB userDB;
	private SQLiteDatabase db;

	private UserDB(Context context) {
		Log.i("UserDB", "UserDB");
		UserOpenHelper userOpenHelper = new UserOpenHelper(context, DBName,
				null, Verson);
		db = userOpenHelper.getWritableDatabase();
	}

	public synchronized static UserDB getInstance(Context context) {
		Log.i("UserDB", "getInstance");
		if (userDB == null) {
			userDB = new UserDB(context);
		}
		return userDB;
	}

	/**
	 * 删除表
	 * 
	 * @param tableName
	 */
	public void del(String tableName) {
		db.execSQL("DELETE FROM " + tableName);
	}

	//保存用户数据
	public void saveUserNub(String id, String password) {
		Log.i("UserDB", "saveUserNub" + id + password);
		if (id != null && password != null) {
			if ("".equals(id) != true && "".equals(password) != true) {
				Log.i("UserDB", id + password);
				ContentValues contentValues = new ContentValues();
				contentValues.put("studentID", id);
				contentValues.put("password", password);
				db.insert("UserNumber", null, contentValues);
			}
		}
	}

	public List loadUserNub(String id) {
		List list = new ArrayList<String>();
		Cursor cursor = db.query("UserNumber", null, null, null, null, null,
				null);
		if (id != null) {
			if (id.equals("first")) {
				if (cursor.moveToFirst()) {
					String studentID = cursor.getColumnName(cursor
							.getColumnIndex("studentID"));
					list.add(studentID);
					list.add(cursor.getColumnName(cursor
							.getColumnIndex("password")));
				}
			}

			else if (cursor.moveToFirst()) {
				do {
					String studentID = cursor.getColumnName(cursor
							.getColumnIndex("studentID"));
					if (id != null && id.equals(studentID)) {
						list.add(studentID);
						list.add(cursor.getColumnName(cursor
								.getColumnIndex("password")));
					}
				} while (cursor.moveToNext());
			}
		}
		return list;
	}

	public List loadUser() {
		List list = new ArrayList<String>();
		Cursor cursor = db.query("UserNumber", null, null, null, null, null,
				null);
		if (cursor.moveToFirst()) {
			String studentID = cursor.getString(cursor
					.getColumnIndex("studentID"));
			list.add(studentID);
			list.add(cursor.getString(cursor.getColumnIndex("password")));
		}
		return list;

	}
	
	public void saveNowWeek(String week,String nowTime){
		del("NowWeek");
		ContentValues contentValues=new ContentValues();
		contentValues.put("week", week);
		contentValues.put("nowTime", nowTime);
		db.insert("NowWeek", null, contentValues);
	}
	
	public String[] loadNowWeek(){
		String nowWeek[]={"0","0"};
		Cursor cursor=db.query("NowWeek", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			nowWeek[0]=cursor.getString((cursor.getColumnIndex("week")));
			nowWeek[1]=cursor.getString((cursor.getColumnIndex("nowTime")));
		}
		return nowWeek;
	}

}
