package com.example.speed.component;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

public class ControlTextView implements ControlView {

    private TextView textView;

    public ControlTextView(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void setValues(final String value) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                textView.setText(value);
            }
        });
    }

    @Override
    public void resetValues(final String value) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                textView.setText(value);
            }
        });
    }
}
