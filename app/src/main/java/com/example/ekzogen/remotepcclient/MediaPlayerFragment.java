package com.example.ekzogen.remotepcclient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.io.IOException;
import java.io.OutputStream;

public class MediaPlayerFragment extends Fragment {
    ImageButton prevButton;
    ImageButton playPauseButton;
    ImageButton nextButton;
    ImageButton VolUpButton;
    ImageButton VolDownButton;
    ImageButton RunCloseButton;

    private OutputStream outStream;

    public MediaPlayerFragment(){
    }
    public void setOutStream(OutputStream stream){outStream = stream;}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mp_section, container, false);

        prevButton = (ImageButton)rootView.findViewById(R.id.previous);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "112114101118105111117115";
                byte[] msgBuffer = message.getBytes();
                try {
                    outStream.write(msgBuffer);
                } catch (IOException e) {
                    Log.d("Err", "ny");
                }
            }
        });

        playPauseButton = (ImageButton)rootView.findViewById(R.id.play_pause);
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "01121089712111297117115101";
                byte[] msgBuffer = message.getBytes();
                try {
                    outStream.write(msgBuffer);
                } catch (IOException e) {
                    Log.d("Err", "ny");
                }
            }
        });

        nextButton = (ImageButton)rootView.findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "110101120116";
                byte[] msgBuffer = message.getBytes();
                try {
                    outStream.write(msgBuffer);
                } catch (IOException e) {
                    Log.d("Err", "ny");
                }
            }
        });

        VolDownButton = (ImageButton)rootView.findViewById(R.id.vol_down);
        VolDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "8611110868111119110";
                byte[] msgBuffer = message.getBytes();
                try {
                    outStream.write(msgBuffer);
                } catch (IOException e) {
                    Log.d("Err", "ny");
                }
            }
        });

        VolUpButton = (ImageButton)rootView.findViewById(R.id.vol_up);
        VolUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "8611110885112";
                byte[] msgBuffer = message.getBytes();
                try {
                    outStream.write(msgBuffer);
                } catch (IOException e) {
                    Log.d("Err", "ny");
                }
            }
        });

        RunCloseButton = (ImageButton)rootView.findViewById(R.id.wmp_logo);
        RunCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "8211711067108111115101";
                byte[] msgBuffer = message.getBytes();
                try {
                    outStream.write(msgBuffer);
                } catch (IOException e) {
                    Log.d("Err", "ny");
                }
            }
        });

        return rootView;
    }
}
