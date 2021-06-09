package com.bytedance.network.socket.tcp;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;


public class ServerTransportThread extends Thread {
    Socket socket;
    public ServerTransportThread(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        try {
            Log.d("socket","服务socket start ");
            BufferedInputStream is = new BufferedInputStream(socket.getInputStream()); // 读进
            byte[] data = new byte[1024*5];// 每次读取的字节数
            int len= -1;
            while ((len=is.read(data) )!= -1) {
                String receive = new String(data,0,len);
                Log.d("socket","服务socket收到数据 "+ receive);
                socket.getOutputStream().write(("收到数据长度 "+receive.length()).getBytes());
            }
            Log.d("socket","关闭服务socket");
            is.close();
            socket.close(); //关闭socket
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}