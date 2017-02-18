package com.sh.blah.dotdash;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class DecoderActivity extends AppCompatActivity implements View.OnTouchListener {
    sView v;
    Thread t = null;
    SurfaceHolder sHolder;
    Boolean safeToRun = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = new sView(this);
        v.setOnTouchListener(this);
        setContentView(v);

    }

    @Override
    protected void onPause() {
        super.onPause();
        v.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        v.resume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    public class sView extends SurfaceView implements Runnable {

        public sView (Context context){
            super(context);
            sHolder = getHolder();
        }

        public void run() {
            while (safeToRun == true){
                if(!sHolder.getSurface().isValid()) {
                    continue;
                }
                Canvas c = sHolder.lockCanvas();
                c.drawARGB(250,150,150,10);
                sHolder.unlockCanvasAndPost(c);
            }

        }


        public void pause() {
            safeToRun = false;
            while(true){
                try{
                    t.join();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        public void resume() {
            safeToRun = true;
            t = new Thread(this);
            t.start();
        }

    }
}
