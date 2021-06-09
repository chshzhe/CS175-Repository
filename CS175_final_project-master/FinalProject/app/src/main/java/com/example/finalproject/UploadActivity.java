package com.example.finalproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener{
    private final static int PERMISSION_REQUEST_CODE = 1001;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_record);
        Button record,upload;
        record=findViewById(R.id.record_btn);
        upload=findViewById(R.id.upload_btn);
        record.setOnClickListener(UploadActivity.this);
        upload.setOnClickListener(UploadActivity.this);

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.record_btn:
                // 调用摄像头录像
                Intent intent=new Intent(this,SystemRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.upload_btn:
                //To do 发布视频
                Intent intent1=new Intent(this,RealUploadActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }

    }

    public void systemRecord(View view) {
        SystemRecordActivity.startUI(this);
    }

    public void customCamera(View view) {
        requestPermission();
    }

    private void recordVideo() {
        CustomCameraActivity.startUI(this);
    }

    private void requestPermission() {
        boolean hasCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean hasAudioPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
        if (hasCameraPermission && hasAudioPermission) {
            recordVideo();
        } else {
            List<String> permission = new ArrayList<String>();
            if (!hasCameraPermission) {
                permission.add(Manifest.permission.CAMERA);
            }
            if (!hasAudioPermission) {
                permission.add(Manifest.permission.RECORD_AUDIO);
            }
            ActivityCompat.requestPermissions(this, permission.toArray(new String[permission.size()]), PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermission = true;
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                hasPermission = false;
                break;
            }
        }
        if (hasPermission) {
            recordVideo();
        } else {
            Toast.makeText(this, "权限获取失败", Toast.LENGTH_SHORT).show();
        }
    }

}
