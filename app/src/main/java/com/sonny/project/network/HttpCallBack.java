package com.sonny.project.network;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import com.library.base.dialog.DialogListener;
import com.library.base.dialog.LoadingDialog;
import com.library.network.interfaces.BaseHttpParams;
import com.library.network.interfaces.IHttpCallBack;

/**
 * Created by linqs on 2016/8/10.
 */
public abstract class HttpCallBack implements IHttpCallBack {

    private LoadingDialog mLoadingDialog;

    @Override
    public final void onBefore(BaseHttpParams httpParams) {
        HttpParams params = (HttpParams) httpParams;
        if (null != params && params.isLoading()) {
            //显示等待匡
            showLoading(params);
        }
        onBefore(params);
    }

    @Override
    public final void onProgress(BaseHttpParams httpParams, long bytesRead, long contentLength, boolean finish) {
        HttpParams params = (HttpParams) httpParams;
        onProgress(params, bytesRead, contentLength, finish);
    }

    @Override
    public final void onAfter(BaseHttpParams httpParams) {
        HttpParams params = (HttpParams) httpParams;
        if (null != params && params.isLoading()) {
            dismissLoading(params);
        }
        onAfter(params);
    }

    @Override
    public final void onFail(BaseHttpParams httpParams, String message) {
        HttpParams params = (HttpParams) httpParams;
        onFail(params, message);
    }

    @Override
    public final void onSuccess(BaseHttpParams httpParams, String body) {
        HttpParams params = (HttpParams) httpParams;
        onSuccess(params, body);
    }

    private void showLoading(HttpParams httpParams) {
        if (null == mLoadingDialog) {
            Context context = httpParams.getContext();
            if (context instanceof Activity) {
                mLoadingDialog = new LoadingDialog((Activity) context);
                mLoadingDialog.setDialogListener(new DialogListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        MyHttpUtils.cancel(httpParams.getTag());
                    }
                });
                mLoadingDialog.show();
            }
        }
    }

    private void dismissLoading(HttpParams httpParams) {
        if (null != mLoadingDialog && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    public void onBefore(HttpParams httpParams) {

    }

    public void onProgress(HttpParams httpParams, long bytesRead, long contentLength, boolean finish) {

    }

    public void onAfter(HttpParams httpParams) {

    }

    public void onFail(HttpParams httpParams, String message) {

    }

    public void onSuccess(HttpParams httpParams, String body) {

    }
}
