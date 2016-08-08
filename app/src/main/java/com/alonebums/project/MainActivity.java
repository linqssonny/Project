package com.alonebums.project;


import android.content.Intent;
import android.view.View;

import com.alonebums.project.image.ImageActivity;
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
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }
    }
}
