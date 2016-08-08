package com.library.image.photo.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by admin on 2016/6/29.
 */
public class Image implements Parcelable {

    //图片id
    public String imageId;
    //缩略图地址
    public String thumbnailPath;
    //原图地址
    public String imagePath;
    //最后修改时间
    public long lastModifiedTime;
    //所在文件夹的id
    public String folderId;

    public Image() {
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    protected Image(Parcel in) {
        imageId = in.readString();
        thumbnailPath = in.readString();
        imagePath = in.readString();
        lastModifiedTime = in.readLong();
        folderId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageId);
        dest.writeString(thumbnailPath);
        dest.writeString(imagePath);
        dest.writeLong(lastModifiedTime);
        dest.writeString(folderId);
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Image) {
            Image image = (Image) o;
            if (TextUtils.isEmpty(imageId)) {
                if (TextUtils.equals(imagePath, image.getImagePath())) {
                    return true;
                }
                return false;
            }
            if (imageId.equals(image.getImageId())) {
                return true;
            }
        }
        return false;
    }
}
