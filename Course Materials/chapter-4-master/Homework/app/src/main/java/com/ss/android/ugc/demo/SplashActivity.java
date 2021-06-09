package com.ss.android.ugc.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView skipBtn = findViewById(R.id.skip);
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override public void run() {
                //跳转到首页
                jumpToMainActivity();
            }
        };
        handler.postDelayed(runnable, 3000);
        skipBtn.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View v) {
                handler.removeCallbacks(runnable);
                jumpToMainActivity();
            }
        });
    }

    private void jumpToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
