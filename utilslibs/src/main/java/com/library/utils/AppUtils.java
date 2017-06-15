package com.library.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by admin on 2016/6/27.
 */
public class AppUtils {

    private Stack<Activity> mActivityStack = new Stack<>();

    private AppUtils() {
    }

    private static final class AppUtilsHolder {
        private static AppUtils instance = new AppUtils();
    }

    public static AppUtils getInstance() {
        return AppUtilsHolder.instance;
    }

    /***
     * 保存activity
     *
     * @param activity
     */
    public synchronized void addActivityToStack(Activity activity) {
        mActivityStack.add(activity);
    }

    /***
     * 移除activity
     *
     * @param activity
     */
    public synchronized void removeActivityFromStack(Activity activity) {
        if (null == mActivityStack || mActivityStack.size() <= 0) {
            return;
        }
        if (null == activity) {
            return;
        }
        for (int i = mActivityStack.size() - 1; i >= 0; i--) {
            Activity eachActivity = mActivityStack.get(i);
            if (null == eachActivity) {
                continue;
            }
            if (eachActivity.getClass().equals(activity.getClass())) {
                mActivityStack.remove(eachActivity);
                break;
            }
        }
    }

    /**
     * 获取当前activity
     *
     * @return
     */
    public Activity getCurrentActivity() {
        if (mActivityStack.size() == 0)
            return null;
        else
            return mActivityStack.get(mActivityStack.size() - 1);
    }

    /***
     * 关闭所有activity
     */
    public synchronized void finishAllActivity() {
        for (Activity eachActivity : mActivityStack) {
            if (eachActivity != null) {
                eachActivity.finish();
            }
        }
        mActivityStack.clear();
    }

    /***
     * 关闭{activityName除外}所有activity
     *
     * @param activityName
     */
    public synchronized void finishAllActivityExcept(String activityName) {
        ArrayList<Activity> finishList = new ArrayList<>();
        for (Activity eachActivity : mActivityStack) {
            if (null == eachActivity) {
                continue;
            }
            if (!eachActivity.getClass().getName().equals(activityName)) {
                eachActivity.finish();
                finishList.add(eachActivity);
            }
        }
        mActivityStack.removeAll(finishList);
        finishList.clear();
    }

    /***
     * 是否存在{activityName}Activity
     *
     * @param activityName
     * @return
     */
    public synchronized boolean isExistActivity(String activityName) {
        for (Activity eachActivity : mActivityStack) {
            if (null == eachActivity) {
                continue;
            }
            if (eachActivity.getClass().getName().equals(activityName)) {
                return true;
            }
        }
        return false;
    }
}
