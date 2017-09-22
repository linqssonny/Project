package com.library.crop;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;

import com.yalantis.ucrop.UCrop;

import java.io.File;

/**
 * 图片裁剪Utils工具类
 * Created by admin on 2016/11/7.
 */

public class CropUtils {

    public static void crop(Crop crop) {
        if (null == crop || null == crop.getActivity()) {
            return;
        }
        File source = new File(crop.getSourcePath());
        File output = new File(crop.getOutputFile());
        UCrop uCrop = UCrop.of(Uri.fromFile(source), Uri.fromFile(output));
        uCrop.withAspectRatio(crop.getCropWidth(), crop.getCropHeight());
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(100);
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        //隐藏底部工具栏
        options.setHideBottomControls(true);
        //裁剪框是否可移动、变大小
        options.setFreeStyleCropEnabled(crop.isFreeCrop());
        //裁剪窗口是否为圆形
        options.setCircleDimmedLayer(crop.isCircle());
        //裁剪框是否显示
        options.setShowCropFrame(crop.isShowCropFrame());
        //裁剪框网格是否显示
        options.setShowCropGrid(crop.isShowCropGrid());
        //顶部toolBar颜色
        if (crop.getToolBarColor() != 0) {
            options.setToolbarColor(crop.getToolBarColor());
        }
        if (!TextUtils.isEmpty(crop.getToolBarTitle())) {
            options.setToolbarTitle(crop.getToolBarTitle());
        }
        uCrop.withOptions(options);
        uCrop.start(crop.getActivity(), crop.getRequestCode());
    }

    public static String handleCropResult(Intent result) {
        if (null == result) {
            return null;
        }
        Uri uri = UCrop.getOutput(result);
        if (null == uri) {
            return null;
        }
        File file = new File(uri.getPath());
        if (null == file) {
            return null;
        }
        return file.getAbsolutePath();
    }
}
