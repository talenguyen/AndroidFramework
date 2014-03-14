package com.talenguyen.androidframework.module.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: GIANG
 * Date: 12/22/13
 * Time: 11:25 AM
 */

public class SQLiteUtil {

    private static SQLiteOpenHelperEx mSqLiteOpenHelperEx = null;

    public static void init(Context context, DBContract contract) {
        if (mSqLiteOpenHelperEx == null) {
            mSqLiteOpenHelperEx = new SQLiteOpenHelperEx(context.getApplicationContext(), contract);
        }
    }

    /**
     * Convenience method for inserting a row into the database.
     *
     * @param item The item to insert
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public static long insert(ITable item) {
        // Verify pre-define.
        verify();
        // Gets the data repository in write mode
        final SQLiteDatabase db = mSqLiteOpenHelperEx.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        final ContentValues values = SQLiteHelper.buildContentValue(item);

        // Insert the new row, returning the primary key value of the new row
        return db.insert(item.getClass().getSimpleName(), null, values);
    }

    /**
     * Convenience method for updating rows in the database.
     *
     * @param values      a map from column names to new column values. null is a valid value that will be translated to NULL.
     * @param whereClause the optional WHERE clause to apply when updating. Passing null will update all rows.
     * @param whereArgs   You may include ?s in the where clause, which will be replaced by the values from whereArgs. The values will be bound as Strings.
     * @return the number of rows affected
     */
    public static int update(Class<? extends ITable> table, ContentValues values, String whereClause, String[] whereArgs) {
        // Verify pre-define.
        verify();

        final SQLiteDatabase db = mSqLiteOpenHelperEx.getWritableDatabase();

        // Update the database, returning the number of rows that affected.
        return db.update(table.getSimpleName(), values, whereClause, whereArgs);
    }

    /**
     * @param table       the table to delete from
     * @param whereClause the optional WHERE clause to apply when deleting. Passing null will delete all rows.
     * @param whereArgs   You may include ?s in the where clause, which will be replaced by the values from whereArgs. The values will be bound as Strings.
     * @return the number of rows affected
     */
    public static int delete(Class<? extends ITable> table, String whereClause, String[] whereArgs) {
        verify();

        final SQLiteDatabase db = mSqLiteOpenHelperEx.getWritableDatabase();

        return db.delete(table.getSimpleName(), whereClause, whereArgs);
    }

