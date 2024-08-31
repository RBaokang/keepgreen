package com.hjq.demo.Tool;

import android.os.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connect {
    public static Handler handler;
    public String Connection(String urll) {
        StringBuilder respone = new StringBuilder();
        new Thread() {
            public void run() {
                try {
                    URL url = new URL(urll);
                    final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream inputstream = connection.getInputStream();
                    final BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        respone.append(line);
                    }
                    /*Message msg = Message.obtain();
                    msg.what = 0;
                    msg.obj = respone.toString();
                    handler.sendMessage(msg);*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return respone.toString();
    }

}
