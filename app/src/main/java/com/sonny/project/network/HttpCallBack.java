package com.sonny.project.network;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.library.base.dialog.DialogListener;
import com.library.base.dialog.LoadingDialog;
import com.sonnyjack.library.network.interfaces.BaseHttpParams;
import com.sonnyjack.library.network.interfaces.IHttpCallBack;

/**
 * Created by linqs on 2016/8/10.
 */
public abstract class HttpCallBack implements IHttpCallBack {

    private LoadingDialog mLoadingDialog;

    @Override
    public final void onBefore(BaseHttpParams httpParams) {
        HttpParams params = (HttpParams) httpParams;
        if (params.isLoading()) {
            //这里可以显示等待框
            showLoading(params);
        }
        //还可以 what you want to do
        onBefore(params);
    }

    @Override
    public final void onProgress(BaseHttpParams httpParams, long bytesRead, long contentLength, boolean finish) {
        //这里是下载、上传进度回调
        HttpParams params = (HttpParams) httpParams;
        onProgress(params, bytesRead, contentLength, finish);
    }

    @Override
    public final void onAfter(BaseHttpParams httpParams) {
        //请求结束后回调
        HttpParams params = (HttpParams) httpParams;
        if (null != params && params.isLoading()) {
            //这里可以关闭等待框
            dismissLoading(params);
        }
        onAfter(params);
    }

    @Override
    public final void onFail(BaseHttpParams httpParams, int error, String message) {
        HttpParams params = (HttpParams) httpParams;
        if (params.isShowErrorMessage()) {
            //这里可以统一toast 错误信息
            Toast.makeText(httpParams.getContext(), "弹出错误信息啦：" + message, Toast.LENGTH_SHORT).show();
        }
        onFail(params, error, message);
    }

    @Override
    public final void onSuccess(BaseHttpParams httpParams, String body) {
        HttpParams params = (HttpParams) httpParams;
        String result = body;
        if (params.isAnalysisResult()) {
            //这里可以统一进行解析一层返回的数据并且可以用fastjson、gson等转为对象
            result = body;
        }
        onSuccess(params, result);
    }

    public void onBefore(HttpParams httpParams) {

    }

    public void onProgress(HttpParams httpParams, long bytesRead, long contentLength, boolean finish) {

    }

    public void onFail(HttpParams httpParams, int error, String message) {

    }

    public abstract void onSuccess(HttpParams httpParams, String body);

    public void onAfter(HttpParams httpParams) {

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
}
