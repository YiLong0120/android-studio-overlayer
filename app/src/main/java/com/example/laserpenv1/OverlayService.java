package com.example.laserpenv1;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class OverlayService extends Service {
    private WindowManager windowManager;
    private RelativeLayout overlayLayout;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        overlayLayout = (RelativeLayout) inflater.inflate(R.layout.overlay_layout, null);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        windowManager.addView(overlayLayout, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (overlayLayout != null) {
            windowManager.removeView(overlayLayout);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}