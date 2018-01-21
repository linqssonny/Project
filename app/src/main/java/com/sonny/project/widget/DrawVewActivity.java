package com.sonny.project.widget;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.library.base.BaseActivity;
import com.sonny.project.R;
import com.sonnyjack.widget.dragview.SonnyJackDragView;

/**
 * Created by linqs on 2018/1/21.
 */

public class DrawVewActivity extends BaseActivity {

    SonnyJackDragView mSonnyJackDragView;

    @Override
    public int getContentViewId() {
        return R.layout.activity_draw_view;
    }

    @Override
    public void initUI() {
        SonnyJackDragView.Builder builder = new SonnyJackDragView.Builder()
                .setActivity(this)
                .setDefaultLeft(30)
                .setDefaultTop(30)
                .setNeedNearEdge(false)//true时，会移至边沿
                .setSize(100);
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.ic_launcher);
        imageView.setOnClickListener((View view) -> {
            Toast.makeText(getActivity(), "点击了...", Toast.LENGTH_SHORT).show();
        });
        mSonnyJackDragView = SonnyJackDragView.addDragView(builder, imageView);
    }
}
