package com.library.image.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * Created by admin on 2016/6/29.
 */
public class ImageUtils {

    public static void displayImage(Context context, String imgUrl, ImageView imageView) {
        Glide.with(context).load(imgUrl).asBitmap().centerCrop().into(imageView);
    }

    public static void displayImage(Context context, int resource, ImageView imageView) {
        Glide.with(context).load(resource).asBitmap().centerCrop().into(imageView);
    }

    public static void displayCircleImage(final Context context, String imgUrl, final ImageView imageView) {
        Glide.with(context).load(imgUrl).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                roundedBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(roundedBitmapDrawable);
            }
        });
    }

    public static void displayCircleImage(final Context context, int resource, final ImageView imageView) {
        Glide.with(context).load(resource).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                roundedBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(roundedBitmapDrawable);
            }
        });
    }

    public static void displayLayerCircleImage(final Context context, String imgUrl, int layerID, final ImageView imageView) {
        Glide.with(context)
                .load(imgUrl)
                .asBitmap()
                .centerCrop()
                .transform(new LayerBitmapTransform(context, layerID))
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        roundedBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(roundedBitmapDrawable);
                    }
                });
    }

    public static void displayLayerCircleImage(final Context context, int resource, int layerID, final ImageView imageView) {
        Glide.with(context)
                .load(resource)
                .asBitmap()
                .centerCrop()
                .transform(new LayerBitmapTransform(context, layerID))
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        roundedBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(roundedBitmapDrawable);
                    }
                });
    }

    /**
     * 直接获取图片的位图
     *
     * @param c         上下文
     * @param resId     本地图片资源id
     * @param maxWidth  横向最大分辨率
     * @param maxHeight 纵向最大分辨率
     * @return
     */
    public static Bitmap getBitmap(Context c, int resId, int maxWidth, int maxHeight) {
        Bitmap result = null;
        try {
            result = Glide.with(c).load(resId).asBitmap().centerCrop().into(maxWidth, maxHeight).get();
        } catch (Exception e) {
            BitmapDrawable drawable = (BitmapDrawable) c.getResources().getDrawable(resId);
            result = drawable.getBitmap();
        }
        return result;
    }

    /**
     * 直接获取图片的位图
     *
     * @param c         上下文
     * @param url       地址
     * @param maxWidth  横向最大分辨率
     * @param maxHeight 纵向最大分辨率
     * @return
     */
    public static Bitmap getBitmap(Context c, String url, int maxWidth, int maxHeight) {
        Bitmap result = null;
        try {
            result = Glide.with(c).load(url).asBitmap().centerCrop().into(maxWidth, maxHeight).get();
        } catch (Exception e) {
        }
        return result;
    }
}
