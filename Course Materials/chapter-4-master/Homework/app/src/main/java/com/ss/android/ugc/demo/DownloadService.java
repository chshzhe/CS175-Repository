package com.ss.android.ugc.demo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import java.util.logging.Logger;

public class DownloadService extends IntentService {

    public DownloadService() {
        super("DownloadService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadService(String name) {
        super(name);
    }

    @Override protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String url = intent.getStringExtra("url");
            try {
                Log.e("DownloadService", "currentThread: " +
                        Thread.currentThread().getName() + " url: " + url);
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
