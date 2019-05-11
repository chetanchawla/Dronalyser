package com.example.dronalyser;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class call_drone extends Activity {
    public String macAddr;
    public  int engorhin;
    private LocationManager lm;                 //sets a location manager named lm
    private LocationListener locationListener;
    public location Loc = new location();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drone_calling_layout);
        engorhin=MainActivity.engOrHin();
        TextView droneText = (TextView)findViewById(R.id.droneText);
        if(engorhin%2==1){
            droneText.setText("ड्रोन को आपका पता भेज रहे हैं");
        }
        macAddr= getMac();
        getLoc();
    }




    public void getLoc() {
        lm = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();
        Location loc = null;
        pl.droidsonroids.gif.GifImageView animeDrone = (pl.droidsonroids.gif.GifImageView)findViewById(R.id.animDrone);
        ProgressBar progBar = (ProgressBar)findViewById(R.id.progBar);
        TextView droneText = (TextView)findViewById(R.id.droneText);
        try {
            lm.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0,
                    locationListener);
            loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (SecurityException e) {
            Toast.makeText(getBaseContext(),
                    "Can't access GPS values",
                    Toast.LENGTH_SHORT).show();
        }


        if (loc != null) {
            Loc.setLat(loc.getLatitude());
            Loc.setAlt(loc.getAltitude());
            Loc.setLong(loc.getLongitude());
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(macAddr);
            myRef.setValue(Loc);
            animeDrone.setVisibility(View.VISIBLE);
            progBar.setVisibility(View.INVISIBLE);
            if(engorhin%2==0){
                droneText.setText("Drone is on its way");
            }
            else{
                droneText.setText("ड्रोन आ रहा है ");
            }
        } else {
            Toast.makeText(getBaseContext(),
                    "Location is null",
                    Toast.LENGTH_SHORT).show();
        }
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
    private class MyLocationListener implements LocationListener
    {
        @Override
        public void onLocationChanged(Location loc) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
            // TODO Auto-generated method stub
        }
    }
}
