package com.sh.blah.dotdash;

import android.hardware.Camera;

import java.util.Map;

/**
 * Created by aesylwinn on 2/18/17.
 */

public class MorseTransmission {

    private final int DotLength = 500;
    private final int DashLength = 1000;
    private final int SpaceLength = 500;
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

    private void pulseSpace()
    {
        try {
            Thread.sleep(SpaceLength);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void transmit(String message)
    {
        String morseMes =  code(message);
        int index;
        for(index = 0; index < morseMes.length(); index++)
        {
            char c = morseMes.charAt(index);
            if(c == '*'){
                pulseDot();
            }else if(c == '-'){
                pulseDash();
            }else if(c == ' '){
                pulseSpace();
            } else {
                pulseWord();
            }
        }
    }

    private String code(String mes){
        int i;
        String out = "";
        for (i = 0; i < mes.length(); i++)
        {
            char c = mes.charAt(i);
            switch(c){
                case 'a':
                case 'A':
                    out += "*-";
                    break;
                case 'b':
                case 'B':
                    out += "-***";
                    break;
                case 'c':
                case 'C':
                    out += "-*-*";
                    break;
                case 'd':
                case 'D':
                    out += "-**";
                    break;
                case 'e':
                case 'E':
                    out += "*";
                    break;
                case 'f':
                case 'F':
                    out += "**-*";
                    break;
                case 'g':
                case 'G':
                    out += "--*";
                    break;
                case 'h':
                case 'H':
                    out += "****";
                    break;
                case 'i':
                case 'I':
                    out += "**";
                    break;
                case 'j':
                case 'J':
                    out += "*---";
                    break;
                case 'k':
                case 'K':
                    out += "-*-";
                    break;
                case 'l':
                case 'L':
                    out += "*-**";
                    break;
                case 'm':
                case 'M':
                    out += "--";
                    break;
                case 'n':
                case 'N':
                    out += "-*";
                    break;
                case 'o':
                case 'O':
                    out += "---";
                    break;
                case 'p':
                case 'P':
                    out += "*--*";
                    break;
                case 'q':
                case 'Q':
                    out += "--*-";
                    break;
                case 'r':
                case 'R':
                    out += "*-*";
                    break;
                case 's':
                case 'S':
                    out += "***";
                    break;
                case 't':
                case 'T':
                    out += "-";
                    break;
                case 'u':
                case 'U':
                    out += "**-";
                    break;
                case 'v':
                case 'V':
                    out += "***-";
                    break;
                case 'w':
                case 'W':
                    out += "*--";
                    break;
                case 'x':
                case 'X':
                    out += "-**-";
                    break;
                case 'y':
                case 'Y':
                    out += "-*--";
                    break;
                case 'z':
                case 'Z':
                    out += "--**";
                    break;
                case '0':
                    out += "-----";
                    break;
                case '1':
                    out += "*----";
                    break;
                case '2':
                    out += "**---";
                    break;
                case '3':
                    out += "***--";
                    break;
                case '4':
                    out += "****-";
                    break;
                case '5':
                    out += "*****";
                    break;
                case '6':
                    out += "-****";
                    break;
                case '7':
                    out += "--***";
                    break;
                case '8':
                    out += "---**";
                    break;
                case '9':
                    out += "----*";
                    break;
                case ' ':
                    out += "/";
                    break;
                default :
                    break;

            }
            out += " ";
        }

        return out;
    }

    public String recieve(Camera camera)
    {
        return "";
    }
}
