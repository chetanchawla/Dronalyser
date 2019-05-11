package com.example.dronalyser;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public int toggleHin=0;
    public String macid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //Function toggles the language of the application from Hindi toEnglish and Vice Versa
    public void convertText(View view){
        TextView heading_eng = (TextView)findViewById(R.id.heading_eng);
        TextView heading_hin = (TextView)findViewById(R.id.heading_hin);
        TextView report_eng = (TextView)findViewById(R.id.report_eng);
        TextView report_hin = (TextView)findViewById(R.id.report_hin);
        TextView drone_eng = (TextView)findViewById(R.id.drone_eng);
        TextView drone_hin = (TextView)findViewById(R.id.drone_hin);
        TextView live_eng = (TextView)findViewById(R.id.live_eng);
        TextView live_hin = (TextView)findViewById(R.id.live_hin);
        Button drone_button_eng = (Button) findViewById(R.id.drone_button_eng);
        Button drone_button_hin = (Button)findViewById(R.id.drone_button_hin);
        Button live_button_eng = (Button) findViewById(R.id.live_button_eng);
        Button live_button_hin = (Button)findViewById(R.id.live_button_hin);
        Button signing_in = (Button) findViewById(R.id.signing_in);
        Button signing_in_hin = (Button)findViewById(R.id.signing_in_hindi);
        Switch translate = (Switch) findViewById(R.id.Translate);
        Switch translate_hin = (Switch) findViewById(R.id.Translate_Hin);


        //Toggling the Englist to Hindi witch and vice versa

        if(toggleHin%2==0){
            heading_eng.setVisibility(View.INVISIBLE);
            heading_hin.setVisibility(View.VISIBLE);
            report_eng.setVisibility(View.INVISIBLE);
            report_hin.setVisibility(View.VISIBLE);
            drone_eng.setVisibility(View.INVISIBLE);
            drone_hin.setVisibility(View.VISIBLE);
            live_eng.setVisibility(View.INVISIBLE);
            live_hin.setVisibility(View.VISIBLE);
            drone_button_eng.setText("ड्रोन बुलायें");
            live_button_eng.setText("जाँच देखें");
            signing_in.setText("सऐन इन");
            translate.setText("English");
        }
        else{
            heading_eng.setVisibility(View.VISIBLE);
            heading_hin.setVisibility(View.INVISIBLE);
            report_eng.setVisibility(View.VISIBLE);
            report_hin.setVisibility(View.INVISIBLE);
            drone_eng.setVisibility(View.VISIBLE);
            drone_hin.setVisibility(View.INVISIBLE);
            live_eng.setVisibility(View.VISIBLE);
            live_hin.setVisibility(View.INVISIBLE);
            drone_button_eng.setText("Call Drone");
            live_button_eng.setText("Live");
            signing_in.setText("Sign In");
            translate.setText("हिन्दी");
        }
        toggleHin++;
    }
    public void buttonSign(View view){
        //Fetch MAC Address of the device to sign in
        macid= getMac();
        Toast.makeText(getBaseContext(),
            macid,
            Toast.LENGTH_SHORT).show();
        //Firebase connectivity to retrieve storage data
    }

    public void droneCall(View view){
        //Start Map activity on click
        //Pass the MAC ID
        //Show the map and ask to plot the area
        startActivity(new Intent(getApplicationContext(), call_drone.class));
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
