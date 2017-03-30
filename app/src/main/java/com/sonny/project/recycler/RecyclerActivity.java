package com.sonny.project.recycler;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sonny.project.R;
import com.library.base.BaseActivity;
import com.library.recycleview.IRecycleOnItemListener;
import com.library.recycleview.utils.RecycleViewUtils;
import com.library.utils.screen.DensityUtils;
import com.library.utils.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends BaseActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mViewFooter;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;

    private boolean mRefresh = true;

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
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mViewFooter = findViewById(R.id.layout_footer_item);
    }

    @Override
    public void initLogic() {
        super.initLogic();
        //方向
        final LinearLayoutManager linearLayoutManager = RecycleViewUtils.createVerticalLinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //分割线
        int dividerSize = DensityUtils.dp2px(this, 0.5f);
        int color = getResources().getColor(R.color.gray);
        mRecyclerView.addItemDecoration(RecycleViewUtils.createHorizontalItemDecoration(dividerSize, color));

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && linearLayoutManager.findLastVisibleItemPosition() + 1 == mRecyclerAdapter.getItemCount()) {
                    mViewFooter.setVisibility(View.VISIBLE);
                    mRefresh = false;
                    loadData();
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefresh = true;
                loadData();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        mRefresh = true;
        final List<String> dataList = createData();
        setAdapter(dataList);
    }

    private void loadData() {
        final List<String> dataList = createData();
        mMainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setAdapter(dataList);
            }
        }, 2000);
    }

    private List<String> createData() {
        List<String> dataList = new ArrayList<>();
        int start = null == mRecyclerAdapter ? 0 : mRecyclerAdapter.getItemCount();
        if (mRefresh) {
            start = 0;
        }
        for (int i = start; i < start + 14; i++) {
            if (i % 2 == 0) {
                dataList.add("测试" + i);
            } else {
                dataList.add("http://www.haopic.me/wp-content/uploads/2014/10/20141015011203530.jpg");
            }
        }
        return dataList;
    }

    private void setAdapter(List<String> dataList) {
        if (mRecyclerAdapter == null) {
            mRecyclerAdapter = new RecyclerAdapter(this, dataList);
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
        } else {
            mRecyclerAdapter.refreshData(dataList, mRefresh);
        }
        mSwipeRefreshLayout.setRefreshing(false);
        mViewFooter.setVisibility(View.GONE);
    }
}
