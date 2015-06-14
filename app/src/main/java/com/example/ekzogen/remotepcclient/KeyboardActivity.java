package com.example.ekzogen.remotepcclient;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.IOException;
import java.io.OutputStream;

public class KeyboardActivity extends Activity{
    private OutputStream outStream = MainActivity.outStream;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keyboard);

        setResult(Activity.RESULT_OK, null);

        SimpleIME ss = new SimpleIME(this, findViewById(R.id.keyboard));
        //ss.onCreateInputView();
        //ss.showCustomKeyboard(getWindow().getDecorView().findViewById(android.R.id.content));

    }
    /*@Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_UP){
            String message = String.valueOf(event.getKeyCode());
            byte[] msgBuffer = message.getBytes();
            try {
                outStream.write(msgBuffer);
            } catch (IOException e) {
                Log.d("zaeb", "ny");
            }
        }
        return super.dispatchKeyEvent(event);
    }*/
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
