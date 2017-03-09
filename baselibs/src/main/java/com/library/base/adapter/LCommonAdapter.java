package com.library.base.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView 通用适配器
 * Created by admin on 2016/5/12.
 */
public abstract class LCommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mDataList;
    private ILOnItemClickListener mLIOnItemClickListener;

    public LCommonAdapter(Context context, List<T> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    @Override
    public int getCount() {
        return null == mDataList ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null == mDataList ? null : mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setILOnItemClickListener(ILOnItemClickListener ilOnItemClickListener) {
        this.mLIOnItemClickListener = ilOnItemClickListener;
    }

    public List<T> getDataList() {
        return mDataList;
    }

    /***
     * 刷新数据
     *
     * @param dataList  数据源
     * @param isRefresh 标识添加还是刷新
     */
    public void refreshData(List<T> dataList, boolean isRefresh) {
        if (isRefresh) {
            mDataList = dataList;
        } else {
            if (null == mDataList) {
                mDataList = new ArrayList<>();
            }
            if(null != dataList && !dataList.isEmpty()) {
                mDataList.addAll(dataList);
            }
        }
        notifyDataSetChanged();
    }
}
