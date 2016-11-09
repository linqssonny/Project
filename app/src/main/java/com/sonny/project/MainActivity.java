package com.sonny.project;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.library.base.BaseActivity;
import com.library.crop.Crop;
import com.library.crop.CropUtils;
import com.library.utils.file.FileUtils;
import com.library.utils.toast.ToastUtils;
import com.sonny.project.db.DBActivity;
import com.sonny.project.image.ImageActivity;
import com.sonny.project.location.LocationActivity;
import com.sonny.project.network.HttpActivity;
import com.sonny.project.qrcode.QrCodeActivity;
import com.sonny.project.recycler.RecyclerActivity;
import com.sonny.project.sp.SpActivity;
import com.sonny.project.utils.LUtils;

import java.io.File;

public class MainActivity extends BaseActivity {

    private String mCropImageUrl;

    @Override
    public void log(String message) {
        //打印日记
        LUtils.d(message);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initUI() {
        //执行顺序  initUI  initLogic  initData
    }

    @Override
    public void initLogic() {
        addOnClick(R.id.btn_main_img);
        addOnClick(R.id.btn_main_crop);
        addOnClick(R.id.btn_main_net);
        addOnClick(R.id.btn_main_sp);
        addOnClick(R.id.btn_main_db);
        addOnClick(R.id.btn_main_recycle);
        addOnClick(R.id.btn_main_qr_code);
        addOnClick(R.id.btn_main_location);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        Integer request = null;
        switch (v.getId()) {
            case R.id.btn_main_img:
                //图片
                intent = new Intent(this, ImageActivity.class);
                break;
            case R.id.btn_main_crop:
                //裁剪
                Crop crop = getCrop();
                CropUtils.crop(crop);
                break;
            case R.id.btn_main_net:
                //网络
                intent = new Intent(this, HttpActivity.class);
                break;
            case R.id.btn_main_sp:
                //SharedPreferences
                intent = new Intent(this, SpActivity.class);
                break;
            case R.id.btn_main_db:
                //数据库
                intent = new Intent(this, DBActivity.class);

                break;
            case R.id.btn_main_recycle:
                //上拉下拉
                intent = new Intent(this, RecyclerActivity.class);
                break;
            case R.id.btn_main_qr_code:
                //二维码
                intent = new Intent(this, QrCodeActivity.class);
                break;
            case R.id.btn_main_location:
                //地图/定位
                intent = new Intent(this, LocationActivity.class);
                break;
        }
        if (null != intent) {
            if (null != request) {
                startActivityForResult(intent, request);
            } else {
                startActivity(intent);
            }
        }
    }

    @NonNull
    private Crop getCrop() {
        Crop crop = new Crop(this);
        crop.setRequestCode(1000);
        crop.setSourcePath(FileUtils.getRootFilePath() + File.separator + "a.png");
        mCropImageUrl = FileUtils.getRootFilePath() + File.separator + FileUtils.createFileNameByDateTime() + ".png";
        crop.setOutputFile(mCropImageUrl);
        //裁剪框不可移动
        crop.setFreeCrop(false);
        //裁剪框大小  比例1：1
        crop.setCropWidth(1);
        crop.setCropHeight(1);
        crop.setToolBarColor(R.color.material_blue_grey_800);
        crop.setToolBarTitle("裁剪图片");
        return crop;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1000:
                    //图片裁剪
                    ToastUtils.showShortMsg(this, "图片裁剪成功,地址：" + mCropImageUrl);
                    break;
            }
        }
    }
}
