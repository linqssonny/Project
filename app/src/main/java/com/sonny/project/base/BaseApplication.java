package com.sonny.project.base;

import android.database.sqlite.SQLiteDatabase;

import com.library.base.db.ISQLiteCallBackListener;
import com.library.base.db.SQLiteHelper;
import com.library.location.LocationHelper;
import com.library.share.ShareHelper;
import com.sonny.project.db.DBTable;
import com.sonny.project.utils.LUtils;

/**
 * Created by linqs on 2016/8/7.
 */
public class BaseApplication extends com.library.base.BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initSP();
        initLog();
        initDB();
        initLocation();
        initShare();
    }

    private void initShare() {
        //初始化分享工具类
        ShareHelper.getInstances().init("sdjWucpVlXqat2gI", "wechat_app_key");
    }

    private void initLocation() {
        LocationHelper.getInstances().init(getApplicationContext());
    }

    private void initDB() {
        SQLiteHelper.getInstances().init(getApplicationContext(), "myDB.db", 1, new ISQLiteCallBackListener() {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(DBTable.CREATE_TABLE_SQL);
                db.execSQL(DBTable.CREATE_INDEX_NAME_SQL);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        });
    }

    private void initLog() {
        //初始化Log工具类
        LUtils.init();
    }

    private void initSP() {
        //SPUtils.getInstance().init(getApplicationContext());
        //可以自定义文件名字
        //SPUtils.getInstance().init(getApplicationContext(),"sp_file_name");
    }
}
