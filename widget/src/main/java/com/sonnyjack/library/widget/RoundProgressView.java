package com.sonnyjack.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.math.BigDecimal;

/**
 * Created by SonnyJack on 2017/11/2.
 */

public class RoundProgressView extends View {

    public static final int MODE_AUTO = 1;
    public static final int MODE_UPDATE = 2;

    private final int DEFAULT_BORDER_COLOR = Color.GRAY;
    private final int DEFAULT_BORDER_WIDTH = 1;
    private final int DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    private final int DEFAULT_TEXT_COLOR = Color.WHITE;
    //the total time for animation execute
    private final int DEFAULT_TOTAL_MILLISECOND = 5 * 1000;
    //default image/bitmap scale
    private final float DEFAULT_BITMAP_SCALE = 2.0f / 5;

    //the time between two draw
    private final int DELAY_MILLISECONDS = 50;

    //the time when draw finish last
    private long mLastDrawTime = 0l;

    //the paint
    private Paint mPaint;

    //the border color
    private int mBorderColor = DEFAULT_BORDER_COLOR;
    //the border size
    private float mBorderWidth = DEFAULT_BORDER_WIDTH;

    //the view background
    private int mBackgroundColor = DEFAULT_BACKGROUND_COLOR;


    private float mStartAngle = 0.0f;
    private float mEndAngle = 360.0f;

    // the total time
    private int mTotalMilliSeconds = DEFAULT_TOTAL_MILLISECOND;
    //the round speed
    private float mSpeed;
    //current angle
    private float mCurrentAngle = mStartAngle;

    //the text
    private String mText;
    //the text color
    private int mTextColor = DEFAULT_TEXT_COLOR;
    //the text size
    private float mTextSize = 16;

    //the bitmap
    private Bitmap mBitmap;
    private Matrix mMatrix;
    private float mBitmapScale = DEFAULT_BITMAP_SCALE;

    //if draw round progress
    private boolean mDrawRoundProgress = false;

    //mode is auto progress auto change
    //mode is update progress change by user update
    private int mMode = MODE_AUTO;

    private IRoundProgressListener mIRoundProgressListener;

    public RoundProgressView(Context context) {
        super(context);
        init(null);
    }

