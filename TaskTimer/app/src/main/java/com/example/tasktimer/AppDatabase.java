package com.example.tasktimer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class AppDatabase extends SQLiteOpenHelper {
	private static final String TAG = "AppDatabase";

	public static final String DATABASE_NAME = "TaskTimer.db";
	public static final int DATABASE_VERSION = 1;

	private static AppDatabase instance = null;

	private AppDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.d(TAG, "AppDatabase: constructor");
	}

	static AppDatabase getInstance(Context context){
		if(instance == null){
			Log.d(TAG, "getInstance: creating new instance");
			instance = new AppDatabase(context);
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate: Starts");
		String sSQL;
	//	sSQL = "CREATE TABLE Tasks (_id INTEGER PRIMARY KEY NOT NULL, Name TEXT NOT NULL, Description TEXT, SortOrder INTEGER);";
		sSQL = "CREATE TABLE " + TasksContract.TABLE_NAME + " ("
				+ TasksContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
				+ TasksContract.Columns.TASKS_NAME + " TEXT NOT NULL, "
				+ TasksContract.Columns.TASKS_DESCRIPTION + " TEXT, "
				+ TasksContract.Columns.TASKS_SORTORDER + " INTEGER);";
		db.execSQL(sSQL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "onUpgrade: starts");
	}


}
