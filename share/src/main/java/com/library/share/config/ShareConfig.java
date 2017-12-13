package com.library.share.config;

import com.tencent.connect.common.Constants;

/**
 * 分享Config
 * Created by linqs on 2017/3/9.
 */

public class ShareConfig {

    public static int IMAGE_MAX_WIDTH = 1080;
    public static int IMAGE_MAX_HEIGHT = 1920;

    /**
     * 请求QQ分享、登陆   requestCode
     */
    public static final int REQUEST_API = Constants.REQUEST_API;

    /**
     * QQ分享结果码
     */
    public static final int REQUEST_QQ_SHARE = Constants.REQUEST_QQ_SHARE;

    /**
     * QQ空间分享结果码
     */
    public static final int REQUEST_QQ_ZONE_SHARE = Constants.REQUEST_QZONE_SHARE;

    /**
     * QQ登陆结果码
     */
    public static final int REQUEST_LOGIN = Constants.REQUEST_LOGIN;

}
