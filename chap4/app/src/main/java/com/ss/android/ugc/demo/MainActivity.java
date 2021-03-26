package com.ss.android.ugc.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int MSG_START_DOWNLOAD = 0;
    private static final int MSG_DOWNLOAD_SUCCESS = 1;
    private static final int MSG_DOWNLOAD_FAIL = 2;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_START_DOWNLOAD:
                    mText.setText(R.string.start_download);
                    break;
                case MSG_DOWNLOAD_SUCCESS:
                    mText.setText(R.string.download_success);
                    break;
                case MSG_DOWNLOAD_FAIL:
                    mText.setText(R.string.download_fail);
                    break;
                default:
                    break;
            }
        }
    };

    private TextView mText;
    private TextView mClockTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO 时钟页面
        mClockTv = findViewById(R.id.clock);
        mClockTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClockActivity.class);
                startActivity(intent);
            }
        });
        mText = findViewById(R.id.text);
        //Demo 1: Handler
//        new DownloadThread("http://www.xxx.mp4").start();
        //Demo 2: AsyncTask
//        new DownloadTask(mText).execute("http://www.xxx.mp4");
        //Demo3: HandlerThread
//        new StockHandlerThread("stock").start();
        //Demo4: IntentService
        Intent intent = new Intent(MainActivity.this, DownloadService.class);
        intent.putExtra("url", "http://www.xxx.mp4");
        startService(intent);
    }

    private class DownloadThread extends Thread {
        private String videoId;

        public DownloadThread(String videoId) {
            this.videoId = videoId;
        }

        @Override
        public void run() {
            super.run();
            mHandler.sendMessage(Message.obtain(mHandler, MSG_START_DOWNLOAD));
            try {
                download(videoId);
                mHandler.sendMessage(Message.obtain(mHandler, MSG_DOWNLOAD_SUCCESS));
            } catch (Exception e) {
                mHandler.sendMessage(Message.obtain(mHandler, MSG_DOWNLOAD_FAIL));
            }
        }

        private void download(String videoId) {
            //后台下载视频....
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
