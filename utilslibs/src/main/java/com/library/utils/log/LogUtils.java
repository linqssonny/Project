package com.library.utils.log;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日志
 * Created by admin on 2016/5/10.
 */
public class LogUtils {

    public static final int V = 0x1;
    public static final int D = 0x2;
    public static final int I = 0x3;
    public static final int W = 0x4;
    public static final int E = 0x5;
    public static final int A = 0x6;

    public static final String NULL_TIPS = "Log with null object";

    public static void v(Object objMsg) {
        v(null, objMsg);
    }

    public static void v(String tagStr, Object objMsg) {
        printLog(V, tagStr, objMsg);
    }

    public static void d(Object objMsg) {
        d(null, objMsg);
    }

    public static void d(String tagStr, Object objMsg) {
        printLog(D, tagStr, objMsg);
    }

    public static void i(Object objMsg) {
        i(null, objMsg);
    }

    public static void i(String tagStr, Object objMsg) {
        printLog(I, tagStr, objMsg);
    }

    public static void w(Object objMsg) {
        w(null, objMsg);
    }

    public static void w(String tagStr, Object objMsg) {
        printLog(W, tagStr, objMsg);
    }

    public static void e(Object objMsg) {
        e(null, objMsg);
    }

    public static void e(String tagStr, Object objMsg) {
        printLog(E, tagStr, objMsg);
    }

    public static void printLog(int type, String tagStr, Object objectMsg) {
        String[] contents = wrapperContent(tagStr, objectMsg);
        String tag = contents[0];
        String m = contents[1];
        String headString = contents[2];
        String msg = headString + m;
        switch (type) {
            case V:
                Log.v(tag, msg);
                break;
            case D:
                Log.d(tag, msg);
                break;
            case I:
                Log.i(tag, msg);
                break;
            case W:
                Log.w(tag, msg);
                break;
            case E:
                Log.e(tag, msg);
                break;
            case A:
                Log.wtf(tag, msg);
                break;
        }
    }

    /***
     * 把日志写到文本中
     *
     * @param msg 日志内容
     */
    public static void printFile(String msg) {
        printFile(msg, null);
    }

    /***
     * 把日志写到文本中
     *
     * @param msg 日志内容
     */
    public static void printFile(String msg, String fileName) {
        printFile(msg, null, fileName);
    }

    /***
     * 把日志写到文本中
     *
     * @param msg 日志内容
     */
    public static void printFile(String msg, String filePath, String fileName) {
        if (TextUtils.isEmpty(filePath)) {
            filePath = getRootFilePath();
        }
        if (TextUtils.isEmpty(fileName)) {
            fileName = getFileName();
        }
        File file = new File(filePath, fileName);
        if (file.exists()) {
            file.mkdirs();
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(new Date(System.currentTimeMillis()));
            fileWriter.write(time + " : " + msg);
            fileWriter.write("\n");
            fileWriter.flush();
        } catch (Exception e) {

        } finally {
            if (null != fileWriter) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getRootFilePath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            return Environment.getRootDirectory().getAbsolutePath();
        }
    }

    private static String getFileName() {
        StringBuilder stringBuilder = new StringBuilder("log_");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        stringBuilder.append(simpleDateFormat.format(new Date()));
        stringBuilder.append(".txt");
        return stringBuilder.toString();
    }

    /**
     * @param tagStr
     * @param objectMsg
     * @return
     */
    private static String[] wrapperContent(String tagStr, Object objectMsg) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int index = 7;
        String className = stackTrace[index].getFileName();
        String methodName = stackTrace[index].getMethodName();
        int lineNumber = stackTrace[index].getLineNumber();
        String methodNameShort = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ (").append(className).append(":").append(lineNumber).append(")#").append(methodNameShort).append(" ] ");

        String tag = (tagStr == null ? className : tagStr);
        String msg = (objectMsg == null) ? NULL_TIPS : objectMsg.toString();
        String headString = stringBuilder.toString();

        return new String[]{tag, msg, headString};
    }
}
