package com.example.laserpenv1;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class TouchOverlayView extends View {

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    public TouchOverlayView(Context context) {
        super(context);
        init(context);
    }

    public TouchOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TouchOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                        WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        setVisibility(View.GONE);
        if (Settings.canDrawOverlays(context)) {
            windowManager.addView(this, layoutParams);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Handle touch down event
                return true;
            case MotionEvent.ACTION_MOVE:
                // Handle touch move event
                return true;
            case MotionEvent.ACTION_UP:
                // Handle touch up event
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    public void show() {
        setVisibility(View.VISIBLE);
    }

    public void hide() {
        setVisibility(View.GONE);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (windowManager != null) {
            windowManager.removeView(this);
        }
    }
}