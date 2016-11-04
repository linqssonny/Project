package com.library.recycleview;

import android.view.View;

/**
 * Created by linqs on 2016/8/21.
 */
public abstract class IRecycleOnItemListener<T> {

    public void onItemClick(View view, int position, T t, Object o) {

    }

    public void onLongClick(View view, int position, T t, Object o) {
    }
}
