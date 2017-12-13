package com.library.share.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.library.share.interfaces.ShareCallBack;

import java.util.ArrayList;

/**
 * Created by admin on 2016/11/10.
 */

public class ShareItem implements Parcelable {

    //分享到QQ
    public static final int SHARE_QQ = 0x100;
    //分享到QQ空间
    public static final int SHARE_QQ_ZONE = 0x101;
    //分享到微信
    public static final int SHARE_WE_CHAT = 0x102;
    //分享到微信朋友圈
    public static final int SHARE_WE_CHAT_MOMENTS = 0x103;
    //添加到微信收藏
    public static final int SHARE_WE_CHAT_COLLECTION = 0x104;

    public static final int SHARE_TYPE_TEXT = 0x100;
    public static final int SHARE_TYPE_IMAGE = 0x101;
    public static final int SHARE_TYPE_URL = 0x102;
    public static final int SHARE_TYPE_MUSIC = 0x103;
    public static final int SHARE_TYPE_VIDEO = 0x104;
    public static final int SHARE_TYPE_APP = 0x105;

    /*分享目标*/
    private int target = SHARE_WE_CHAT;
    /*分享标题*/
    private String title;
    /*缩略图(可有可无，没有的话取image字段)*/
    private String thumb;
    /*分享内容*/
    private String content;
    /*分享链接*/
    private String url;
    /*分享图片(允许多张)*/
    private ArrayList<String> images = new ArrayList<>();
    /*分享音乐*/
    private String music;
    /*分享视频*/
    private String video;
    /*分享类型*/
    private int type = SHARE_TYPE_URL;

    /*分享回调*/
    private ShareCallBack shareCallBack;

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

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
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

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
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

    public ShareCallBack getShareCallBack() {
        return shareCallBack;
    }

    public void setShareCallBack(ShareCallBack shareCallBack) {
        this.shareCallBack = shareCallBack;
    }

    /**
     * 是否有缩略图
     *
     * @return
     */
    public boolean hasThumb() {
        return !TextUtils.isEmpty(thumb);
    }

    public String getImage() {
        if (null == images || images.isEmpty()) {
            return null;
        }
        return images.get(0);
    }

    public void addImage(String imageUrl) {
        if (null == images) {
            images = new ArrayList<>();
        }
        images.add(imageUrl);
    }

    public void removeImage(String imageUrl) {
        if (TextUtils.isEmpty(imageUrl)) {
            return;
        }
        if (null == images || images.isEmpty()) {
            return;
        }
        if (!images.contains(imageUrl)) {
            return;
        }
        images.remove(imageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected ShareItem(Parcel in) {
        target = in.readInt();
        title = in.readString();
        thumb = in.readString();
        content = in.readString();
        url = in.readString();
        images = in.createStringArrayList();
        music = in.readString();
        video = in.readString();
        type = in.readInt();
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(target);
        dest.writeString(title);
        dest.writeString(thumb);
        dest.writeString(content);
        dest.writeString(url);
        dest.writeStringList(images);
        dest.writeString(music);
        dest.writeString(video);
        dest.writeInt(type);
    }
}
