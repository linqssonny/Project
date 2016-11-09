package com.sonny.project.location;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.library.base.BaseActivity;
import com.library.location.ILocationCallBack;
import com.library.location.LocationBean;
import com.library.location.LocationHelper;
import com.library.location.SonnyMapView;
import com.library.utils.permission.PermissionUtils;
import com.library.utils.toast.ToastUtils;
import com.sonny.project.R;

/**
 * Created by admin on 2016/11/9.
 */

public class LocationActivity extends BaseActivity implements ILocationCallBack {

    private SonnyMapView mSonnyMapView;

    @Override
    public int getContentViewId() {
        return R.layout.activity_location;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSonnyMapView.onCreate(savedInstanceState);
    }

    @Override
    public void initUI() {
        mSonnyMapView = (SonnyMapView) findViewById(R.id.map_view_location);
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
        setPosition(locationBean);
    }

    private void setPosition(LocationBean locationBean) {
        mSonnyMapView.setPosition(locationBean);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mSonnyMapView.onResume ()，实现地图生命周期管理
        mSonnyMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mSonnyMapView.onPause ()，实现地图生命周期管理
        mSonnyMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mSonnyMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mSonnyMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        LocationHelper.getInstances().removeCallBack(this);
        mSonnyMapView.onDestroy();
        super.onDestroy();
    }
}
