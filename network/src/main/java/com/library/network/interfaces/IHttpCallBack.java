package com.library.network.interfaces;


/**
 * Created by admin on 2016/6/14.
 */
public interface IHttpCallBack {

    void onBefore(IHttpParams httpParams);

    void onProgress(IHttpParams httpParams, long bytesRead, long contentLength, boolean finish);

    void onAfter(IHttpParams httpParams);

    void onFail(IHttpParams httpParams, String message);

    void onSuccess(IHttpParams httpParams, String body);
}
