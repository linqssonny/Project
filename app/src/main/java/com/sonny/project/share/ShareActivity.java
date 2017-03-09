package com.sonny.project.share;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.library.base.BaseActivity;
import com.library.recycleview.IRecycleOnItemListener;
import com.library.recycleview.utils.RecycleViewUtils;
import com.library.share.ShareCallBack;
import com.library.share.ShareConfig;
import com.library.share.ShareHelper;
import com.library.share.ShareItem;
import com.sonny.project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/11/10.
 */

public class ShareActivity extends BaseActivity {

    private ShareMenuAdapter mShareMenuAdapter;
    private RecyclerView mRecyclerView;

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
            public void onItemClick(View view, int position, Menu o, Object o2) {
                super.onItemClick(view, position, o, o2);
                showMessage("点击分享：" + o.getTitle());
                share(o);
                finish();
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
        menu.setMenuId(ShareItem.SHARE_QZONE);
        menu.setImageRes(R.drawable.common_icon_room_enable);
        menu.setTitle("空间");
        list.add(menu);

        menu = new Menu();
        menu.setMenuId(ShareItem.SHARE_WECHAT);
        menu.setImageRes(R.drawable.common_icon_weixin_enable);
        menu.setTitle("好友");
        list.add(menu);

        menu = new Menu();
        menu.setMenuId(ShareItem.SHARE_WECHATMOMENTS);
        menu.setImageRes(R.drawable.common_icon_sns_enable);
        menu.setTitle("朋友圈");
        list.add(menu);

        return list;
    }

    private void share(Menu menu) {
        if (null == menu) {
            return;
        }
        ShareItem shareItem = new ShareItem();
        shareItem.setTarget(menu.getMenuId());//分享目的地：QQ、QQ空间、微信好友、微信朋友圈
        shareItem.setTitle("分享标题");
        shareItem.setContent("分享内容、概要");
        shareItem.setType(ShareItem.SHARE_TYPE_URL);//分享类型：链接、图片、文字等
        shareItem.setUrl("分享链接的地址---可选");
        //shareItem.setUrl("http://my.csdn.net/SonnyJack");
        shareItem.setImage("分享图片地址---可选");//如多个图片地址：地址1,地址2,地址3
        //shareItem.setImage("http://avatar.csdn.net/B/7/F/1_sonnyjack.jpg");//如多个图片地址：地址1,地址2,地址3
        shareItem.setMusic("分享音乐地址---可选");
        shareItem.setVideo("分享视频地址---可选");
        shareItem.setShareCallBack(createShareCallBack(shareItem));//分享成功后QQ回调---可选
        ShareHelper.getInstances().share(this, shareItem);
    }

    /**
     * 创建分享回调
     *
     * @param shareItem
     * @return
     */
    private ShareCallBack createShareCallBack(ShareItem shareItem) {
        mShareCallBack = new ShareCallBack(shareItem) {
            @Override
            protected void onComplete(ShareItem shareItem, Object object) {

            }

            @Override
            protected void onError(ShareItem shareItem, int what, String message) {

            }

            @Override
            protected void onCancel(ShareItem shareItem) {

            }
        };
        return mShareCallBack;
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
            if (resultCode == ShareConfig.REQUEST_QQ_SHARE || resultCode == ShareConfig.REQUEST_QZONE_SHARE) {
                //QQ分享
            } else if (resultCode == ShareConfig.REQUEST_LOGIN) {
                //QQ登陆
            }
            ShareHelper.getInstances().onActivityResultData(this, requestCode, resultCode, data, mShareCallBack);
        }
    }
}
