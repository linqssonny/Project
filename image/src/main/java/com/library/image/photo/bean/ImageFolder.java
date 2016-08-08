package com.library.image.photo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2016/6/29.
 */
public class ImageFolder implements Parcelable {

    private String folderId;
    private String folderName;
    private String firstImgPath;
    private int num;

    public ImageFolder() {

    }

    public ImageFolder(String folderId) {
        this.folderId = folderId;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFirstImgPath() {
        return firstImgPath;
    }

    public void setFirstImgPath(String firstImgPath) {
        this.firstImgPath = firstImgPath;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(folderId);
        dest.writeString(folderName);
        dest.writeString(firstImgPath);
        dest.writeInt(num);
    }

    protected ImageFolder(Parcel in) {
        folderId = in.readString();
        folderName = in.readString();
        firstImgPath = in.readString();
        num = in.readInt();
    }

    public static final Creator<ImageFolder> CREATOR = new Creator<ImageFolder>() {
        @Override
        public ImageFolder createFromParcel(Parcel in) {
            return new ImageFolder(in);
        }

        @Override
        public ImageFolder[] newArray(int size) {
            return new ImageFolder[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof ImageFolder) {
            ImageFolder imageFolder = (ImageFolder) o;
            if (folderId.equals(imageFolder.getFolderId())) {
                return true;
            }
        }
        return false;
    }
}
