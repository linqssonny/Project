package com.library.utils;

import java.util.List;

/**
 * Created by admin on 2016/11/4.
 */

public class BaseUtils {

    /***
     * 判断List是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmptyForList(List list) {
        return !isNotEmptyForList(list);
    }

    /***
     * 判断List是否为空
     *
     * @param list
     * @return
     */
    public static boolean isNotEmptyForList(List list) {
        if (null == list || list.isEmpty()) {
            return false;
        }
        return true;
    }

    /***
     * 判断数组是否为空
     *
     * @param objects
     * @return
     */
    public static boolean isEmptyForArray(Object... objects) {
        return !isNotEmptyForArray(objects);
    }

    /***
     * 判断数组是否为空
     *
     * @param objects
     * @return
     */
    public static boolean isNotEmptyForArray(Object... objects) {
        if (null == objects || objects.length == 0) {
            return false;
        }
        return true;
    }
}
