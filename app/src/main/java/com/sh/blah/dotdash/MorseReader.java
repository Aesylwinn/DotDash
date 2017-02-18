package com.sh.blah.dotdash;

import android.app.Activity;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;

import java.io.IOException;

public class MorseReader extends AppCompatActivity {
    private SurfaceView sView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse_reader);
        sView = (SurfaceView)findViewById(R.id.surfaceView);
        StartCamera();
    }

    void StartCamera(){
        Camera cam = Camera.open();
        cam.setDisplayOrientation(0);
        try {
            cam.setPreviewDisplay(sView.getHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
        cam.startPreview();
    }
}
