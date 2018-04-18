package com.sonny.project.network;

import android.content.Context;

import com.sonnyjack.library.network.HttpUtils;
import com.sonnyjack.utils.file.FileUtils;
import com.sonnyjack.utils.log.LogUtils;
import com.sonnyjack.utils.system.SystemUtils;
import com.sonnyjack.utils.toast.ToastUtils;

/**
 * Created by linqs on 2016/8/10.
 */
public class MyHttpUtils {
    public static void getAsync(Context context) {
        HttpParams httpParams = new HttpParams();
        httpParams.setContext(context);
        //显示等待匡
        httpParams.setLoading(true);
        //默认在主线程回调   如需要子线程回调  设置true
        httpParams.setAsyncBack(false);
        //错误回调时  Toast  错误信息
        httpParams.setShowErrorMessage(true);
        //HttpCallBack回调不对返回结果进行一次解析
        httpParams.setAnalysisResult(false);
        //网络请求地址
        httpParams.setHttpUrl("http://wthrcdn.etouch.cn/weather_mini");
        //添加参数
        httpParams.addParam("citykey", "101010100");
        //设置tag  取消用到
        if (null == httpParams.getTag()) {
            httpParams.setTag(System.currentTimeMillis());
        }
        /*HttpUtils.getInstance().getAsync(httpParams, new HttpCallBack() {
            @Override
            public void onSuccess(HttpParams httpParams, String body) {
                LogUtils.d(body);
                ToastUtils.showMsgInCenter(context, body);
            }

            @Override
            public void onFail(HttpParams httpParams, int error, String message) {
                super.onFail(httpParams, error, message);
                LogUtils.d(message);
                ToastUtils.showMsgInCenter(context, message);
            }
        });*/

        HttpUtils.getInstance().getAsync(httpParams, new HttpObjectCallBack<String>() {
            @Override
            public void onSuccess(HttpParams httpParams, String body) {
                LogUtils.d(body);
                ToastUtils.showMsgInCenter(context, body);
            }

            @Override
            public void onFail(HttpParams httpParams, int error, String message) {
                super.onFail(httpParams, error, message);
                LogUtils.d(message);
                ToastUtils.showMsgInCenter(context, message);
            }
        });

        /*HttpUtils.getInstances().postAsync(httpParams, new HttpCallBack() {
            @Override
            public void onSuccess(HttpParams httpParams, String body) {
                //post请求
            }
        });*/
    }

    public static void download(Context context, HttpCallBack httpCallBack) {
        HttpParams httpParams = new HttpParams();
        httpParams.setContext(context);
        //不显示等待匡
        httpParams.setLoading(true);
        httpParams.setHttpUrl("http://dldir1.qq.com/weixin/android/weixin661android1220_1.apk");
        httpParams.setSaveFilePath(SystemUtils.getRootFolderAbsolutePath());
        httpParams.setSaveFileName(FileUtils.buildFileNameByUrl(httpParams.getHttpUrl()));
        HttpUtils.getInstance().download(httpParams, httpCallBack);
    }

    /**
     * 设置时间
     *
     * @param connectionTime 连接时间(读取时间)
     * @param downloadTime   下载最大时长
     */
    public static void setHttpConnectionTimeAndDownloadTime(long connectionTime, long downloadTime) {
        HttpUtils.getInstance().setHttpConnectionTimeAndDownloadTime(connectionTime, downloadTime);
    }

    public static void cancel(Object tag) {
        HttpUtils.getInstance().cancel(tag);
    }
}
