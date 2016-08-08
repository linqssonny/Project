package com.library.base.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.library.base.R;

/**
 * Created by admin on 2016/7/5.
 */
public class ActionSheetDialog extends BaseDialog {

    private TextView mTvMessage, mTvLeft, mTvRight;

    public ActionSheetDialog(Context context) {
        super(context);
    }

    @Override
    public int createContentView() {
        return R.layout.dialog_action_sheet;
    }

    @Override
    public void initUI(View view) {
        mTvLeft = (TextView) view.findViewById(R.id.tv_action_sheet_left);
        mTvRight = (TextView) view.findViewById(R.id.tv_action_sheet_right);
        mTvMessage = (TextView) view.findViewById(R.id.tv_action_sheet_message);
    }

    @Override
    public void initLogic(View view) {
        mTvLeft.setText(R.string.cancel);
        mTvLeft.setOnClickListener(this);
        mTvRight.setText(R.string.confirm);
        mTvRight.setOnClickListener(this);
    }

    public void setLeftText(int left) {
        String m = mContext.getResources().getString(left);
        setLeftText(m);
    }

    public void setLeftText(String left) {
        mTvLeft.setText(left);
    }

    public void setRightText(int right) {
        String m = mContext.getResources().getString(right);
        setRightText(m);
    }

    public void setRightText(String right) {
        mTvLeft.setText(right);
    }

    public void setLeftAndRightText(int left, int right) {
        setLeftText(left);
        setRightText(right);
    }

    public void setLeftAndRightText(String left, String right) {
        setLeftText(left);
        setRightText(right);
    }

    public void setMessage(int message) {
        String m = mContext.getResources().getString(message);
        setMessage(m);
    }

    public void setMessage(String message) {
        mTvMessage.setText(message);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_action_sheet_left) {
            //取消
            dismiss();
        }
        if (id == R.id.tv_action_sheet_left) {
            //确定
        }
        super.onClick(v);
    }
}
