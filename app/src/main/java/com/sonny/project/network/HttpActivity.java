package com.sonny.project.network;

import android.support.annotation.NonNull;
import android.view.View;

import com.library.base.BaseActivity;
import com.library.base.permission.PermissionUtils;
import com.sonny.project.R;
import com.sonnyjack.utils.toast.ToastUtils;

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
                startDownload();
                break;
        }
    }

    private void startDownload() {
        requestPermissions(1000, PermissionUtils.PERMISSION_GROUP_STORAGE);
    }

    @Override
    public void requestPermissionsSuccess(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.requestPermissionsSuccess(requestCode, permissions, grantResults);
        if (1000 == requestCode) {
            download();
        }
    }

    private void download() {
        MyHttpUtils.download(getActivity(), new HttpCallBack() {
            @Override
            public void onSuccess(HttpParams httpParams, String body) {
                super.onSuccess(httpParams, body);
                ToastUtils.showLongMsg(getActivity(), "下载成功：" + body);
            }

            @Override
            public void onFail(HttpParams httpParams, String message) {
                super.onFail(httpParams, message);
                ToastUtils.showLongMsg(getActivity(), "下载失败：" + message);
            }
        });
    }
}
