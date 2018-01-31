package com.sonny.project.network;

import android.content.Context;

import com.library.network.interfaces.BaseHttpParams;

/**
 * Created by linqs on 2016/8/10.
 */
public class HttpParams extends BaseHttpParams {

    //上下文
    private Context context;

    //是否显示等待匡
    private boolean loading;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }
}
