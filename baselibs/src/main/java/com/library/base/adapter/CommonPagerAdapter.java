package com.library.base.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/10/8.
 */

public abstract class CommonPagerAdapter<T> extends PagerAdapter {

    protected Context mContext;
    protected List<T> mDataList;
    private ILOnItemClickListener mLIOnItemClickListener;

    public CommonPagerAdapter(Context context, List<T> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    @Override
    public int getCount() {
        return null == mDataList ? 0 : mDataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setILOnItemClickListener(ILOnItemClickListener ilOnItemClickListener) {
        this.mLIOnItemClickListener = ilOnItemClickListener;
    }

    /***
     * 刷新数据
     *
     * @param dataList
     * @param refresh
     */
    public void refreshData(List<T> dataList, boolean refresh) {
        if (refresh) {
            mDataList = dataList;
        } else {
            if (null == mDataList) {
                mDataList = new ArrayList<>();
            }
            if (null != dataList) {
                mDataList.addAll(dataList);
            }
        }
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        if (null == mDataList || position < 0 || position > mDataList.size() - 1) {
            return null;
        }
        return mDataList.get(position);
    }
}
