package com.library.utils.file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.channels.FileChannel;

/**
 * Created by admin on 2016/11/10.
 */

public class StreamUtils {

    /**
     * 关闭流
     *
     * @param inputStream
     * @return
     */
    public static boolean close(InputStream inputStream) {
        boolean success = false;
        if (null == inputStream) {
            return success;
        }
        try {
            inputStream.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    public static boolean close(OutputStream outputStream) {
        boolean success = false;
        if (null == outputStream) {
            return success;
        }
        try {
            outputStream.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    public static boolean close(FileChannel fileChannel) {
        boolean success = false;
        if (null == fileChannel) {
            return success;
        }
        try {
            fileChannel.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    public static boolean close(Reader reader) {
        boolean success = false;
        if (null == reader) {
            return success;
        }
        try {
            reader.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 读取流(转为bytes)
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }
}
