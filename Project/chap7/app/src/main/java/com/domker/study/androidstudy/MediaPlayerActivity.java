package com.domker.study.androidstudy;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MediaPlayerActivity extends AppCompatActivity {
    private SurfaceView surfaceView;
    private MediaPlayer player;
    private SurfaceHolder holder;

    private View controllerView;
    private PopupWindow popupWindow;

    private ImageView imageView_play;
    private ImageView imageView_fullscreen;
    private SeekBar seekBar;
    private TextView textView_playTime;
    private TextView textView_duration;

    private float densityRatio = 1.0f;

    private int videoheight = 400;
    private static final int HIDDEN_TIME = 5000;

    private Timer timer = null;
    private final static int WHAT = 0;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case WHAT:
                    if (player != null) {
                        int currentPlayer = player.getCurrentPosition();
                        if (currentPlayer > 0) {
                            player.getCurrentPosition();
                            textView_playTime.setText(formatTime(currentPlayer));

                            int progress = (int) ((currentPlayer / (float) player.getDuration()) * 100);

                            seekBar.setProgress(progress);
                        } else {
                            textView_playTime.setText("00:00");
                            seekBar.setProgress(0);
                        }
                    }

                    break;
                default:
                    break;
            }
        };
    };

    private String formatTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        return formatter.format(new Date(time));
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            showOrHiddenController();
        }
    };

    private void showOrHiddenController() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            int controllerHeightPixel = (int) (densityRatio * 50);
            popupWindow.showAsDropDown(surfaceView, 0, -controllerHeightPixel);
            handler.postDelayed(r, HIDDEN_TIME);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("MediaPlayer");
        setContentView(R.layout.layout_media_player);
        surfaceView = findViewById(R.id.surfaceView);




        player = new MediaPlayer();
        try {
            player.setDataSource(getResources().openRawResourceFd(R.raw.big_buck_bunny));
            holder = surfaceView.getHolder();
            holder.setFormat(PixelFormat.TRANSPARENT);
            holder.addCallback(new PlayerCallBack());
            player.prepare();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.start();
                    player.setLooping(true);
                }
            });
            player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    System.out.println(percent);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        findViewById(R.id.buttonPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.start();
            }
        });

        findViewById(R.id.buttonPause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.pause();
            }
        });

        findViewById(R.id.buttonResume).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.seekTo(0);
                player.start();
            }
        });

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                textView_duration.setText(formatTime(player.getDuration()));
                timer = new Timer();
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        handler.sendEmptyMessage(WHAT);
                    }
                }, 0, 1000);
            }
        });

        surfaceView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        showOrHiddenController();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        initController();
        videoheight = surfaceView.getLayoutParams().height;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.stop();
            player.release();
        }
    }

    private class PlayerCallBack implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            player.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }

    private void initController() {

        controllerView = getLayoutInflater().inflate(
                R.layout.popupwindow, null);

        // 初始化popopWindow
        popupWindow = new PopupWindow(controllerView,
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);

        imageView_play = (ImageView) controllerView
                .findViewById(R.id.imageView_play);
        imageView_fullscreen = (ImageView) controllerView
                .findViewById(R.id.imageView_fullscreen);

        seekBar = (SeekBar) controllerView.findViewById(R.id.seekbar);

        textView_playTime = (TextView) controllerView
                .findViewById(R.id.textView_playtime);
        textView_duration = (TextView) controllerView
                .findViewById(R.id.textView_duration);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                handler.postDelayed(r, HIDDEN_TIME);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacks(r);
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (fromUser) {
                    int playtime = progress * player.getDuration() / 100;
                    player.seekTo(playtime);
                }

            }
        });
        imageView_play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (player.isPlaying()) {
                    player.pause();
                    imageView_play.setImageResource(R.drawable.video_btn_on);
                } else {
                    player.start();
                    imageView_play.setImageResource(R.drawable.video_btn_off);

                }

            }
        });
        imageView_fullscreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MediaPlayerActivity.this, "点击全屏", Toast.LENGTH_SHORT);
                Log.d("this",String.valueOf(getRequestedOrientation()));
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    MediaPlayerActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    imageView_fullscreen
                            .setImageResource(R.drawable.video_full_screen);

                    findViewById(R.id.buttonPlay).setVisibility(View.VISIBLE);
                    findViewById(R.id.buttonPause).setVisibility(View.VISIBLE);
                    findViewById(R.id.buttonResume).setVisibility(View.VISIBLE);
                    surfaceView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                    surfaceView.getLayoutParams().height = videoheight;

                } else if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT|| getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
                    MediaPlayerActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    imageView_fullscreen
                            .setImageResource(R.drawable.video_inner_screen);
                    findViewById(R.id.buttonPlay).setVisibility(View.GONE);
                    findViewById(R.id.buttonPause).setVisibility(View.GONE);
                    findViewById(R.id.buttonResume).setVisibility(View.GONE);
                    surfaceView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                    surfaceView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                }
                surfaceView.setLayoutParams(surfaceView.getLayoutParams());


            }
        });
    }
}

