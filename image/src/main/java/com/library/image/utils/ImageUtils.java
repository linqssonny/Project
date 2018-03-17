package com.library.image.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

//https://muyangmin.github.io/glide-docs-cn/doc/getting-started.html  glide  api文档

//https://github.com/wasabeef/glide-transformations  第三方变换

/**
 * Created by admin on 2016/6/29.
 */
public class ImageUtils {

    private ImageDisplayOption mImageDisplayOption;

    private ImageUtils() {

    }

    private static class ImageUtilsManager {
        private static ImageUtils sImageUtils = new ImageUtils();
    }

    public static ImageUtils getInstances() {
        return ImageUtilsManager.sImageUtils;
    }

    public void init(ImageDisplayOption imageDisplayOption) {
        mImageDisplayOption = imageDisplayOption;
    }

    private RequestOptions buildRequestOptions(ImageDisplayOption imageDisplayOption) {
        ImageDisplayOption displayOption = (null == imageDisplayOption ? mImageDisplayOption : imageDisplayOption);
        if (null == displayOption) {
            displayOption = createDefaultImageDisplayOption();
        }
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        if (displayOption.getPlaceholder() > 0) {
            requestOptions.placeholder(displayOption.getPlaceholder());
        }
        if (displayOption.getError() > 0) {
            requestOptions.error(displayOption.getError());
        }
        if (displayOption.getOverrideWidth() > 0 || displayOption.getOverrideHeight() > 0) {
            requestOptions.override(displayOption.getOverrideWidth(), displayOption.getOverrideHeight());
        }
        switch (displayOption.getDecodeFormat()) {
            case ImageDisplayOption.DECODE_FORMAT_RGB_565:
                requestOptions.format(DecodeFormat.PREFER_RGB_565);
                break;
            case ImageDisplayOption.DECODE_FORMAT_ARGB_8888:
                requestOptions.format(DecodeFormat.PREFER_ARGB_8888);
                break;
            default:
                requestOptions.format(DecodeFormat.PREFER_RGB_565);
                break;
        }

        return requestOptions;
    }

    public ImageDisplayOption createDefaultImageDisplayOption() {
        ImageDisplayOption imageDisplayOption = new ImageDisplayOption();
        return imageDisplayOption;
    }

    /**
     * 删除内存缓存
     *
     * @param context
     */
    public void clearMemory(Context context) {
        // 必须在UI线程中调用
        Glide.get(context).clearMemory();
    }

    /**
     * 删除磁盘缓存
     *
     * @param context
     */
    public void clearDiskCache(Context context) {
        // 必须在后台线程中调用，建议同时clearMemory()
        Glide.get(context.getApplicationContext()).clearDiskCache();
    }

    public void displayImage(Context context, String imgUrl, ImageView imageView) {
        displayImage(context, imgUrl, imageView, null);
    }

    public void displayImage(Context context, String imgUrl, ImageView imageView, ImageDisplayOption imageDisplayOption) {
        try {
            RequestOptions requestOptions = buildRequestOptions(imageDisplayOption);
            Glide.with(context).load(imgUrl).apply(requestOptions).into(imageView);
        } catch (Exception e) {

        }
    }

    public void displayImage(Context context, int resource, ImageView imageView) {
        displayImage(context, resource, imageView, null);
    }

    public void displayImage(Context context, int resource, ImageView imageView, ImageDisplayOption imageDisplayOption) {
        try {
            RequestOptions requestOptions = buildRequestOptions(imageDisplayOption);
            Glide.with(context).load(resource).apply(requestOptions).into(imageView);
        } catch (Exception e) {

        }
    }

    public void cancelDisplayImage(Context context, ImageView imageView) {
        try {
            Glide.with(context).clear(imageView);
        } catch (Exception e) {

        }
    }

    public void displayCircleImage(Context context, String imgUrl, ImageView imageView) {
        displayCircleImage(context, imgUrl, imageView, null);
    }

    public void displayCircleImage(final Context context, String imgUrl, ImageView imageView, ImageDisplayOption imageDisplayOption) {
        try {
            RequestOptions requestOptions = buildRequestOptions(imageDisplayOption);
            requestOptions.circleCrop();
            Glide.with(context).load(imgUrl).apply(requestOptions).into(imageView);
        } catch (Exception e) {

        }
    }

    public void displayCircleImage(Context context, int resource, ImageView imageView) {
        displayCircleImage(context, resource, imageView, null);
    }

    public void displayCircleImage(final Context context, int resource, final ImageView imageView, ImageDisplayOption imageDisplayOption) {
        try {
            RequestOptions requestOptions = buildRequestOptions(imageDisplayOption);
            Glide.with(context).load(resource).apply(requestOptions).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayRadiusImage(Context context, int resource, ImageView imageView, int radius) {
        displayRadiusImage(context, resource, imageView, radius, null);
    }

    public void displayRadiusImage(Context context, int resource, ImageView imageView, int radius, ImageDisplayOption imageDisplayOption) {
        try {
            RequestOptions requestOptions = buildRequestOptions(imageDisplayOption);
            //圆角需要两个transform才能生效
            requestOptions.transforms(new CenterCrop(), new RoundedCorners(radius));
            Glide.with(context).load(resource).apply(requestOptions).into(imageView);
        } catch (Exception e) {

        }
    }

    public void displayRadiusImage(final Context context, String imageUrl, final ImageView imageView, int radius) {
        displayRadiusImage(context, imageUrl, imageView, radius, null);
    }

    public void displayRadiusImage(final Context context, String imageUrl, final ImageView imageView, int radius, ImageDisplayOption imageDisplayOption) {
        try {
            RequestOptions requestOptions = buildRequestOptions(imageDisplayOption);
            //圆角需要两个transform才能生效
            requestOptions.transforms(new CenterCrop(), new RoundedCorners(radius));
            Glide.with(context).load(imageUrl).apply(requestOptions).into(imageView);
        } catch (Exception e) {

        }
    }

    /**
     * 直接获取图片(本地图片)
     *
     * @param c         上下文
     * @param resId     本地图片资源id
     * @param maxWidth  横向最大分辨率
     * @param maxHeight 纵向最大分辨率
     * @return
     */
    public Bitmap getBitmap(Context c, int resId, int maxWidth, int maxHeight) {
        Bitmap result = null;
        try {
            result = Glide.with(c).asBitmap().load(resId).submit(maxWidth, maxHeight).get();
        } catch (Exception e) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) c.getResources().getDrawable(resId);
            result = bitmapDrawable.getBitmap();
        }
        return result;
    }

    /**
     * 直接获取图片(网络图片)
     *
     * @param c         上下文
     * @param url       地址
     * @param maxWidth  横向最大分辨率
     * @param maxHeight 纵向最大分辨率
     * @return
     */
    public void download(Context c, String url, int maxWidth, int maxHeight, final ImageDownloadCallBack imageDownloadCallBack) {
        RequestOptions requestOptions = buildRequestOptions(null);
        if (maxWidth > 0 || maxHeight > 0) {
            requestOptions.override(maxWidth, maxHeight);
        }
        Glide.with(c).asBitmap().load(url).apply(requestOptions).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                if (null != imageDownloadCallBack) {
                    imageDownloadCallBack.onFail();
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                if (null != imageDownloadCallBack) {
                    imageDownloadCallBack.onSuccess(resource);
                }
                return false;
            }
        }).preload();
    }
}
