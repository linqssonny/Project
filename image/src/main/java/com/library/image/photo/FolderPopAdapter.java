package com.library.image.photo;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.library.image.R;
import com.library.image.photo.bean.ImageFolder;
import com.library.image.utils.ImageUtils;

import java.util.List;

/**
 *
 */
class FolderPopAdapter extends BaseAdapter {

    private Context mContext;
    private List<ImageFolder> mFolderList;
    private ImageFolder mCurFolder;

    public FolderPopAdapter(Context context, List<ImageFolder> folders, ImageFolder curFolder) {
        this.mContext = context.getApplicationContext();
        this.mFolderList = folders;
        this.mCurFolder = curFolder;
    }

    public void refreshData(List<ImageFolder> folders) {
        this.mFolderList = folders;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == mFolderList ? 0 : mFolderList.size();
    }

    @Override
    public Object getItem(int position) {
        return null == mFolderList ? null : mFolderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_choose_photo_folder_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ImageFolder imageFolder = mFolderList.get(position);
        String firstImgPath = imageFolder.getFirstImgPath();
        if (!TextUtils.isEmpty(firstImgPath)) {
            ImageUtils.displayImage(mContext, firstImgPath, viewHolder.mIvImage);
        }
        viewHolder.mTvFolderName.setText(imageFolder.getFolderName());
        viewHolder.mTvFolderNum.setText(mContext.getString(R.string.image_choose_photo_num, String.valueOf(imageFolder.getNum())));
        if (mCurFolder != null && TextUtils.equals(imageFolder.getFolderId(), mCurFolder.getFolderId()))
            viewHolder.mIvImageSelect.setVisibility(View.VISIBLE);
        else
            viewHolder.mIvImageSelect.setVisibility(View.GONE);
        return convertView;
    }

    private class ViewHolder {
        ImageView mIvImage;
        TextView mTvFolderName;
        TextView mTvFolderNum;
        ImageView mIvImageSelect;

        ViewHolder(View convertView) {
            mIvImage = (ImageView) convertView.findViewById(R.id.iv_choose_photo_folder_item_img);
            mTvFolderName = (TextView) convertView.findViewById(R.id.tv_choose_photo_folder_item_name);
            mTvFolderNum = (TextView) convertView.findViewById(R.id.tv_choose_photo_folder_item_num);
            mIvImageSelect = (ImageView) convertView.findViewById(R.id.iv_choose_photo_folder_item_select);
        }
    }
}
