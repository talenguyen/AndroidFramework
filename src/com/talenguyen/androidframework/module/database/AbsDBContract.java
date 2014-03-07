package com.talenguyen.androidframework.module.database.androiddb.androiddb;

/**
 * Created with IntelliJ IDEA.
 * User: GIANG
 * Date: 12/22/13
 * Time: 11:22 AM
 */
public abstract class AbsDBContract {

    /**
     * Get array of database tables in Class.
     * @return array of Class present for database table.
     */
    public abstract Class[] getTableClasses();

    /**
     * Get version of database
     * @return Version of database
     */
    public abstract int getDBVersion();

    /**
     * Get name of database
     * @return Name of database
     */
    public abstract String getDBName();
}
