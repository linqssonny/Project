package com.library.base.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by admin on 2016/11/8.
 */

public interface ISQLiteCallBackListener {
    void onCreate(SQLiteDatabase db);
    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}
