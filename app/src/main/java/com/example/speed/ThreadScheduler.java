package com.example.speed;

import android.os.Handler;
import android.os.Looper;
import android.widget.Button;

import com.example.speed.checkers.Checker;

import java.util.ArrayList;
import java.util.List;

public class ThreadScheduler {

    private Button button;

    public ThreadScheduler(Button button) {
        this.button = button;
    }

    public void start(List<Checker> threadList) throws InterruptedException {

        for (Checker thread : threadList) {
            thread.start();
            thread.join();

            if(!thread.getSuccess()){
               break;
            }
        }

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                button.setEnabled(true);
            }
        });


    }
}
