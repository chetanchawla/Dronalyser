package com.example.dronalyser;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.dronalyser.R;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class liveView extends Activity {
    public String macAddr;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_view);
        macAddr = getMac();
        WebView myWebView = (WebView) findViewById(R.id.webb);
        myWebView.loadUrl("192.168.0.27:8080");

    }

    public String getMac() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            //handle exception
            Toast.makeText(getBaseContext(),
                    "Cannot fetch MAC ID",
                    Toast.LENGTH_SHORT).show();
        }
        return "";
    }
}
