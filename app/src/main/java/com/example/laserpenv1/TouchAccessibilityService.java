package com.example.laserpenv1;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public class TouchAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // 处理触摸事件
    }

    @Override
    public void onInterrupt() {
        // 处理服务中断的情况
    }
}