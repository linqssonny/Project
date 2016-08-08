package com.library.image.photoview;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.library.image.R;
import com.library.image.photo.bean.Image;

import java.util.ArrayList;

/**
 * Created by admin on 2016/7/1.
 */
public class PreviewPhotoActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    public static final int PREVIEW_PHOTO_REQUEST_CODE = 234;
    public static final String IMAGE_SELECT = "image_select";
    private ArrayList<Image> mSelectImage;
    private ArrayList<Image> mSelectImageResult;

    private HackyViewPager mViewPager;
    private PreviewPagerAdapter mPreviewPagerAdapter;
    private TextView mTvPosition, mTvSelect;

    //当前位置
    private int mCurPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_photo);
        initUI();
        initLogic();
        initData();
    }

    private void initUI() {
        mViewPager = (HackyViewPager) findViewById(R.id.vp_preview_photo_content);
        mTvSelect = (TextView) findViewById(R.id.tv_preview_photo_select);
        mTvPosition = (TextView) findViewById(R.id.tv_preview_photo_position);
    }

    private void initLogic() {
        TextView textView = (TextView) findViewById(R.id.tv_choose_photo_title);
        textView.setText(R.string.preview_photo_title);

        Button btnConfirm = (Button) findViewById(R.id.btn_choose_photo_confirm);
        btnConfirm.setText(R.string.image_choose_photo_confirm);
        btnConfirm.setOnClickListener(this);

        mTvSelect.setOnClickListener(this);

        mViewPager.addOnPageChangeListener(this);

        findViewById(R.id.iv_choose_photo_back).setOnClickListener(this);
        findViewById(R.id.tv_choose_photo_back).setOnClickListener(this);

        Intent intent = getIntent();
        mSelectImage = intent.getParcelableArrayListExtra(IMAGE_SELECT);
        mSelectImageResult = new ArrayList<>(mSelectImage);
    }

    private void initData() {
        mPreviewPagerAdapter = new PreviewPagerAdapter(this, mSelectImage);
        mViewPager.setAdapter(mPreviewPagerAdapter);
        setSelectTextView(true);
        mCurPosition = 0;
        setPosition();
    }

    private void setSelectTextView(boolean isCheck) {
        Drawable drawable;
        if (isCheck) {
            drawable = getResources().getDrawable(R.drawable.image_choose_photo_ck_check);
        } else {
            drawable = getResources().getDrawable(R.drawable.image_choose_photo_ck_uncheck);
        }
        int size = dp2px(12);
        drawable.setBounds(0, 0, size, size);
        mTvSelect.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    private void setPosition() {
        int size = null == mSelectImage ? 0 : mSelectImage.size();
        mTvPosition.setText((mCurPosition + 1) + "/" + size);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    private int dp2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_choose_photo_confirm) {
            //完成
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra(IMAGE_SELECT, mSelectImageResult);
            setResult(RESULT_OK, intent);
            finish();
        } else if (id == R.id.tv_preview_photo_select) {
            //选择
            Image image = mSelectImage.get(mCurPosition);
            boolean isCheck;
            if (mSelectImageResult.contains(image)) {
                mSelectImageResult.remove(image);
                isCheck = false;
            } else {
                mSelectImageResult.add(image);
                isCheck = true;
            }
            setSelectTextView(isCheck);
        } else if (id == R.id.iv_choose_photo_back) {
            //返回
            finish();
        } else if (id == R.id.tv_choose_photo_back) {
            //返回
            finish();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurPosition = position;
        Image image = mSelectImage.get(mCurPosition);
        if (mSelectImageResult.contains(image)) {
            setSelectTextView(true);
        } else {
            setSelectTextView(false);
        }
        setPosition();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
