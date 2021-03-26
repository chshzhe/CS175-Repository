package com.example.chapter2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        final SimpleAdapter adapter = new SimpleAdapter();
        recyclerView.setAdapter(adapter);
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; ++i) {
            list.add(String.valueOf(i));

        }
        adapter.notifyItems(list);
        editText = findViewById(R.id.edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                button.setVisibility(s.toString().isEmpty() ? View.GONE : View.VISIBLE);

                List<String> temp = new ArrayList<>();
                for (int i = 0; i < list.size(); ++i) {
                    if (list.get(i).contains(s))
                        temp.add(list.get(i));
                }
                adapter.notifyItems(temp);

            }
        });

    }

    class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.TextViewHolder> {
        List<String> mItems = new ArrayList<>();

        public void notifyItems(List<String> items) {
            mItems.clear();
            mItems.addAll(items);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_0, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
            holder.bind(mItems.get(position));
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        class TextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView textView;

            public TextViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.textview);
                textView.setOnClickListener(this);
            }

            public void bind(String s) {
                textView.setText("这是第" + s + "行！");
            }

            @Override
            public void onClick(View v) {
                //.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), SecondActivity.class);
                intent.putExtra("text", textView.getText());
                v.getContext().startActivity(intent);
            }
        }
    }

}

/*
import colorsys
def float2hexbyte(num):
    num=int(num*255)
    assert 0<=num<=255
    return '{:02X}'.format(num)
def get_color_hex(h,s,v):
    return '#'+''.join([float2hexbyte(i) for i in colorsys.hsv_to_rgb(h, s, v)])
def colored_text(word,hue_val):
    return f'[color="{get_color_hex(hue_val,1,0.8)}"]{word}[/color]'
def to_rainbow(text):
    return ''.join([colored_text(t,i/len(text)*0.8) for i,t in enumerate(text)])

print(to_rainbow('看我的彩虹体啊，多好看多好看！喜欢吗？'))
 */