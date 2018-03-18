package com.library.network;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;

/**
 * Created by admin on 2016/6/23.
 */
class HttpFileUtils {

    public static void closeInputStream(InputStream inputStream) {
        if (null == inputStream) {
            return;
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /***
     * save stream into file
     *
     * @param inputStream
     * @param folder
     * @param fileName
     * @return
     */
    public static boolean saveFile(InputStream inputStream, String folder, String fileName) {
        boolean success = false;
        if (TextUtils.isEmpty(folder) || TextUtils.isEmpty(fileName) || null == inputStream) {
            return success;
        }
        File fileDir = createFolder(folder);
        File file = new File(fileDir, fileName);
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            fos = new FileOutputStream(file);
            while ((len = inputStream.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    /**
     * create folder by file path
     *
     * @param filePath
     * @return
     */
    public static File createFolder(String filePath) {
        File file = null;
        if (TextUtils.isEmpty(filePath)) {
            return file;
        }
        file = new File(filePath);
        if (null != file && !file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /***
     * return folder absolute path external storage
     *
     * @return
     */
    public static String getRootFolderAbsolutePath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            return Environment.getRootDirectory().getAbsolutePath();
        }
    }
}
