package com.example.dronalyser;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class call_drone extends Activity {
    public String macAddr;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drone_calling_layout);
    }
    //macAddr= M
    // ainActivity.getMac();
}
