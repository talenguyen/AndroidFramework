package com.talenguyen.androidframework.module.database.contentprovider;

import com.talenguyen.androidframework.module.database.ITable;
import com.talenguyen.androidframework.module.database.SQLiteHelper;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

public class ContentProviderUtil {

	private static String sBaseUri;

	private static ContentResolver sContentResolver;

	private static Context sContext;

	public static void init(Context context, String authority) {
		sBaseUri = "content://" + authority;
		sContentResolver = context.getContentResolver();
		sContext = context;
	}

	/**
	 * Deletes row(s) specified by a content URI. If the content provider
	 * supports transactions, the deletion will be atomic.
	 * 
	 * @param tableClass
	 *            The table to be deleted
	 * @param where
	 *            A filter to apply to rows before deleting, formatted as an SQL
	 *            WHERE clause (excluding the WHERE itself).
	 * @param selectionArgs
	 *            You may include ?s in selection, which will be replaced by the
	 *            values from selectionArgs, in the order that they appear in
	 *            the selection. The values will be bound as Strings.
	 * @return The number of rows deleted.
	 */
	public static int delete(Class<? extends ITable> tableClass, String where,
			String[] selectionArgs) {
		// Verify required first
		verify();
		return sContentResolver
				.delete(getUri(tableClass), where, selectionArgs);
	}

	/**
	 * Inserts a row into a table. If the content provider supports transactions
	 * the insertion will be atomic.
	 * 
	 * @param item
	 *            The item to be inserted
	 * @return the URL of the newly created row.
	 */
	public static Uri insert(ITable item) {
		// Verify required first
		verify();

		ContentValues values;
		try {
			values = SQLiteHelper.convert2ContentValues(item);
			if (values != null) {
				return sContentResolver.insert(getUri(item.getClass()), values);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Update row(s) in a content URI. If the content provider supports
	 * transactions the update will be atomic.
	 * 
	 * @param item
	 *            The item to be updated
	 * @return the number of rows updated.
	 * @throws Throws
	 *             NullPointerException if uri or values are null
	 */
	public static int update(ITable item) {
		// Verify required first
		verify();

		ContentValues values;
		try {
			values = SQLiteHelper.convert2ContentValues(item);
			if (values != null) {
				return sContentResolver.update(getUri(item.getClass()), values,
						"_id LIKE ?", new String[] { String.valueOf(item.get_id()) });
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Deletes row(s) specified by a content URI. If the content provider
	 * supports transactions, the deletion will be atomic.
	 * 
	 * @return The number of rows deleted.
	 */
	public static int delete(ITable... items) {
		if (items == null || items.length == 0) {
			return 0;
		}

		// Verify required first
		verify();

		// Build the where and selection args.
		final StringBuilder sb = new StringBuilder("_id in (");
		final String[] ids = new String[items.length];
		for (int i = 0; i < items.length; i++) {
			if (i == items.length - 1) {
				sb.append("?)");
			} else {
				sb.append("?, ");
			}
			ids[i] = String.valueOf(items[i].get_id());
		}
		return sContentResolver.delete(getUri(items[0].getClass()),
				sb.toString(), ids);
	}

	/**
	 * Query the given URI, returning a Cursor over the result set. For best
	 * performance, the caller should follow these guidelines: Provide an
	 * explicit projection, to prevent reading data from storage that aren't
	 * going to be used. Use question mark parameter markers such as 'phone=?'
	 * instead of explicit values in the selection parameter, so that queries
	 * that differ only by those values will be recognized as the same for
	 * caching purposes.
	 * 
	 * @param tableClass
	 *            The table where perform query
	 * @param selection
	 *            A filter declaring which rows to return, formatted as an SQL
	 *            WHERE clause (excluding the WHERE itself). Passing null will
	 *            return all rows for the given URI.
	 * @param selectionArgs
	 *            You may include ?s in selection, which will be replaced by the
	 *            values from selectionArgs, in the order that they appear in
	 *            the selection. The values will be bound as Strings.
	 * @param sortOrder
	 *            How to order the rows, formatted as an SQL ORDER BY clause
	 *            (excluding the ORDER BY itself). Passing null will use the
	 *            default sort order, which may be unordered.
	 * @return A Cursor object, which is positioned before the first entry, or
	 *         null
	 */
	public static Cursor query(Class<? extends ITable> tableClass,
			String selection, String[] selectionArgs, String sortOrder) {
		// Verify required first
		verify();
		return sContentResolver.query(getUri(tableClass), null, selection,
				selectionArgs, sortOrder);
	}

	/**
	 * 
	 * @param tableClass
	 *            The table where perform query
	 * @param selection
	 *            A filter declaring which rows to return, formatted as an SQL
	 *            WHERE clause (excluding the WHERE itself). Passing null will
	 *            return all rows for the given URI.
	 * @param selectionArgs
	 *            You may include ?s in selection, which will be replaced by the
	 *            values from selectionArgs, in the order that they appear in
	 *            the selection. The values will be bound as Strings.
	 * @param sortOrder
	 *            How to order the rows, formatted as an SQL ORDER BY clause
	 *            (excluding the ORDER BY itself). Passing null will use the
	 *            default sort order, which may be unordered.
	 * @return
	 */
	public static CursorLoader getCursorLoader(
			Class<? extends ITable> tableClass, String selection,
			String[] selectionArgs, String sortOrder) {
		verify();

		return new CursorLoader(sContext, getUri(tableClass), null, selection,
				selectionArgs, sortOrder);
	}

	private static void verify() {
		if (sContext == null || sContentResolver == null) {
			throw new NullPointerException(
					"Call init(Context context, String authority) first");
		}
	}

	private static Uri getUri(Class<?> table) {
		return Uri.parse(sBaseUri + "/" + table.getSimpleName());
	}
}
