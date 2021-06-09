package com.example.chapter3.demo.animation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.example.chapter3.demo.R;

import java.util.Random;

public class TransitionActivity extends AppCompatActivity {

    private static final String EXTRA_EXIT_ANIM = "extra_exit_anim";

    private int exitAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        exitAnim = getIntent().getIntExtra(EXTRA_EXIT_ANIM, 0);
        bindTransition(R.id.btn_slide_vertical, R.anim.slide_up, R.anim.slide_down);
        bindTransition(R.id.btn_slide_horizontal, R.anim.slide_right, R.anim.slide_left);
        bindTransition(R.id.btn_fade, R.anim.fade_in, R.anim.fade_out);

        Random random = new Random();
        getWindow().getDecorView().setBackgroundColor(Color.argb(255,
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)));
    }

    @Override
    public void finish() {
        super.finish();
        if (exitAnim != 0) {
            overridePendingTransition(0, exitAnim);
        }
    }

    private void bindTransition(final int btnId, final int enterAnim, final int exitAnim) {
        findViewById(btnId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransitionActivity.this, TransitionActivity.class);
                intent.putExtra(EXTRA_EXIT_ANIM, exitAnim);
                startActivity(intent);
                overridePendingTransition(enterAnim, 0);
            }
        });
    }
}
