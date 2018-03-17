package com.library.image.utils;

/**
 * Created by linqs on 2018/3/17.
 */

public class ImageDisplayOption {

    public static final int DECODE_FORMAT_RGB_565 = 1;
    public static final int DECODE_FORMAT_ARGB_8888 = 2;

    private int placeholder;//默认图片
    private int error;//下载失败显示的图片

    private int overrideWidth;//指定图片宽度
    private int overrideHeight;//指定图片高度

    private int decodeFormat = DECODE_FORMAT_RGB_565;//bitmap编码格式 清晰度

    public int getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(int placeholder) {
        this.placeholder = placeholder;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getOverrideWidth() {
        return overrideWidth;
    }

    public void setOverrideWidth(int overrideWidth) {
        this.overrideWidth = overrideWidth;
    }

    public int getOverrideHeight() {
        return overrideHeight;
    }

    public void setOverrideHeight(int overrideHeight) {
        this.overrideHeight = overrideHeight;
    }

    public int getDecodeFormat() {
        return decodeFormat;
    }

    public void setDecodeFormat(int decodeFormat) {
        this.decodeFormat = decodeFormat;
    }
}
