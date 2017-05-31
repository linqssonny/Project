package com.library.recycleview;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * RecyclerView通用ViewHolder
 * Created by linqs on 2016/8/20.
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViewArray;

    public CommonViewHolder(View itemView) {
        super(itemView);
        init();
    }

    private void init() {
        mViewArray = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        View view = mViewArray.get(viewId);
        if (null == view) {
            view = itemView.findViewById(viewId);
            mViewArray.put(viewId, view);
        }
        return (T) view;
    }
}
