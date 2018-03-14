package com.library.share.utils;

import android.app.Activity;
import android.graphics.Bitmap;

import com.sonnyjack.utils.bitmap.BitmapUtils;
import com.sonnyjack.utils.screen.ScreenUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by linqs on 2017/12/6.
 */

public class ShareUtils {

    /**
     * 生成指定大小的bitmap
     *
     * @param imageUrl 本地图片地址
     * @param size     生成的最大图片大小(单位KB)
     * @return
     */
    public static Bitmap createAppointBitmap(String imageUrl, int size) {
        File file = new File(imageUrl);
        if (null == file || !file.exists()) {
            return null;
        }
        Bitmap bitmap = BitmapUtils.decodeBitmap(imageUrl, 1080, 1980);
        return createAppointBitmap(bitmap, size);
    }

    /**
     * 生成指定大小的bitmap
     *
     * @param bitmap 本地图片
     * @param size   生成的最大图片大小(单位KB)
     * @return
     */
    public static Bitmap createAppointBitmap(Bitmap bitmap, int size) {
        if (null == bitmap || bitmap.isRecycled()) {
            return null;
        }
        if (size <= 0) {
            return bitmap;
        }
        //转为byte
        size = size * 1024;
        int bitmapSize = getBitmapSize(bitmap);
        if (bitmapSize <= size) {
            return bitmap;
        }
        Bitmap result = bitmap;
        while (bitmapSize > size) {
            int width = result.getWidth();
            if (bitmapSize > 2 * 1024 * 1024) {
                //大于2M
                width = width / 3;
            } else if (bitmapSize > 1 * 1024 * 1024) {
                //大于1M
                width = (int) (width / 2.0f);
            } else if (bitmapSize > 512 * 1024) {
                //大于512KB
                width = (int) (width / 1.5f);
            } else if (bitmapSize > 256 * 1024) {
                //大于256KB
                width = (int) (width / 1.2f);
            } else {
                //其它
                width = (int) (width / 1.1f);
            }
            float scale = result.getWidth() * 1.0f / width;
            int height = (int) (result.getHeight() * 1.0f / scale);
            result = Bitmap.createScaledBitmap(result, width, height, true);
            bitmapSize = getBitmapSize(result);
        }
        return result;
    }

    /**
     * 获取bitmap的大小
     *
     * @param bitmap
     * @return
     */
    public static int getBitmapSize(Bitmap bitmap) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return bytes.length;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getScreenWidth(Activity activity) {
        return ScreenUtils.getScreenWidth(activity);
    }

    public static int getScreenHeight(Activity activity) {
        return ScreenUtils.getScreenHeight(activity);
    }
}
