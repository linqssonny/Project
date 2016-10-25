package com.library.utils.file;

import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by linqs on 2016/7/12.
 */
public class FileUtils {

    /***
     * 文件复制
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @return
     */
    public static boolean copyFile(File sourceFile, File targetFile) {
        if (null == sourceFile || null == targetFile) {
            return true;
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fis = new FileInputStream(sourceFile);
            fos = new FileOutputStream(targetFile);
            in = fis.getChannel();
            out = fos.getChannel();
            in.transferTo(0, in.size(), out);
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
                if (null != fis) {
                    fis.close();
                }
                if (null != fos) {
                    fos.close();
                }
            } catch (Exception e) {
            }

        }
        return true;
    }

    /***
     * 把一段String保存到指定文件里
     *
     * @param filePath
     * @param content
     * @return
     */
    public static boolean saveFile(String filePath, String content) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            byte[] bytes = content.getBytes();
            fileOutputStream.write(bytes, 0, bytes.length);
            fileOutputStream.flush();
        } catch (Exception e) {
            return false;
        } finally {
            if (null != fileOutputStream) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                }
            }
        }
        return true;
    }

    /***
     * 保存文件
     *
     * @param inputStream 数据流
     * @param filePath    文件路径
     * @param fileName    文件名称
     * @return
     * @throws IOException
     */
    public static String saveFile(InputStream inputStream, String filePath, String fileName) throws IOException {
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File file = new File(fileDir, fileName);
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = new FileOutputStream(file);
        while ((len = inputStream.read(buf)) != -1) {
            fos.write(buf, 0, len);
        }
        fos.flush();
        fos.close();
        inputStream.close();
        return file.getAbsolutePath();
    }

    /***
     * 读取文件到String
     *
     * @param filePath
     * @return
     */
    public static String readFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                stringBuffer.append(s);
                stringBuffer.append("\n");
            }
        } catch (Exception e) {
            return null;
        } finally {
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                }
            }
        }
        return stringBuffer.toString();
    }

    /***
     * 删除指定文件或文件夹
     *
     * @param file
     */
    public static void delete(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
        } else {
            for (File f : file.listFiles()) {
                delete(f);
            }
            file.delete();
        }
    }

    /***
     * 获取外部存储根目录
     *
     * @return
     */
    public static String getRootFilePath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            return Environment.getRootDirectory().getAbsolutePath();
        }
    }

    /**
     * 根据当前时期时间生成文件名
     * 年月日时分秒毫秒
     *
     * @return
     */
    public static String createFileNameByDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault());
        return simpleDateFormat.format(new Date());
    }
}
