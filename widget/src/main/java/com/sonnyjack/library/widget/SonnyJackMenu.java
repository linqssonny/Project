package com.sonnyjack.library.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Created by linqs on 2017/9/15.
 */

public class SonnyJackMenu extends FrameLayout {

    private Activity mActivity;
    private View mChildView;

    private float mStartX, mStartY;

    //the screen's width and height, while menu max right and end
    private int mScreenWidth, mScreenHeight;

    private int mLeft, mTop, mRight, mEnd;

    public SonnyJackMenu(@NonNull Context context) {
        super(context);
    }

    private SonnyJackMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private SonnyJackMenu(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addAttach(Activity activity, View itemView) {
        if (null == activity) {
            throw new NullPointerException("the param activity is null");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
            throw new IllegalStateException("the activity already destroy");
        }
        if (null == itemView) {
            throw new NullPointerException("the child view is null");
        }
        mActivity = activity;
        mChildView = itemView;
        init();
        addChildView();
        addToWindow();
    }

    public void removeAttach() {
        mActivity = null;
    }

    private void init() {
        WindowManager wm = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
        mScreenHeight = outMetrics.heightPixels;
    }

    private void addChildView() {
        if (null == mChildView) {
            return;
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        addView(mChildView, layoutParams);
    }

    private void addToWindow() {
        FrameLayout rootLayout = (FrameLayout) mActivity.getWindow().getDecorView();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        rootLayout.addView(this, layoutParams);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getRawX();
                mStartY = event.getRawY();
                Log.e(getClass().getSimpleName(), "down : x = " + mStartX + ", y = " + mStartY);
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                Log.e(getClass().getSimpleName(), "move : x = " + x + ", y = " + y);
                resetLayout(x, y);
                break;
            case MotionEvent.ACTION_UP:
                float endX = event.getRawX();
                float endY = event.getRawY();
                Log.e(getClass().getSimpleName(), "move : x = " + endX + ", y = " + endY);
                if (Math.abs(endX - mStartX) < 15 && Math.abs(endY - mStartY) < 15) {
                    return super.onTouchEvent(event);
                }
                break;
        }
        //return super.onTouchEvent(event);
        return true;
    }

    private void resetLayout(int posX, int posY) {
        mLeft = posX;
        mTop = posY;
        mRight = posX + getWidth();
        mEnd = posY + getHeight();
        if (mRight > mScreenWidth) {
            mRight = mScreenWidth;
            mLeft = mRight - getWidth();
        }
        if (mEnd > mScreenHeight) {
            mEnd = mScreenHeight;
            mTop = mEnd - getHeight();
        }
        layout(mLeft, mTop, mRight, mEnd);
    }

    public void notifyInvalidate() {
        layout(mLeft, mTop, mRight, mEnd);
        postInvalidate();
    }
}