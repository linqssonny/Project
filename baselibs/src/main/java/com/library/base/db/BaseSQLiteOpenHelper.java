package com.library.base.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by admin on 2016/11/8.
 */

class BaseSQLiteOpenHelper extends SQLiteOpenHelper {

    private ISQLiteCallBackListener mISQLiteCallBackListener;

    public BaseSQLiteOpenHelper(Context context, String name, int version, ISQLiteCallBackListener isqLiteCallBackListener) {
        super(context, name, null, version);
        mISQLiteCallBackListener = isqLiteCallBackListener;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (null != mISQLiteCallBackListener) {
            mISQLiteCallBackListener.onCreate(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (null != mISQLiteCallBackListener) {
            mISQLiteCallBackListener.onUpgrade(db, oldVersion, newVersion);
        }
    }
}
