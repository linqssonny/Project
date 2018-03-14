package com.library.base.code;

import android.text.TextUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by linqs on 2017/4/18.
 */

public class UrlCodeUtils {

    /**
     * UTF-8方式encode字符串
     *
     * @param value
     * @return
     */
    public static String encodeByUTF8(String value) {
        if (TextUtils.isEmpty(value)) {
            return value;
        }
        String returnValue;
        try {
            String s = URLEncoder.encode(value, "UTF-8");
            returnValue = s;
        } catch (Exception e) {
            returnValue = value;
        }
        return returnValue;
    }

    /**
     * UTF-8方式decode字符串
     *
     * @param value
     * @return
     */
    public static String decodeByUTF8(String value) {
        if (TextUtils.isEmpty(value)) {
            return value;
        }
        String returnValue;
        try {
            String s = URLDecoder.decode(value, "UTF-8");
            returnValue = s;
        } catch (Exception e) {
            returnValue = value;
        }
        return returnValue;
    }
}
