package com.example.ekzogen.remotepcclient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class TpKbFragment extends Fragment {
    ImageButton mouse;
    ImageButton keyboard;

    private OnKeyboardButtonListener kbListener;

    private OnMouseButtonListener mListener;

    public void setListeners(OnMouseButtonListener ml,  OnKeyboardButtonListener kbl) {
        mListener = ml;
        kbListener = kbl;
    }

    public interface OnKeyboardButtonListener {
        public abstract void OnKeyboardButtonClick();
    }

    public interface OnMouseButtonListener {
        public abstract void OnMouseButtonClick();
    }

    public TpKbFragment(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tp_kb_section, container, false);

        mouse = (ImageButton)rootView.findViewById(R.id.mouse);
        mouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnMouseButtonClick();
            }
        });

        keyboard = (ImageButton)rootView.findViewById(R.id.keyboard);
        keyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kbListener.OnKeyboardButtonClick();
            }
        });

        return rootView;
    }
}
