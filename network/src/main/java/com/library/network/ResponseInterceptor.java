package com.library.network;

import com.library.network.interfaces.IHttpCallBack;
import com.library.network.interfaces.IHttpParams;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by admin on 2016/6/23.
 */
class ResponseInterceptor implements Interceptor {

    private IHttpParams mIHttpParams;
    private IHttpCallBack mIHttpCallBack;

    public ResponseInterceptor(IHttpParams httpParams, IHttpCallBack httpCallBack) {
        this.mIHttpParams = httpParams;
        this.mIHttpCallBack = httpCallBack;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //拦截
        Response originalResponse = chain.proceed(chain.request());
        //包装响应体并返回
        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body(), new ProgressListener(mIHttpParams, mIHttpCallBack)))
                .build();
    }
}
