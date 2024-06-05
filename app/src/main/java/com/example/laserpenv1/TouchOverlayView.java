package com.example.laserpenv1;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class TouchOverlayView extends View {
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private OnTouchListener touchListener;

    public TouchOverlayView(Context context) {
        super(context);
        init(context);
    }

    public TouchOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        setVisibility(View.GONE);
        windowManager.addView(this, layoutParams);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (windowManager != null) {
            windowManager.removeView(this);
        }
    }

    public void show() {
        setVisibility(View.VISIBLE);
    }

    public void hide() {
        setVisibility(View.GONE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (touchListener != null) {
            return touchListener.onTouch(this, event);
        }
        return super.onTouchEvent(event);
    }

    public void setTouchListener(OnTouchListener listener) {
        this.touchListener = listener;
    }
}
