package com.example.speed.checkers;

import com.example.speed.component.ControlView;

import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

public class DownloadTest extends Checker {

    long beginTestTime = 0;
    long endTestTime = 0;
    HttpsURLConnection httpsURLConnection;
    String link = "";
    private ControlView textView;
    double dlTimeEnd;
    int count = 0;
    final DecimalFormat decFormat = new DecimalFormat("#.##");

    public DownloadTest(String link, ControlView textView) {
        this.link = link;
        this.textView = textView;
    }

    @Override
    public void run() {
        URL urlLink;
        int dByte = 0;
        link += "random2500x2500.jpg";  // 12 Megabyte
        beginTestTime = System.currentTimeMillis();
        try {
            urlLink = new URL(link);
            httpsURLConnection = (HttpsURLConnection) urlLink.openConnection();
            httpsURLConnection.setSSLSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());
            httpsURLConnection.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            httpsURLConnection.connect();
            //int c = httpsURLConnection.getResponseCode();
            if (httpsURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                byte[] buffer = new byte[10240];

                InputStream inputStream = httpsURLConnection.getInputStream();
                int length = 0;

                while ((length = inputStream.read(buffer)) != -1) {
                    dByte += length;
                    endTestTime = System.currentTimeMillis();
                    double downloadTime = (endTestTime - beginTestTime) / 1000.0;
                    dlTimeEnd = ((dByte * 8) / (1000 * 1000.0)) / downloadTime;
                    count++;
                    if (count % 100 == 0) {
                        textView.setValues(decFormat.format(dlTimeEnd) + " Mbps");
                    }
                }
                inputStream.close();
                httpsURLConnection.disconnect();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        textView.setValues(decFormat.format(dlTimeEnd) + " Mbps");
        textView.setValues(decFormat.format(dlTimeEnd) + "");


    }
}
