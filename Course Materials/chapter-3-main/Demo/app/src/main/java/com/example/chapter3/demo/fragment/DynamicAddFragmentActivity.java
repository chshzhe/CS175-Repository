package com.example.chapter3.demo.fragment;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter3.demo.R;

public class DynamicAddFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_add_fragment);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new HelloFragment())
                .commit();
    }
}
