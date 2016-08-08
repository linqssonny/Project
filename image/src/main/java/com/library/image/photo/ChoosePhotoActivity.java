package com.library.image.photo;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.library.image.R;
import com.library.utils.file.FileUtils;
import com.library.image.photo.bean.Image;
import com.library.image.photo.bean.ImageFolder;
import com.library.image.photoview.PreviewPhotoActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by admin on 2016/6/29.
 */
public class ChoosePhotoActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int PHOTO_REQUEST_CODE = 0x1000;
    private final int CAMERA_REQUEST_CODE = 0x1001;

    public static final String COLUMNS_NUM = "columns_num";
    public static final String IMAGE_MAX_NUM = "image_max_num";
    public static final String IMAGE_SELECT = "image_select";
    public static final String CAMERA_PATH = "camera_path";

    private GridView mGridView;
    private ProgressBar mProgressBar;
    private ImageAdapter mImageAdapter;
    private Button mBtnConfirm;
    private TextView mTvImageFolder;

    private ArrayList<ImageFolder> mImageFolderList;
    private ArrayList<Image> mImageList;
    private int mMaxNum = 1;
    private ArrayList<Image> mSelectImage;
    private int mColumnNum = 3;

    private ImageChooseFolderPop mImageChooseFolderPop;
    private ImageFolder mCurrentImageFolder;

    //拍照地址
    private String mCameraPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_photo);
        initUI();
        initLogic();
        initData();
    }

    private void initUI() {
        mGridView = (GridView) findViewById(R.id.gv_choose_photo_layout);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_choose_photo_bar);
        mBtnConfirm = (Button) findViewById(R.id.btn_choose_photo_confirm);
        mTvImageFolder = (TextView) findViewById(R.id.tv_choose_photo_folder);
    }

    private void initLogic() {
        Intent intent = getIntent();
        mColumnNum = intent.getIntExtra(COLUMNS_NUM, 3);
        mGridView.setNumColumns(mColumnNum);

        mMaxNum = intent.getIntExtra(IMAGE_MAX_NUM, 3);
        mSelectImage = intent.getParcelableArrayListExtra(IMAGE_SELECT);

        mCameraPath = intent.getStringExtra(CAMERA_PATH);

        mBtnConfirm.setOnClickListener(this);
        mTvImageFolder.setOnClickListener(this);
        findViewById(R.id.tv_choose_photo_preview).setOnClickListener(this);
    }

    private void initData() {
        new ScanImagesAsyncTask().execute();
    }

    private void fillData() {
        Image image = new Image();
        image.setImageId("-1");
        mImageList.add(0, image);

        ImageFolder imageFolder = new ImageFolder();
        imageFolder.setFolderId("-1");
        imageFolder.setFolderName(getString(R.string.image_choose_photo_all));
        imageFolder.setNum(mImageList.size() - 1);
        if (null != mImageList && mImageList.size() > 0) {
            imageFolder.setFirstImgPath(mImageList.get(0).getThumbnailPath());
        }
        mImageFolderList.add(0, imageFolder);

        mCurrentImageFolder = imageFolder;
        setImageData();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_choose_photo_back) {
            //返回
            finish();
        } else if (id == R.id.tv_choose_photo_back) {
            //返回
            finish();
        } else if (id == R.id.btn_choose_photo_confirm) {
            //确定
            mSelectImage = mImageAdapter.getSelectList();
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra(IMAGE_SELECT, mSelectImage);
            setResult(RESULT_OK, intent);
            finish();
        } else if (id == R.id.tv_choose_photo_preview) {
            //预览
            mSelectImage = mImageAdapter.getSelectList();
            if (null == mSelectImage || mSelectImage.size() <= 0) {
                return;
            }
            Intent intent = new Intent(this, PreviewPhotoActivity.class);
            intent.putParcelableArrayListExtra(PreviewPhotoActivity.IMAGE_SELECT, mSelectImage);
            startActivityForResult(intent, PreviewPhotoActivity.PREVIEW_PHOTO_REQUEST_CODE);
        } else if (id == R.id.tv_choose_photo_folder) {
            //图片文件
            initImageChooseFolderPop();
            View view = findViewById(R.id.rl_choose_photo_footer_layout);
            mImageChooseFolderPop.showAtLocation(view, Gravity.BOTTOM, 0, view.getHeight());
        }
    }

    private void initImageChooseFolderPop() {
        if (null == mImageChooseFolderPop) {
            mImageChooseFolderPop = new ImageChooseFolderPop(this);
            mImageChooseFolderPop.setImageFolderData(mImageFolderList, mCurrentImageFolder);
            mImageChooseFolderPop.setOnFolderChangeListener(new ImageChooseFolderPop.OnFolderChangeListener() {
                @Override
                public void onFolderChanged(ImageFolder imageFolder) {
                    //文件夹改变
                    mCurrentImageFolder = imageFolder;
                    setImageData();
                }
            });
        } else {
            mImageChooseFolderPop.setImageFolderData(mImageFolderList, mCurrentImageFolder);
        }
    }

    private void setImageData() {
        if (null == mImageAdapter) {
            mImageAdapter = new ImageAdapter(this, mImageList, mSelectImage, mColumnNum, mMaxNum);
            mImageAdapter.setOnChoosePhotoListener(new ImageAdapter.OnChoosePhotoListener() {
                @Override
                public void onItemClick(Image image) {
                    if ("-1".equals(image.getImageId())) {
                        //拍照
                        takeCamera();
                    } else {
                        //预览
                    }
                }

                @Override
                public void onSelectChanged(ArrayList<Image> selectImages) {
                    int num = null == selectImages ? 0 : selectImages.size();
                    if (num <= 0) {
                        mBtnConfirm.setText(R.string.image_choose_photo_confirm);
                    } else {
                        mBtnConfirm.setText(getString(R.string.image_choose_photo_confirm_num, "(" + num + "/" + mMaxNum + ")"));
                    }
                    mSelectImage = selectImages;
                }
            });
            mGridView.setAdapter(mImageAdapter);
        }
        if (null == mCurrentImageFolder || "-1".equals(mCurrentImageFolder.getFolderId())) {
            mImageAdapter.refreshData(mImageList);
        } else {
            ArrayList<Image> folders = new ArrayList<>();
            for (Image image : mImageList) {
                if (null == image) {
                    continue;
                }
                if (TextUtils.equals(mCurrentImageFolder.getFolderId(), image.getFolderId())) {
                    folders.add(image);
                }
            }
            mImageAdapter.refreshData(folders);
        }
        mTvImageFolder.setText(mCurrentImageFolder.getFolderName());
    }

    private void takeCamera() {
        File file;
        if (TextUtils.isEmpty(mCameraPath)) {
            mCameraPath = FileUtils.getRootFilePath();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault());
            String fileName = simpleDateFormat.format(new Date()) + ".png";
            file = new File(mCameraPath, fileName);
            mCameraPath = file.getAbsolutePath();
        } else {
            file = new File(mCameraPath);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PreviewPhotoActivity.PREVIEW_PHOTO_REQUEST_CODE:
                    //预览回调
                    mSelectImage = data.getParcelableArrayListExtra(PreviewPhotoActivity.IMAGE_SELECT);
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra(IMAGE_SELECT, mSelectImage);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                case CAMERA_REQUEST_CODE:
                    //拍照
                    intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(new File(mCameraPath)));
                    sendBroadcast(intent);//通知图库刷新
                    intent = new Intent();
                    intent.putExtra(CAMERA_PATH, mCameraPath);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
            }
        }
    }

    class ScanImagesAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
            mImageFolderList = new ArrayList<>();
            mImageList = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... params) {
            PhotoImageUtils.scanAllImages(ChoosePhotoActivity.this, mImageList, mImageFolderList);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            fillData();
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
