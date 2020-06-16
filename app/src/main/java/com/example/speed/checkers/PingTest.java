package com.example.speed.checkers;

import com.example.speed.component.ControlTextView;
import com.example.speed.component.ControlView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PingTest extends Checker {

    private String server = "";
    private int toTry;
    private double pingT = 0.0;
    private double rtt = 0.0; // round trip time
    private ControlView textView;

    public PingTest(String serverIpAddress, int pingTryCount, ControlView textView) {
        super();
        this.server = serverIpAddress;
        this.toTry = pingTryCount;
        this.textView = textView;
    }

    @Override
    public void run() {
        pingCMD();
    }

    private void pingCMD() {

        try {
            // similar to cmd write
            ProcessBuilder processBuilder = new ProcessBuilder("ping", "-c " + toTry, this.server);

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String fullMessage = "";
            String line;
            while ((line = reader.readLine()) != null) {
                fullMessage += '\n' + line;

                if (line.contains("icmp_seq")) {
                    pingT = Double.parseDouble(line.split(" ")[line.split(" ").length - 2].replace("time=", ""));
                    textView.setValues(pingT + " ms");
                }
                if (line.startsWith("rtt ")) {
                    rtt = Double.parseDouble(line.split("/")[4]); // ping
                    textView.setValues(rtt + " ms");
                    break;
                }
                if (line.contains("Unreachable") || line.contains("Unknown") || line.contains("100% packet loss")) { // disconnect
                    textView.setValues(-1 + " ms");
                    return;
                }
            }
            // wait process finished
            process.waitFor();
            reader.close();
            setSuccess(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getPingT() {
        return pingT;
    }

    public double getRtt() {
        return rtt;
    }

}
