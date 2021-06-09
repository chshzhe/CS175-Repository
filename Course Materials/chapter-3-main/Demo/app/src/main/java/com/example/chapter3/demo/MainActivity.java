package com.example.chapter3.demo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.example.chapter3.demo.animation.AnimationActivity;
import com.example.chapter3.demo.animation.FrameAnimationActivity;
import com.example.chapter3.demo.animation.LottieActivity;
import com.example.chapter3.demo.animation.MarqueeTextViewActivity;
import com.example.chapter3.demo.animation.RotationPropertyActivity;
import com.example.chapter3.demo.animation.ScalePropertyActivity;
import com.example.chapter3.demo.animation.TransitionActivity;
import com.example.chapter3.demo.fragment.DynamicAddFragmentActivity;
import com.example.chapter3.demo.fragment.LifecycleFragmentActivity;
import com.example.chapter3.demo.fragment.StaticColorActivity;
import com.example.chapter3.demo.fragment.ViewPagerActivity;
import com.example.chapter3.demo.fragment.ViewPagerCommunicationActivity;
import com.example.chapter3.demo.fragment.ViewPagerWithTabActivity;
import com.example.chapter3.demo.fragment.masterdetail.ItemsListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindActivity(R.id.btn_marquee_demo, MarqueeTextViewActivity.class);
        bindActivity(R.id.btn_frame_animation, FrameAnimationActivity.class);
        bindActivity(R.id.btn_rotation_demo, RotationPropertyActivity.class);
        bindActivity(R.id.btn_scale_demo, ScalePropertyActivity.class);
        bindActivity(R.id.btn_transition_demo, TransitionActivity.class);
        bindActivity(R.id.btn_show_animation, AnimationActivity.class);
        bindActivity(R.id.btn_lottie_demo, LottieActivity.class);
        bindActivity(R.id.btn_fragment_lifecycle_demo, LifecycleFragmentActivity.class);
        bindActivity(R.id.btn_fragment_add_demo, DynamicAddFragmentActivity.class);
        bindActivity(R.id.btn_view_pager, ViewPagerActivity.class);
        bindActivity(R.id.btn_view_pager_with_tab, ViewPagerWithTabActivity.class);
        bindActivity(R.id.btn_fragment_arguments, StaticColorActivity.class);
        bindActivity(R.id.btn_fragment_communication, ViewPagerCommunicationActivity.class);
        bindActivity(R.id.btn_master_detail, ItemsListActivity.class);
    }

    private void bindActivity(final int btnId, final Class<?> activityClass) {
        findViewById(btnId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, activityClass));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}
