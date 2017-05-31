package com.library.base.adapter;

import android.util.SparseArray;
import android.view.View;

import com.library.base.R;

/**
 * Created by linqs on 2017/4/15.
 */

public class LViewHolder {

    private View mViewLayout;
    private SparseArray<View> mViewList;

    public LViewHolder(View viewLayout) {
        this.mViewLayout = viewLayout;
        mViewList = new SparseArray<>();
    }

    public <T extends View> T findView(int id) {
        View view = mViewList.get(id);
        if (null == view) {
            view = mViewLayout.findViewById(id);
            mViewList.put(id, view);
        }
        return (T) view;
    }
}
