package com.example.speed;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.speed.checkers.Checker;
import com.example.speed.component.ControlTextView;
import com.example.speed.component.ControlView;
import com.example.speed.checkers.PingTest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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

    GetNetworkData getNetworkData =null;
    HashSet<String> tempBlackList;

    @Override
    protected void onResume() {
        super.onResume();
        getNetworkData = new GetNetworkData();
        tempBlackList = new HashSet<>();
        getNetworkData.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        final Button startButton = (Button) findViewById(R.id.btnId);
        // TODO

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startButton.setEnabled(false);
                    }
                });

                if(getNetworkData == null){
                    getNetworkData = new GetNetworkData();
                    getNetworkData.start();
                }



                while (!getNetworkData.con){

                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TextView uploadId = findViewById(R.id.uploadId);
                        TextView downloadId = findViewById(R.id.downloadId);
                        ControlView pingTextView = new ControlTextView((TextView) findViewById(R.id.pingId));
                        ControlView upload = new ControlTextView(uploadId);
                        ControlView download = new ControlTextView(downloadId);

                        // Todo check network
                        pingTextView.resetValues("0 ms");
                        upload.resetValues("0 Mbps");
                        download.resetValues("0 Mbps");

                        HashMap<Integer, String> mapKey = getNetworkData.getMapAddress();
                        HashMap<Integer, List<String>> mapValue = getNetworkData.getAddressValue();
                        double selfLat = getNetworkData.getLat;
                        double selfLon = getNetworkData.getLon;
                        double tmp = Integer.MAX_VALUE;
                        //double dist = 0.0;
                        List<Integer> indexList = new ArrayList<>();
                        List<Double> distances = new ArrayList<>();

                        Integer findServerIndex = 0;
                        for (int index : mapKey.keySet()) {

                            Location source = new Location("Source");
                            source.setLatitude(selfLat);
                            source.setLongitude(selfLon);

                            List<String> ls = mapValue.get(index);
                            Location dest = new Location("Dest");
                            dest.setLatitude(Double.parseDouble(ls.get(0)));
                            dest.setLongitude(Double.parseDouble(ls.get(1)));

                            double distance = source.distanceTo(dest);

                            if (tmp > distance) {
                                tmp = distance;
                                //dist = distance;
                                findServerIndex = index;
                            }

                        }

                        //String testAddr = mapKey.get(findServerIndex).replace("http://", "https://");
                        final List<String> info = mapValue.get(findServerIndex);
                        final List<Double> pingList = new ArrayList<>();
                        //final List<Double> downloadList = new ArrayList<>();
                        //final List<Double> uploadList = new ArrayList<>();

                        List<Checker> checkerList = new ArrayList<>();
                        final PingTest pingTest = new PingTest(info.get(6).replace(":8080", ""), 7, pingTextView);

                        checkerList.add(pingTest);


                        ThreadScheduler threadScheduler = new ThreadScheduler(startButton);
                        try {
                            threadScheduler.start(checkerList);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }



                    }
                }).start();

            }


        });
    }
}



