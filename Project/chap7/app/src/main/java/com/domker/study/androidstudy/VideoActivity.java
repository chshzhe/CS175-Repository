package com.domker.study.androidstudy;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {
    private Button buttonPlay;
    private Button buttonPause;
    private Button buttonResume;
    private VideoView videoView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_video);

        buttonPause = findViewById(R.id.buttonPause);
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.pause();
            }
        });

        buttonPlay = findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.start();
            }
        });

        buttonPlay = findViewById(R.id.buttonResume);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.start();
                videoView.resume();
            }
        });
        MediaController mMediaController = new MediaController(this);
        videoView = findViewById(R.id.videoView);
        videoView.getHolder().setFormat(PixelFormat.TRANSPARENT);
        videoView.setZOrderOnTop(true);
        videoView.setVideoPath(getVideoPath(R.raw.big_buck_bunny));
        videoView.setMediaController(mMediaController);

    }
    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }
}
