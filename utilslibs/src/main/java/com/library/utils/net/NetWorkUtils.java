package com.library.utils.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络Utils
 * Created by linqs on 2016/7/12.
 */
public class NetWorkUtils {

    /***
     * 判断是否有网络
     *
     * @param context
     * @return
     */
    public boolean isNetworkConnected(Context context) {
        if (null == context) {
            throw new NullPointerException("context must not null");
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (null != networkInfo) {
                return networkInfo.isConnected();
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi链接
     *
     * @param context
     * @return
     */
    public boolean isWifiConnected(Context context) {
        if (null == context) {
            throw new NullPointerException("context must not null");
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            }
        }
        return false;
    }
}
