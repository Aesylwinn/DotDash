package com.sh.blah.dotdash;

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

        setContentView(R.layout.activity_main);
        morseText = (EditText) findViewById(R.id.morseTextField);

        transmitter = new MorseTransmission();
    }
    //HELLO FOOLS

    public void SendButtonClick (View v){

        transmitter.transmit("blah");
    }
}

