package com.sh.blah.dotdash;

import android.hardware.Camera;

import java.util.Map;

/**
 * Created by aesylwinn on 2/18/17.
 */

public class MorseTransmission {

    MorseTransmission(){
    }


    /**
     * Pulses the camera flashlight
     * @param length the duration in milliseconds
     */
    public void pulse(int length)
    {
        Camera camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();
        try {
            Thread.sleep(length);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        camera.stopPreview();
        camera.release();
    }

    public void transmit(String message)
    {
        pulse(1000);
    }

    public String recieve(Camera camera)
    {
        return "";
    }
}
