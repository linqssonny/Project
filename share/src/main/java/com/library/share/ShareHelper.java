package com.library.share;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.tencent.connect.share.QQShare;
import com.tencent.tauth.Tencent;

/**
 * Created by admin on 2016/11/10.
 */

public class ShareHelper {

    private ShareHelper() {

    }

    private String mQQKey;
    private String mWechatKey;

    private static class ShareHelperManager {
        private static ShareHelper sShareHelper = new ShareHelper();
    }

    public static ShareHelper getInstances() {
        return ShareHelperManager.sShareHelper;
    }

    public void init(String qq_app_key, String wechat_app_key) {
        mQQKey = qq_app_key;
        mWechatKey = wechat_app_key;
    }

    public void share(Activity activity, ShareItem shareItem) {
        if (null == activity || null == shareItem) {
            return;
        }
        switch (shareItem.getTarget()) {
            case ShareItem.SHARE_QQ:
                //分享到QQ
                shareQQ(activity, shareItem);
                break;
            case ShareItem.SHARE_QZONE:
                //分享到QQ空间
                shareQzone(activity, shareItem);
                break;
            case ShareItem.SHARE_WECHAT:
                //分享到微信
                shareWechat(activity, shareItem);
                break;
            case ShareItem.SHARE_WECHATMOMENTS:
                //分享到朋友圈
                shareWechatMoments(activity, shareItem);
                break;
        }
    }

    private void shareQQ(Activity activity, ShareItem shareItem) {
        Tencent tencent = Tencent.createInstance(mQQKey, activity);
        Bundle params = new Bundle();
        // 分享的类型 必填
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        // 分享的标题, 最长30个字符。 必填
        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareItem.getTitle());
        // 分享的消息摘要，最长40个字。 可选 ----content字段(content为空取url,url为空取title)
        String summary = shareItem.getTitle();
        if (!TextUtils.isEmpty(shareItem.getUrl())) {
            summary = shareItem.getUrl();
        }
        if (!TextUtils.isEmpty(shareItem.getContent())) {
            summary = shareItem.getContent();
        }
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        //分享地址
        if (!TextUtils.isEmpty(shareItem.getUrl())) {
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareItem.getUrl());
        }
        // 分享图片地址(本地图片地址)
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareItem.getImage());
        // 分享图片地址(网络地址)
        // params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareParams.getImagePath());
        // 不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框
        // params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        // 享时隐藏分享到QZone按钮
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        tencent.shareToQQ(activity, params, shareItem.getShareQQListener());
    }

    private void shareQzone(Context context, ShareItem shareItem) {

    }

    private void shareWechat(Context context, ShareItem shareItem) {

    }

    private void shareWechatMoments(Context context, ShareItem shareItem) {

    }
}
