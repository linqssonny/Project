package com.sonny.project.network;

import android.Manifest;
import android.view.View;

import com.library.base.BaseActivity;
import com.sonny.project.R;
import com.sonnyjack.permission.IRequestPermissionCallBack;
import com.sonnyjack.permission.PermissionUtils;
import com.sonnyjack.utils.toast.ToastUtils;

import java.util.ArrayList;

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
        ArrayList<String> permissionList = new ArrayList<>();
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        PermissionUtils.getInstances().requestPermission(this, permissionList, new IRequestPermissionCallBack() {
            @Override
            public void onGranted() {
                download();
            }

            @Override
            public void onDenied() {

            }
        });
    }

    private void download() {
        MyHttpUtils.download(getActivity(), new HttpCallBack() {
            @Override
            public void onSuccess(HttpParams httpParams, String body) {
                ToastUtils.showLongMsg(getActivity(), "下载成功：" + body);
            }

            @Override
            public void onFail(HttpParams httpParams, int error,  String message) {
                super.onFail(httpParams, error, message);
                ToastUtils.showLongMsg(getActivity(), "下载失败：" + message);
            }
        });
    }
}
