package com.library.recycleview.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.library.recyclerview.decoration.DividerItemDecoration;

/**
 * Created by linqs on 2016/9/25.
 */

public class RecycleViewUtils {

    /***
     * 创建Horizontal(LinearLayoutManager)
     *
     * @param context
     * @return
     */
    public static LinearLayoutManager createHorizontalLinearLayoutManager(Context context) {
        if (null == context) {
            return null;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return linearLayoutManager;
    }

    /***
     * Vertical(LinearLayoutManager)
     *
     * @param context
     * @return
     */
    public static LinearLayoutManager createVerticalLinearLayoutManager(Context context) {
        if (null == context) {
            return null;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return linearLayoutManager;
    }

    /***
     * 创建横线
     *
     * @param itemSize
     * @param color
     * @return
     */
    public static DividerItemDecoration createHorizontalItemDecoration(int itemSize, int color) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(LinearLayoutManager.HORIZONTAL, itemSize, color);
        return dividerItemDecoration;
    }

    /***
     * 创建竖线
     *
     * @param itemSize
     * @param color
     * @return
     */
    public static DividerItemDecoration createVerticalItemDecoration(int itemSize, int color) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(LinearLayoutManager.VERTICAL, itemSize, color);
        return dividerItemDecoration;
    }
}
