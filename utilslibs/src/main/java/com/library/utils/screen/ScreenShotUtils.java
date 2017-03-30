package com.library.utils.screen;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;

import java.util.ArrayList;

/**
 * 截屏
 * Created by linqs on 2017/3/30.
 */

public class ScreenShotUtils {

    /**
     * 截图回调函数
     */
    public interface ScreenShotCallBack {
        void complete(Bitmap bitmap);
    }

    /**
     *
     */
    private static class ScreenShot {

        private boolean last;
        private Bitmap bitmap;
        private float backX;
        private float backY;

        private int newImageW;
        private int newImageH;

        public ScreenShot(int newImageW, int newImageH,
                          Bitmap bitmap, float backX, float backY) {
            this.newImageW = newImageW;
            this.newImageH = newImageH;
            this.bitmap = bitmap;
            this.backX = backX;
            this.backY = backY;
        }

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public float getBackX() {
            return backX;
        }

        public void setBackX(float backX) {
            this.backX = backX;
        }

        public float getBackY() {
            return backY;
        }

        public void setBackY(float backY) {
            this.backY = backY;
        }

        public int getNewImageW() {
            return newImageW;
        }

        public void setNewImageW(int newImageW) {
            this.newImageW = newImageW;
        }

        public int getNewImageH() {
            return newImageH;
        }

        public void setNewImageH(int newImageH) {
            this.newImageH = newImageH;
        }
    }

    /**
     * 截取rootView屏幕
     * 注意：仅限当前显示部分
     *
     * @param rootView
     * @param screenShotCallBack
     */
    public static void buildScreenShotByView(View rootView, ScreenShotCallBack screenShotCallBack) {
        if (null == rootView || null == screenShotCallBack) {
            return;
        }
        try {
            rootView.setDrawingCacheEnabled(true);
            rootView.buildDrawingCache();
            rootView.setDrawingCacheEnabled(true);
            Bitmap bitmap = rootView.getDrawingCache();
            if (null != screenShotCallBack) {
                screenShotCallBack.complete(bitmap);
            }
            rootView.destroyDrawingCache();
        } catch (Exception e) {
            if (null != screenShotCallBack) {
                screenShotCallBack.complete(null);
            }
        }
    }

    private static Bitmap sBitmap = null;
    private static ArrayList<ScreenShot> sScreenShotList = null;

    /**
     * 滚动截屏(WebView)
     *
     * @param webView
     * @param screenShotCallBack
     */
    public static void buidlScreenShotByWebView(Activity activity, final WebView webView, final ScreenShotCallBack screenShotCallBack) {
        if (null == webView || null == screenShotCallBack) {
            return;
        }
        webView.scrollTo(0, 0);
        webView.buildDrawingCache(true);
        webView.setDrawingCacheEnabled(true);
        webView.setVerticalScrollBarEnabled(false);

        sScreenShotList = new ArrayList<>();

        sBitmap = getViewBitmapWithoutBottom(webView);

        //开启拼接图片线程
        mergeBitmap(activity, new ScreenShotCallBack() {
            @Override
            public void complete(Bitmap bitmap) {
                // 回滚到顶部
                webView.scrollTo(0, 0);
                webView.setVerticalScrollBarEnabled(true);
                webView.setDrawingCacheEnabled(false);
                webView.destroyDrawingCache();
                if (null != screenShotCallBack) {
                    screenShotCallBack.complete(bitmap);
                }
            }
        });

        // 可见高度
        int height = webView.getHeight();
        // 容器内容实际高度
        int contentHeight = (int) (webView.getContentHeight() * webView.getScale());
        if (contentHeight > height) {
            int screenWidth = getScreenWidth(activity);
            int absVh = height - webView.getPaddingTop() - webView.getPaddingBottom();
            ScreenShotWebView(webView, absVh, screenWidth, height, contentHeight);
        } else {
            if (null != screenShotCallBack) {
                screenShotCallBack.complete(sBitmap);
            }
            sBitmap = null;
            sScreenShotList = null;
        }
    }

    private static Bitmap getViewBitmapWithoutBottom(View v) {
        if (null == v) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap bp = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, v.getMeasuredWidth(), v.getMeasuredHeight() - v.getPaddingBottom());
        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        return bp;
    }

    private static Bitmap getViewBitmap(View v) {
        if (null == v) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap bp = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        return bp;
    }

    private static void ScreenShotWebView(final WebView webView, final int absVh, final int screenWidth, final int height, final int contentHeight) {
        webView.post(new Runnable() {
            @Override
            public void run() {
                int restHeight = contentHeight - height;
                int h = height;
                Bitmap temp;
                if (restHeight <= absVh) {
                    webView.scrollBy(0, restHeight);
                    h += restHeight;
                    temp = getViewBitmap(webView);
                } else {
                    webView.scrollBy(0, absVh);
                    h += absVh;
                    temp = getViewBitmapWithoutBottom(webView);
                }
                ScreenShot screenShot = new ScreenShot(screenWidth, h, temp, 0, webView.getScrollY());
                if (h >= contentHeight) {
                    screenShot.setLast(true);
                }
                sScreenShotList.add(screenShot);
                if (h < contentHeight) {
                    ScreenShotWebView(webView, absVh, screenWidth, h, contentHeight);
                }
            }
        });
    }

    /**
     * 拼接图片
     *
     * @param screenShotCallBack
     * @return
     */
    private static void mergeBitmap(final Activity activity, final ScreenShotCallBack screenShotCallBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (sScreenShotList.isEmpty()) {
                        continue;
                    }
                    ScreenShot screenShot;
                    synchronized (sScreenShotList) {
                        screenShot = sScreenShotList.get(0);
                        sScreenShotList.remove(screenShot);
                    }
                    if (screenShot == null) {
                        continue;
                    }
                    final Bitmap b = mergeBitmap(screenShot.getNewImageH(), screenShot.getNewImageW(),
                            screenShot.getBitmap(), screenShot.getBackX(),
                            screenShot.getBackY(), sBitmap, 0, 0);
                    recycle(sBitmap);
                    recycle(screenShot.getBitmap());
                    sBitmap = b;
                    if (screenShot.isLast()) {
                        if (null != screenShotCallBack) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    screenShotCallBack.complete(b);
                                }
                            });
                        }
                        sBitmap = null;
                        sScreenShotList = null;
                        break;
                    }
                }
            }
        }).start();
    }

    /**
     * 拼接图片
     *
     * @param newImageH
     * @param newImageW
     * @param background
     * @param backX
     * @param backY
     * @param foreground
     * @param foreX
     * @param foreY
     * @return
     */
    private static Bitmap mergeBitmap(int newImageH, int newImageW, Bitmap background, float backX, float backY, Bitmap foreground, float foreX, float foreY) {
        if (null == background || null == foreground) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(newImageW, newImageH, Bitmap.Config.RGB_565);
        Canvas cv = new Canvas(bitmap);
        cv.drawBitmap(background, backX, backY, null);
        cv.drawBitmap(foreground, foreX, foreY, null);
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();
        return bitmap;
    }

    /**
     * get the width of screen
     */
    public static int getScreenWidth(Context ctx) {
        int w = 0;
        if (Build.VERSION.SDK_INT > 13) {
            Point p = new Point();
            ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(p);
            w = p.x;
        } else {
            w = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        }
        return w;
    }

    public static void recycle(Bitmap bitmap) {
        if (null != bitmap && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }
}
