package com.library.image.photoview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.library.image.photo.bean.Image;
import com.library.image.utils.ImageUtils;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;


/**
 * Created by admin on 2016/7/1.
 */
class PreviewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<Image> mImageList;

    public PreviewPagerAdapter(Context context, ArrayList<Image> imageList) {
        this.mContext = context;
        this.mImageList = imageList;
    }

    @Override
    public int getCount() {
        return null == mImageList ? 0 : mImageList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Image image = mImageList.get(position);
        PhotoView photoView = new PhotoView(mContext);
        ImageUtils.displayImage(mContext, image.getThumbnailPath(), photoView);
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
