package com.library.share;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.library.share.bean.ShareItem;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;

/**
 * Created by linqs on 2017/12/11.
 */

class QQShareLogin {

    /**
     * 获取Tencent对象
     *
     * @param activity
     * @return
     */
    public static Tencent createTencent(Activity activity) {
        return Tencent.createInstance(ShareHelper.getInstances().getQQKey(), activity.getApplicationContext());
    }

    /***
     * 分享到QQ
     *
     * @param activity
     * @param shareItem
     */
    public static void shareQQ(Activity activity, final ShareItem shareItem) {
        Tencent tencent = createTencent(activity);
        Bundle params = new Bundle();
        // 分享的类型 必填
        params.putInt(com.tencent.connect.share.QQShare.SHARE_TO_QQ_KEY_TYPE, com.tencent.connect.share.QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        // 分享的标题, 最长30个字符。 必填
        params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_TITLE, shareItem.getTitle());
        // 分享的消息摘要，最长40个字。 可选 ----content字段(content为空取url,url为空取title)
        String summary = shareItem.getTitle();
        if (TextUtils.isEmpty(summary)) {
            summary = shareItem.getContent();
        }
        if (TextUtils.isEmpty(summary)) {
            summary = shareItem.getUrl();
        }
        if (!TextUtils.isEmpty(summary)) {
            params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_SUMMARY, summary);
        }
        //分享地址
        if (!TextUtils.isEmpty(shareItem.getUrl())) {
            params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_TARGET_URL, shareItem.getUrl());
        }
        // 分享图片地址(本地图片地址)
        params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareItem.getImage());
        // 分享图片地址(网络地址)
        // params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareParams.getImagePath());
        // 不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框
        // params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        // 享时隐藏分享到QZone按钮
        params.putInt(com.tencent.connect.share.QQShare.SHARE_TO_QQ_EXT_INT, com.tencent.connect.share.QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        tencent.shareToQQ(activity, params, shareItem.getShareCallBack());
    }

    /***
     * 分享到QQ空间
     *
     * @param activity
     * @param shareItem
     */
    public static void shareQZone(Activity activity, ShareItem shareItem) {
        Tencent tencent = createTencent(activity);
        Bundle params = new Bundle();
        // 分享的类型 必填
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        // 分享的标题, 最长30个字符。 必填
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareItem.getTitle());
        // 点击后的跳转URL 必填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareItem.getUrl());
        // 分享的消息摘要，最长40个字。 可选 ----content字段(content为空取url,url为空取title)
        String summary = shareItem.getTitle();
        if (TextUtils.isEmpty(summary)) {
            summary = shareItem.getContent();
        }
        if (TextUtils.isEmpty(summary)) {
            summary = shareItem.getUrl();
        }
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);
        // 分享图片地址(本地、网络图片地址)-----支持传多个imageUrl
        if (!TextUtils.isEmpty(shareItem.getImage())) {
            ArrayList<String> imageUrls = new ArrayList<>();
            String[] imageArray = shareItem.getImage().split(",");
            if (null != imageArray && imageArray.length > 0) {
                for (int i = 0; i < imageArray.length; i++) {
                    imageUrls.add(imageArray[i]);
                }
            }
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
        }
        tencent.shareToQzone(activity, params, shareItem.getShareCallBack());
    }

    /****************************** 第三方登陆 **************************/

    public void loginQQ(Activity activity, ShareItem shareItem) {
        if (!ShareHelper.getInstances().check(activity, shareItem)) {
            return;
        }
        Tencent tencent = createTencent(activity);
        tencent.login(activity, "all", shareItem.getShareCallBack());
    }
}
