package com.talenguyen.androidframework.module.database.quick;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by TALE on 6/27/2014.
 */
public interface ITable {

    long get_id();

    String getName();

    String getCreateStatement();

    ContentValues toContentValues();

    void consume(Cursor cursor);
}
