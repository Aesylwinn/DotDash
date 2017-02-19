package com.sh.blah.dotdash;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    MorseTransmission transmitter;
    EditText morseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transmitter = new MorseTransmission();

        if (getIntent().hasExtra(Intent.EXTRA_RETURN_RESULT)) {
            double[] durations = getIntent().getExtras().getDoubleArray(Intent.EXTRA_RETURN_RESULT);
            String code = time_Morse(durations); // Translate function goes here
            String result = transmitter.decrypt(code);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("You recieved a message!");
            builder.setMessage(result);

            AlertDialog dialog = builder.create();
            dialog.show();
        }

        // Load layout and retrieve elements
        setContentView(R.layout.activity_main);
        morseText = (EditText) findViewById(R.id.morseTextField);


        // Request permissions for Android 6
        int camPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (camPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    //HELLO FOOLS
    public void SendButtonClick(View v) {
        transmitter.transmit(morseText.getText().toString());
    }

    //Read Button Click
    public void ReadButtonClick(View v) {
        Intent i = new Intent(this, DecoderActivity.class);
        startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int reqCode, String[] permissions, int[] results) {
        super.onRequestPermissionsResult(reqCode, permissions, results);
    }
    public String time_Morse(double b[]) {
        String out = "";
        for (int i = 0; i < b.length; i++) {
            if (b[i] >= 1000) {
                i++;
                continue;
            }
            if (b[i] < 450) {
                if (b[i] < 250)
                    out += '*';
                else
                    out += '-';
            } else if ((b[i] >= 450) && (b[i] < 700)) {
                out += ' ';
            } else {
                out += '/';
            }
        }
        return out;
    }
}

