package com.sonny.project.image;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.library.base.BaseActivity;
import com.library.image.photo.ChoosePhotoActivity;
import com.library.image.photo.bean.Image;
import com.library.image.photoview.PreviewPhotoActivity;
import com.library.image.utils.ImageDisplayOption;
import com.library.image.utils.ImageUtils;
import com.sonny.project.R;
import com.sonnyjack.permission.IRequestPermissionCallBack;
import com.sonnyjack.permission.PermissionUtils;
import com.sonnyjack.utils.date.DateUtils;
import com.sonnyjack.utils.system.SystemUtils;
import com.sonnyjack.utils.toast.ToastUtils;

import java.io.File;
import java.util.ArrayList;

public class ImageActivity extends BaseActivity {

    private ImageView mIvImg;

    private String mImgUrl = "http://www.haopic.me/wp-content/uploads/2014/10/20141015011203530.jpg";
    private String mImgUrl_1 = "http://www.haopic.me/wp-content/uploads/2016/08/20160808105305131.jpg";

    private ArrayList<Image> mSelect = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
        addOnClick(R.id.btn_image_select);

        addOnClick(mIvImg);

        //init image display config
        ImageDisplayOption imageDisplayOption = new ImageDisplayOption();
        imageDisplayOption.setError(R.mipmap.ic_launcher);
        ImageUtils.getInstances().init(imageDisplayOption);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_image_show:
                //显示图片
                ImageUtils.getInstances().displayImage(this, mImgUrl, mIvImg);
                //ImageUtils.displayImage(this, R.drawable.icon_image_show, mIvImg);
                break;
            case R.id.btn_image_circle:
                //圆形图片
                //ImageUtils.displayCircleImage(this, R.drawable.icon_image_show, mIvImg);
                //本地图片圆角
                //ImageUtils.displayCircleImage(this, mImgUrl, mIvImg);

                //圆角 自定义半径
                ImageUtils.getInstances().displayRadiusImage(this, R.drawable.icon_image_show, mIvImg, 50);

                //下载图片
                /*ImageUtils.getInstances().download(this, mImgUrl, 2000, 2000, new ImageDownloadCallBack() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        mIvImg.setImageBitmap(bitmap);
                    }
                });*/
                break;
            case R.id.iv_image_img:
                //点击图片预览
                Intent intent = new Intent(this, PreviewPhotoActivity.class);
                ArrayList<Image> arrayList = new ArrayList<>();
                Image image = new Image();
                image.setImagePath(mImgUrl);
                image.setThumbnailPath(mImgUrl);
                arrayList.add(image);

                image = new Image();
                image.setImagePath(mImgUrl_1);
                image.setThumbnailPath(mImgUrl_1);
                arrayList.add(image);

                intent.putParcelableArrayListExtra(PreviewPhotoActivity.IMAGE_SELECT, arrayList);
                startActivityForResult(intent, 100);
                //startActivity(intent);
                break;
            case R.id.btn_image_select:
                //选择图片
                selectImage();
                break;
        }
    }

    private void selectImage() {
        ArrayList<String> permissionList = new ArrayList<>();
        permissionList.add(Manifest.permission.CAMERA);
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        PermissionUtils.getInstances().requestPermission(getActivity(), permissionList, new IRequestPermissionCallBack() {
            @Override
            public void onGranted() {
                //选择图片
                Intent intent = new Intent(getActivity(), ChoosePhotoActivity.class);
                //每行显示图片张数
                intent.putExtra(ChoosePhotoActivity.COLUMNS_NUM, 3);
                //最多选择的图片数
                intent.putExtra(ChoosePhotoActivity.IMAGE_MAX_NUM, 2);

                //选中的图片
                intent.putParcelableArrayListExtra(ChoosePhotoActivity.IMAGE_SELECT, mSelect);

                File file = new File(SystemUtils.getRootFolderAbsolutePath() + File.separator + "Project");
                file.mkdirs();
                file = new File(file.getAbsolutePath(), DateUtils.buildCurrentDateString(DateUtils.DEFAULT_MILLISECOND_FORMAT) + ".png");
                //拍照  自定义存储地址
                intent.putExtra(ChoosePhotoActivity.CAMERA_PATH, file.getAbsolutePath());
                startActivityForResult(intent, 101);
            }

            @Override
            public void onDenied() {
                showMessage("请在应用管理中打开拍照权限");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            if (100 == requestCode) {
                ArrayList<Image> arrayList = data.getParcelableArrayListExtra(PreviewPhotoActivity.IMAGE_SELECT);
                if (null != arrayList) {
                    ToastUtils.showLongMsg(this, "the select image size : " + arrayList.size());
                }
                mSelect = arrayList;
            }
            if (101 == requestCode) {
                ArrayList<Image> arrayList = data.getParcelableArrayListExtra(PreviewPhotoActivity.IMAGE_SELECT);
                if (null != arrayList) {
                    ToastUtils.showLongMsg(this, "the select image size : " + arrayList.size());
                }
                mSelect = arrayList;
            }
        }
    }
}
