package com.library.image.photo;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.library.image.R;
import com.library.image.photo.bean.ImageFolder;

import java.util.ArrayList;


class ImageChooseFolderPop implements View.OnClickListener, AdapterView.OnItemClickListener {

    private PopupWindow mPop;
    private Context mContext;
    private ListView mListView;
    private OnFolderChangeListener mOnFolderChangeListener;


    public interface OnFolderChangeListener {
        void onFolderChanged(ImageFolder imageFolder);
    }

    public ImageChooseFolderPop(Activity context) {
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_choose_photo_folder, null);
        mPop = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        mPop.setBackgroundDrawable(new BitmapDrawable());// 响应返回键必须的语句。
        mPop.setFocusable(true);//设置pop可获取焦点
        mPop.setAnimationStyle(R.style.image_folder_anim);//设置显示、消失动画
        mPop.setOutsideTouchable(true);//设置点击外部可关闭pop

        mListView = (ListView) view.findViewById(R.id.lv_choose_photo_folder_content);
        mListView.setOnItemClickListener(this);
    }

    public void setOnFolderChangeListener(OnFolderChangeListener onFolderChangeListener) {
        this.mOnFolderChangeListener = onFolderChangeListener;
    }

    public void setImageFolderData(ArrayList<ImageFolder> imageFolders, ImageFolder curImageFolder) {
        FolderPopAdapter adapter = new FolderPopAdapter(mContext, imageFolders, curImageFolder);
        mListView.setAdapter(adapter);
    }

    public void showAtLocation(View view, int gravity, int x, int y) {
        mPop.showAtLocation(view, gravity, x, y);
    }

    public boolean isShowing() {
        return mPop != null && mPop.isShowing();
    }

    public void dismiss() {
        mPop.dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageFolder imageFolder = (ImageFolder) parent.getItemAtPosition(position);
        if (null != mOnFolderChangeListener) {
            mOnFolderChangeListener.onFolderChanged(imageFolder);
        }
        dismiss();
    }

    @Override
    public void onClick(View v) {
    }

}
