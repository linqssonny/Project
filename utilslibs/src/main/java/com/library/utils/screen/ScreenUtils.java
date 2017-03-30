package com.library.utils.screen;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 屏幕相关Utils
 * Created by linqs on 2016/7/12.
 */
public class ScreenUtils {

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        if (null == context) {
            throw new NullPointerException("context must not null");
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        return screenWidth;
    }

    /***
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        if (null == context) {
            throw new NullPointerException("context must not null");
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.heightPixels;
        return screenWidth;
    }
}
