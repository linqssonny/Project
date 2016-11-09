package com.sonny.project.network;

import android.view.View;

import com.sonny.project.R;
import com.library.base.BaseActivity;
import com.library.network.HttpUtils;
import com.library.utils.file.FileUtils;
import com.library.utils.toast.ToastUtils;

/**
 * Created by linqs on 2016/8/10.
 */
public class HttpActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_http;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initLogic() {
        addOnClick(R.id.btn_http);
        addOnClick(R.id.btn_http_download);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_http:
                MyHttpUtils.getAsync(this);
                break;
            case R.id.btn_http_download:
                //下载
                download();
                break;
        }
    }

    private void download() {
        HttpParams httpParams = new HttpParams();
        //不显示等待匡
        httpParams.setLoading(false);
        httpParams.setHttpUrl("http://img2.3lian.com/2014/f6/173/d/51.jpg");
        httpParams.setSaveFilePath(FileUtils.getRootFilePath());
        httpParams.setSaveFileName(FileUtils.createFileNameByDateTime() + ".jpg");
        HttpUtils.getInstances().download(httpParams, new HttpCallBack() {
            @Override
            public void onProgress(HttpParams httpParams, long bytesRead, long contentLength, boolean finish) {
                super.onProgress(httpParams, bytesRead, contentLength, finish);
            }

            @Override
            public void onSuccess(HttpParams httpParams, String body) {
                ToastUtils.showLongMsg(getActivity(), "下载成功");
            }
        });
    }
}
