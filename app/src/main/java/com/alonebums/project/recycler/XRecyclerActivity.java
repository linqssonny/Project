package com.alonebums.project.recycler;

import android.os.Bundle;
import android.view.View;

import com.alonebums.project.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.library.base.BaseActivity;
import com.library.recycleview.IRecycleOnItemListener;
import com.library.recycleview.utils.RecycleViewUtils;
import com.library.utils.DensityUtils;
import com.library.utils.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class XRecyclerActivity extends BaseActivity {

    private XRecyclerView mXRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;

    private boolean mRefresh = true;

    @Override
    public int getContentViewId() {
        return R.layout.activity_x_recycle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initUI() {
        mXRecyclerView = (XRecyclerView) findViewById(R.id.x_recycler_view);
    }

    @Override
    public void initLogic() {
        super.initLogic();
        //方向
        mXRecyclerView.setLayoutManager(RecycleViewUtils.createVerticalLinearLayoutManager(this));
        //分割线
        int dividerSize = DensityUtils.dp2px(this, 0.5f);
        int color = getResources().getColor(R.color.gray);
        mXRecyclerView.addItemDecoration(RecycleViewUtils.createHorizontalItemDecoration(dividerSize, color));

        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);

        mXRecyclerView.setPullRefreshEnabled(true);
        mXRecyclerView.setLoadingMoreEnabled(true);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mRefresh = true;
                loadData();
                mMainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mXRecyclerView.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                mRefresh = false;
                loadData();
                mMainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mXRecyclerView.loadMoreComplete();
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        mRefresh = true;
        loadData();
    }

    private void loadData() {
        List<String> dataList = createData();
        setAdapter(dataList);
    }

    private List<String> createData() {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
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
            mXRecyclerView.setAdapter(mRecyclerAdapter);
        } else {
            mRecyclerAdapter.refreshData(dataList, mRefresh);
        }
    }
}
