package com.sonny.project.location;

import android.support.annotation.NonNull;
import android.view.View;

import com.library.base.BaseActivity;
import com.library.location.ILocationCallBack;
import com.library.location.LocationBean;
import com.library.location.LocationHelper;
import com.library.utils.permission.PermissionUtils;
import com.library.utils.toast.ToastUtils;
import com.sonny.project.R;

/**
 * Created by admin on 2016/11/9.
 */

public class LocationActivity extends BaseActivity implements ILocationCallBack {

    @Override
    public int getContentViewId() {
        return R.layout.activity_location;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_location_start:
                startLocation();
                break;
        }
    }

    private void startLocation() {
        //开始定位
        requestPermissions(1000, PermissionUtils.PERMISSION_GROUP_LOCATION, PermissionUtils.PERMISSION_GROUP_STORAGE, PermissionUtils.PERMISSION_GROUP_PHONE);
    }

    @Override
    public void requestPermissionsSuccess(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.requestPermissionsSuccess(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000:
                LocationHelper.getInstances().startLocation(this);
                break;
        }
    }

    @Override
    public void locationComplete(LocationBean locationBean) {
        if (null == locationBean) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(locationBean.getCountry())
                .append(locationBean.getProvince())
                .append(locationBean.getCity())
                .append(locationBean.getDistrict())
                .append(locationBean.getStreet())
                .append(locationBean.getStreetNum());
        ToastUtils.showShortMsg(this, "经度:" + locationBean.getLon() + ",纬度：" + locationBean.getLat() + "," + stringBuffer.toString() + "," + locationBean.getAddress());
    }

    @Override
    protected void onDestroy() {
        LocationHelper.getInstances().removeCallBack(this);
        super.onDestroy();
    }
}
