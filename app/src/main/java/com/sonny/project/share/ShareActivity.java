package com.sonny.project.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.library.base.BaseActivity;
import com.library.recycleview.IRecycleOnItemListener;
import com.library.recycleview.utils.RecycleViewUtils;
import com.library.share.interfaces.ShareCallBack;
import com.library.share.config.ShareConfig;
import com.library.share.ShareHelper;
import com.library.share.bean.ShareItem;
import com.sonny.project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/11/10.
 */

public class ShareActivity extends BaseActivity {

    private ShareMenuAdapter mShareMenuAdapter;
    private RecyclerView mRecyclerView;

    //分享item
    private ShareItem mShareItem;
    //分享回调
    private ShareCallBack mShareCallBack;

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
            public void onItemClick(View view, int position, Menu menu, Object o2) {
                super.onItemClick(view, position, menu, o2);
                showMessage("点击分享：" + menu.getTitle());
                share(menu);
            }
        });
        mRecyclerView.setAdapter(mShareMenuAdapter);
    }

    private List<Menu> createData() {
        List<Menu> list = new ArrayList<>();
        Menu menu = new Menu();
        menu.setMenuId(ShareItem.SHARE_QQ);
        menu.setImageRes(R.drawable.common_icon_qq_enable);
        menu.setTitle("QQ");
        list.add(menu);

        menu = new Menu();
        menu.setMenuId(ShareItem.SHARE_QQ_ZONE);
        menu.setImageRes(R.drawable.common_icon_room_enable);
        menu.setTitle("空间");
        list.add(menu);

        menu = new Menu();
        menu.setMenuId(ShareItem.SHARE_WE_CHAT);
        menu.setImageRes(R.drawable.common_icon_weixin_enable);
        menu.setTitle("好友");
        list.add(menu);

        menu = new Menu();
        menu.setMenuId(ShareItem.SHARE_WE_CHAT_MOMENTS);
        menu.setImageRes(R.drawable.common_icon_sns_enable);
        menu.setTitle("朋友圈");
        list.add(menu);

        return list;
    }

    private void share(Menu menu) {
        if (null == menu) {
            return;
        }
        mShareItem = new ShareItem();
        mShareItem.setTarget(menu.getMenuId());//分享目的地：QQ、QQ空间、微信好友、微信朋友圈
        mShareItem.setTitle("分享标题");
        mShareItem.setContent("分享内容、概要");
        //mShareItem.setThumb("缩略图，不传取image字段值");
        mShareItem.setThumb("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2135367176,3038208295&fm=27&gp=0.jpg");
        mShareItem.setType(ShareItem.SHARE_TYPE_URL);//分享类型：链接、图片、文字等
        //mShareItem.setUrl("分享链接的地址---可选");
        mShareItem.setUrl("http://my.csdn.net/SonnyJack");
        //mShareItem.addImage("分享图片地址---可选");//多个地址调用多次
        mShareItem.addImage("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1279864326,149287344&fm=27&gp=0.jpg");//如多个图片地址：地址1,地址2,地址3
        //mShareItem.setMusic("分享音乐地址");
        //mShareItem.setVideo("分享视频地址");
        mShareItem.setShareCallBack(createShareCallBack());//分享成功后QQ回调---可选
        ShareHelper.getInstances().share(this, mShareItem);

        //ShareHelper.getInstances().loginQQ(this, mShareItem);//QQ授权登陆
        //ShareHelper.getInstances().loginWeChat(this, mShareItem);//微信授权登陆
    }

    /**
     * 创建分享回调(微信需特殊处理)
     *
     * @return
     */
    private ShareCallBack createShareCallBack() {
        mShareCallBack = new ShareCallBack(mShareItem) {
            @Override
            public void onComplete(ShareItem shareItem, Object object) {
                //分享完成、登陆成功
                resetShareInfo();
                showMessage("分享成功|登陆成功");
                finish();
            }

            @Override
            public void onError(ShareItem shareItem, int what, String message) {
                //分享错误、登陆失败
                resetShareInfo();
                showMessage(message);
                finish();
            }

            @Override
            public void onCancel(ShareItem shareItem) {
                //分享取消、登陆取消
                resetShareInfo();
                finish();
            }
        };
        return mShareCallBack;
    }

    /**
     * 重置分享info
     */
    private void resetShareInfo() {
        mShareItem = null;
        mShareCallBack = null;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //QQ回调
        if (requestCode == ShareConfig.REQUEST_API) {
            if (resultCode == ShareConfig.REQUEST_QQ_SHARE || resultCode == ShareConfig.REQUEST_QQ_ZONE_SHARE) {
                //QQ分享
            } else if (resultCode == ShareConfig.REQUEST_LOGIN) {
                //QQ登陆
            }
            ShareHelper.getInstances().onActivityResultData(this, requestCode, resultCode, data, mShareCallBack);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != mShareItem) {
            outState.putParcelable("ShareItem", outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mShareItem = savedInstanceState.getParcelable("ShareItem");
        if (null != mShareItem) {
            createShareCallBack();
        }
    }
}
