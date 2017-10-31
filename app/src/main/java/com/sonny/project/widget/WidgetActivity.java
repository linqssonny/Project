package com.sonny.project.widget;

import android.view.View;

import com.library.base.BaseActivity;
import com.sonny.project.R;

/**
 * 自定义view
 * Created by linqs on 2017/9/25.
 */

public class WidgetActivity extends BaseActivity {


    @Override
    public int getContentViewId() {
        return R.layout.activity_widget;
    }

    @Override
    public void initUI() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.layout_menu:
                showMessage("点击了菜单");
                break;
        }
    }
}
