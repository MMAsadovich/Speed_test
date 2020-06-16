package com.example.speed;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.speed.ui.home.DownThread;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);



    }

    @Override
    protected void onStart() {
        super.onStart();


        Button startButton = (Button) findViewById(R.id.btnId);

        startButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                final TextView ping = findViewById(R.id.pingId);
                final TextView download = findViewById(R.id.downloadId);
                final TextView upload = findViewById(R.id.uploadId);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ping.setText("0 ms");
                        download.setText("0 Mbps");
                        upload.setText("0 Mbps");
                    }
                });


                // Ping check and write


                // Download check and write


                // Upload check and write

                // test
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final TextView view = findViewById(R.id.downloadId);
                        for (int i = 0; i < 100; i++) {
                            final DownThread downThread = new DownThread();
                            Thread thread = new Thread(downThread);
                            thread.start();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.setText(downThread.rate + " Mbps");
                                }
                            });
                            while (thread.isAlive()) {

                            }
                        }
                    }
                }).start();*/

            }
        });
    }
}