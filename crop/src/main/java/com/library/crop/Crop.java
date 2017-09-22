package com.library.crop;

import android.app.Activity;

/**
 * Created by admin on 2016/11/7.
 */

public class Crop {
    private Activity activity;
    private String sourcePath;//原图片路径
    private int cropWidth;//裁剪框宽度
    private int cropHeight;//裁剪框高度
    private String outputFile;//输出文件路径
    private int requestCode;//请求码

    //裁剪框是否可移动、变大小
    private boolean freeCrop = true;
    //裁剪窗口是否为圆形
    private boolean circle = false;
    //裁剪框是否显示
    private boolean showCropFrame = true;
    //裁剪框网格是否显示
    private boolean showCropGrid = true;
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

    public boolean isCircle() {
        return circle;
    }

    public void setCircle(boolean circle) {
        this.circle = circle;
    }

    public boolean isShowCropFrame() {
        return showCropFrame;
    }

    public void setShowCropFrame(boolean showCropFrame) {
        this.showCropFrame = showCropFrame;
    }

    public boolean isShowCropGrid() {
        return showCropGrid;
    }

    public void setShowCropGrid(boolean showCropGrid) {
        this.showCropGrid = showCropGrid;
    }
}
