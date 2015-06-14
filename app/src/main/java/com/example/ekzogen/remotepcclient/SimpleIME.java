package com.example.ekzogen.remotepcclient;

import android.app.Activity;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.IOException;
import java.io.OutputStream;

public class SimpleIME extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView kv;
    private Keyboard keyboard;

    private boolean caps = false;

    OutputStream outStream = MainActivity.outStream;
    Activity host;

    public SimpleIME() {
    }

    public SimpleIME(Activity h, View kbview) {
        host = h;
        kv = (KeyboardView)kbview;
        keyboard = new Keyboard(h, R.xml.qwerty);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        showCustomKeyboard();
    }

    public void hideCustomKeyboard() {
        kv.setVisibility(View.GONE);
        kv.setEnabled(false);
    }

    public void showCustomKeyboard() {
        kv.setVisibility(View.VISIBLE);
        kv.setEnabled(true);
        //if( v!=null ) ((InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        switch(primaryCode){
            case Keyboard.KEYCODE_DELETE :
                break;
            case Keyboard.KEYCODE_DONE:
                break;
            case 20:
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;
            default:
                char code = (char)primaryCode;
                if(Character.isLetter(code) && caps){
                    code = Character.toUpperCase(code);
                }
        }
        String message = String.valueOf(primaryCode);
        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
        } catch (IOException e) {
            Log.d("Err", "ny");
        }
    }

    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void swipeLeft() {
        hideCustomKeyboard();
        keyboard = new Keyboard(host, R.xml.numpad);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        showCustomKeyboard();
    }

    @Override
    public void swipeRight() {
        hideCustomKeyboard();
        keyboard = new Keyboard(host, R.xml.qwerty);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        showCustomKeyboard();
    }

    @Override
    public void swipeUp() {
    }
}