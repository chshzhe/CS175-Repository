package com.example.chapter3.demo.animation;


import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter3.demo.R;

public class PropertyActivity extends AppCompatActivity {
    private Button mTestButton1;
    private Button mTranslate;
    private Button mAnimationTranslate;
    private Button mTestButton2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.property_anim_layout);
        mTestButton1 = findViewById(R.id.button);
        mTranslate = findViewById(R.id.button1);
        mAnimationTranslate = findViewById(R.id.button2);
        mTestButton2 = findViewById(R.id.button3);

        mTestButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "点击了button1", Toast.LENGTH_LONG).show();
            }
        });

        mTestButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "点击了button2", Toast.LENGTH_LONG).show();
            }
        });

        mTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PropertyValuesHolder pvh1 = PropertyValuesHolder.ofFloat("translateX",300);
//                PropertyValuesHolder pvh2 = PropertyValuesHolder.ofFloat("translateY",300);
//                ObjectAnimator.ofPropertyValuesHolder(mTestButton1,pvh1,pvh2).setDuration(1000).start();

                ValueAnimator animator = ValueAnimator.ofFloat(0f,300f);
                animator.setDuration(1000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float animatedValue = (float) animation.getAnimatedValue();
                        mTestButton1.setTranslationX(animatedValue);
                    }
                });
                animator.start();

            }
        });
        mAnimationTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TranslateAnimation animation = new TranslateAnimation(0.0f, 300f, 0.0f, 300.0f);
                animation.setDuration(1000);
                animation.setFillAfter(true);
                animation.setFillBefore(false);
                mTestButton2.startAnimation(animation);

            }
        });

    }
}
