package com.sh.blah.dotdash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    MorseTransmission transmitter;
    EditText morseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load layout and retrieve elements
        setContentView(R.layout.activity_main);
        morseText = (EditText) findViewById(R.id.morseTextField);

        // Initialization
        transmitter = new MorseTransmission();

        // Request permissions for Android 6
        int camPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (camPermission != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA }, 1 );
        }
    }
    //HELLO FOOLS

    public void SendButtonClick (View v){

        transmitter.transmit("blah");
    }

    @Override
    public void onRequestPermissionsResult(int reqCode, String[] permissions, int[] results) {
        super.onRequestPermissionsResult(reqCode, permissions, results);
    }
}

