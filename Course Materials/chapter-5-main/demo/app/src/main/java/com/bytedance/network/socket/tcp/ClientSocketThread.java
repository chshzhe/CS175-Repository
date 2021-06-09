package com.bytedance.network.socket.tcp;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.DatagramSocket;
import java.net.Socket;

public class ClientSocketThread extends Thread {
//    String content = "HEAD /xxjj/index.html HTTP/1.1\r\nHost:www.sjtu.edu.cn\r\n\r\n";
    public ClientSocketThread(Activity activity) {
        this.activity = activity;
    }

    private Activity activity;
    private boolean stopFlag = false;
    private volatile String message = "";

    public synchronized void sendMsg(String msg) {
        this.message = msg;
    }

    public void disconnect() {
        stopFlag = true;
    }

    private synchronized void clearMsg() {
        this.message = "";
    }

    @Override
    public void run() {
        Log.d("socket", "客户端线程start ");
        try {
//            Socket socket = new Socket("www.sjtu.edu.cn", 80); //服务器IP及端口
            Socket socket = new Socket("localhost", 30000); //服务器IP及端口

            BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());
            BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
            // 读文件
            double n = 1;
            byte[] data = new byte[1024 * 5];//每次读取的字节数
            int len = -1;
            while (!stopFlag && socket.isConnected()) {
                if (!message.isEmpty()) {
                    Log.d("socket", "客户端发送 " + message);
                    os.write(message.getBytes());
                    os.flush();
                    clearMsg();
                    int reciveLen = is.read(data);
                    if (reciveLen!=-1){
                        String receive = new String(data, 0, reciveLen);
                        Log.d("socket", "客户端收到 " + receive);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, receive, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        Log.d("socket", "客户端收到-1");
                    }

                }
                sleep(300);
            }
            Log.d("socket", "客户端断开 ");
            os.flush();
            os.close();
            socket.close(); //关闭socket

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}