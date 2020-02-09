package com.example.gallery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView mImageView;
    static int REQUEST_GALLERY = 1;
    static int REQUEST_CODE = 1;
    Uri pickImge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.gallery);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestPermission();
                } else {
                    openGallery();
                }
            }


        });

    }

    private void openGallery() {
         Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
         startActivityForResult(intent,REQUEST_CODE);
    }

    private void checkAndRequestPermission() {
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
             if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                 Toast.makeText(MainActivity.this,"Required permission for gallery",Toast.LENGTH_SHORT).show();
             } else {
                 ActivityCompat.requestPermissions(MainActivity.this,
                         new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_GALLERY);
             }
        } else {
            openGallery();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_GALLERY && data != null) {
            pickImge = data.getData();
            mImageView.setImageURI(pickImge);
        }
    }
}
