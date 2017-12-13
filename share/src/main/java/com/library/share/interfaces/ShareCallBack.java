package com.library.share.interfaces;

import com.library.share.bean.ShareItem;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * Created by admin on 2016/11/10.
 */

public abstract class ShareCallBack implements IUiListener {

    private ShareItem mShareItem;

    public ShareCallBack(ShareItem shareItem) {
        this.mShareItem = shareItem;
    }

    public ShareItem getShareItem() {
        return this.mShareItem;
    }

    public abstract void onComplete(ShareItem shareItem, Object object);

    public abstract void onError(ShareItem shareItem, int what, String message);

    public abstract void onCancel(ShareItem shareItem);

    @Override
    public final void onComplete(Object o) {
        onComplete(mShareItem, o);
    }

    @Override
    public final void onError(UiError uiError) {
        onError(mShareItem, uiError.errorCode, uiError.errorMessage);
    }

    @Override
    public final void onCancel() {
        onCancel(mShareItem);
    }
}