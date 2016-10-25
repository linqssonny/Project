package com.alonebums.project.recycler;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alonebums.project.R;
import com.library.base.BaseActivity;
import com.library.recycleview.IRecycleOnItemListener;
import com.library.recycleview.utils.RecycleViewUtils;
import com.library.utils.DensityUtils;
import com.library.utils.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;

    private List<String> mDataList;

    @Override
    public int getContentViewId() {
        return R.layout.activity_recycle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initUI() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    @Override
    public void initLogic() {
        super.initLogic();
        //方向
        mRecyclerView.setLayoutManager(RecycleViewUtils.createVerticalLinearLayoutManager(this));
        //分割线
        int dividerSize = DensityUtils.dp2px(this, 0.5f);
        int color = getResources().getColor(R.color.gray);
        mRecyclerView.addItemDecoration(RecycleViewUtils.createHorizontalItemDecoration(dividerSize, color));
    }

    @Override
    public void initData() {
        super.initData();
        mDataList = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            if (i % 2 == 0) {
                mDataList.add("测试" + i);
            }else {
                mDataList.add("http://www.haopic.me/wp-content/uploads/2014/10/20141015011203530.jpg");
            }
        }
        mRecyclerAdapter = new RecyclerAdapter(this, mDataList);
        mRecyclerAdapter.setIRecycleOnItemListener(new IRecycleOnItemListener<String>() {
            @Override
            public void onItemClick(View view, int position, String s, Object o) {
                super.onItemClick(view, position, s, o);
                ToastUtils.showShortMsg(getActivity(), "点击：" + s);
            }

            @Override
            public void onLongClick(View view, int position, String s, Object o) {
                super.onLongClick(view, position, s, o);
                ToastUtils.showShortMsg(getActivity(), "长按：" + s);
            }
        });
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }
}
