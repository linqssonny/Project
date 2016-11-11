package com.library.share;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2016/11/10.
 */

public class ShareItem implements Parcelable {

    public static final int SHARE_QQ = 0x100;
    public static final int SHARE_QZONE = 0x101;
    public static final int SHARE_WECHAT = 0x102;
    public static final int SHARE_WECHATMOMENTS = 0x103;

    public static final int SHARE_TYPE_TEXT = 0x100;
    public static final int SHARE_TYPE_IMAGE = 0x101;
    public static final int SHARE_TYPE_URL = 0x102;
    public static final int SHARE_TYPE_MUSIC = 0x103;
    public static final int SHARE_TYPE_VIDEO = 0x104;

    /*分享目标*/
    private int target = SHARE_WECHAT;
    /*分享标题*/
    private String title;
    /*分享内容*/
    private String content;
    /*分享链接*/
    private String url;
    /*分享图片*/
    private String image;
    /*分享音乐*/
    private String music;
    /*分享视频*/
    private String video;
    /*分享类型*/
    private int type = SHARE_TYPE_URL;

    /*QQ分享回调*/
    private ShareQQListener shareQQListener;

    public ShareItem() {

    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public ShareQQListener getShareQQListener() {
        return shareQQListener;
    }

    public void setShareQQListener(ShareQQListener shareQQListener) {
        this.shareQQListener = shareQQListener;
    }

    protected ShareItem(Parcel in) {
        target = in.readInt();
        title = in.readString();
        content = in.readString();
        url = in.readString();
        image = in.readString();
        music = in.readString();
        video = in.readString();
        type = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(target);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(url);
        dest.writeString(image);
        dest.writeString(music);
        dest.writeString(video);
        dest.writeInt(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShareItem> CREATOR = new Creator<ShareItem>() {
        @Override
        public ShareItem createFromParcel(Parcel in) {
            return new ShareItem(in);
        }

        @Override
        public ShareItem[] newArray(int size) {
            return new ShareItem[size];
        }
    };
}
