package com.library.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.library.share.bean.ShareItem;
import com.sonnyjack.utils.bitmap.BitmapUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信分享
 * Created by linqs on 2017/12/11.
 */

class WXShareLogin {

    /***
     * 分享到微信(好友、朋友圈)、添加收藏
     * @param activity
     * @param shareItem  分享参数
     * @param image      分享的图片
     * @param thumb      分享的缩略图
     * @param scene      分享的场景(好友、朋友圈、收藏)
     */
    public static void shareToWeChat(Activity activity, ShareItem shareItem, Bitmap image, Bitmap thumb, int scene) {
        String mWeChatKey = ShareHelper.getInstances().getWeChatKey();
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(activity, mWeChatKey, true);
        iwxapi.registerApp(mWeChatKey);
        if (!iwxapi.isWXAppInstalled()) {
            ShareHelper.getInstances().executeErrorCallBack(shareItem, -1, activity.getString(R.string.share_error_info_not_install_we_chat));
            return;
        }
        WXMediaMessage wxMediaMessage = new WXMediaMessage();
        Bitmap bitmap = BitmapUtils.createThumbBitmap(thumb, 32);
        if (null != bitmap) {
            wxMediaMessage.thumbData = BitmapUtils.bitmap2Bytes(bitmap, 100);
            BitmapUtils.recycleBitmap(bitmap);
        }
        switch (shareItem.getType()) {
            case ShareItem.SHARE_TYPE_TEXT:
                //分享文本
                WXTextObject wxTextObject = new WXTextObject();
                wxTextObject.text = shareItem.getTitle();
                wxMediaMessage.mediaObject = wxTextObject;
                break;
            case ShareItem.SHARE_TYPE_IMAGE:
                //分享图片(最大2M)
                bitmap = BitmapUtils.createThumbBitmap(image, 2 * 1024 * 1024);
                WXImageObject imgObj = new WXImageObject(bitmap);
                wxMediaMessage.mediaObject = imgObj;
                break;
            case ShareItem.SHARE_TYPE_URL:
                //分享链接
                WXWebpageObject wxWebpageObject = new WXWebpageObject();
                wxWebpageObject.webpageUrl = shareItem.getUrl();
                wxMediaMessage.mediaObject = wxWebpageObject;
                break;
            case ShareItem.SHARE_TYPE_MUSIC:
                //分享音乐
                WXMusicObject wxMusicObject = new WXMusicObject();
                wxMusicObject.musicUrl = shareItem.getMusic();
                wxMediaMessage.mediaObject = wxMusicObject;
                break;
            case ShareItem.SHARE_TYPE_VIDEO:
                //分享视频
                WXVideoObject wxVideoObject = new WXVideoObject();
                wxVideoObject.videoUrl = shareItem.getVideo();
                wxMediaMessage.mediaObject = wxVideoObject;
                break;
        }
        wxMediaMessage.title = shareItem.getTitle();
        String description = shareItem.getContent();
        if (TextUtils.isEmpty(description)) {
            description = shareItem.getTitle();
        }
        if (TextUtils.isEmpty(description)) {
            description = shareItem.getUrl();
        }
        wxMediaMessage.description = description;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction();
        req.message = wxMediaMessage;
        req.scene = scene;
        iwxapi.sendReq(req);
    }

    /**
     * 登陆微信
     *
     * @param activity
     * @param shareItem
     */
    public void loginWeChat(Activity activity, ShareItem shareItem) {
        if (!ShareHelper.getInstances().check(activity, shareItem)) {
            return;
        }
        String weChatKey = ShareHelper.getInstances().getWeChatKey();
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(activity, weChatKey);
        iwxapi.registerApp(weChatKey);
        if (!iwxapi.isWXAppInstalled()) {
            ShareHelper.getInstances().executeErrorCallBack(shareItem, -1, activity.getString(R.string.share_error_info_not_install_we_chat));
            return;
        }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        iwxapi.sendReq(req);
    }

    private static String buildTransaction() {
        return String.valueOf(System.currentTimeMillis());
    }
}
