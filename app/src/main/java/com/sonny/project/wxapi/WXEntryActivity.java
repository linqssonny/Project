package com.sonny.project.wxapi;

import com.library.base.BaseActivity;
import com.library.share.ShareHelper;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信回调
 * Created by linqs on 2017/3/31.
 */

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initLogic() {
        super.initLogic();
        api = WXAPIFactory.createWXAPI(getActivity(), ShareHelper.getInstances().getWeChatKey(), false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onReq(BaseReq baseReq) {
        //当微信发送请求到你的应用，将通过IWXAPIEventHandler接口的onReq方法进行回调
    }

    @Override
    public void onResp(BaseResp baseResp) {
        //应用请求微信的响应结果将通过onResp回调
        switch (baseResp.errCode){
            case BaseResp.ErrCode.ERR_OK:
                //请求成功，比如：分享成功、授权成功
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //分享取消、授权取消
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //用户拒绝授权
                break;
        }
    }
}
