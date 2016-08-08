package com.library.network.interfaces;

import android.os.Handler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/6/14.
 */
public abstract class IHttpParams {

    //请求地址
    private String httpUrl;

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    //Http请求参数
    private Map<String, Object> param = new HashMap<>();

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    public void addParam(String name, Object value) {
        if (null == value) {
            return;
        }
        param.put(name, value.toString());
    }

    public String getParamString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (null != param && param.size() > 0) {
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                if (stringBuffer.length() > 0) {
                    stringBuffer.append("&");
                }
                if (null == entry) {
                    continue;
                }
                stringBuffer.append(entry.getKey()).append("=").append(entry.getValue().toString());
            }
        }
        return stringBuffer.toString();
    }

    //请求Tag
    private Object tag;

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    //是否主线程回调(默认主线程回调)
    private boolean asyncBack;

    public boolean isAsyncBack() {
        return asyncBack;
    }

    public void setAsyncBack(boolean asyncBack) {
        this.asyncBack = asyncBack;
    }

    //保存文件路径
    private String saveFilePath;

    public String getSaveFilePath() {
        return saveFilePath;
    }

    public void setSaveFilePath(String saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

    //保存文件名称
    private String saveFileName;

    public String getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    //Handler
    private Handler handler;

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
