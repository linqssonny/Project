package com.sonny.project.network;

import android.content.Context;

import com.library.network.HttpUtils;
import com.library.utils.log.LogUtils;

/**
 * Created by linqs on 2016/8/10.
 */
public class MyHttpUtils {
    public static void getAsync(Context context) {
        HttpParams httpParams = new HttpParams();
        httpParams.setContext(context);
        //显示等待匡
        httpParams.setLoading(true);
        //默认在主线程回调   如需要异步回调  设置true
        httpParams.setAsyncBack(false);
        //网络请求地址
        httpParams.setHttpUrl("http://wthrcdn.etouch.cn/weather_mini");
        //添加参数
        httpParams.addParam("citykey", "101010100");
        HttpUtils.getInstances().getAsync(httpParams, new HttpCallBack() {
            @Override
            public void onSuccess(HttpParams httpParams, String body) {
                LogUtils.d(body);
            }
        });
    }
}
