package com.example.speed.ui.home;

import android.widget.TextView;

public class DownThread implements Runnable {
    public Integer rate =0;
    @Override
    public void run() {
        for (int i = 0; i < 1000000; i++) {
            this.rate=i;
        }
    }
}
