package com.example.ekzogen.remotepcclient;

import com.example.ekzogen.remotepcclient.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;

public class SlideShowFragment extends Fragment {
    ImageButton startSlideShow;
    ImageButton prevSlide;
    ImageButton nextSlide;
    ImageButton stopSlideShow;

    private OutputStream outStream = MainActivity.outStream;

    public SlideShowFragment(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pp_section, container, false);

        startSlideShow = (ImageButton)rootView.findViewById(R.id.slideshow_start);
        startSlideShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "11511697114116115108105100101115104111119";
                byte[] msgBuffer = message.getBytes();
                try {
                    outStream.write(msgBuffer);
                } catch (IOException e) {
                    Log.d("Err", "ny");
                }
            }
        });

        stopSlideShow = (ImageButton)rootView.findViewById(R.id.slideshow_close);
        stopSlideShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "0115116111112115108105100101115104111119";
                byte[] msgBuffer = message.getBytes();
                try {
                    outStream.write(msgBuffer);
                } catch (IOException e) {
                    Log.d("Err", "ny");
                }
            }
        });

        prevSlide = (ImageButton)rootView.findViewById(R.id.prev_slide);
        prevSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "0112114101118105111117115115108105100101";
                byte[] msgBuffer = message.getBytes();
                try {
                    outStream.write(msgBuffer);
                } catch (IOException e) {
                    Log.d("Err", "ny");
                }
            }
        });

        nextSlide = (ImageButton)rootView.findViewById(R.id.next_slide);
        nextSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "0110101120116115108105100101";
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
