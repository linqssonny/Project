package com.sonny.project.network;

import com.sonnyjack.library.network.interfaces.BaseHttpParams;

/**
 * Created by linqs on 2016/8/10.
 */
public class HttpParams extends BaseHttpParams {
    //是否要解析返回结果
    private boolean isAnalysisResult;

    public boolean isAnalysisResult() {
        return isAnalysisResult;
    }

    public void setAnalysisResult(boolean analysisResult) {
        isAnalysisResult = analysisResult;
    }

    //显示错误信息
    private boolean showErrorMessage;

    public boolean isShowErrorMessage() {
        return showErrorMessage;
    }

    public void setShowErrorMessage(boolean showErrorMessage) {
        this.showErrorMessage = showErrorMessage;
    }
}
