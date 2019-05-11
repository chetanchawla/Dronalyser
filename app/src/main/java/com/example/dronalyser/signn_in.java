package com.example.dronalyser;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class signn_in extends Activity{
    public StorageReference mStorage;
    public String macAddr;
    public String nameMacAddr;
    public  int engorhin;
    public int zoom=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);
        macAddr = getMac();
        nameMacAddr = macAddr.replaceAll(Pattern.quote(":"), "_");
        Toast.makeText(getBaseContext(),
                "Signed In",
                Toast.LENGTH_SHORT).show();
        TextView textVieww=(TextView)findViewById(R.id.textView);
        Button clickhere=(Button)findViewById(R.id.button);
        engorhin=MainActivity.engOrHin();
        if(engorhin%2==0){
            textVieww.setText("Welcome User "+ nameMacAddr);
        }
        else{
            textVieww.setText("स्वागत है "+ nameMacAddr);
            clickhere.setText("रिपोर्ट देखें");
        }

    }
//    @GlideModule
//    public final class MyAppGlideModule extends AppGlideModule {}


    public void showIm(View view){
        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        // ImageView in your Activity
        final ImageView reportImage = findViewById(R.id.reportImage);
        final ImageView droneImage = findViewById(R.id.droneImage);
        final Button buttonn=findViewById(R.id.button);
        final TextView textVieww=findViewById(R.id.textView);
        final Button button2 = findViewById(R.id.button2);
        storageReference.child(nameMacAddr + ".png").getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use the bytes to display the image
                Toast.makeText(getBaseContext(),
                    "Downloaded report",
                    Toast.LENGTH_SHORT).show();
                droneImage.setVisibility(View.INVISIBLE);
                buttonn.setVisibility(View.INVISIBLE);
                textVieww.setVisibility(View.INVISIBLE);
                if(engorhin%2==1){
                    button2.setText("ज़ूम इन");
                }
                button2.setVisibility(View.VISIBLE);
                bmptoimg.setImageViewWithByteArray(reportImage,bytes);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


        //        mStorage=FirebaseStorage.getInstance().getReference("image.png");
//        File localFile = null;
//        try {
//            localFile = File.createTempFile("image", "png");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mStorage.getFile(localFile)
//                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                        // Successfully downloaded data to local file
//                        //Show file
//                        Toast.makeText(getBaseContext(),
//                                "Downloaded pic",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle failed download
//                Toast.makeText(getBaseContext(),
//                        "Failed downloading pic",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
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
    public void orientLand(View view){
        zoom++;
        Button button2=findViewById(R.id.button2);
        if(zoom%2==1) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            if(engorhin%2==0) {
                button2.setText("Zoom out");
            }
            else{
                button2.setText("ज़ूम आउट");
            }
        }
        else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            if(engorhin%2==0) {
                button2.setText("Zoom in");
            }
            else{
                button2.setText("ज़ूम इन");
            }
        }
    }

}
