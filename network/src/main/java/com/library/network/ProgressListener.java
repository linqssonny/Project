package com.library.network;

import com.library.network.interfaces.IHttpCallBack;
import com.library.network.interfaces.IHttpParams;

/**
 * Created by admin on 2016/6/23.
 */
class ProgressListener {

    private IHttpParams mIHttpParams;
    private IHttpCallBack mIHttpCallBack;

    public ProgressListener(IHttpParams httpParams, IHttpCallBack httpCallBack) {
        this.mIHttpParams = httpParams;
        this.mIHttpCallBack = httpCallBack;
    }

    public void onProgress(final long bytesRead, final long contentLength, final boolean finish) {
        if (null != mIHttpCallBack) {
            mIHttpParams.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    mIHttpCallBack.onProgress(mIHttpParams, bytesRead, contentLength, finish);
                }
            });
        }
    }
}
