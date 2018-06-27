package com.sonny.project.widget;

import android.content.Intent;
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
        addOnClick(R.id.btn_round_progress_view);
        addOnClick(R.id.btn_draw_view);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_round_progress_view://圆形进度条
                intent = new Intent(getActivity(), RoundProgressViewActivity.class);
                break;
            case R.id.btn_draw_view:
                //可拖动菜单
                intent = new Intent(getActivity(), DragVewActivity.class);
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }
    }

}
