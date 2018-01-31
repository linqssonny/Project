package com.sonny.project.network;

import android.view.View;

import com.library.base.BaseActivity;
import com.library.utils.toast.ToastUtils;
import com.sonny.project.R;

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
        MyHttpUtils.download(getActivity(), new HttpCallBack() {
            @Override
            public void onSuccess(HttpParams httpParams, String body) {
                super.onSuccess(httpParams, body);
                ToastUtils.showLongMsg(getActivity(), "下载成功");
            }
        });
    }
}
