package com.alonebums.project;


import android.content.Intent;
import android.view.View;

import com.alonebums.project.image.ImageActivity;
import com.alonebums.project.network.HttpActivity;
import com.alonebums.project.recycler.RecyclerActivity;
import com.library.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initLogic() {
        addOnClick(R.id.btn_main_img);
        addOnClick(R.id.btn_main_net);
        addOnClick(R.id.btn_main_recycle);
        addOnClick(R.id.btn_main_qr_code);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_main_img:
                //图片
                intent = new Intent(this, ImageActivity.class);
                break;
            case R.id.btn_main_net:
                //网络
                intent = new Intent(this, HttpActivity.class);
                break;
            case R.id.btn_main_recycle:
                //上拉下拉
                intent = new Intent(this, RecyclerActivity.class);
                break;
            case R.id.btn_main_qr_code:
                //intent = new Intent(this, CaptureActivity.class);
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }
    }
}
