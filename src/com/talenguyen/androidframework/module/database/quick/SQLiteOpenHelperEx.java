package com.talenguyen.androidframework.module.database.quick;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.List;

class SQLiteOpenHelperEx extends SQLiteOpenHelper {

    private DBContract contract;

    public SQLiteOpenHelperEx(Context context, DBContract contract) {
        super(context, contract.getDBName(), null, contract.getDBVersion());
        this.contract = contract;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final List<ITable> tables = contract.getTables();
        if (tables == null || tables.size() == 0) {
            return;
        }

        for (int i = 0, count = tables.size(); i < count; i++) {
            final ITable table = tables.get(i);
            final String createStatement = table.getCreateStatement();
            if (TextUtils.isEmpty(createStatement)) {
                throw new NullPointerException("The CREATE statement must not be NULL or EMPTY");
            }
            sqLiteDatabase.execSQL(createStatement);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        final List<ITable> tables = contract.getTables();
        if (tables == null || tables.size() == 0) {
            return;
        }

        for (int i = 0, count = tables.size(); i < count; i++) {
            final ITable table = tables.get(i);
            final String name = table.getName();
            if (TextUtils.isEmpty(name)) {
                throw new NullPointerException("The TABLE's NAME must not be NULL or EMPTY");
            }
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + name);
        }
        onCreate(sqLiteDatabase);
    }
}