package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.widget.TextView;

//测试用
//import java.text.SimpleDateFormat;
//import java.util.Date;

public class FriendsDisplayActivity extends AppCompatActivity {
    Handler handler;
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Intent intent = getIntent();

        String text = intent.getStringExtra("extra");
        int i = 14;
        for(;i>0;i--)
        {
            if(text.contains(Integer.toString(i)))
                break;
        }
        TextView textView = findViewById(R.id.textview);
       final String  text2 = "这是你的好友ID为："+Integer.toString(i)+"的个人页面";
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                String htmlText = "";
                int step = 0xFFFFFF / text2.length();
                int start = (int)(Math.random()*(0xFFFFFF));
                for(int i =0 ;i<text2.length();i++)
                {
                    int rgb = (start + step*i)%0xFFFFFF;
                    String st = Integer.toHexString(rgb).toUpperCase();
                    st = String.format("%6s",st);
                    st= st.replaceAll(" ","0");

                    htmlText+="<span style=\"color:#"+st+"\">"+text2.charAt(i)+"</span>";
                }

                textView.setText(Html.fromHtml(htmlText));



                handler.postDelayed(this,200);
            }
        };
        handler.postDelayed(runnable,100);
    }
    public void onBackPressed() {
        NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
        handler.removeCallbacks(runnable);
        finish();
    }
}