package com.ss.android.ugc.demo;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

class StockHandlerThread extends HandlerThread implements Handler.Callback {

    public static final int MSG_QUERY_STOCK = 100;

    private Handler mHandler;

    public StockHandlerThread(String name) {
        super(name);
    }

    @Override protected void onLooperPrepared() {
        super.onLooperPrepared();
        mHandler = new Handler(getLooper(), this);
        //首次请求
        mHandler.sendEmptyMessage(MSG_QUERY_STOCK);
    }

    @Override public boolean handleMessage(@NonNull Message msg) {
        if (msg.what == MSG_QUERY_STOCK) {
            //请求股票数据
            //..
            //回调到主线程或者写入数据库
            Log.e("StockHandlerThread", "handleMessage :" + msg.what);
            //..
            //10s后再次请求
            mHandler.sendEmptyMessageDelayed(MSG_QUERY_STOCK, 10 * 1000);
        }
        return true;
    }
}
