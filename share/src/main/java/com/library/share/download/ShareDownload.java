package com.library.share.download;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;

import com.library.share.config.ShareConfig;
import com.library.share.interfaces.IShareDownloadCallBack;
import com.library.utils.bitmap.BitmapUtils;
import com.library.utils.file.StreamUtils;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by linqs on 2017/12/8.
 */

public class ShareDownload {

    /**
     * 下载图片
     *
     * @param activity
     * @param imageUrl
     * @param iShareDownloadCallBack
     */
    public static void downloadImage(final Activity activity, final String imageUrl, final IShareDownloadCallBack iShareDownloadCallBack) {
        if (TextUtils.isEmpty(imageUrl)) {
            executeDownloadFail(activity, iShareDownloadCallBack);
            return;
        }
        File file = new File(imageUrl);
        if (null != file && file.exists()) {
            Bitmap bitmap = BitmapUtils.decodeBitmap(imageUrl, ShareConfig.IMAGE_MAX_WIDTH, ShareConfig.IMAGE_MAX_HEIGHT);
            executeDownloadSuccess(activity, iShareDownloadCallBack, imageUrl, bitmap);
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                download(activity, imageUrl, iShareDownloadCallBack);
            }
        }).start();
    }

    private static void download(Activity activity, String imageUrl, final IShareDownloadCallBack iShareDownloadCallBack) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5 * 1000);
            httpURLConnection.setRequestMethod("GET");
            InputStream inputStream = httpURLConnection.getInputStream();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                byte[] bytes = StreamUtils.readStream(inputStream);
                StreamUtils.close(inputStream);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                executeDownloadSuccess(activity, iShareDownloadCallBack, imageUrl, bitmap);
            } else {
                executeDownloadFail(activity, iShareDownloadCallBack);
            }
        } catch (Exception e) {
            executeDownloadFail(activity, iShareDownloadCallBack);
        }
    }


    private static void executeDownloadFail(Activity activity, final IShareDownloadCallBack iShareDownloadCallBack) {
        if (null == activity) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
            return;
        }
        if (null == iShareDownloadCallBack) {
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                iShareDownloadCallBack.downloadFail();
            }
        });
    }

    private static void executeDownloadSuccess(Activity activity, final IShareDownloadCallBack iShareDownloadCallBack, final String originImageUrl, final Bitmap bitmap) {
        if (null == iShareDownloadCallBack) {
            return;
        }
        if (null == activity) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                iShareDownloadCallBack.downloadSuccess(originImageUrl, bitmap);
            }
        });
    }
}
