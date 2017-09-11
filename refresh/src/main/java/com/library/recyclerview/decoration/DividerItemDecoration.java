package com.library.recyclerview.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/***
 * 创建RecyclerView  分割线
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private int mOrientation = LinearLayoutManager.VERTICAL;

    private int mItemSize = 1;

    private Paint mPaint;

    private int mLeftPadding, mRightPadding, mTopPadding, mBottomPadding;

    //是否绘制头部分割线
    private boolean mDrawHeadDivider = true;
    //是否绘制底部分割线
    private boolean mDrawFooterDivider = true;

    public DividerItemDecoration(int orientation, int itemSize, int color) {
        this.mOrientation = orientation;
        this.mItemSize = itemSize;
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("the orientation is error");
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void setDrawHeadDivider(boolean drawHeadDivider) {
        this.mDrawHeadDivider = drawHeadDivider;
    }

    public void setDrawFooterDivider(boolean drawFooterDivider) {
        this.mDrawFooterDivider = drawFooterDivider;
    }

    public void setPadding(int leftPadding, int rightPadding, int topPadding, int bottomPadding) {
        mLeftPadding = leftPadding;
        mRightPadding = rightPadding;
        mTopPadding = topPadding;
        mBottomPadding = bottomPadding;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            //画竖线
            drawVertical(c, parent);
        } else {
            //画横线
            drawHorizontal(c, parent);
        }
    }

    /**
     * 绘制纵向 item 分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop() + mTopPadding;
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom() - mBottomPadding;
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            if (i == 0 && !mDrawHeadDivider) {
                continue;
            }
            if (i == childSize - 1 && !mDrawFooterDivider) {
                continue;
            }
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mItemSize;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    /**
     * 绘制横向 item 分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft() + mLeftPadding;
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight() - mRightPadding;
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            if (i == 0 && !mDrawHeadDivider) {
                continue;
            }
            if (i == childSize - 1 && !mDrawFooterDivider) {
                continue;
            }
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mItemSize;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            //画竖线
            outRect.set(0, 0, 0, mItemSize);
        } else {
            //画横线
            outRect.set(0, 0, mItemSize, 0);
        }
    }
}
