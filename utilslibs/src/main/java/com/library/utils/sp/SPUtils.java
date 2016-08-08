package com.library.utils.sp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by admin on 2016/5/11.
 */
public class SPUtils {

    private SharedPreferences mSharedPreferences;
    private String mName;

    private Context mContext;

    private SPUtils() {
    }

    private static class  SPUtilsHolder{
        private static SPUtils sInstance = new SPUtils();
    }

    public static SPUtils getInstance() {
        return SPUtilsHolder.sInstance;
    }

    public void init(Context context) {
        init(context, null);
    }

    public void init(Context context, String name) {
        if (null == context) {
            throw new NullPointerException("context is null");
        }
        mContext = context.getApplicationContext();
        mName = name;
        if (TextUtils.isEmpty(mName)) {
            mName = context.getPackageName();
        }
        mSharedPreferences = mContext.getSharedPreferences(mName, Activity.MODE_PRIVATE);
    }

    private SharedPreferences getSharedPreferences() {
        if (null == mSharedPreferences) {
            synchronized (SPUtils.class) {
                if (null == mSharedPreferences) {
                    if (TextUtils.isEmpty(mName)) {
                        mName = mContext.getPackageName();
                    }
                    mSharedPreferences = mContext.getSharedPreferences(mName, Activity.MODE_PRIVATE);
                }
            }
        }
        return mSharedPreferences;
    }

    public void reset(String name) {
        if (TextUtils.isEmpty(name)) {
            name = mContext.getPackageName();
        }
        mSharedPreferences = mContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
    }

    public boolean put(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    public boolean put(String key, int value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public int getInt(String key) {
        return getSharedPreferences().getInt(key, 0);
    }

    public boolean put(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    public boolean put(String key, float value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public float getFloat(String key) {
        return getSharedPreferences().getFloat(key, 0.0f);
    }

    public boolean put(String key, long value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public long getLong(String key) {
        return getSharedPreferences().getLong(key, 0L);
    }

    public boolean put(String name, String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public String getString(String name, String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    public boolean put(String name, String key, int value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public int getInt(String name, String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    public boolean put(String name, String key, boolean value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public boolean getBoolean(String name, String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public boolean put(String name, String key, float value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public float getFloat(String name, String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, 0.0f);
    }

    public boolean put(String name, String key, long value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public long getLong(String name, String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0L);
    }
}
