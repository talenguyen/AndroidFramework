package com.talenguyen.androidframework.module.database;

/**
 * Created by TALE on 2/26/14.
 */
public abstract class AbsDBColumn {

    private long _id;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }
}
