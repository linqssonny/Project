package com.library.recycleview.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
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

    public static GridLayoutManager createGridLayoutManager(Context context, int size) {
        if (null == context) {
            return null;
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, size);
        return gridLayoutManager;
    }

    /***
     * 创建横线
     *
     * @param itemSize
     * @param color
     * @return
     */
    public static DividerItemDecoration createHorizontalItemDecoration(int itemSize, int color) {
        return createHorizontalItemDecoration(itemSize, color, false, false);
    }

    /**
     * 创建横线
     *
     * @param itemSize
     * @param color
     * @param drawFirst  是否绘制第一条分割线
     * @param drawFooter 是否绘制最后一条分割线
     * @return
     */
    public static DividerItemDecoration createHorizontalItemDecoration(int itemSize, int color, boolean drawFirst, boolean drawFooter) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(LinearLayoutManager.HORIZONTAL, itemSize, color);
        dividerItemDecoration.setDrawHeadDivider(drawFirst);
        dividerItemDecoration.setDrawFooterDivider(drawFooter);
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
        return createVerticalItemDecoration(itemSize, color, false, false);
    }

    /**
     * 创建竖线
     *
     * @param itemSize
     * @param color
     * @param drawFirst  是否绘制第一条分割线
     * @param drawFooter 是否绘制最后一条分割线
     * @return
     */
    public static DividerItemDecoration createVerticalItemDecoration(int itemSize, int color, boolean drawFirst, boolean drawFooter) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(LinearLayoutManager.VERTICAL, itemSize, color);
        dividerItemDecoration.setDrawHeadDivider(drawFirst);
        dividerItemDecoration.setDrawFooterDivider(drawFooter);
        return dividerItemDecoration;
    }
}
