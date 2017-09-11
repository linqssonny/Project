package com.sonny.project.process;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.sonny.project.IMyAidlInterface;

/**
 * Created by linqs on 2017/7/13.
 */

public class MultiProcessService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        IBinder iBinder = new IMyAidlInterface.Stub() {
            @Override
            public String requestData(String value) throws RemoteException {
                return "zhe shi xin de jin cheng : " + value;
            }
        };
        return iBinder;
    }
}
