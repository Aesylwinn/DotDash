package com.sh.blah.dotdash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Chronometer;
import android.widget.FrameLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

public class DecoderActivity extends Activity implements Camera.PreviewCallback {

    final int ExaminedWidth = 500 * 3;
    final int ExaminedHeight = 500 * 3;

    final long SpecSignal = 1500;

    private Camera mCamera;
    private CameraView mCameraView;

    long prev = 0;
    boolean state = false;
    boolean running = false;

    double[] oldValues = new double[20];
    int rollingOffset = 0;

    public static ArrayList<Long> timings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder2);

        mCameraView = new CameraView(this, null);//create a SurfaceView to show camera data
        FrameLayout camera_view = (FrameLayout)findViewById(R.id.camera_view);
        camera_view.addView(mCameraView);//add the SurfaceView to the layout
    }

    @Override
    protected void onPause() {
        mCameraView.setCamera(null);
        if (mCamera != null)
            mCamera.release();

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        prev = System.currentTimeMillis();
        state = false;
        running = false;
        timings = new ArrayList<>();

        mCamera = Camera.open();
        mCameraView.setCamera(mCamera);
    }

    protected void onStop(View view)
    {
        Log.d("st", "pressed");
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(Intent.EXTRA_RETURN_RESULT, timings);
        startActivity(i);
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

        long now = System.currentTimeMillis();
        long diff = now > prev ? (now - prev) : (prev - now);

        Camera.Size size = camera.getParameters().getPreviewSize();

        int scalar = 3; // YUV - nv21, 3 bytes

        int xOffset = 0;
        int yOffset = 0;

        int xMax = Math.max(0, Math.min(size.width - 1, xOffset + ExaminedWidth));
        int yMax = Math.max(0, Math.min(size.height - 1, yOffset + ExaminedHeight));


        double brightness = 0;
        int count = 1;

        for (int y = yOffset; y < yMax; y += 1)
        {
            for (int x = xOffset; x < xMax; x += scalar)
            {
                brightness += data[x + y * size.width] & 0xFF;
                ++count;
            }
        }
        brightness /= count;

        double rollingAverage = 0;
        for (double x : oldValues)
            rollingAverage += x;
        rollingAverage /= oldValues.length;

        oldValues[rollingOffset++ % oldValues.length] = brightness;

        boolean high = (brightness > rollingAverage);
        if (high != state)
        {
            if (!running && high && diff > SpecSignal)
                running = true;
            else if (running && diff > SpecSignal-200) {
                running = false;
                Log.d("timings", timings.toString());
            }
            else if (state && timings.size() % 2 == 0)
                timings.add(diff);
            else if (!state && timings.size() % 2 == 1)
                timings.add(diff);

            state = high;
            prev = now;
            Log.d("time", Double.toString(diff));
        }
        //Log.d("bright", Double.toString(brightness));
    }

    public class CameraView extends SurfaceView implements SurfaceHolder.Callback{
        private SurfaceHolder mHolder;
        private Camera mCamera;

        public CameraView(Context context, Camera camera){
            super(context);

            setCamera(camera);
            mHolder = getHolder();
            mHolder.addCallback(this);
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
        }

        void setCamera(Camera cam)
        {
            mCamera = cam;
            if (mCamera != null)
            {
                mCamera.setDisplayOrientation(90);
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setPreviewFormat(ImageFormat.NV21);
                if (parameters.isAutoWhiteBalanceLockSupported())
                    parameters.setAutoWhiteBalanceLock(true);
                if (parameters.isAutoExposureLockSupported())
                    parameters.setAutoExposureLock(true);
                mCamera.setParameters(parameters);
            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            try{
                if (mCamera != null) {
                    mCamera.setPreviewDisplay(surfaceHolder);
                    mCamera.startPreview();
                }
            } catch (Exception e) {
                Log.d("ERROR", "Camera error on surfaceCreated " + e.getMessage());
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            if(mHolder.getSurface() == null)//check if the surface is ready to receive camera data
                return;

            try{
                if (mCamera != null) {
                    mCamera.setPreviewCallback(null);
                    mCamera.stopPreview();
                }
            } catch (Exception e){
                //this will happen when you are trying the camera if it's not running
            }

            try{
                if (mCamera != null) {
                    mCamera.setPreviewDisplay(mHolder);
                    mCamera.setPreviewCallback((Camera.PreviewCallback) getContext());
                    mCamera.startPreview();
                }
            } catch (Exception e) {
                Log.d("ERROR", "Camera error on surfaceChanged " + e.getMessage());
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            try {
                if (mCamera != null) {
                    mCamera.stopPreview();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
