package com.example.tasktimer;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class TasksContract {

	public static final String TABLE_NAME = "Tasks";

	public static class Columns{
		public static final String _ID = BaseColumns._ID;
		public static final String TASKS_NAME = "Name";
		public static final String TASKS_DESCRIPTION = "Description";
		public static final String TASKS_SORTORDER = "SortOrder";

		private Columns() {
		}
	}
	public static final Uri CONTENT_URI = Uri.withAppendedPath(AppProvider.CONTENT_AUTHORITY_URI, TABLE_NAME);

	static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AppProvider.CONTENT_AUTHORITY + "." + TABLE_NAME;
	static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AppProvider.CONTENT_AUTHORITY + "." + TABLE_NAME;

	static Uri buildTaskUri(long taskId){
		return ContentUris.withAppendedId(CONTENT_URI, taskId);
	}

	static long getTaskId(Uri uri){ return ContentUris.parseId(uri); }
}
