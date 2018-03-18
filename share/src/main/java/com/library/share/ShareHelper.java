package com.library.share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.library.share.bean.ShareItem;
import com.library.share.download.ShareDownload;
import com.library.share.interfaces.IShareDownloadCallBack;
import com.library.share.interfaces.ShareCallBack;
import com.sonnyjack.utils.bitmap.BitmapUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.tauth.Tencent;

import java.io.File;

/**
 * Created by admin on 2016/11/10.
 */

public class ShareHelper {

    private String mQQKey;
    private String mWeChatKey;

    private String mImageFolder;

    private ShareHelper() {

    }

    private static class ShareHelperManager {
        private static ShareHelper sShareHelper = new ShareHelper();
    }

    public static ShareHelper getInstances() {
        return ShareHelperManager.sShareHelper;
    }

    /**
     * @param qq_app_key      QQ分享所需要的app_key
     * @param we_chat_app_key 微信分享所有需要的app_key
     */
    public void init(String qq_app_key, String we_chat_app_key) {
        mQQKey = qq_app_key;
        mWeChatKey = we_chat_app_key;
    }

    public void initImageFolder(String imageFolder) {
        mImageFolder = imageFolder;
    }

    public String getImageFoler() {
        if (TextUtils.isEmpty(mImageFolder)) {
            mImageFolder = Environment.getExternalStorageDirectory() + File.separator + "shareImageFolder";
        }
        return mImageFolder;
    }

    public String getWeChatKey() {
        return mWeChatKey;
    }

    public String getQQKey() {
        return mQQKey;
    }

    public boolean check(Activity activity, ShareItem shareItem) {
        if (null == activity || null == shareItem) {
            return false;
        }
        //版本17以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
            return false;
        }
        return true;
    }

    public void share(Activity activity, ShareItem shareItem) {
        if (!check(activity, shareItem)) {
            return;
        }
        switch (shareItem.getTarget()) {
            case ShareItem.SHARE_QQ:
                //分享到QQ
                QQShareLogin.shareQQ(activity, shareItem);
                break;
            case ShareItem.SHARE_QQ_ZONE:
                //分享到QQ空间
                QQShareLogin.shareQZone(activity, shareItem);
                break;
            case ShareItem.SHARE_WE_CHAT:
                //分享到微信
                shareToWeChat(activity, shareItem, SendMessageToWX.Req.WXSceneSession);
                break;
            case ShareItem.SHARE_WE_CHAT_MOMENTS:
                //分享到朋友圈
                shareToWeChat(activity, shareItem, SendMessageToWX.Req.WXSceneTimeline);
                break;
            case ShareItem.SHARE_WE_CHAT_COLLECTION:
                //添加到微信收藏
                shareToWeChat(activity, shareItem, SendMessageToWX.Req.WXSceneFavorite);
                break;
        }
    }

    /**
     * 分享到微信(好友、朋友圈、添加到收藏)
     *
     * @param activity
     * @param shareItem 分享参数
     * @param scene     分享场景(好友、朋友圈、收藏)
     */
    private void shareToWeChat(final Activity activity, final ShareItem shareItem, final int scene) {
        if (shareItem.hasThumb() && !TextUtils.equals(shareItem.getThumb(), shareItem.getImage())) {
            //有缩略图并且缩略图跟image不相同
            ShareDownload.downloadImage(activity, shareItem.getThumb(), new IShareDownloadCallBack() {
                @Override
                public void downloadFail() {
                    executeErrorCallBack(shareItem, -2, activity.getString(R.string.download_image_fail));
                }

                @Override
                public void downloadSuccess(String originImageUrl, Bitmap bitmap) {
                    Bitmap thumb = BitmapUtils.createThumbBitmap(bitmap, 32);
                    shareToWeChat(activity, shareItem, thumb, scene);
                }
            });
        } else {
            //没有缩略图
            shareToWeChat(activity, shareItem, null, scene);
        }
    }

    /**
     * 分享到微信(好友、朋友圈、添加到收藏)
     *
     * @param activity
     * @param shareItem 分享参数
     * @param thumb     缩略图
     * @param scene     分享场景(好友、朋友圈、收藏)
     */
    private void shareToWeChat(final Activity activity, final ShareItem shareItem, final Bitmap thumb, final int scene) {
        ShareDownload.downloadImage(activity, shareItem.getImage(), new IShareDownloadCallBack() {
            @Override
            public void downloadFail() {
                executeErrorCallBack(shareItem, -2, activity.getString(R.string.download_image_fail));
            }

            @Override
            public void downloadSuccess(String originImageUrl, Bitmap bitmap) {
                Bitmap newThumb = thumb;
                if (null == newThumb || newThumb.isRecycled()) {
                    newThumb = BitmapUtils.createThumbBitmap(bitmap, 32);
                }
                WXShareLogin.shareToWeChat(activity, shareItem, bitmap, newThumb, scene);
            }
        });
    }


    /**
     * 执行成功回调
     *
     * @param shareItem
     */
    private void executeSuccessCallBack(ShareItem shareItem) {
        if (null == shareItem.getShareCallBack()) {
            return;
        }
        shareItem.getShareCallBack().onComplete(shareItem, null);
    }

    /**
     * 执行失败回调
     *
     * @param shareItem
     * @param what
     * @param message
     */
    public void executeErrorCallBack(ShareItem shareItem, int what, String message) {
        if (null == shareItem.getShareCallBack()) {
            return;
        }
        shareItem.getShareCallBack().onError(shareItem, what, message);
    }

    /**
     * 腾讯回调
     *
     * @param activity
     * @param requestCode
     * @param resultCode
     * @param data
     * @param shareCallBack
     */
    public void onActivityResultData(Activity activity, int requestCode, int resultCode, Intent data, ShareCallBack shareCallBack) {
        Tencent tencent = QQShareLogin.createTencent(activity);
        if (null == tencent) {
            return;
        }
        tencent.onActivityResultData(requestCode, resultCode, data, shareCallBack);
    }
}
