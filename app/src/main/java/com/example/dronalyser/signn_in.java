package com.example.dronalyser;

import android.app.Activity;
import android.content.Intent;
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

public class signn_in extends Activity{
    public StorageReference mStorage;
    public String macAddr;
    public  int engorhin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);
        macAddr = getMac();
        Toast.makeText(getBaseContext(),
                "Signed In",
                Toast.LENGTH_SHORT).show();
        TextView textVieww=(TextView)findViewById(R.id.textView);
        Button clickhere=(Button)findViewById(R.id.button);
        engorhin=MainActivity.engOrHin();
        if(engorhin%2==0){
            textVieww.setText("Welcome User "+ macAddr);
        }
        else{
            textVieww.setText("स्वागत है "+ macAddr);
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

        storageReference.child("image.png").getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use the bytes to display the image
                Toast.makeText(getBaseContext(),
                    "Downloaded pic",
                    Toast.LENGTH_SHORT).show();
                droneImage.setVisibility(View.INVISIBLE);
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


}
