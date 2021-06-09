package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PersonalHomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_home);
        Intent intent = getIntent();
        String text = intent.getStringExtra("extra");
        int i;
        for(i = 13;i >= 0;i--){
            if(text.contains(Integer.toString(i)))
                break;
        }
        ImageView imageView = findViewById(R.id.iv_head);
        TextView statusTextView = findViewById(R.id.tv_addfocus);
        TextView idTextView = findViewById(R.id.IDnumber);
        TextView nickNameTextview = findViewById(R.id.tv_nickname);
        if(i!=13)
        {
            String status = "已关注此好友";
           statusTextView.setText(status);
           idTextView.setText("抖音号："+Integer.toString(i));
           nickNameTextview.setText(names[i]);

        }
        if(i == 13)
        {
            statusTextView.setVisibility(View.GONE);
        }

        int resID = imageRes[i];
        imageView.setImageResource(resID);
    }

    int[]imageRes = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image8,
            R.drawable.image9,
            R.drawable.image10,
            R.drawable.image11,
            R.drawable.image12,
            R.drawable.image13,
            R.drawable.image14,
    };
    String[]names = {
            "Alfie", "Lily", "Edward", "Ella", "Georgia", "Juliet", "Archer", "Brooks", "Carter", "Fletcher", "Graham", "Huxley",
            "Mason", "Reed", "Sawyer", "Wilder"
    };
}