    public RoundProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RoundProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (null != attrs) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RoundProgressView);
            mBorderWidth = typedArray.getInt(R.styleable.RoundProgressView_border_width, DEFAULT_BORDER_WIDTH);
            mBorderColor = typedArray.getColor(R.styleable.RoundProgressView_border_color, DEFAULT_BORDER_COLOR);
            mBackgroundColor = typedArray.getColor(R.styleable.RoundProgressView_background_color, DEFAULT_BACKGROUND_COLOR);
            mTotalMilliSeconds = typedArray.getInt(R.styleable.RoundProgressView_milli_second, DEFAULT_TOTAL_MILLISECOND);

            mText = typedArray.getString(R.styleable.RoundProgressView_text);
            mTextColor = typedArray.getColor(R.styleable.RoundProgressView_text_color, DEFAULT_TEXT_COLOR);
            mTextSize = typedArray.getDimensionPixelSize(R.styleable.RoundProgressView_text_size, 16);

            mMode = typedArray.getInt(R.styleable.RoundProgressView_mode, MODE_AUTO);

            int imageResource = typedArray.getResourceId(R.styleable.RoundProgressView_image_resource, 0);
            if (0 != imageResource) {
                mBitmap = BitmapFactory.decodeResource(getResources(), imageResource);
            }
            float scale = typedArray.getFloat(R.styleable.RoundProgressView_image_scale, DEFAULT_BITMAP_SCALE);
            setImageScale(scale);
            typedArray.recycle();
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public void setIRoundProgressListener(IRoundProgressListener iRoundProgressListener) {
        this.mIRoundProgressListener = iRoundProgressListener;
    }

    /**
     * if you set bitmap than the text is invalid
     * while the bitmap valid
     *
     * @param bitmap
     */
    public void setImageBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    /**
     * set text value
     *
     * @param value
     */
    public void setText(String value) {
        mText = value;
    }

    /**
     * set text color
     *
     * @param color
     */
    public void setTextColor(int color) {
        mTextColor = color;
    }

    /**
     * set text size and the size unit is pix
     *
     * @param textSize
     */
    public void setTextSize(float textSize) {
        mTextSize = textSize;
    }

    /**
     * set image scale and scale between 0.1 - 0.6
     * the scale valid while has set image
     *
     * @param scale
     */
    public void setImageScale(float scale) {
        if (scale < 0.1f) {
            mBitmapScale = 0.1f;
        }
        if (scale > 0.6f) {
            scale = 0.6f;
        }
        mBitmapScale = scale;
        mMatrix = null;
    }

    public void setMode(int mode) {
        if (mode != MODE_AUTO && mode != MODE_UPDATE) {
            throw new IllegalStateException("the mode value invalid");
        }
        mMode = mode;
    }

    /**
     * start animation
     *
     * @param milliSeconds unit Millisecond
     */
    public void start(int milliSeconds) {
        if (mMode == MODE_AUTO) {
            mTotalMilliSeconds = milliSeconds;
        }
        start();
    }

    /**
     * start animation
     */
    public void start() {
        mDrawRoundProgress = true;
        if (mMode == MODE_AUTO) {
            mSpeed = (mEndAngle - mStartAngle) / mTotalMilliSeconds;
            mLastDrawTime = 0;
        }
        postInvalidateDelayed(0);
    }

    /**
     * update progress by user and the value between 0 - 100
     *
     * @param value
     */
    public void updateProgress(float value) {
        if (mMode != MODE_UPDATE) {
            return;
        }
        if (value < 0 || value > 100) {
            throw new IllegalStateException("the value must between 0 and 100");
        }
        mCurrentAngle = value * (mEndAngle - mStartAngle) / 100;
        mDrawRoundProgress = true;
        postInvalidateDelayed(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        float paddingLeft = getPaddingLeft();
        float paddingRight = getPaddingRight();
        float paddingTop = getPaddingTop();
        float paddingBottom = getPaddingBottom();

        //draw the background
        float left = paddingLeft + mBorderWidth - 0.1f;
        float top = paddingTop + mBorderWidth - 0.1f;
        float right = getWidth() - paddingRight - mBorderWidth + 0.1f;
        float bottom = getHeight() - paddingBottom - mBorderWidth + 0.1f;
        RectF oval = new RectF(left, top, right, bottom);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(mBackgroundColor);
        mPaint.setStrokeWidth(0);
        canvas.drawArc(oval, 0, 360, true, mPaint);

        if (mDrawRoundProgress) {

            //draw the progress
            left = paddingLeft + mBorderWidth / 2;
            top = paddingTop + mBorderWidth / 2;
            right = getWidth() - paddingRight - mBorderWidth / 2;
            bottom = getHeight() - paddingBottom - mBorderWidth / 2;
            oval = new RectF(left, top, right, bottom);

            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(mBorderWidth);
            mPaint.setColor(mBorderColor);

            //the mode is auto must calculate progress
            if (isAutoMode()) {
                calculateAutoModeProgress();
            }
            canvas.drawArc(oval, -90, mCurrentAngle, false, mPaint);

            callBack(mCurrentAngle);

            if (mCurrentAngle < mEndAngle && isAutoMode()) {
                postInvalidateDelayed(DELAY_MILLISECONDS);
            }
        }

        //draw text
        if (!drawBitmap()) {
            String value = mText;
            if (TextUtils.isEmpty(value)) {
                value = formatProgress(mCurrentAngle);
            }
            mPaint.setColor(mTextColor);
            mPaint.setTextSize(mTextSize);
            mPaint.setStrokeWidth(0);
            mPaint.setTextAlign(Paint.Align.CENTER);
            //get font info by paint
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            // calculate font height
            float fontHeight = fontMetrics.bottom - fontMetrics.top;
            // calculate font baseline
            float textBaseY = getHeight() / 2 + fontHeight / 2 - fontMetrics.bottom;
            canvas.drawText(value, getWidth() / 2, textBaseY, mPaint);
        } else {
            //draw bitmap
            if (null == mMatrix) {
                mMatrix = new Matrix();
                //scale
                mMatrix.postScale(mBitmapScale, mBitmapScale);
                float dx = (getWidth() * (1 - mBitmapScale)) / 2.0f;
                float dy = (getHeight() * (1 - mBitmapScale)) / 2.0f;
                mMatrix.postTranslate(dx, dy);
            }
            canvas.drawBitmap(mBitmap, mMatrix, mPaint);
        }
    }

    private void calculateAutoModeProgress() {
        if (mLastDrawTime <= 0) {
            mLastDrawTime = System.currentTimeMillis();
        }
        long time = System.currentTimeMillis();
        mCurrentAngle += (mSpeed * (time - mLastDrawTime));
        if (mCurrentAngle >= mEndAngle) {
            mCurrentAngle = mEndAngle;
        }
        mLastDrawTime = time;
    }

    private boolean isAutoMode() {
        return mMode == MODE_AUTO;
    }

    /**
     * judge is or not draw bitmap
     *
     * @return
     */
    private boolean drawBitmap() {
        if (null == mBitmap || mBitmap.isRecycled()) {
            return false;
        }
        return true;
    }

    /**
     * call back by progress change when mode is auto valid
     *
     * @param angle
     */
    private void callBack(float angle) {
        if (null == mIRoundProgressListener) {
            return;
        }
        //the total angle
        float totalAngle = mEndAngle - mStartAngle;
        //calculate progress
        float progress = angle * 100 / totalAngle;
        progress = formatDecimal(progress, 2);
        mIRoundProgressListener.progress(progress, Math.abs(100));
    }

    private String formatProgress(float angle) {
        //the total angle
        float totalAngle = mEndAngle - mStartAngle;
        int result = (int) (angle / totalAngle * 100);
        return result + "%";
    }

    private float formatDecimal(float value, int newScale) {
        BigDecimal bg = new BigDecimal(value);
        return bg.setScale(newScale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public interface IRoundProgressListener {
        void progress(float progress, float total);
    }

}
