package com.library.network.interfaces;


/**
 * Created by admin on 2016/6/14.
 */
public interface IHttpCallBack {

    void onBefore(BaseHttpParams httpParams);

    void onProgress(BaseHttpParams httpParams, long bytesRead, long contentLength, boolean finish);

    void onAfter(BaseHttpParams httpParams);

    void onFail(BaseHttpParams httpParams, String message);

    void onSuccess(BaseHttpParams httpParams, String body);
}
