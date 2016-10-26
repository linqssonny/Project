package com.alonebums.project.base;

import android.app.Application;

import com.alonebums.project.utils.LUtils;
import com.library.utils.sp.SPUtils;

/**
 * Created by linqs on 2016/8/7.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initSP();
        initLog();
    }

    private void initLog() {
        //初始化Log工具类
        LUtils.init();
    }

    private void initSP() {
        SPUtils.getInstance().init(getApplicationContext());
        //可以自定义文件名字
        //SPUtils.getInstance().init(getApplicationContext(),"sp_file_name");
    }
}
