package com.sh.blah.dotdash;

import android.app.Activity;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.io.IOException;
import java.util.List;

public class DecoderActivity extends Activity implements Camera.PreviewCallback {

    final int ExaminedWidth = 500 * 3;
    final int ExaminedHeight = 500 * 3;

    private Camera mCamera = null;
    private CameraView mCameraView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder2);

        try{
            mCamera = Camera.open();
        } catch (Exception e){
            Log.d("ERROR", "Failed to get camera: " + e.getMessage());
        }

        if(mCamera != null) {
            mCameraView = new CameraView(this, mCamera);//create a SurfaceView to show camera data
            FrameLayout camera_view = (FrameLayout)findViewById(R.id.camera_view);
            camera_view.addView(mCameraView);//add the SurfaceView to the layout
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
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
        Log.d("bright", Double.toString(brightness));
    }

    public class CameraView extends SurfaceView implements SurfaceHolder.Callback{
        private SurfaceHolder mHolder;
        private Camera mCamera;

        public CameraView(Context context, Camera camera){
            super(context);

            mCamera = camera;
            mCamera.setDisplayOrientation(90);
            Camera.Parameters parameters = camera.getParameters();
            parameters.setPreviewFormat(ImageFormat.NV21);
            if (parameters.isAutoWhiteBalanceLockSupported())
                parameters.setAutoWhiteBalanceLock(true);
            if (parameters.isAutoExposureLockSupported())
                parameters.setAutoExposureLock(true);
            camera.setParameters(parameters);
            mHolder = getHolder();
            mHolder.addCallback(this);
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            try{
                mCamera.setPreviewDisplay(surfaceHolder);
                mCamera.startPreview();
            } catch (IOException e) {
                Log.d("ERROR", "Camera error on surfaceCreated " + e.getMessage());
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            if(mHolder.getSurface() == null)//check if the surface is ready to receive camera data
                return;

            try{
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
            } catch (Exception e){
                //this will happen when you are trying the camera if it's not running
            }

            try{
                mCamera.setPreviewDisplay(mHolder);
                mCamera.setPreviewCallback((Camera.PreviewCallback) getContext());
                mCamera.startPreview();
            } catch (IOException e) {
                Log.d("ERROR", "Camera error on surfaceChanged " + e.getMessage());
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            mCamera.stopPreview();
            mCamera.release();
        }
    }
}
