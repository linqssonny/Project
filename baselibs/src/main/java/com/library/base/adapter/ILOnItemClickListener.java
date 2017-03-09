package com.library.base.adapter;

import android.view.View;

/**
 * 通用接口  用以adapter、dialog
 * Created by admin on 2016/5/12.
 */
public interface ILOnItemClickListener<T> {
    void onClick(View v, int position, T t);
}
