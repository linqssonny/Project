package com.alonebums.project.utils;

import com.library.utils.file.FileUtils;
import com.library.utils.log.LogUtils;

import java.io.File;

/**
 * Created by admin on 2016/10/26.
 */

public class LUtils {

    public static void init() {
        //针对LogUtils封装一层的话  第二个参数传1   两层的话第二个参数传2  以此类推
        LogUtils.init(FileUtils.getRootFilePath() + File.separator + "Project" + File.separator + "log", 1);
    }

    public static void d(Object message) {
        LogUtils.d(message);
    }
}
