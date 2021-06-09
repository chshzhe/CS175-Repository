package com.bytedance.network.socket.tcp;

import android.app.Activity;
import android.widget.Toast;

import com.bytedance.network.socket.SocketTestActivity;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerListener extends Thread {
    private List<Socket> sockets = new ArrayList<>();
    private Activity activity;
    public ServerListener(Activity activity){
        this.activity = activity;
    }
    public void stopServer(){
        for (Socket s : sockets){
            if (!s.isClosed()) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(30000);
            // 循环的监听
            while (true) {
                Socket socket = serverSocket.accept();// 阻塞
                activity.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        Toast.makeText(activity,"有客户端连接到本机的30000端口！",Toast.LENGTH_SHORT).show();
                    }
                });
                // 将socket传给新的线程
                ServerTransportThread ts = new ServerTransportThread(socket);
                ts.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}