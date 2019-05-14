package com.example.tasktimer;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class AppProvider extends ContentProvider {
	private static final String TAG = "AppProvider";

	private AppDatabase openHelper;

	public static final UriMatcher uriMatcher = buildUriMacher();

	static final String CONTENT_AUTHORITY = "com.example.tasktimer.provider";
	public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

	public static final int TASKS =100;
	public static final int TASKS_ID = 101;

	public static final int TIMINGS = 200;
	public static final int TIMINGS_ID = 201;

	public static final int TASK_DURATIONS = 400;
	public static final int TASK_DURATIONS_ID = 401;

	private static UriMatcher buildUriMacher(){
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

		matcher.addURI(CONTENT_AUTHORITY, TasksContract.TABLE_NAME, TASKS);
		matcher.addURI(CONTENT_AUTHORITY, TasksContract.TABLE_NAME + "/#", TASKS_ID);

//		matcher.addURI(CONTENT_AUTHORITY, TimingsContract.TABLE_NAME, TIMINGS);
//		matcher.addURI(CONTENT_AUTHORITY, imingsContract.TABLE_NAME + "/#", TIMINGS_ID);
//
//		matcher.addURI(CONTENT_AUTHORITY, DurationContract.TABLE_NAME, TASK_DURATIONS);
//		matcher.addURI(CONTENT_AUTHORITY, DurationContract.TABLE_NAME + "/#", TASK_DURATIONS_ID);

		return matcher;
	}


	@Override
	public boolean onCreate() {
		openHelper = AppDatabase.getInstance(getContext());
		return true;
	}

	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
		Log.d(TAG, "query: called with URI " + uri);
		final int match = uriMatcher.match(uri);
		Log.d(TAG, "query: match is " + match);

		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		switch (match){
			case TASKS:
				queryBuilder.setTables(TasksContract.TABLE_NAME);
				break;
			case TASKS_ID:
				queryBuilder.setTables(TasksContract.TABLE_NAME);
				long taskId = TasksContract.getTaskId(uri);
				queryBuilder.appendWhere(TasksContract.Columns._ID + " = " + taskId);
				break;
//			case TIMINGS:
//				queryBuilder.setTables(TimingsContract.TABLE_NAME);
//				break;
//			case TIMINGS_ID:
//				queryBuilder.setTables(TimingsContract.TABLE_NAME);
//				long timingId = TimingsContract.getTaskId(uri);
//				queryBuilder.appendWhere(TimingsContract.Columns._ID + " = " + taskId);
//				break;
//			case TASK_DURATIONS:
//				queryBuilder.setTables(DurationsContract.TABLE_NAME);
//				break;
//			case TASK_DURATIONS_ID:
//				queryBuilder.setTables(DurationsContract.TABLE_NAME);
//				long durationId = DurationsContract.getTaskId(uri);
//				queryBuilder.appendWhere(DurationsContract.Columns._ID + " = " + taskId);
//				break;

			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		SQLiteDatabase db = openHelper.getReadableDatabase();
		return queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

	}

	@Nullable
	@Override
	public String getType(@NonNull Uri uri) {
		final int match = uriMatcher.match(uri);

		switch (match) {
			case TASKS:
				return TasksContract.CONTENT_TYPE;
			case TASKS_ID:
				return TasksContract.CONTENT_ITEM_TYPE;

//			case TIMINGS:
//				return TimingsContract.CONTENT_TYPE;
//			case TIMINGS_ID:
//				return TimingsContract.CONTENT_ITEM_TYPE;
//
//			case TASK_DURATIONS:
//				return DurationContract.CONTENT_TYPE;
//			case TASK_DURATIONS_ID:
//				return DurationContract.CONTENT_ITEM_TYPE;

			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}

	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
		Log.d(TAG, "insert: called with uri:" + uri);
		final int match = uriMatcher.match(uri);
		Log.d(TAG, "match is " + match);

		final SQLiteDatabase db;

		Uri returnUri;
		long recordId;
		switch (match){
			case TASKS:
				db = openHelper.getWritableDatabase();
				recordId = db.insert(TasksContract.TABLE_NAME, null, values);
				if(recordId >= 0){ returnUri = TasksContract.buildTaskUri(recordId);
				} else {throw new android.database.SQLException("Failed to insert into " + uri.toString());}
				break;
			case TIMINGS:
//				db = openHelper.getWritableDatabase();
//				recordId = db.insert(TimingsContract.Timings.buildTimingUri(recordId));
//				if(recordId >= 0){ returnUri = TimingsContract.Timings.buildTimingUri(recordId);
//				} else {throw new android.database.SQLException("Failed to insert into " + uri.toString());}
//				break;

			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		Log.d(TAG, "Exiting insert + " + returnUri);
		return returnUri;

	}

	@Override
	public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
		return 0;
	}

	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
		return 0;
	}
}
