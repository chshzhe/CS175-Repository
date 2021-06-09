package com.example.finalproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView home,friends,messages,me,currentView;
    private FeedAdapter adapter = new FeedAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        home=findViewById(R.id.home);
        clicked(home);
        friends=findViewById(R.id.friends);
        released(friends);
        messages=findViewById(R.id.messages);
        released(messages);
        me=findViewById(R.id.me);
        released(me);
        ImageButton add=findViewById(R.id.btn_add);
        add.setOnClickListener(this);
        home.setOnClickListener(this);
        friends.setOnClickListener(this);
        messages.setOnClickListener(this);
        me.setOnClickListener(this);
        currentView=home;

        Fresco.initialize(MainActivity.this);//
        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        getData(null);



    }
    @Override
    public void onClick(View v) {
        released(currentView);
        switch (v.getId()){
            case R.id.home:
                currentView=home;
                clicked(currentView);
                Fresco.initialize(MainActivity.this);//
                RecyclerView recyclerView = findViewById(R.id.rv_list);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
                getData(null);
                break;
            case R.id.friends:
                currentView=friends;
                clicked(currentView);
                Intent intent1 = new Intent(this, FriendsListActivity.class);
                startActivity(intent1);
                break;
            case R.id.messages:
                currentView=messages;
                clicked(currentView);
                Intent intent = new Intent(this, MessageActivity.class);
                startActivity(intent);
                break;
            case R.id.me:
                currentView=me;
                clicked(currentView);

                Intent intent2 = new Intent(this, PersonalHomeActivity.class);
                intent2.putExtra("extra",Integer.toString(13));
                v.getContext().startActivity(intent2);

                break;
            case R.id.btn_add:
                Intent intent3=new Intent(this,UploadActivity.class);
                startActivity(intent3);
        }
    }

    public void clicked(TextView textView){

        textView.setTextColor(Color.WHITE);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }
    public void released(TextView textView){

        textView.setTextColor(Color.GRAY);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
    }

    private void getData(String studentId) {
        Log.d("TEST", "getData");
        new Thread(new Runnable() {
            @Override
            public void run() {
                MessageListResponse mess = baseGetResponseFromRemote(
                        "video", "application/vnd+json");
                if (mess != null && (mess.success)) {
                    new Handler(getMainLooper()).post(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void run() {
                            adapter.setData(mess.feeds);
                        }
                    });
                    Log.d("TEST", mess.feeds.toString());
                }

            }
        }).start();

    }
    public MessageListResponse baseGetResponseFromRemote(String method, String accept) {
        String urlStr = Constants.BASE_URL + method;
        MessageListResponse result = null;
        try{
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(6000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept",accept);
            Log.d("TEST", String.valueOf(conn.getResponseCode()));
            Log.d("TEST", "test");
            if (conn.getResponseCode() == 200) {
                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                result = new Gson().fromJson(reader, new TypeToken<MessageListResponse>() {
                }.getType());
                Log.d("TEST", "200 OK");
                reader.close();
                in.close();
            } else {
                int error = conn.getResponseCode();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "网络异常" + error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TEST", "Failure");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "网络异常" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        return result;
    }
}