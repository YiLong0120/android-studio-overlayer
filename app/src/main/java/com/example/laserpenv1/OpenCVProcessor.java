package com.example.laserpenv1;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class OpenCVProcessor implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "OpenCVProcessor";
    private TextView coordinateTextView;
    private Context context;

    public OpenCVProcessor(Context context, TextView textView) {
        this.context = context;
        this.coordinateTextView = textView;
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        // Initialization if needed
    }

    @Override
    public void onCameraViewStopped() {
        // Cleanup if needed
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat rgbaMat = inputFrame.rgba();
        Mat hsvMat = new Mat();

        Imgproc.cvtColor(rgbaMat, hsvMat, Imgproc.COLOR_RGBA2RGB);
        Imgproc.cvtColor(hsvMat, hsvMat, Imgproc.COLOR_RGB2HSV);

        Scalar lowerThresholdForScreen = new Scalar(0, 0, 200);
        Scalar upperThresholdForScreen = new Scalar(180, 25, 255);

        Scalar lowerThresholdForLaser = new Scalar(35, 50, 50);
        Scalar upperThresholdForLaser = new Scalar(85, 255, 255);

        Mat screenMask = new Mat();
        Core.inRange(hsvMat, lowerThresholdForScreen, upperThresholdForScreen, screenMask);

        Mat laserMask = new Mat();
        Core.inRange(hsvMat, lowerThresholdForLaser, upperThresholdForLaser, laserMask);

        List<MatOfPoint> screenContours = new ArrayList<>();
        Imgproc.findContours(screenMask, screenContours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        List<MatOfPoint> laserContours = new ArrayList<>();
        Imgproc.findContours(laserMask, laserContours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        double maxVal = 0;
        Point laserPoint = new Point();
        for (MatOfPoint contour : laserContours) {
            double contourArea = Imgproc.contourArea(contour);
            if (contourArea > maxVal && contourArea > 50) {
                maxVal = contourArea;
                Rect laserRect = Imgproc.boundingRect(contour);
                laserPoint = new Point(laserRect.x + laserRect.width / 2, laserRect.y + laserRect.height / 2);
            }
        }

        if (maxVal > 0) {
            Imgproc.circle(rgbaMat, laserPoint, 10, new Scalar(255, 0, 0), 3);
            final String coordinates = "Laser point coordinates: (" + laserPoint.x + ", " + laserPoint.y + ")";
            coordinateTextView.post(new Runnable() {
                @Override
                public void run() {
                    coordinateTextView.setText(coordinates);
                }
            });
        }

        return rgbaMat;
    }
}