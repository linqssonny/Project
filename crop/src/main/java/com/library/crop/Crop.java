package com.library.crop;

import android.app.Activity;

/**
 * Created by admin on 2016/11/7.
 */

public class Crop {
    private Activity activity;
    private String sourcePath;
    private int cropWidth;
    private int cropHeight;
    private String outputFile;
    private int requestCode;

    //裁剪框是否可移动
    private boolean freeCrop = true;
    //顶部toolBar颜色
    private int toolBarColor;
    //顶部toolBar标题
    private String toolBarTitle;

    public Crop(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public int getCropWidth() {
        return cropWidth;
    }

    public void setCropWidth(int cropWidth) {
        this.cropWidth = cropWidth;
    }

    public int getCropHeight() {
        return cropHeight;
    }

    public void setCropHeight(int cropHeight) {
        this.cropHeight = cropHeight;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public boolean isFreeCrop() {
        return freeCrop;
    }

    public void setFreeCrop(boolean freeCrop) {
        this.freeCrop = freeCrop;
    }

    public int getToolBarColor() {
        return toolBarColor;
    }

    public void setToolBarColor(int toolBarColor) {
        this.toolBarColor = toolBarColor;
    }

    public String getToolBarTitle() {
        return toolBarTitle;
    }

    public void setToolBarTitle(String toolBarTitle) {
        this.toolBarTitle = toolBarTitle;
    }
}
