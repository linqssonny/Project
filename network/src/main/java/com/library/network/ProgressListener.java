package com.library.network;

import com.library.network.interfaces.IHttpCallBack;
import com.library.network.interfaces.BaseHttpParams;

/**
 * Created by admin on 2016/6/23.
 */
class ProgressListener {

    private BaseHttpParams mBaseHttpParams;
    private IHttpCallBack mIHttpCallBack;

    public ProgressListener(BaseHttpParams httpParams, IHttpCallBack httpCallBack) {
        this.mBaseHttpParams = httpParams;
        this.mIHttpCallBack = httpCallBack;
    }

    public void onProgress(final long bytesRead, final long contentLength, final boolean finish) {
        if (null != mIHttpCallBack) {
            mBaseHttpParams.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    mIHttpCallBack.onProgress(mBaseHttpParams, bytesRead, contentLength, finish);
                }
            });
        }
    }
}
