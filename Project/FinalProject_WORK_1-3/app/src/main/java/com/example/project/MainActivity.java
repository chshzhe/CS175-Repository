package com.example.project;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import com.example.project.model.Message;
import com.example.project.model.MessageListResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

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
                break;
            case R.id.friends:
                currentView=friends;
                clicked(currentView);
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
                break;
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


    //TODO 界面跳转，传递当前videoURL给MediaPlayerActivity里的TODO部分的videoURL
//    class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.TextViewHolder> {
//        List<String> mItems = new ArrayList<>();
//
//        public void notifyItems(List<String> items) {
//            mItems.clear();
//            mItems.addAll(items);
//            notifyDataSetChanged();
//        }
//
//        @NonNull
//        @Override
//        public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            return new TextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_0, parent, false));
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
//            holder.bind(mItems.get(position));
//        }
//
//        @Override
//        public int getItemCount() {
//            return mItems.size();
//        }
//
//        class TextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//            TextView textView;
//
//            public TextViewHolder(@NonNull View itemView) {
//                super(itemView);
//                textView = itemView.findViewById(R.id.textview);
//                textView.setOnClickListener(this);
//            }
//
//            public void bind(String s) {
//                textView.setText("这是第" + s + "行！");
//            }
//
//            @Override
//            public void onClick(View v) {
//                //.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(v.getContext(), MediaPlayerActivity.class);
//                intent.putExtra("text", textView.getText());
//                v.getContext().startActivity(intent);
//            }
//        }
//    }


}