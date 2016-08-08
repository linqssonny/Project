package com.library.utils.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/7/4.
 */
public class PermissionUtils {

    /***
     * 判断是否有权限
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean checkSelfPermission(Context context, String permission) {
        int hasPermission = ContextCompat.checkSelfPermission(context, permission);
        if (hasPermission == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    public static boolean requestPermissions(Activity activity, List<String> permissions, int requestCode) {
        if (null == activity || null == permissions) {
            return false;
        }
        List<String> permissionsList = new ArrayList<>();
        for (int i = 0; i < permissions.size(); i++) {
            String permission = permissions.get(i);
            if (!PermissionUtils.checkSelfPermission(activity, permission)) {
                permissionsList.add(permission);
            }
        }
        if (permissionsList.size() <= 0) {
            return true;
        } else {
            if (!PermissionUtils.shouldShowRequestPermissionRationale(activity, permissionsList.get(0))) {
                ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]), requestCode);
            } else {
                ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]), requestCode);
            }
        }
        return false;
    }
}
