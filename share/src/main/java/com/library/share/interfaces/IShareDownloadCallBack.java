package com.library.share.interfaces;

import android.graphics.Bitmap;

/**
 * Created by linqs on 2017/12/8.
 */

public interface IShareDownloadCallBack {
    void downloadFail();
    void downloadSuccess(String originImageUrl, Bitmap bitmap);
}
