package com.library.utils.toast;

import android.content.Context;
import android.widget.Toast;

/**
 * ToastUtils
 */
public class ToastUtils {

    private ToastUtils() {
    }

    /**
     * 显示时长较长的Toast
     */
    public static void showLongMsg(Context c, int resId) {
        show(c, c.getResources().getString(resId), Toast.LENGTH_LONG);
    }

    /**
     * 显示时长较长的Toast
     */
    public static void showLongMsg(Context c, String s) {
        show(c, s, Toast.LENGTH_LONG);
    }

    /**
     * 显示时长较短的Toast
     */
    public static void showShortMsg(Context c, int resId) {
        show(c, c.getResources().getString(resId), Toast.LENGTH_SHORT);
    }

    /**
     * 显示时长较短的Toast
     */
    public static void showShortMsg(Context c, String s) {
        show(c, s, Toast.LENGTH_SHORT);
    }

    //执行Toast的方法
    private static void show(Context context, String s, int duration) {
        Toast.makeText(context, s, duration).show();
    }
}
