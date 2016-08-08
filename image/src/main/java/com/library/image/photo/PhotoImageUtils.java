package com.library.image.photo;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.library.image.photo.bean.Image;
import com.library.image.photo.bean.ImageFolder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/6/29.
 */
class PhotoImageUtils {

    static void scanAllImages(Context context, ArrayList<Image> imageList, ArrayList<ImageFolder> folderList) {
        if (null == context) {
            throw new NullPointerException("context is null");
        }
        if (null == imageList) {
            throw new NullPointerException("imageList is null");
        }
        if (null == folderList) {
            throw new NullPointerException("folderList is null");
        }
        Map<String, String> thumbnails = queryImageThumbnail(context);
        ContentResolver contentResolver = context.getContentResolver();
        String columns[] = new String[]{
                MediaStore.Images.Media._ID,//图片ID
                MediaStore.Images.Media.BUCKET_ID,//图片所在文件夹ID
                MediaStore.Images.Media.DATA,//图片地址
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME//图片所在文件夹名称
        };
        String order = MediaStore.Images.Media._ID + " desc";
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, order);
        if (null == cursor) {
            return;
        }
        if (!cursor.moveToFirst()) {
            return;
        }
        int idIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
        int folderIndex = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
        int dataIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        int folderNameIndex = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        String imageId, folderId, imagePath, folderName, thumbnail;
        Image image;
        ImageFolder imageFolder;
        File file;
        do {
            imageId = cursor.getString(idIndex);
            folderId = cursor.getString(folderIndex);
            imagePath = cursor.getString(dataIndex);
            folderName = cursor.getString(folderNameIndex);
            thumbnail = thumbnails.get(imageId) == null ? imagePath : thumbnails.get(imageId);

            imageFolder = new ImageFolder(folderId);
            int index = folderList.indexOf(imageFolder);
            if (index < 0) {
                imageFolder.setFolderName(folderName);
                imageFolder.setNum(1);
                folderList.add(imageFolder);
            } else {
                imageFolder = folderList.get(index);
                imageFolder.setNum(imageFolder.getNum() + 1);
            }
            imageFolder.setFirstImgPath(thumbnail);

            file = new File(imagePath);
            if (file.exists()) {
                image = new Image();
                image.setImageId(imageId);
                image.setImagePath(imagePath);
                image.setFolderId(folderId);
                image.setThumbnailPath(thumbnail);
                image.setLastModifiedTime(file.lastModified());
                imageList.add(image);
            }
        } while (cursor.moveToNext());
    }

    static Map<String, String> queryImageThumbnail(Context context) {
        Map<String, String> map = new HashMap<>();
        ContentResolver contentResolver = context.getContentResolver();
        String[] projection = {MediaStore.Images.Thumbnails.IMAGE_ID, MediaStore.Images.Thumbnails.DATA};
        String order = MediaStore.Images.Thumbnails.IMAGE_ID + " desc";
        Cursor cursor = contentResolver.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, order);
        if (null == cursor) {
            return map;
        }
        if (!cursor.moveToFirst()) {
            return map;
        }
        int idIndex = cursor.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID);
        int dataIndex = cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
        do {
            map.put(cursor.getString(idIndex), cursor.getString(dataIndex));
        } while (cursor.moveToNext());
        cursor.close();
        return map;
    }
}
