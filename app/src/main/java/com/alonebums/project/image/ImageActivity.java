package com.alonebums.project.image;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alonebums.project.R;
import com.library.base.BaseActivity;
import com.library.image.utils.ImageUtils;

public class ImageActivity extends BaseActivity {

    private ImageView mIvImg;

    private String mImgUrl = "http://www.haopic.me/wp-content/uploads/2014/10/20141015011203530.jpg";

    @Override
    public int getContentViewId() {
        return R.layout.activity_image;
    }

    @Override
    public void initUI() {
        mIvImg = (ImageView) findViewById(R.id.iv_image_img);
    }

    @Override
    public void initLogic() {
        addOnClick(R.id.btn_image_show);
        addOnClick(R.id.btn_image_circle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_image_show:
                //显示图片
                ImageUtils.displayImage(this, mImgUrl, mIvImg);
                //ImageUtils.displayImage(this, R.drawable.icon_image_show, mIvImg);
                break;
            case R.id.btn_image_circle:
                //圆形图片
                ImageUtils.displayCircleImage(this, R.drawable.icon_image_show, mIvImg);
                //ImageUtils.displayCircleImage(this, mImgUrl, mIvImg);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