    /**
     * Query the given URL, returning a Cursor over the result set.
     *
     * @param table         The table name to compile the query against.
     * @param distinct      true if you want each row to be unique, false otherwise.
     * @param selection     A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings.
     * @param groupBy       A filter declaring how to group rows, formatted as an SQL GROUP BY clause (excluding the GROUP BY itself). Passing null will cause the rows to not be grouped.
     * @param having        A filter declare which row groups to include in the cursor, if row grouping is being used, formatted as an SQL HAVING clause (excluding the HAVING itself). Passing null will cause all row groups to be included, and is required when row grouping is not being used.
     * @param orderBy       How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort order, which may be unordered.
     * @param limit         Limits the number of rows returned by the query, formatted as LIMIT clause. Passing null denotes no LIMIT clause.
     * @return A Cursor object, which is positioned before the first entry. Note that Cursors are not synchronized, see the documentation for more details.
     */
    public static Cursor query(Class<? extends ITable> table, boolean distinct, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        verify();

        final SQLiteDatabase db = mSqLiteOpenHelperEx.getReadableDatabase();

        final ClassParser classParser = ClassParser.parse(table);
        return db.query(distinct, table.getSimpleName(), classParser.getterFields, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    /**
     * Query the given URL, returning a Cursor over the result set.
     *
     * @param table         The table name to compile the query against.
     * @param selection     A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings.
     * @return A Cursor object, which is positioned before the first entry. Note that Cursors are not synchronized, see the documentation for more details.
     */
    public static Cursor quickQuery(Class<? extends ITable> table, String selection, String[] selectionArgs) {
        return query(table, false, selection, selectionArgs, null, null, "_id asd", null);
    }

    /**
     * Runs the provided SQL and returns a Cursor over the result set.
     *
     * @param sql           the SQL query. The SQL string must not be ; terminated
     * @param selectionArgs You may include ?s in where clause in the query, which will be replaced by the values from selectionArgs. The values will be bound as Strings.
     * @return A Cursor object, which is positioned before the first entry. Note that Cursors are not synchronized, see the documentation for more details.
     */
    public static Cursor rawQuery(String sql, String[] selectionArgs) {
        verify();

        final SQLiteDatabase db = mSqLiteOpenHelperEx.getReadableDatabase();

        return db.rawQuery(sql, selectionArgs);
    }

    /**
     * Query the given URL, returning a list of <ITable> objects.
     *
     * @param table         The table name to compile the query against.
     * @param distinct      true if you want each row to be unique, false otherwise.
     * @param selection     A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings.
     * @param groupBy       A filter declaring how to group rows, formatted as an SQL GROUP BY clause (excluding the GROUP BY itself). Passing null will cause the rows to not be grouped.
     * @param having        A filter declare which row groups to include in the cursor, if row grouping is being used, formatted as an SQL HAVING clause (excluding the HAVING itself). Passing null will cause all row groups to be included, and is required when row grouping is not being used.
     * @param orderBy       How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort order, which may be unordered.
     * @param limit         Limits the number of rows returned by the query, formatted as LIMIT clause. Passing null denotes no LIMIT clause.
     * @return A list of <ITable> objects.
     */
    public static List<? extends ITable> queryObjects(Class<? extends ITable> table, boolean distinct, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {

        final Cursor cursor = query(table, distinct, selection, selectionArgs, groupBy, having, orderBy, limit);

        return parseCursor(table, cursor);
    }

    /**
     * Query the given URL, returning a list of <ITable> objects.
     *
     * @param table         The table name to compile the query against.
     * @param selection     A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings.
     * @return A list of <? extends ITable> objects.
     */
    public static List<? extends ITable> quickQueryObjects(Class<? extends ITable> table, String selection, String[] selectionArgs) {
        return queryObjects(table, false, selection, selectionArgs, null, null, "_id asc", null);
    }

    /**
     * Runs the provided SQL and returns a list of T objects.
     *
     * @param table         The table name to compile the query against.
     * @param sql           the SQL query. The SQL string must not be ; terminated
     * @param selectionArgs You may include ?s in where clause in the query, which will be replaced by the values from selectionArgs. The values will be bound as Strings.
     * @return A list of <ITable> objects.
     */
    public static List<? extends ITable> rawQueryObjects(Class<? extends ITable> table, String sql, String[] selectionArgs) {
        final Cursor cursor = rawQuery(sql, selectionArgs);

        return parseCursor(table, cursor);
    }

    private static List<? extends ITable> parseCursor(Class<? extends ITable> table, Cursor cursor) {
        if (cursor == null || !cursor.moveToFirst()) {
            return null;
        }

        final int size = cursor.getCount();
        final List<ITable> result = new ArrayList<ITable>(size);
        do {
            final ITable item = SQLiteHelper.fromCursor(cursor, table);
            if (item != null) {
                result.add(item);
            }
        } while (cursor.moveToNext());
        cursor.close();
        return result;
    }

    private static void verify() {
        if (mSqLiteOpenHelperEx == null) {
            throw new NullPointerException("Call init(Context context, DBContract contract) first");
        }
    }

    private static class SQLiteOpenHelperEx extends SQLiteOpenHelper {

        private DBContract contract;

        public SQLiteOpenHelperEx(Context context, DBContract contract) {
            super(context, contract.getDBName(), null, contract.getDBVersion());
            this.contract = contract;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            final Class[] tables = contract.getTableClasses();
            if (tables == null || tables.length == 0) {
                return;
            }

            for (int i = 0; i < tables.length; i++) {
                final Class table = tables[i];
                final String createStatement = SQLiteHelper.buildCreateTableStatement(table);
                sqLiteDatabase.execSQL(createStatement);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            final Class[] tables = contract.getTableClasses();
            if (tables == null || tables.length == 0) {
                return;
            }

            for (int i = 0; i < tables.length; i++) {
                final Class table = tables[i];
                final String deleteStatement = SQLiteHelper.buildDeleteTableStatement(table);
                sqLiteDatabase.execSQL(deleteStatement);
            }
            onCreate(sqLiteDatabase);
        }
    }

}
