package com.library.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linqs on 2016/8/20.
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

    protected Context mContext;
    protected List<T> mDataList;

    protected IRecycleOnItemListener<T> mIRecycleOnItemListener;

    protected abstract int createItemLayout(int viewType);

    protected abstract void onBindViewHolder(CommonViewHolder viewHolder, T item, int position);

    public CommonAdapter(Context context, List<T> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(mContext).inflate(createItemLayout(viewType), parent, false);
        CommonViewHolder commonViewHolder = new CommonViewHolder(contentView);
        return commonViewHolder;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        T item = getItem(position);
        onBindViewHolder(holder, item, position);
    }

    @Override
    public int getItemCount() {
        return null == mDataList ? 0 : mDataList.size();
    }

    public void setIRecycleOnItemListener(IRecycleOnItemListener iRecycleOnItemListener) {
        this.mIRecycleOnItemListener = iRecycleOnItemListener;
    }

    public T getItem(int position) {
        if (null == mDataList || position >= mDataList.size() || position < 0) {
            return null;
        }
        return mDataList.get(position);
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void refreshData(List<T> dataList) {
        refreshData(dataList, true);
    }

    public void addData(List<T> dataList) {
        refreshData(dataList, false);
    }

    public void refreshData(List<T> dataList, boolean refresh) {
        if (refresh) {
            mDataList = dataList;
        } else {
            if (null == mDataList) {
                mDataList = new ArrayList<>();
            }
            if (null != dataList && dataList.size() > 0) {
                mDataList.addAll(dataList);
            }
        }
        notifyDataSetChanged();
    }
}
