package com.example.laserpenv1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int OVERLAY_PERMISSION_REQUEST_CODE = 2;
    private static final int ACCESSIBILITY_REQUEST_CODE = 3;
    private static final String TAG = "MainActivity";

    private CameraBridgeViewBase mOpenCvCameraView;
    private OpenCVProcessor mOpenCVProcessor;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        // 初始化相机视图
        mOpenCvCameraView = findViewById(R.id.cameraView);
        mOpenCvCameraView.setVisibility(CameraBridgeViewBase.VISIBLE);
        mOpenCVProcessor = new OpenCVProcessor(this, findViewById(R.id.coordinateTextView));
        mOpenCvCameraView.setCvCameraViewListener(mOpenCVProcessor);

        // 初始化按钮点击事件
        Button startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(v -> {
            Switch overlaySwitch = findViewById(R.id.overlay_permission_switch);
            Switch cameraSwitch = findViewById(R.id.camera_permission_switch);
            Switch accessibilitySwitch = findViewById(R.id.accessibility_permission_switch);

            if (!overlaySwitch.isChecked() || !cameraSwitch.isChecked() || !accessibilitySwitch.isChecked()) {
                Toast.makeText(this, "请检查并开启所有权限以继续", Toast.LENGTH_SHORT).show();
                return;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                requestOverlayPermission();
            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
            } else if (!isAccessibilityServiceEnabled(this, TouchAccessibilityService.class)) {
                requestAccessibilityPermission();
            } else {
                startOverlayService();
            }
        });

        // 初始化权限开关
        initPermissionSwitches();

        // 加载 OpenCV 库
        new LoadOpenCVTask(this).execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    // 加载 OpenCV 库的异步任务
    private static class LoadOpenCVTask extends AsyncTask<Void, Void, Boolean> {
        private MainActivity activity;

        public LoadOpenCVTask(MainActivity activity) {
            this.activity = activity;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return OpenCVLoader.initDebug();
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Log.d(TAG, "OpenCV 加载成功！");
                activity.mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
            } else {
                Log.e(TAG, "OpenCV 加载失败！");
            }
        }
    }

    // 初始化权限开关
    private void initPermissionSwitches() {
        Switch overlaySwitch = findViewById(R.id.overlay_permission_switch);
        Switch cameraSwitch = findViewById(R.id.camera_permission_switch);
        Switch accessibilitySwitch = findViewById(R.id.accessibility_permission_switch);

        overlaySwitch.setChecked(Settings.canDrawOverlays(this));
        cameraSwitch.setChecked(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
        accessibilitySwitch.setChecked(isAccessibilityServiceEnabled(this, TouchAccessibilityService.class));
    }

    // 请求悬浮窗权限
    private void requestOverlayPermission() {
        new AlertDialog.Builder(this)
                .setTitle("需要悬浮窗权限")
                .setMessage("请授予应用悬浮窗权限以继续")
                .setPositiveButton("授予", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE);
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    Toast.makeText(this, "悬浮窗权限是必需的", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .show();
    }

    // 请求辅助功能权限
    private void requestAccessibilityPermission() {
        new AlertDialog.Builder(this)
                .setTitle("需要辅助功能权限")
                .setMessage("请授予应用辅助功能权限以继续")
                .setPositiveButton("授予", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivityForResult(intent, ACCESSIBILITY_REQUEST_CODE);
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    Toast.makeText(this, "辅助功能权限是必需的", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .show();
    }

    // 检查辅助功能是否已启用
    private boolean isAccessibilityServiceEnabled(Context context, Class<?> accessibilityService) {
        String serviceId = context.getPackageName() + "/" + accessibilityService.getName();
        TextUtils.SimpleStringSplitter colonSplitter = new TextUtils.SimpleStringSplitter(':');
        String settingValue = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

        if (settingValue != null) {
            colonSplitter.setString(settingValue);
            while (colonSplitter.hasNext()) {
                if (colonSplitter.next().equalsIgnoreCase(serviceId)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, requestCode, data);

        if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                ((Switch) findViewById(R.id.overlay_permission_switch)).setChecked(true);
            } else {
                Toast.makeText(this, "悬浮窗权限是必需的", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else if (requestCode == ACCESSIBILITY_REQUEST_CODE) {
            if (isAccessibilityServiceEnabled(this, TouchAccessibilityService.class)) {
                ((Switch) findViewById(R.id.accessibility_permission_switch)).setChecked(true);
            } else {
                Toast.makeText(this, "辅助功能权限是必需的", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ((Switch) findViewById(R.id.camera_permission_switch)).setChecked(true);
            } else {
                Toast.makeText(this, "相机权限是必需的", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    // 启动悬浮窗服务
    private void startOverlayService() {
        Intent serviceIntent = new Intent(this, OverlayService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(this, serviceIntent);
        } else {
            startService(serviceIntent);
        }
    }
}