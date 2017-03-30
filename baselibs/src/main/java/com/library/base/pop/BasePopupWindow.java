package com.library.base.pop;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by linqs on 2016/8/16.
 */
public abstract class BasePopupWindow implements View.OnClickListener {

    private Context mContext;
    private PopupWindow mPopupWindow;

    private PopupWindowListener mPopupWindowListener;

    public BasePopupWindow(Context mContext) {
        this.mContext = mContext;
        init();
    }

    protected abstract int createContentView();

    public abstract void initUI(View view);

    public void initLogic(View view) {

    }

    public void initData() {

    }

    protected int getWidth() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    protected int getHeight() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    public boolean outsideTouchable() {
        return true;
    }

    private void init() {
        View contentView = LayoutInflater.from(mContext).inflate(createContentView(), null);
        mPopupWindow = new PopupWindow(contentView, getWidth(), getHeight());
        mPopupWindow.setOutsideTouchable(outsideTouchable());
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (null != mPopupWindowListener) {
                    mPopupWindowListener.onDismiss();
                }
            }
        });
        initUI(contentView);
        initLogic(contentView);
        initData();
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        if (null != mPopupWindow) {
            mPopupWindow.showAtLocation(parent, gravity, x, y);
        }
    }

    public void showAsDropDown(View parent) {
        if (null != mPopupWindow) {
            mPopupWindow.showAsDropDown(parent);
        }
    }

    public void showAsDropDown(View parent, int xoff, int yoff) {
        if (null != mPopupWindow) {
            mPopupWindow.showAsDropDown(parent, xoff, yoff);
        }
    }

    public void showAsDropDown(View parent, int xoff, int yoff, int gravity) {
        if (null != mPopupWindow && Build.VERSION.SDK_INT >= 19) {
            mPopupWindow.showAsDropDown(parent, xoff, yoff, gravity);
        }
    }

    public void setPopupWindowListener(PopupWindowListener popupWindowListener) {
        this.mPopupWindowListener = popupWindowListener;
    }

    @Override
    public void onClick(View view) {

    }
}
