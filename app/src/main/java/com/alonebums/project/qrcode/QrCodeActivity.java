package com.alonebums.project.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alonebums.project.R;
import com.google.zxing.client.android.CaptureActivity;
import com.library.base.BaseActivity;
import com.library.qrcode.QrCodeUtils;
import com.library.utils.DensityUtils;

/**
 * 扫描二维码
 * Created by admin on 2016/11/2.
 */

public class QrCodeActivity extends BaseActivity {

    private final int REQUESTCODE = 1003;

    private TextView mTvValue;
    private ImageView mIvImg;

    @Override
    public int getContentViewId() {
        return R.layout.activity_qr_code;
    }

    @Override
    public void initUI() {
        mTvValue = (TextView) findViewById(R.id.tv_qr_code_value);
        mIvImg = (ImageView) findViewById(R.id.iv_qr_code_img);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        Integer request = null;
        switch (v.getId()) {
            case R.id.btn_qr_code_scan:
                //扫描二维码/条形码
                request = REQUESTCODE;
                intent = new Intent(this, CaptureActivity.class);
                break;
            case R.id.btn_qr_code_decode:
                //生成二维码
                buildQrCode("福建省厦门市思明区");
                break;
            case R.id.btn_bar_code_decode:
                //生成条形码
                buildBarCode("15859059335");
                break;
            case R.id.btn_qr_code_encode:
                //扫描本地二维码
                readerQrCodeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.qr_code));
                break;
            case R.id.btn_bar_code_encode:
                //扫描本地条形码
                readerBarCodeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bar_code));
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

    private void readerQrCodeBitmap(Bitmap bitmap) {
        if (null == bitmap || bitmap.isRecycled()) {
            return;
        }
        String s = QrCodeUtils.decodeQrCode(bitmap);
        mTvValue.setText(s);
    }

    private void readerBarCodeBitmap(Bitmap bitmap) {
        if (null == bitmap || bitmap.isRecycled()) {
            return;
        }
        String s = QrCodeUtils.decodeBarCode(bitmap);
        mTvValue.setText(s);
    }

    private void buildQrCode(String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        int size = DensityUtils.dp2px(this, 120);
        Bitmap bitmap = QrCodeUtils.buildQrCodeBitmap(value, size, size);
        mIvImg.setImageBitmap(bitmap);
    }

    private void buildBarCode(String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        int width = DensityUtils.dp2px(this, 240);
        int height = DensityUtils.dp2px(this, 120);
        Bitmap bitmap = QrCodeUtils.buildBarCodeBitmap(value, width, height);
        mIvImg.setImageBitmap(bitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case REQUESTCODE:
                    mTvValue.setText(data.getStringExtra(CaptureActivity.QR_CODE_RESULT));
                    break;
            }
        }
    }
}
