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
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends Activity {

    MorseTransmission transmitter;
    EditText morseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transmitter = new MorseTransmission();

        if (DecoderActivity.timings.size() > 0){//getIntent().hasExtra(Intent.EXTRA_RETURN_RESULT)) {
            //getIntent().getExtras().getLongArray(Intent.EXTRA_RETURN_RESULT);

            long[] durations = new long[DecoderActivity.timings.size()];
            for (int x = 0; x < DecoderActivity.timings.size(); ++x)
                durations[x] = DecoderActivity.timings.get(x);

            Log.d("dur", durations.toString());
            String code = transmitter.time_Morse(durations); // Translate function goes here
            Log.d("code", code);
            String result = transmitter.decrypt(code);
            Log.d("result", result);

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
}

