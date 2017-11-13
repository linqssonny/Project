package com.sonny.project.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.TextView;

import com.library.base.BaseActivity;
import com.sonny.project.R;
import com.sonnyjack.widget.progress.RoundProgressView;

/**
 * 自定义view
 * Created by linqs on 2017/9/25.
 */

public class WidgetActivity extends BaseActivity {

    private RoundProgressView mRoundProgressView;
    private TextView mTvProgress;
    private int mTime = 5 * 1000;

    @Override
    public int getContentViewId() {
        return R.layout.activity_widget;
    }

    @Override
    public void initUI() {
        initProgressView();
    }

    private void initProgressView() {
        mRoundProgressView = findViewById(R.id.rpv_widget_progress);
        mRoundProgressView.setOnClickListener(this::click);
        mTvProgress = findViewById(R.id.tv_widget_progress);
        mRoundProgressView.setIRoundProgressListener((float progress, float total) -> {
            if (progress >= total) {
                mTvProgress.setText("完成");
            } else {
                int value = (int) (progress / total * 100);
                /*BigDecimal bg = new BigDecimal(value);
                value = bg.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();*/
                mTvProgress.setText(value + "%");
            }
        });
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        //mRoundProgressView.setImageBitmap(bitmap);
        //mRoundProgressView.setImageScale(0.4f);
        mRoundProgressView.setText("你好");
        //int mode = RoundProgressView.MODE_AUTO;
        int mode = RoundProgressView.MODE_UPDATE;
        mRoundProgressView.setMode(mode);
        if (mode == RoundProgressView.MODE_AUTO) {
            //mRoundProgressView.start();
            mRoundProgressView.start(mTime);
        } else {
            post(0);
        }
    }

    private void post(float progress) {
        if (progress > 100) {
            return;
        }
        mRoundProgressView.updateProgress(progress);
        mMainHandler.postDelayed(() -> {
            post(progress + 100.0f / 100);
        }, mTime / 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    public void click(View v) {
        showMessage("点击了");
    }
}
