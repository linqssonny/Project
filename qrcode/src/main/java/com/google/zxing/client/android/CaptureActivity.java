/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.client.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.client.android.camera.CameraManager;
import com.google.zxing.client.android.result.ResultHandler;
import com.google.zxing.client.android.result.ResultHandlerFactory;
import com.library.qrcode.R;
import com.library.utils.toast.ToastUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;


/**
 * This activity opens the camera and does the actual scanning on a background thread. It draws a
 * viewfinder to help the user place the barcode correctly, shows feedback as the image processing
 * is happening, and then overlays the results when a scan is successful.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class CaptureActivity extends Activity implements SurfaceHolder.Callback {

    private static final String TAG = CaptureActivity.class.getSimpleName();
    private CameraManager mCameraManager;
    private SurfaceView mSurfaceView;
    private View mViewScan;
    private ImageView mIvLine;
    private TranslateAnimation mTranslateAnimation;

    private CaptureActivityHandler mCaptureActivityHandler;

    private boolean mHasSurface;
    private InactivityTimer mInactivityTimer;
    private BeepManager mBeepManager;

    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;

    public Handler getCaptureActivityHandler() {
        return mCaptureActivityHandler;
    }

    CameraManager getCameraManager() {
        return mCameraManager;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.capture);

        initUI();

        mHasSurface = false;
        mInactivityTimer = new InactivityTimer(this);
        mBeepManager = new BeepManager(this);

        initParams();
    }

    private void initUI() {
        mSurfaceView = (SurfaceView) findViewById(R.id.preview_view);
        mIvLine = (ImageView) findViewById(R.id.iv_capture_line);
        mViewScan = findViewById(R.id.ll_capture_content);
    }

    private void initParams() {
        Intent intent = getIntent();
        if (intent != null) {
            String action = intent.getAction();
            if (Intents.Scan.ACTION.equals(action)) {
                // Scan the formats the intent requested, and return the result to the calling activity.
                decodeFormats = DecodeFormatManager.parseDecodeFormats(intent);
                decodeHints = DecodeHintManager.parseDecodeHints(intent);
                if (intent.hasExtra(Intents.Scan.WIDTH) && intent.hasExtra(Intents.Scan.HEIGHT)) {
                    int width = intent.getIntExtra(Intents.Scan.WIDTH, 0);
                    int height = intent.getIntExtra(Intents.Scan.HEIGHT, 0);
                    if (width > 0 && height > 0) {
                        mCameraManager.setManualFramingRect(width, height);
                    }
                }
            }
            characterSet = intent.getStringExtra(Intents.Scan.CHARACTER_SET);
        }
    }

    private void initAnimation() {
        if (null == mTranslateAnimation) {
            mTranslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation
                    .RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.1f, Animation.RELATIVE_TO_PARENT,
                    0.9f);
            mTranslateAnimation.setDuration(3000);
            mTranslateAnimation.setRepeatCount(-1);
            mTranslateAnimation.setInterpolator(new AccelerateDecelerateInterpolator(this, null));
            mTranslateAnimation.setRepeatMode(Animation.RESTART);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // CameraManager must be initialized here, not in onCreate(). This is necessary because we don't
        // want to open the camera driver and measure the screen size if we're going to show the help on
        // first launch. That led to bugs where the scanning rectangle was the wrong size and partially
        // off screen.
        mCameraManager = new CameraManager(getApplication());

        mCaptureActivityHandler = null;

        mBeepManager.init();
        mInactivityTimer.onResume();

        if (null == mSurfaceView) {
            mSurfaceView = (SurfaceView) findViewById(R.id.preview_view);
        }

        SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
        if (mHasSurface) {
            // The activity was paused but not stopped, so the surface still exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(surfaceHolder);
        } else {
            // Install the callback and wait for surfaceCreated() to init the camera.
            surfaceHolder.addCallback(this);
        }

        initAnimation();
        mIvLine.clearAnimation();
        mIvLine.startAnimation(mTranslateAnimation);
    }

    @Override
    protected void onPause() {
        if (mCaptureActivityHandler != null) {
            mCaptureActivityHandler.quitSynchronously();
            mCaptureActivityHandler = null;
        }
        mInactivityTimer.onPause();
        mBeepManager.close();
        mCameraManager.closeDriver();
        if (!mHasSurface) {
            SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        mIvLine.clearAnimation();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mInactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                mCameraManager.setTorch(false);
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                mCameraManager.setTorch(true);
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!mHasSurface) {
            mHasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mHasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * A valid barcode has been found, so give an indication of success and show the results.
     *
     * @param rawResult   The contents of the barcode.
     * @param scaleFactor amount by which thumbnail was scaled
     * @param barcode     A greyscale bitmap of the camera data which was decoded.
     */
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        mInactivityTimer.onActivity();
        ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(this, rawResult);

        boolean fromLiveScan = barcode != null;
        if (fromLiveScan) {
            // Then not from history, so beep/vibrate and we have an image to draw on
            mBeepManager.playBeepSoundAndVibrate();
        }
        ToastUtils.showShortMsg(this, resultHandler.getResult().getDisplayResult());
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (mCameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            mCameraManager.openDriver(surfaceHolder);
            // Creating the mCaptureActivityHandler starts the preview, which can also throw a RuntimeException.
            if (mCaptureActivityHandler == null) {
                mCaptureActivityHandler = new CaptureActivityHandler(this, decodeFormats, decodeHints, characterSet, mCameraManager);
            }
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.msg_camera_framework_bug));
        builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }
}
