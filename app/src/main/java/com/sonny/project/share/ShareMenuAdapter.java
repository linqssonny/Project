package com.sonny.project.share;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.library.recycleview.CommonAdapter;
import com.library.recycleview.CommonViewHolder;
import com.sonny.project.R;

import java.util.List;

/**
 * Created by linqs on 2016/8/20.
 */
public class ShareMenuAdapter extends CommonAdapter<Menu> {

    public ShareMenuAdapter(Context context, List<Menu> dataList) {
        super(context, dataList);
    }

    @Override
    protected int createItemLayout(int viewType) {
        return R.layout.adapter_share_menu;
    }

    @Override
    protected void onBindViewHolder(CommonViewHolder viewHolder, final Menu item, final int position) {
        ImageView imageView = viewHolder.getView(R.id.iv_share_menu_img);
        imageView.setImageResource(item.getImageRes());
        TextView textView = viewHolder.getView(R.id.tv_share_menu_text);
        textView.setText(item.getTitle());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mIRecycleOnItemListener) {
                    mIRecycleOnItemListener.onItemClick(v, position, item, null);
                }
            }
        });
    }
}
