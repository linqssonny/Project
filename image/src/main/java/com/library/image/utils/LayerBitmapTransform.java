package com.library.image.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.lang.ref.WeakReference;
import java.security.MessageDigest;

/**
 * Created by admin on 2016/7/28.
 */
public class LayerBitmapTransform extends BitmapTransformation {

    private WeakReference<Context> mContext;
    private int mResID;

    public LayerBitmapTransform(Context context, int resID) {
        super(context);
        this.mContext = new WeakReference<>(context);
        this.mResID = resID;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        if (null == toTransform) {
            return toTransform;
        }
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        if (null != mContext && mContext.get() != null) {
            Bitmap bitmap = ImageUtils.getInstances().getBitmap(mContext.get(), mResID, width, height);
            if (null != bitmap) {
                Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(b);
                canvas.drawBitmap(toTransform, 0, 0, null);
                canvas.drawBitmap(bitmap, 0, 0, null);
                canvas.save(Canvas.ALL_SAVE_FLAG);//保存
                canvas.restore();
                return b;
            }
        }
        return toTransform;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
