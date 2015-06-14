package com.example.ekzogen.remotepcclient;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.OutputStream;

public class TouchpadActivity extends Activity {
    private OutputStream outStream = MainActivity.outStream;
    int x, y;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touchpad);

        // this is the view on which you will listen for touch events
        final View touchView = findViewById(R.id.touchView);
        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    x = (int) event.getX();
                    y = (int) event.getY();
                } else {
                    byte[] msgBuffer = (String.valueOf((int) event.getX() - x) + "x" + String.valueOf((int) event.getY() - y) + ";").getBytes();
                    x = (int) event.getX();
                    y = (int) event.getY();
                    try {
                        outStream.write(msgBuffer);
                    } catch (IOException e) {
                        Log.d("Err", "ny");
                    }
                }
                return true;
            }
        });

        final View leftButton = findViewById(R.id.left_click);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "1081011021169910810599107";
                byte[] msgBuffer = message.getBytes();
                try {
                    outStream.write(msgBuffer);
                } catch (IOException e) {
                    Log.d("Err", "ny");
                }
            }
        });

        final View rightButton = findViewById(R.id.right_click);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "01141051031041169910810599107";
                byte[] msgBuffer = message.getBytes();
                try {
                    outStream.write(msgBuffer);
                } catch (IOException e) {
                    Log.d("Err", "ny");
                }
            }
        });
        setResult(Activity.RESULT_OK, null);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

