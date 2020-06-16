package com.example.speed.ui.home;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PingTest extends Thread {

    String server = "";
    int toTry;
    double pingT =0.0;
    double rtt = 0.0; // round trip time
    boolean done = false;

    @Override
    public void run() {

        PingCMD();

    }

    private void PingCMD(){

        try {
            // similar to cmd write
            ProcessBuilder processBuilder = new ProcessBuilder("ping", "-c " + toTry, this.server);

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("icmp_seq")) {
                    pingT = Double.parseDouble(line.split(" ")[line.split(" ").length - 2].replace("time=", ""));
                }
                if (line.startsWith("rtt ")) {
                    rtt = Double.parseDouble(line.split("/")[4]); // ping
                    break;
                }
                if (line.contains("Unreachable")) { // disconnect
                    return;
                }
            }
            // wait process finished
            process.waitFor();
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        done = true;
    }
}
