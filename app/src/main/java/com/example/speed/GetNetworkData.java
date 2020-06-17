package com.example.speed;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GetNetworkData extends Thread {

    double getLat = 0.0;
    double getLon = 0.0;
    boolean con = false;
    HashMap<Integer,String> mapAddress = new HashMap<>();
    HashMap<Integer,List<String>> addressValue = new HashMap<>();
    /* example
        <server url="http://speedtest.uztelecom.uz:8080/speedtest/upload.php"
        lat="41.2667"
        lon="69.2167"
        name="Tashkent"
        country="Uzbekistan"
        cc="UZ"
        sponsor="UZTELECOM"
        id="22096"
        host="speedtest.uztelecom.uz:8080"/>
        */
    String urlAddress = "";
    String latitude = "";
    String longitude = "";
    String name = "";
    String country = "";
    String cc = "";
    String sponsor = "";
    //String id = "";
    String host = "";

    public  HashMap<Integer, String> getMapAddress() {
        return mapAddress;
    }

    public HashMap<Integer, List<String>> getAddressValue() {
        return addressValue;
    }


    @Override
    public void run() {
        getLatLon();

        //get data
        int key = 0;
        addData(key);
        con = true;
    }
    private void addData(int key){
        try {
            URL serverURL = new URL("https://www.speedtest.net/speedtest-servers-static.php");
            HttpURLConnection isConnection = (HttpURLConnection) serverURL.openConnection();
            int code = isConnection.getResponseCode();

            if (code == 200) {
                BufferedReader bReader = new BufferedReader(
                        new InputStreamReader(isConnection.getInputStream()));

                String text;
                while ((text = bReader.readLine()) != null) {
                    if (text.contains("<server url")) {

                        urlAddress = text.split("server url=\"")[1].split("\"")[0];
                        latitude = text.split("lat=\"")[1].split("\"")[0];
                        longitude = text.split("lon=\"")[1].split("\"")[0];
                        name = text.split("name=\"")[1].split("\"")[0];
                        country = text.split("country=\"")[1].split("\"")[0];
                        cc = text.split("cc=\"")[1].split("\"")[0];
                        sponsor = text.split("sponsor=\"")[1].split("\"")[0];
                        host = text.split("host=\"")[1].split("\"")[0];

                        List<String> list = Arrays.asList(latitude, longitude, name, country, cc, sponsor, host);
                        mapAddress.put(key, urlAddress);
                        addressValue.put(key, list);
                        key++;
                    }
                }

                bReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLatLon(){
        try {
            URL getURL = new URL("https://www.speedtest.net/speedtest-config.php");
            HttpURLConnection isConnection = (HttpURLConnection) getURL.openConnection();
            int code = isConnection.getResponseCode();
            // connect ok
            if (code == 200) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(isConnection.getInputStream()));
                String line;
                // <client ip="82.215.103.34" lat="41" lon="64" isp="PE Turon Media"
                while ((line = br.readLine()) != null) {
                    if (!line.contains("isp=")) {
                        //uz -> isp = PE Turon Media
                        continue;
                    }
                    //lat="41" lon="64" isp="PE Turon Media"
                    getLat = Double.parseDouble(line.split("lat=\"")[1].split(" ")[0].replace("\"", ""));
                    getLon = Double.parseDouble(line.split("lon=\"")[1].split(" ")[0].replace("\"", ""));
                    break;
                }
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

