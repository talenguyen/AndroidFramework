package com.talenguyen.androidframework.module.database.contentprovider;

import com.talenguyen.androidframework.module.database.ITable;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class ContentProviderUtil {

	private static String sBaseUri;
	private static ContentResolver sContentResolver;
	
	public static void init(Context context, String authority) {
		sBaseUri = "content://" + authority;
		sContentResolver = context.getContentResolver();
	}
	
	public static Cursor query(Class<? extends ITable> tableClass, String selection, String[] selectionArgs, String sortOrder) {
		// Verify required first 
		verify();
		return sContentResolver.query(getUri(tableClass), null, selection, selectionArgs, sortOrder);
	}
	
	private static void verify() {
        if (sContentResolver == null) {
            throw new NullPointerException("Call init(Context context, String authority) first");
        }
    }
	
	private static Uri getUri(Class<?> table) {
		return Uri.parse(sBaseUri + "/" + table.getSimpleName());
	}
}
