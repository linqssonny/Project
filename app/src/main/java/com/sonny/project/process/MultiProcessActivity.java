package com.sonny.project.process;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

import com.library.base.BaseActivity;
import com.library.utils.toast.ToastUtils;
import com.sonny.project.IMyAidlInterface;
import com.sonny.project.R;

/**
 * 多进程开发
 * Created by linqs on 2017/7/13.
 */

public class MultiProcessActivity extends BaseActivity {

    private SonnyServiceConnection mSonnyServiceConnection;
    private IMyAidlInterface mIMyAidlInterface;

    @Override
    public int getContentViewId() {
        return R.layout.activity_multi_process;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mSonnyServiceConnection) {
            unbindService(mSonnyServiceConnection);
        }
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initLogic() {
        super.initLogic();
        findViewById(R.id.btn_multi_process_start).setOnClickListener(this);
        findViewById(R.id.btn_multi_process_bind).setOnClickListener(this);
        findViewById(R.id.btn_multi_process_interactive).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_multi_process_start:
                //开启多进程服务
                startMultiProcess();
                ToastUtils.showShortMsg(getActivity(), "开启服务");
                break;
            case R.id.btn_multi_process_bind:
                //绑定服务
                bindMultiService();
                ToastUtils.showShortMsg(getActivity(), "绑定服务");
                break;
            case R.id.btn_multi_process_interactive:
                //多进程交互
                try {
                    String txt = mIMyAidlInterface.requestData("调用");
                    ToastUtils.showShortMsg(getActivity(), txt);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void bindMultiService() {
        Intent intent = new Intent(getActivity(), MultiProcessService.class);
        mSonnyServiceConnection = new SonnyServiceConnection();
        bindService(intent, mSonnyServiceConnection, BIND_AUTO_CREATE);
    }

    private void startMultiProcess() {
        Intent intent = new Intent(getActivity(), MultiProcessService.class);
        startService(intent);
    }

    private class SonnyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIMyAidlInterface = null;
        }
    }
}
