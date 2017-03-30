package com.library.utils.screen;

import android.content.Context;

/**
 * 像素(px)、dpi相关
 * Created by linqs on 2016/7/20.
 */
public class DensityUtils {

    /***
     * px值转为dp值
     *
     * @param context
     * @param value
     * @return
     */
    public static int px2dp(Context context, float value) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (value / scale + 0.5f);
    }

    /***
     * dp值转为px值
     *
     * @param context
     * @param value
     * @return
     */
    public static int dp2px(Context context, float value) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (value * scale + 0.5f);
    }

    /***
     * 将px值转为sp值
     *
     * @param context
     * @param value
     * @return
     */
    public static int px2sp(Context context, float value) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (value / scale + 0.5f);
    }

    /***
     * sp值转为px值
     *
     * @param context
     * @param value
     * @return
     */
    public static int sp2px(Context context, float value) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (value * scale + 0.5f);
    }
}
