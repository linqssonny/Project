package com.library.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.library.base.R;

/**
 * Created by admin on 2016/7/5.
 */
public abstract class BaseDialog implements DialogInterface.OnDismissListener
        , DialogInterface.OnShowListener, DialogInterface.OnCancelListener, View.OnClickListener {

    protected Context mContext;
    protected Dialog mDialog;

    //监听
    protected DialogListener mDialogListener;

    public BaseDialog(Context context) {
        this.mContext = context;
        init();
    }

    protected abstract int createContentView();

    public abstract void initUI(View view);

    public void initLogic(View view) {

    }

    public void initData() {

    }

    public boolean canceledOnTouchOutside() {
        return true;
    }

    public boolean cancelable() {
        return true;
    }

    //设置窗口透明度
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    public void onClick(View v) {
        if (null != mDialogListener) {
            mDialogListener.onClick(v);
        }
    }

    public void setDialogListener(DialogListener dialogListener) {
        this.mDialogListener = dialogListener;
    }

    private void init() {
        mDialog = new Dialog(mContext, R.style.base_dialog_style);
        mDialog.setOnCancelListener(this);
        mDialog.setOnShowListener(this);
        mDialog.setOnDismissListener(this);
        View view = LayoutInflater.from(mContext).inflate(createContentView(), null);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(canceledOnTouchOutside());
        mDialog.setCancelable(cancelable());
        setAlpha(getAlpha());
        initUI(view);
        initLogic(view);
        initData();
    }

    private void setAlpha(float alpha) {
        if (null == mDialog) {
            return;
        }
        WindowManager.LayoutParams attributes = mDialog.getWindow().getAttributes();
        attributes.alpha = alpha;
        mDialog.getWindow().setAttributes(attributes);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (null != mDialogListener) {
            mDialogListener.onCancel(dialog);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (null != mDialogListener) {
            mDialogListener.onDismiss(dialog);
        }
    }

    @Override
    public void onShow(DialogInterface dialog) {
        if (null != mDialogListener) {
            mDialogListener.onShow(dialog);
        }
    }

    public void show() {
        if (null != mDialog && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void dismiss() {
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void cancel() {
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.cancel();
        }
    }

    public boolean isShowing() {
        if (null != mDialog && mDialog.isShowing()) {
            return true;
        }
        return false;
    }
}
