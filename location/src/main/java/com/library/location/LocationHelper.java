package com.library.location;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/11/9.
 */

public class LocationHelper {

    private Context mContext;
    private List<ILocationCallBack> mListCallBack = new ArrayList<>();
    private AMapLocationClient mAMapLocationClient = null;

    private LocationHelper() {

    }

    private static class LocationManager {
        private static LocationHelper sLocationHelper = new LocationHelper();
    }

    public static LocationHelper getInstances() {
        return LocationManager.sLocationHelper;
    }

    public void init(Context context) {
        if (null == context) {
            return;
        }
        mContext = context.getApplicationContext();
    }

    private void initLocationClient() {
        if (null == mContext) {
            throw new NullPointerException("context is null");
        }
        if (null != mAMapLocationClient) {
            return;
        }
        mAMapLocationClient = new AMapLocationClient(mContext);
        AMapLocationClientOption aMapLocationClientOption = new AMapLocationClientOption();
        //30秒定位一次
        aMapLocationClientOption.setInterval(30 * 1000);
        mAMapLocationClient.setLocationOption(aMapLocationClientOption);
        mAMapLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                LocationBean locationBean = change(aMapLocation);
                locationCallBack(locationBean);
            }
        });
    }

    public void startLocation() {
        startLocation(null);
    }

    public void startLocation(ILocationCallBack iLocationCallBack) {
        if (null != iLocationCallBack) {
            mListCallBack.add(iLocationCallBack);
        }
        initLocationClient();
        if (null != mAMapLocationClient) {
            mAMapLocationClient.startLocation();
        }
    }

    public void removeCallBack(ILocationCallBack iLocationCallBack) {
        if (null == iLocationCallBack) {
            return;
        }
        if (mListCallBack.contains(iLocationCallBack)) {
            mListCallBack.remove(iLocationCallBack);
        }
    }

    private LocationBean change(AMapLocation aMapLocation) {
        LocationBean locationBean = new LocationBean();
        if (null == aMapLocation) {
            locationBean.setErrorCode(LocationBean.LOCATION_FAIL);
            locationBean.setErrorInfo("location fail");
            return locationBean;
        }
        if (aMapLocation.getErrorCode() == 0) {
            locationBean.setErrorCode(LocationBean.LOCATION_SUCCESS);
        } else {
            locationBean.setErrorCode(LocationBean.LOCATION_FAIL);
        }
        locationBean.setErrorInfo(aMapLocation.getErrorInfo());
        //纬度
        locationBean.setLat(aMapLocation.getLatitude());
        //经度
        locationBean.setLon(aMapLocation.getLongitude());
        //国家
        locationBean.setCountry(aMapLocation.getCountry());
        //省
        locationBean.setProvince(aMapLocation.getProvince());
        //市
        locationBean.setCity(aMapLocation.getCity());
        //区、县
        locationBean.setDistrict(aMapLocation.getDistrict());
        //街道
        locationBean.setStreet(aMapLocation.getStreet());
        //门牌号
        locationBean.setStreetNum(aMapLocation.getStreetNum());
        //poi 名字
        locationBean.setPoiName(aMapLocation.getPoiName());
        //当前速度(米/秒)
        locationBean.setSpeed(aMapLocation.getSpeed());
        //精度
        locationBean.setAccuracy(aMapLocation.getAccuracy());
        //完整地址信息
        locationBean.setAddress(aMapLocation.getAddress());
        return locationBean;
    }

    private void locationCallBack(LocationBean locationBean) {
        for (int i = 0; i < mListCallBack.size(); i++) {
            ILocationCallBack iLocationCallBack = mListCallBack.get(i);
            if (null != iLocationCallBack) {
                iLocationCallBack.locationComplete(locationBean);
            }
        }
    }
}
