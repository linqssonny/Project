package com.library.base.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库操作类
 * Created by admin on 2016/11/8.
 */

public class SQLiteHelper {

    private BaseSQLiteOpenHelper mBaseSQLiteOpenHelper;
    private Context mContext;

    private SQLiteHelper() {

    }

    private static class SQLiteHelperManager {
        private static SQLiteHelper sSQLiteHelper = new SQLiteHelper();
    }

    public static SQLiteHelper getInstances() {
        return SQLiteHelperManager.sSQLiteHelper;
    }

    public void init(Context context, String name, int version, ISQLiteCallBackListener isqLiteCallBackListener) {
        if (null == context) {
            throw new NullPointerException("context is null");
        }
        mContext = context.getApplicationContext();
        mBaseSQLiteOpenHelper = new BaseSQLiteOpenHelper(context, name, version, isqLiteCallBackListener);
    }

    public SQLiteDatabase getSQLiteDatabase() {
        if (null == mBaseSQLiteOpenHelper) {
            throw new NullPointerException("user before must execute init method");
        }
        return mBaseSQLiteOpenHelper.getWritableDatabase();
    }

    public void close() {
        if (null != mBaseSQLiteOpenHelper) {
            try {
                mBaseSQLiteOpenHelper.close();
            } finally {
                mBaseSQLiteOpenHelper = null;
            }
        }
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs) {
        return query(table, columns, selection, selectionArgs, null, null, null, null);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String limit) {
        return query(table, columns, selection, selectionArgs, null, null, null, limit);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return getSQLiteDatabase().query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public long update(String table, ContentValues contentValues, String whereClause, String[] whereArgs) {
        SQLiteDatabase sqLiteDatabase = getSQLiteDatabase();
        sqLiteDatabase.beginTransaction();
        long rows = sqLiteDatabase.update(table, contentValues, whereClause, whereArgs);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        return rows;
    }

    public long delete(String table, String whereClause, String[] whereArgs) {
        SQLiteDatabase sqLiteDatabase = getSQLiteDatabase();
        sqLiteDatabase.beginTransaction();
        long rows = sqLiteDatabase.delete(table, whereClause, whereArgs);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        return rows;
    }
}
