package com.sh.blah.dotdash;

import android.hardware.Camera;

import java.util.Map;

/**
 * Created by aesylwinn on 2/18/17.
 */

public class MorseTransmission {

    private final int DotLength = 500;
    private final int DashLength = 1000;
    private final int IntervalLength = 333;

    public MorseTransmission(){
    }


    /**
     * Pulses the camera flashlight
     * @param length the duration in milliseconds
     */
    private void pulse(int length)
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

        try {
            Thread.sleep(IntervalLength);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void pulseDot()
    {
        pulse(DotLength);
    }

    private void pulseDash()
    {
        pulse(DashLength);
    }

    public void transmit(String message)
    {
        pulseDot();
        pulseDot();
        pulseDot();

        pulseDash();
        pulseDash();
        pulseDash();

        pulseDot();
        pulseDot();
        pulseDot();
    }

    public String recieve(Camera camera)
    {
        return "";
    }
}
