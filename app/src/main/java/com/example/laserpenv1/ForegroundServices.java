package com.example.laserpenv1;
//
//import android.annotation.SuppressLint;
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
//import android.util.Log;
//
//import androidx.annotation.Nullable;
//
//public class ForegroundServices extends Service {
//    @SuppressLint("ForegroundServiceType")
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        while (true) {
//                            Log.e("Service", "Service is running...");
//                            try {
//                                Thread.sleep(2000);
//                            } catch (InterruptedException e) {
//                                throw new RuntimeException(e);
//                            }
//                        }
//                    }
//                }
//        ).start();
//
//        final String CHANNELID = "Foreground Service ID";
//        NotificationChannel channel = new NotificationChannel(
//                CHANNELID,
//                CHANNELID,
//                NotificationManager.IMPORTANCE_LOW
//        );
//        getSystemService(NotificationManager.class).createNotificationChannel(channel);
//        Notification.Builder notification = new Notification.Builder(this, CHANNELID)
//                .setContentText("Service is running...")
//                .setContentTitle("Service enabled")
//                .setSmallIcon(R.drawable.ic_launcher_background);
//
//        startForeground(1001, notification.build());
//
//
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//}

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.Point;

public class ForegroundServices extends Service implements CameraBridgeViewBase.CvCameraViewListener2 {
    private static final String TAG = "ForegroundService";
    private static final String CHANNEL_ID = "ForegroundServiceChannel";

    private CameraBridgeViewBase mOpenCvCameraView;
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();

        notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("Service is running")
                .setSmallIcon(R.drawable.ic_launcher_background);

        Notification notification = notificationBuilder.build();
        startForeground(1, notification);

        // 初始化 OpenCV
        if (!OpenCVLoader.initDebug()) {
            Log.e(TAG, "OpenCV initialization failed!");
            return;
        }

        // 初始化摄像头视图
        mOpenCvCameraView = new JavaCameraView(this, -1); // 使用默认摄像头
        mOpenCvCameraView.setVisibility(CameraBridgeViewBase.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        mOpenCvCameraView.enableView(); // 启动摄像头
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.hasExtra("coordinates")) {
            String coordinates = intent.getStringExtra("coordinates");
            updateNotification(coordinates);
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null) {
            mOpenCvCameraView.disableView(); // 关闭摄像头
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(serviceChannel);
            }
        }
    }

    public void updateNotification(String contentText) {
        if (notificationBuilder != null && notificationManager != null) {
            notificationBuilder.setContentText(contentText);
            Notification notification = notificationBuilder.build();
            notificationManager.notify(1, notification);
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {}

    @Override
    public void onCameraViewStopped() {}

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat rgbaMat = inputFrame.rgba();

        // 在这里执行 OpenCV 图像处理以检测雷射笔光点
        Point laserPoint = detectLaserPoint(rgbaMat);

        // 将雷射笔光点位置记录在 LogCat 中
        if (laserPoint != null) {
            Log.d(TAG, "Laser point detected at: " + laserPoint.toString());
        }

        return rgbaMat;
    }

    // 在图像中检测雷射笔光点的方法
    private Point detectLaserPoint(Mat rgbaMat) {
        // 在这里执行雷射笔光点的检测算法，返回光点的位置
        // 示例：假设光点位置为(100, 200)
        return new Point(100, 200);
    }
}
