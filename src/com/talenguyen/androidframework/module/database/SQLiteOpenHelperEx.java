package com.talenguyen.androidframework.module.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteOpenHelperEx extends SQLiteOpenHelper {

    private DBContract contract;

    public SQLiteOpenHelperEx(Context context, DBContract contract) {
        super(context, contract.getDBName(), null, contract.getDBVersion());
        this.contract = contract;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final Class<? extends ITable>[] tables = contract.getTableClasses();
        if (tables == null || tables.length == 0) {
            return;
        }

        for (int i = 0; i < tables.length; i++) {
            final Class<? extends ITable> table = tables[i];
            final String createStatement = SQLiteHelper.buildCreateTableStatement(table);
            sqLiteDatabase.execSQL(createStatement);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        final Class<? extends ITable>[] tables = contract.getTableClasses();
        if (tables == null || tables.length == 0) {
            return;
        }

        for (int i = 0; i < tables.length; i++) {
            final Class<? extends ITable> table = tables[i];
            final String deleteStatement = SQLiteHelper.buildDeleteTableStatement(table);
            sqLiteDatabase.execSQL(deleteStatement);
        }
        onCreate(sqLiteDatabase);
    }
}