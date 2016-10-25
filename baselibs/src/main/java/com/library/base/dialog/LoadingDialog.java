package com.library.base.dialog;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.library.base.R;


/**
 * Created by admin on 2016/6/28.
 */
public class LoadingDialog extends BaseDialog {

    private ImageView mImageView;
    private Animation mOperatingAnim;

    private TextView mTvMessage;

    @Override
    public boolean canceledOnTouchOutside() {
        return false;
    }

    public LoadingDialog(Activity context) {
        super(context);
    }

    @Override
    public int createContentView() {
        return R.layout.dialog_loading;
    }

    @Override
    public void initUI(View view) {
        mImageView = (ImageView) view.findViewById(R.id.iv_dialog_loading_img);
        mTvMessage = (TextView) view.findViewById(R.id.tv_dialog_loading_message);
        mOperatingAnim = AnimationUtils.loadAnimation(mContext, R.anim.dialog_loading_rotate);
        LinearInterpolator lin = new LinearInterpolator();
        mOperatingAnim.setInterpolator(lin);
        setMessage(mContext.getString(R.string.common_loading_message), true);
    }

    public void setMessage(String message, boolean showMessage) {
        if (showMessage) {
            String m;
            if (TextUtils.isEmpty(message)) {
                m = mContext.getString(R.string.common_loading_message);
            } else {
                m = message;
            }
            mTvMessage.setText(m);
            mTvMessage.setVisibility(View.VISIBLE);
        } else {
            mTvMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void show() {
        super.show();
        mImageView.startAnimation(mOperatingAnim);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mImageView.clearAnimation();
    }

    @Override
    public void cancel() {
        super.cancel();
        mImageView.clearAnimation();
    }
}
