package com.library.location;

import android.content.Context;
import android.util.AttributeSet;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;

/**
 * Created by admin on 2016/11/9.
 */

public class SonnyMapView extends MapView{

    public SonnyMapView(Context context) {
        super(context);
    }

    public SonnyMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SonnyMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public SonnyMapView(Context context, AMapOptions aMapOptions) {
        super(context, aMapOptions);
    }

    public void setPosition(LocationBean locationBean){
        if(null == locationBean){
            return;
        }
        AMap aMap = getMap();
        LatLng latLng = new LatLng(locationBean.getLat(), locationBean.getLon());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(locationBean.getPoiName());
        markerOptions.draggable(true);
        Marker marker = aMap.addMarker(markerOptions);
        marker.showInfoWindow();

        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f));
    }
}
