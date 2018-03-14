package com.library.base;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.library.base.permission.PermissionUtils;
import com.sonnyjack.utils.app.AppUtils;
import com.sonnyjack.utils.collection.CollectionUtils;
import com.sonnyjack.utils.log.LogUtils;
import com.sonnyjack.utils.toast.ToastUtils;

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

    public void handleMessage(Message msg) {

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

    public void showMessage(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShortMsg(getActivity(), value);
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
        AppUtils.getInstance().addActivity(this);
        mMainHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                BaseActivity.this.handleMessage(msg);
            }
        };
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
        AppUtils.getInstance().removeActivity(this);
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

    public void requestPermissions(int requestCode, Integer... actions) {
        List<String> permissionsList = new ArrayList<>();
        if (CollectionUtils.isEmpty(actions)) {
            throw new IllegalStateException("the actions value is valid");
        }
        for (int i = 0; i < actions.length; i++) {
            int action = actions[i];
            List<String> p = PermissionUtils.createRequestPermission(action);
            permissionsList.addAll(p);
        }
        boolean result = PermissionUtils.requestPermissions(this, permissionsList, requestCode);
        if (result) {
            int size = permissionsList.size();
            requestPermissionsSuccess(requestCode, permissionsList.toArray(new String[size]), PermissionUtils.createGrantedArray(size));
        }
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
