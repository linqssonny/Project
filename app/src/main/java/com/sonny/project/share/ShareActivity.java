package com.sonny.project.share;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.library.base.BaseActivity;
import com.library.recycleview.IRecycleOnItemListener;
import com.library.recycleview.utils.RecycleViewUtils;
import com.sonny.project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/11/10.
 */

public class ShareActivity extends BaseActivity {

    private ShareMenuAdapter mShareMenuAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public int getContentViewId() {
        return R.layout.activity_share;
    }

    @Override
    public void initUI() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_share_menu);
    }

    @Override
    public void initLogic() {
        super.initLogic();
        //方向
        final GridLayoutManager gridLayoutManager = RecycleViewUtils.createGridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void initData() {
        super.initData();
        List<Menu> dataList = createData();
        mShareMenuAdapter = new ShareMenuAdapter(this, dataList);
        mShareMenuAdapter.setIRecycleOnItemListener(new IRecycleOnItemListener<Menu>() {
            @Override
            public void onItemClick(View view, int position, Menu o, Object o2) {
                super.onItemClick(view, position, o, o2);
                showMessage("点击分享：" + o.getTitle());
                finish();
            }
        });
        mRecyclerView.setAdapter(mShareMenuAdapter);
    }

    private List<Menu> createData() {
        List<Menu> list = new ArrayList<>();
        Menu menu = new Menu();
        menu.setImageRes(R.drawable.common_icon_qq_enable);
        menu.setTitle("QQ");
        list.add(menu);

        menu = new Menu();
        menu.setImageRes(R.drawable.common_icon_room_enable);
        menu.setTitle("空间");
        list.add(menu);

        menu = new Menu();
        menu.setImageRes(R.drawable.common_icon_weixin_enable);
        menu.setTitle("好友");
        list.add(menu);

        menu = new Menu();
        menu.setImageRes(R.drawable.common_icon_sns_enable);
        menu.setTitle("朋友圈");
        list.add(menu);

        return list;
    }

    @Override
    public void finish() {
        super.finish();
        //退出动画
        overridePendingTransition(0, R.anim.fade_out_from_bottom);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        finish();
    }
}
