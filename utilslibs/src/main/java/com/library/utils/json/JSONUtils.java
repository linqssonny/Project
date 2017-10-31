package com.library.utils.json;

import android.text.TextUtils;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 转为json字符串格式的操作类
 * Created by linqs on 2017/5/3.
 */

public class JSONUtils {

    /**
     * 构建String  根据传入的key、value
     *
     * @param values 含义：key1，value1，key2，value2...
     * @return
     */
    public static String parseStrByArray(Object... values) {
        JSONStringer jsonStringer = parseJsonStrByArray(values);
        if (null == jsonStringer) {
            return "";
        }
        return jsonStringer.toString();
    }

    /**
     * 构建JSONString  根据传入的key、value
     *
     * @param values 含义：key1，value1，key2，value2...
     * @return
     */
    public static JSONStringer parseJsonStrByArray(Object... values) {
        if (null == values || values.length <= 0) {
            return null;
        }
        int size = values.length;
        if (size % 2 != 0) {
            values = Arrays.copyOf(values, size + 1);
        }
        JSONStringer jsonStringer = new JSONStringer();
        try {
            jsonStringer.object();
            for (int i = 0; i < values.length - 1; i += 2) {
                if (null == values[i] || values[i].toString().trim().length() <= 0) {
                    //key为空时  不添加
                    continue;
                }
                jsonStringer.key(values[i].toString()).value((null == values[i + 1] || "null".equals(values[i + 1].toString())) ? "" : values[i + 1]);
            }
            jsonStringer.endObject();
        } catch (Exception e) {
        }
        return jsonStringer;
    }

    /**
     * 构建JSONString  根据传入的map
     *
     * @param param
     * @return
     */
    public static String parseStrByMap(Map<String, Object> param) {
        if (null == param || param.size() <= 0) {
            return "";
        }
        Object[] objects = new Object[param.size() * 2];
        int position = 0;
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            objects[2 * position] = entry.getKey();
            objects[2 * position + 1] = entry.getValue();
            position++;
        }
        return parseStrByArray(objects);
    }

    /**
     * 解析json格式的字符串  keys为json里面的key数组
     *
     * @param value
     * @param keys
     * @return
     */
    public static Map<String, String> optJson(String value, String... keys) {
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObject = toJSONObject(value);
        if (null != jsonObject && null != keys && keys.length > 0) {
            for (int i = 0; i < keys.length; i++) {
                map.put(keys[i], jsonObject.optString(keys[i]));
            }
        }
        return map;
    }

    /**
     * 解析json格式的字符串
     *
     * @param value
     * @param result
     * @return
     */
    public static void optJson(String value, Map<String, Object> result) {
        if (null == result) {
            throw new NullPointerException("the map obj must not null");
        }
        JSONObject jsonObject = toJSONObject(value);
        if (null != jsonObject && null != result && result.size() > 0) {
            for (String key : result.keySet()) {
                if (TextUtils.isEmpty(key)) {
                    continue;
                }
                result.put(key, jsonObject.opt(key));
            }
        }
    }

    /**
     * 将String转为JSONObject对象
     *
     * @param value
     * @return
     */
    public static JSONObject toJSONObject(String value) {
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        if ("null".equals(value)) {
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(value);
            return jsonObject;
        } catch (Exception e) {
        }
        return null;
    }
}
