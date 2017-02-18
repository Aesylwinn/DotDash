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
     * @param camera the camera object
     * @param length the duration in milliseconds
     */
    public void pulse(Camera camera, int length)
    {
        camera.startPreview();
        try {
            Thread.sleep(length);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        camera.stopPreview();
    }

    public void transmit(String message)
    {
        Camera camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();

        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);

        pulse(camera, 1000);
    }

    public String recieve(Camera camera)
    {
        return "";
    }
}
