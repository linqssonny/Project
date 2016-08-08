package com.alonebums.project.base;

import android.app.Application;

import com.library.utils.sp.SPUtils;

/**
 * Created by linqs on 2016/8/7.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initSP();
    }

    private void initSP() {
        SPUtils.getInstance().init(getApplicationContext());
        //SPUtils.getInstance().init(getApplicationContext(),"sp_file_name");
    }
}
