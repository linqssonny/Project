package com.alonebums.project.recycler;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alonebums.project.R;
import com.library.image.utils.ImageUtils;
import com.library.recycleview.CommonViewHolder;
import com.library.recycleview.CommonAdapter;
import com.library.utils.log.LogUtils;

import java.util.List;

/**
 * Created by linqs on 2016/8/20.
 */
public class RecyclerAdapter extends CommonAdapter<String> {

    public RecyclerAdapter(Context context, List<String> dataList) {
        super(context, dataList);
    }

    @Override
    protected int createItemLayout(int viewType) {
        LogUtils.d("execute method : createItemLayout()");
        if (viewType == 0) {
            return R.layout.adapter_recycle_text;
        } else {
            return R.layout.adapter_recycle_image;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    protected void onBindViewHolder(CommonViewHolder viewHolder, final String item, final int position) {
        if (position % 2 == 0) {
            TextView textView = viewHolder.getView(R.id.tv_recycle_content);
            textView.setText(item);
        } else {
            ImageView imageView = viewHolder.getView(R.id.iv_recycle_content);
            ImageUtils.displayImage(mContext, getItem(position), imageView);
        }
        /*textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mIRecycleOnItemListener) {
                    mIRecycleOnItemListener.onItemClick(v, position, item, null);
                }
            }
        });
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mIRecycleOnItemListener) {
                    mIRecycleOnItemListener.onLongClick(v, position, item, null);
                }
                return false;
            }
        });*/
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mIRecycleOnItemListener) {
                    mIRecycleOnItemListener.onItemClick(v, position, item, null);
                }
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mIRecycleOnItemListener) {
                    mIRecycleOnItemListener.onLongClick(v, position, item, null);
                }
                return true;
            }
        });
    }
}
