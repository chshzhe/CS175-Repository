package com.example.chapter3.demo.animation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.chapter3.demo.R;


public class AnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mAlphaBtn;
    private Button mTranslateBtn;
    private Button mScaleBtn;
    private Button mRotateBtn;
    private Button mAnimationSet;
    private Button mPropertyAnimation;
    private ImageView mImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annimation);
        mAlphaBtn = findViewById(R.id.btn_alpha);
        mTranslateBtn = findViewById(R.id.btn_translate);
        mScaleBtn = findViewById(R.id.btn_scale);
        mRotateBtn = findViewById(R.id.btn_rotate);
        mAnimationSet = findViewById(R.id.btn_animation_set);
        mPropertyAnimation = findViewById(R.id.btn_property);
        mImage = findViewById(R.id.btn_image);
        mAlphaBtn.setOnClickListener(this);
        mTranslateBtn.setOnClickListener(this);
        mScaleBtn.setOnClickListener(this);
        mRotateBtn.setOnClickListener(this);
        mAnimationSet.setOnClickListener(this);
        mPropertyAnimation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Animation loadAnimation;
        switch (v.getId()) {
            case R.id.btn_alpha:
                loadAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha);
                mImage.startAnimation(loadAnimation);
                break;
            case R.id.btn_translate:
                loadAnimation = AnimationUtils.loadAnimation(this, R.anim.translate);
                mImage.startAnimation(loadAnimation);
                break;
            case R.id.btn_scale:
                loadAnimation = AnimationUtils.loadAnimation(this, R.anim.scale);
                mImage.startAnimation(loadAnimation);
                break;
            case R.id.btn_rotate:
                loadAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
                mImage.startAnimation(loadAnimation);
                break;
            case R.id.btn_animation_set:
                loadAnimation = AnimationUtils.loadAnimation(this, R.anim.continue_anim);
                mImage.startAnimation(loadAnimation);
                break;
            case R.id.btn_property:
                startActivity(new Intent(this, PropertyActivity.class));
                break;
            default:
                break;
        }
    }
}
