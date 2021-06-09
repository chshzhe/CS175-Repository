package com.example.chapter3.demo.animation;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter3.demo.R;

public class MarqueeTextViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marquee_text_view);
        setTitle(R.string.marquee_text_view_demo);
        findViewById(R.id.text_view).setSelected(true);
    }
}
