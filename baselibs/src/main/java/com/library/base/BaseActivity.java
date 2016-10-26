package com.library.base;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.library.utils.AppUtils;
import com.library.utils.log.LogUtils;
import com.library.utils.permission.PermissionUtils;
import com.library.utils.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/5/10.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public View mRootLayout;
    public Handler mMainHandler;

    public Activity getActivity() {
        return this;
    }

    public abstract int getContentViewId();

    public void beforeSetContentView(Bundle savedInstanceState) {

    }

    public abstract void initUI();

    public void initLogic() {

    }

    public void initData() {
    }

    @Override
    public void onClick(View v) {

    }

    public void addOnClick(View view) {
        if (null == view) {
            return;
        }
        view.setOnClickListener(this);
    }

    public void addOnClick(int id) {
        View view = findViewById(id);
        addOnClick(view);
    }

    public void showMessage(int value) {
        showMessage(getString(value));
    }

    public void showMessage(final String value) {
        toastMessage(Toast.LENGTH_SHORT, value);
    }

    public void toastMessage(final int duration, final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.show(getActivity(), value, duration);
            }
        });
    }

    /*************************************************************************
     * 生命周期方法
     *************************************************************************/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log(getClass().getSimpleName() + " is onCreate");
        AppUtils.getInstance().addActivityToStack(this);
        mMainHandler = new Handler(Looper.getMainLooper());
        if (mRootLayout == null) {
            mRootLayout = getLayoutInflater().inflate(getContentViewId(), null);
        }
        beforeSetContentView(savedInstanceState);
        setContentView(mRootLayout);
        initUI();
        initLogic();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        log(getClass().getSimpleName() + " is onStart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        log(getClass().getSimpleName() + " is onRestoreInstanceState");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        log(getClass().getSimpleName() + " is onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log(getClass().getSimpleName() + " is onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        log(getClass().getSimpleName() + " is onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        log(getClass().getSimpleName() + " is onStop");
    }

    @Override
    protected void onDestroy() {
        AppUtils.getInstance().removeActivityFromStack(this);
        super.onDestroy();
        log(getClass().getSimpleName() + " is onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        log(getClass().getSimpleName() + " is onSaveInstanceState");
    }

    public void log(String message) {
        LogUtils.i(message);
    }

    /***********************************************************************
     * start 6.0权限问题
     ***********************************************************************/

    public boolean requestPermissions(String permission, int requestCode) {
        List<String> permissions = new ArrayList<>();
        permissions.add(permission);
        return requestPermissions(permissions, requestCode);
    }

    public boolean requestPermissions(List<String> permissions, int requestCode) {
        return PermissionUtils.requestPermissions(this, permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermission = true;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                hasPermission = false;
                break;
            }
        }
        if (hasPermission) {
            //权限授权成功
            requestPermissionsSuccess(requestCode, permissions, grantResults);
        } else {
            //权限授权失败
            requestPermissionsFail(requestCode, permissions, grantResults);
        }
    }

    public void requestPermissionsSuccess(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    public void requestPermissionsFail(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    /***********************************************************************
     *   end 6.0权限问题
     ***********************************************************************/
}
