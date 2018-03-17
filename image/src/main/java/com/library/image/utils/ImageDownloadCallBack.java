package com.library.image.utils;

import android.graphics.Bitmap;

/**
 * Created by linqs on 2018/3/17.
 */

public abstract class ImageDownloadCallBack {
    public void onFail() {

    }

    public abstract void onSuccess(Bitmap bitmap);

}
