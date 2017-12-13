package com.library.share;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.library.share.bean.ShareItem;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.Tencent;

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
        switch (shareItem.getType()) {
            case ShareItem.SHARE_TYPE_IMAGE:
                //分享图片
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
                //需要分享的本地图片路径。
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareItem.getImage());//必填
                break;
            case ShareItem.SHARE_TYPE_MUSIC:
                //分享音乐
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareItem.getUrl());//必填
                //音乐文件的远程链接, 以URL的形式传入, 不支持本地音乐。
                params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, shareItem.getMusic());//必填
                if (!TextUtils.isEmpty(shareItem.getImage())) {
                    //分享图片的URL或者本地路径
                    params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareItem.getImage());//选填
                }
                break;
            case ShareItem.SHARE_TYPE_APP:
                //分享应用
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);
                if (!TextUtils.isEmpty(shareItem.getImage())) {
                    //分享图片的URL或者本地路径
                    params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareItem.getImage());//选填
                }
                break;
            case ShareItem.SHARE_TYPE_URL://分享链接
            default:
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//必填
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareItem.getUrl());//必填
                if (!TextUtils.isEmpty(shareItem.getImage())) {
                    //分享图片的URL或者本地路径
                    params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareItem.getImage());//选填
                }
                break;
        }
        // 分享的标题, 最长30个字符。
        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareItem.getTitle());//必填
        // 分享的消息摘要【可选】，最长40个字。
        if (!TextUtils.isEmpty(shareItem.getContent())) {
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareItem.getContent());
        }
        // 不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框
        // params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        // 享时隐藏分享到QZone按钮
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        tencent.shareToQQ(activity, params, shareItem.getShareCallBack());
    }

    /***
     * 分享到QQ空间
     *(只支持图文分享)
     * @param activity
     * @param shareItem
     */
    public static void shareQZone(Activity activity, ShareItem shareItem) {
        Tencent tencent = createTencent(activity);
        Bundle params = new Bundle();
        // 分享的类型 必填
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        // 分享的标题，最多200个字符。 必填
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareItem.getTitle());
        // 点击后的跳转URL 必填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareItem.getUrl());
        // 分享的消息摘要【可选】，最长40个字。
        if (!TextUtils.isEmpty(shareItem.getContent())) {
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareItem.getContent());
        }
        //分享的图片【选填】, 以ArrayList<String>的类型传入，以便支持多张图片（注：图片最多支持9张图片，多余的图片会被丢弃）。
        if (null != shareItem.getImages() && !shareItem.getImages().isEmpty()) {
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, shareItem.getImages());
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
