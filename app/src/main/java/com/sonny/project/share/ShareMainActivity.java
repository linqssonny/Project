package com.sonny.project.share;

import android.content.Intent;
import android.view.View;

import com.library.base.BaseActivity;
import com.sonny.project.R;

/**
 * Created by admin on 2016/11/10.
 */

public class ShareMainActivity extends BaseActivity {
    @Override
    public int getContentViewId() {
        return R.layout.activity_share_main;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_share_start:
                //开始分享
                startShare();
                break;
        }
    }

    private void startShare() {
        startActivity(new Intent(this, ShareActivity.class));
    }
}
