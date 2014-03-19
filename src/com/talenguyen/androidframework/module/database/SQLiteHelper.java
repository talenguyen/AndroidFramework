package com.talenguyen.androidframework.module.database;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created with IntelliJ IDEA.
 * User: GIANG
 * Date: 12/22/13
 * Time: 12:29 AM
 */
class SQLiteHelper {

    private static final String TAG = SQLiteHelper.class.getSimpleName();

    public static String buildCreateTableStatement(Class<?> clazz) {
        final ClassParser classParser = ClassParser.parse(clazz);
        if (classParser == null || classParser.methods == null || classParser.methods.size() == 0) {
            return null;
        }

        final StringBuilder columnsBuilder = new StringBuilder("");
        for (int i = 0, count = classParser.methods.size(); i < count; i++) {
            final MethodParser methodParser = classParser.methods.get(i);
            final String columnDeclareString = buildColumnDeclareStatement(methodParser);
            if (columnDeclareString != null) {
                columnsBuilder.append(columnDeclareString);
                columnsBuilder.append(",");
            }
        }
        if (columnsBuilder.length() == 0) {
            return null;
        }
        columnsBuilder.deleteCharAt(columnsBuilder.length() - 1);

        final StringBuilder createTableStatementBuilder = new StringBuilder("");
        createTableStatementBuilder.append("CREATE TABLE ");
        createTableStatementBuilder.append(clazz.getSimpleName());
        createTableStatementBuilder.append(" (");
        createTableStatementBuilder.append(columnsBuilder.toString());
        createTableStatementBuilder.append(" );");
        return createTableStatementBuilder.toString();
    }

    public static String buildDeleteTableStatement(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        return "DROP TABLE IF EXISTS " + clazz.getSimpleName();
    }

    public static ContentValues buildContentValue(Object item) {
        final ClassParser classParser = ClassParser.parse(item.getClass());
        if (classParser == null || classParser.methods == null || classParser.methods.size() == 0) {
            return null;
        }

        final ContentValues contentValues = new ContentValues();
        for (int i = 0, count = classParser.methods.size(); i < count; i++) {
            final MethodParser methodParser = classParser.methods.get(i);
            if (methodParser.getterName.equals("_id")) {
                continue;
            }
            Object resultData;
            if (methodParser.returnType.toLowerCase().contains("boolean")) {
                resultData = ReflectionUtil.involveMethod(item, "is" + methodParser.getterName);
                if (resultData.toString().equals("true")) {
                    resultData = 1;
                } else {
                    resultData = 0;
                }
            } else {
                resultData = ReflectionUtil.involveMethod(item, "get" + methodParser.getterName);
            }
            if (resultData == null) {
                continue;
            }
            contentValues.put(methodParser.getterName, String.valueOf(resultData));
        }
        return contentValues;
    }

    public static <T> T fromCursor(Cursor cursor, Class<? extends T> table) {
        final ClassParser classParser = ClassParser.parse(table);
        if (classParser == null || classParser.methods == null || classParser.methods.size() == 0) {
            return null;
        }

        final T item = ReflectionUtil.newInstance(table);
        final int size = classParser.methods.size();
        for (int i = 0; i < size; i++) {
            final MethodParser methodParser = classParser.methods.get(i);
            final String getterMethod = getCursorGetterMethod(methodParser.returnType);
            if (getterMethod != null) {
                final int index = cursor.getColumnIndex(methodParser.getterName);
                if (index == -1) {
                    continue;
                }
                final Object value = ReflectionUtil.involveMethod(cursor, getterMethod, index);
                if (methodParser.returnType.toLowerCase().contains("boolean")) {
                    ReflectionUtil.involveMethod(item, "set" + methodParser.getterName, Integer.valueOf(value.toString()) == 0 ? false : true );
                } else {
                    ReflectionUtil.involveMethod(item, "set" + methodParser.getterName, value);
                }
            }
        }
        return item;
    }

    private static String buildColumnDeclareStatement(MethodParser method) {
        final String sqliteType = mapToSQLiteType(method.returnType);
        if (sqliteType != null) {
            final StringBuilder methodDeclareBuilder = new StringBuilder("");
            methodDeclareBuilder.append(method.getterName);
            methodDeclareBuilder.append(" ");
            methodDeclareBuilder.append(sqliteType);
            if (method.getterName.equals("_id")) {
                methodDeclareBuilder.append(" primary key autoincrement");
            }
            return methodDeclareBuilder.toString();
        }
        return null;
    }


    private static String getCursorGetterMethod(String returnType) {
        if (returnType.toLowerCase().contains("int") || returnType.toLowerCase().contains("boolean")) {
            return "getInt";
        } else if (returnType.toLowerCase().contains("long")) {
            return "getLong";
        } else if (returnType.toLowerCase().contains("float")) {
            return "getFloat";
        } else if (returnType.toLowerCase().contains("double")) {
            return "getDouble";
        } else if (returnType.toLowerCase().contains("string")) {
            return "getString";
        }

        return null;
    }

    private static String mapToSQLiteType(String type) {
        if (type.toLowerCase().contains("int") || type.toLowerCase().contains("long") || type.toLowerCase().contains("boolean")) {
            return "INTEGER";
        } else if (type.equals("float") || type.equals("java.lang.Float") || type.equals("double")) {
            return "REAL";
        } else if (type.equals("java.lang.String")) {
            return "TEXT";
        }
        return null;
    }

}