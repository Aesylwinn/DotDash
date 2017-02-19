package com.sh.blah.dotdash;

import android.hardware.Camera;

import java.util.Map;

/**
 * Created by aesylwinn on 2/18/17.
 */

public class MorseTransmission {

    private final int DotLength = 100;
    private final int DashLength = 400;
    private final int SpaceLength = 600;
    private final int WordLength = 800;
    private final int IntervalLength = 25;
    private final int SpecialLength = 1500;

    public MorseTransmission(){
    }

    private void waitDur(long length)
    {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < length);
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
        waitDur(length);
        camera.stopPreview();
        camera.release();

        waitDur(IntervalLength);
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
        waitDur(SpaceLength);
    }

    private void pulseWord()
    {
        waitDur(WordLength);
    }

    private void pulseStart()
    {
        waitDur(SpecialLength);
    }

    public void transmit(String message)
    {
        pulseStart();

        String morseMes = code(message);
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

        pulseStart();
    }

    /*public String time_To_Morse(int [] )
    {
    MANI's FUNCTION
    }*/

    public String decrypt(String input)//Function takes in string: (ie: "*- -* / ***") and turns it into original form: ("an s")
    {
        String decoded = "";
        String temp = "";
        int j;
        for(j = 0; j < input.length(); j++)
        {
            char b = input.charAt(j);

            if(b == ' ')
            {
                decoded += morToAlph(temp);
                temp = "";
            }
            else if((input.length() - 1) == j)
            {
                temp += b;
                decoded += morToAlph(temp);
            }
            else if(b == '/')
            {
                decoded += ' ';
            }
            else
            {
                temp += b;
            }

        }
        return decoded;
    }

    public static String morToAlph(String morse)//Function is called by decrypt to individually make characters
    {
        String alphabet = "";
        switch(morse)
        {
            case "*-":
                alphabet += 'a';
                break;
            case "-***":
                alphabet += 'b';
                break;
            case "-*-*":
                alphabet += 'c';
                break;
            case "-**":
                alphabet += 'd';
                break;
            case "*":
                alphabet += 'e';
                break;
            case "**-*":
                alphabet += 'f';
                break;
            case "--*":
                alphabet += 'g';
                break;
            case "****":
                alphabet += 'h';
                break;
            case "**":
                alphabet += 'i';
                break;
            case "*---":
                alphabet += 'j';
                break;
            case "-*-":
                alphabet += 'k';
                break;
            case "*-**":
                alphabet += 'l';
                break;
            case "--":
                alphabet += 'm';
                break;
            case "-*":
                alphabet += 'n';
                break;
            case "---":
                alphabet += 'o';
                break;
            case "*--*":
                alphabet += 'p';
                break;
            case "--*-":
                alphabet += 'q';
                break;
            case "*-*":
                alphabet += 'r';
                break;
            case "***":
                alphabet += 's';
                break;
            case "-":
                alphabet += 't';
                break;
            case "**-":
                alphabet += 'u';
                break;
            case "***-":
                alphabet += 'v';
                break;
            case "*--":
                alphabet += 'w';
                break;
            case "-**-":
                alphabet += 'x';
                break;
            case "-*--":
                alphabet += 'y';
                break;
            case "--**":
                alphabet += 'z';
                break;
            case "-----":
                alphabet += '0';
                break;
            case "*----":
                alphabet += '1';
                break;
            case "**---":
                alphabet += '2';
                break;
            case "***--":
                alphabet += '3';
                break;
            case "****-":
                alphabet += '4';
                break;
            case "*****":
                alphabet += '5';
                break;
            case "-****":
                alphabet += '6';
                break;
            case "--***":
                alphabet += '7';
                break;
            case "---**":
                alphabet += '8';
                break;
            case "----*":
                alphabet += '9';
                break;
            default:
                break;
        }
        return alphabet;
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
}
