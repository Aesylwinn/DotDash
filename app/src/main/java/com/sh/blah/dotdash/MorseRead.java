package com.sh.blah.dotdash;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Royal Ardor on 2/18/2017.
 */

public class MorseRead {

    MorseRead(){
    }

    void StartCamera(){
        Camera cam = Camera.open();
        cam.startPreview();
        cam.release();
    }

}
