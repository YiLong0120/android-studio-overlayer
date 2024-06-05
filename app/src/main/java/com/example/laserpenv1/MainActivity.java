package com.example.laserpenv1;


import android.Manifest;
import android.app.PictureInPictureParams;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import java.util.Collections;
import java.util.List;

public class MainActivity extends CameraActivity {

    private static final String TAG = "OCVSample::Activity";
    private static final int PERMISSION_REQUEST_CODE = 1;

    private CameraBridgeViewBase mOpenCvCameraView;
    private OpenCVProcessor mOpenCVProcessor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);

        if (OpenCVLoader.initDebug()) {
            Log.i(TAG, "OpenCV loaded successfully");
        } else {
            Log.e(TAG, "OpenCV initialization failed!");
            Toast.makeText(this, "OpenCV initialization failed!", Toast.LENGTH_LONG).show();
            return;
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        mOpenCvCameraView = findViewById(R.id.cameraView);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);

        TextView coordinateTextView = findViewById(R.id.coordinateTextView);

        // Initialize OpenCVProcessor
        mOpenCVProcessor = new OpenCVProcessor(this, coordinateTextView);
        mOpenCvCameraView.setCvCameraViewListener(mOpenCVProcessor);

        requestPermissionsIfNecessary();

        // 设置画中画模式按钮
        Button pipButton = findViewById(R.id.start_pip_button);
        pipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterPipMode();
            }
        });
    }

    private void requestPermissionsIfNecessary() {
        String[] permissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.FOREGROUND_SERVICE
        };

        boolean permissionsNeeded = false;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded = true;
                break;
            }
        }

        if (permissionsNeeded) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        } else {
            mOpenCvCameraView.enableView();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {
                mOpenCvCameraView.enableView();
            } else {
                Toast.makeText(this, "Permissions are required for this app", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(mOpenCvCameraView);
    }

    @Override
    public void onPause() {
        super.onPause();
        // 仅当不是在画中画模式下时，才禁用摄像头视图
        if (!isInPictureInPictureMode() && mOpenCvCameraView != null) {
            mOpenCvCameraView.disableView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // 仅当不是在画中画模式下时，才启用摄像头视图
        if (mOpenCvCameraView != null) {
            mOpenCvCameraView.enableView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null) {
            mOpenCvCameraView.disableView();
        }
    }

    // 更改为 public
    public void enterPipMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Rational aspectRatio = new Rational(mOpenCvCameraView.getWidth(), mOpenCvCameraView.getHeight());
            PictureInPictureParams.Builder pipBuilder = new PictureInPictureParams.Builder();
            pipBuilder.setAspectRatio(aspectRatio);
            enterPictureInPictureMode(pipBuilder.build());
        } else {
            Toast.makeText(this, "Picture-in-Picture mode is not supported on this device", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        enterPipMode(); // 在用户切换到后台时自动进入PiP模式
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, @NonNull Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        if (isInPictureInPictureMode) {
            // 进入画中画模式时，保持摄像头视图启用状态
            if (!mOpenCvCameraView.isEnabled()) {
                mOpenCvCameraView.enableView();
            }
        } else {
            // 退出画中画模式时恢复正常状态
            if (mOpenCvCameraView != null && !mOpenCvCameraView.isEnabled()) {
                mOpenCvCameraView.enableView();
            }
        }
    }
}