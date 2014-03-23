package com.talenguyen.androidframework.module.database.contentprovider;

import java.util.List;

import com.talenguyen.androidframework.module.database.DBContract;
import com.talenguyen.androidframework.module.database.ITable;
import com.talenguyen.androidframework.module.database.SQLiteOpenHelperEx;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public abstract class ContentProviderEx extends ContentProvider {

	private SQLiteOpenHelper mSQLiteOpenHelper;
	private UriMatcher mUriMatcher;
	private List<Class<? extends ITable>> mTables;
	

	@Override
	public boolean onCreate() {
		final DBContract dbContract = getDBContract();
		if (dbContract == null) {
			throw new NullPointerException("DBContract can not be null");
		}
		
		mSQLiteOpenHelper = new SQLiteOpenHelperEx(getContext(), dbContract);
		
		mTables = dbContract.getTableClasses();
		
		if (mTables == null) {
			throw new NullPointerException("List<ITable> can not be null");
		}
		
		final String authority = getAuthority();
		
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		
		for (int i = 0, count = mTables.size(); i < count; i++) {
			final Class<?> table = mTables.get(i);
			mUriMatcher.addURI(authority, table.getSimpleName(), i);
			mUriMatcher.addURI(authority, table.getSimpleName() + "/#", i);
		}
		
		return true;
	}
	 
	/**
	 * @return The <b>authority</b> of {@link ContentProvider}
	 */
	public abstract String getAuthority();
	
	/**
	 * @return The {@link DBContract} object
	 */
	public abstract DBContract getDBContract();
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count;
		final int matchedIndex = mUriMatcher.match(uri);
		if (matchedIndex < 0) {
			// In case there is no match
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		final String table = mTables.get(matchedIndex).getSimpleName();
		
		// Opens the database object in "write" mode.
		final SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
		

		final long id = ContentUris.parseId(uri);
		 
		if (id == -1) {
			count = db.delete(table, selection, selectionArgs);
		} else {
			// Performs the delete.
			count = db.delete(table, "_id LIKE ?", new String[]{String.valueOf(id)});
		}

		/*
		 * Gets a handle to the content resolver object for the current context,
		 * and notifies it that the incoming URI changed. The object passes this
		 * along to the resolver framework, and observers that have registered
		 * themselves for the provider are notified.
		 */
		getContext().getContentResolver().notifyChange(uri, null);
		// Returns the number of rows deleted.
		return count;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		final int matchedIndex = mUriMatcher.match(uri);
		if (matchedIndex < 0) {
			// In case there is no match
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		final String table = mTables.get(matchedIndex).getSimpleName();
		
		// Opens the database object in "write" mode.
		final SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
		
		// Performs the insert and returns the ID of the new row.
		long rowId = db.insert(table, null, values);
		// If the insert succeeded, the row ID exists.
		if (rowId > 0) {
			// Creates a URI with the item ID pattern and the new row ID
			// appended to it.
			final Uri rowUri = ContentUris.withAppendedId(uri, rowId);
			// Notifies observers registered against this provider that the data
			// changed.
			getContext().getContentResolver().notifyChange(rowUri, null);
			return rowUri;
		}
		// If the insert didn't succeed, then the rowID is <= 0. Throws an
		// exception.
		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		final int matchedIndex = mUriMatcher.match(uri);
		if (matchedIndex < 0) {
			// In case there is no match
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		final String table = mTables.get(matchedIndex).getSimpleName();
		
		final long id = ContentUris.parseId(uri);
		 
		// Constructs a new query builder and sets its table name
		final SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(table);
		if (id != -1) {
			/*
			 * If the incoming URI is for a single item identified by its ID,
			 * chooses the item ID projection, and appends "_ID = <id>" to the
			 * where clause, so that it selects that single item.
			 */
			qb.appendWhere("_id = " + id);
		}
		
		// Opens the database object in "read" mode, since no writes need to be
		// done.
		final SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
		
		/*
		 * Performs the query. If no problems occur trying to read the database,
		 * then a Cursor object is returned; otherwise, the cursor variable
		 * contains null. If no records were selected, then the Cursor object is
		 * empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, sortOrder);
		// Tells the Cursor what URI to watch, so it knows when its source data
		// changes
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int count;
		final int matchedIndex = mUriMatcher.match(uri);
		if (matchedIndex < 0) {
			// In case there is no match
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		final String table = mTables.get(matchedIndex).getSimpleName();
		
		// Opens the database object in "write" mode.
		final SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
		

		final long id = ContentUris.parseId(uri);
		 
		if (id == -1) {
			// Does the update and returns the number of rows updated.
			count = db.update(table, // The database table name.
					values, // A map of column names and new values to use.
					selection, // The where clause column names.
					selectionArgs // The where clause column values to select on.
					);
		} else {
			// Performs the delete.
			count = db.update(table, values, "_id LIKE ?", new String[]{String.valueOf(id)});
		}
		/*
		 * Gets a handle to the content resolver object for the current context,
		 * and notifies it that the incoming URI changed. The object passes this
		 * along to the resolver framework, and observers that have registered
		 * themselves for the provider are notified.
		 */
		getContext().getContentResolver().notifyChange(uri, null);
		// Returns the number of rows updated.
		return count;
	}

}
