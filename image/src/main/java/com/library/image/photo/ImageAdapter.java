package com.library.image.photo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.library.image.R;
import com.library.image.photo.bean.Image;
import com.library.image.utils.ImageUtils;
import com.sonnyjack.utils.screen.ScreenUtils;

import java.util.ArrayList;

/**
 * Created by admin on 2016/6/30.
 */
class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Image> mDataList;
    private OnChoosePhotoListener mOnChoosePhotoListener;

    //选中的图片
    private ArrayList<Image> mSelectList = new ArrayList<>();
    private int mImageSize;
    //最多选择数量
    private int mMaxImage = 1;//最多选择数量

    private final int TYPE_CAMERA = 1;
    private final int TYPE_IMAGE = 2;

    public ImageAdapter(Context context, ArrayList<Image> selectList, int columnsNum, int maxImage) {
        this(context, null, selectList, columnsNum, maxImage);
    }

    public ImageAdapter(Context context, ArrayList<Image> dataList, ArrayList<Image> selectList, int columnsNum, int maxImage) {
        this.mContext = context;
        this.mDataList = dataList;
        if (null != selectList && selectList.size() > 0) {
            this.mSelectList = selectList;
        }
        this.mImageSize = ScreenUtils.getScreenWidth(mContext) / columnsNum;
        this.mMaxImage = maxImage;
    }

    public void setOnChoosePhotoListener(OnChoosePhotoListener onChoosePhotoListener) {
        this.mOnChoosePhotoListener = onChoosePhotoListener;
    }

    public void refreshData(ArrayList<Image> dataList) {
        this.mDataList = dataList;
        notifyDataSetChanged();
    }

    public ArrayList<Image> getSelectList() {
        return mSelectList;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        Image image = mDataList.get(position);
        if (image.getImageId().equals("-1")) {
            return TYPE_CAMERA;
        }
        return TYPE_IMAGE;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        int viewType = getItemViewType(position);
        if (null == convertView) {
            switch (viewType) {
                case TYPE_CAMERA:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_choose_photo_camera, null);
                    break;
                case TYPE_IMAGE:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_choose_photo_image, null);
                    break;
            }
            viewHolder = new ViewHolder(convertView);

            switch (viewType) {
                case TYPE_CAMERA:
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewHolder.mLlCamera.getLayoutParams();
                    layoutParams.width = mImageSize;
                    layoutParams.height = mImageSize;
                    viewHolder.mLlCamera.setLayoutParams(layoutParams);
                    break;
                case TYPE_IMAGE:
                    layoutParams = (LinearLayout.LayoutParams) viewHolder.mRlImage.getLayoutParams();
                    layoutParams.width = mImageSize;
                    layoutParams.height = mImageSize;
                    viewHolder.mRlImage.setLayoutParams(layoutParams);
                    break;
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Image image = mDataList.get(position);
        if (null != image) {
            setData(viewType, position, image, viewHolder);
        }
        return convertView;
    }

    private void setData(int viewType, int position, final Image image, ViewHolder viewHolder) {
        if (viewType == TYPE_CAMERA) {
            //拍照
            viewHolder.mLlCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mOnChoosePhotoListener) {
                        mOnChoosePhotoListener.onItemClick(image);
                    }
                }
            });
        } else {
            //图片
            ImageUtils.displayImage(mContext, image.getThumbnailPath(), viewHolder.mIvImage);
            viewHolder.mChkBox.setOnCheckedChangeListener(null);
            if (mSelectList.contains(image)) {
                viewHolder.mChkBox.setChecked(true);
            } else {
                viewHolder.mChkBox.setChecked(false);
            }
            viewHolder.mChkBox.setTag(position);
            viewHolder.mChkBox.setOnCheckedChangeListener(new OnCheckedChangeListenerImpl());
            viewHolder.mIvImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mOnChoosePhotoListener) {
                        mOnChoosePhotoListener.onItemClick(image);
                    }
                }
            });
        }
    }

    class OnCheckedChangeListenerImpl implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            CheckBox checkBox = (CheckBox) buttonView;
            int position = (int) checkBox.getTag();
            if (isChecked) {
                if (mSelectList.size() <= mMaxImage - 1) {
                    mSelectList.add(mDataList.get(position));
                } else {
                    checkBox.setChecked(false);
                    mSelectList.remove(mDataList.get(position));
                    Toast.makeText(mContext, mContext.getString(R.string.image_choose_photo_max, String.valueOf(mMaxImage)), Toast.LENGTH_SHORT).show();
                }
            } else {
                mSelectList.remove(mDataList.get(position));
            }
            if (mOnChoosePhotoListener != null) {
                mOnChoosePhotoListener.onSelectChanged(mSelectList);
            }
        }
    }

    class ViewHolder {
        private View mLlCamera, mRlImage;
        private ImageView mIvImage;
        private CheckBox mChkBox;

        public ViewHolder(View convertView) {
            mLlCamera = convertView.findViewById(R.id.ll_choose_photo_camera);
            mRlImage = convertView.findViewById(R.id.rl_choose_photo_image);
            mIvImage = (ImageView) convertView.findViewById(R.id.iv_choose_photo_image);
            mChkBox = (CheckBox) convertView.findViewById(R.id.cb_choose_photo_image);
        }
    }

    public interface OnChoosePhotoListener {
        void onItemClick(Image image);

        void onSelectChanged(ArrayList<Image> selectImages);
    }
}
