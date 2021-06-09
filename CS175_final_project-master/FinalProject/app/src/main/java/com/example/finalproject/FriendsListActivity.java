package com.example.finalproject;

import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
//import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class FriendsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        SearchAdapter searchAdapter = new SearchAdapter();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(searchAdapter);

        List<String> allEntries = new ArrayList<>();
        for(int i=0;i<14;i++)
            allEntries.add(names[i]+"    （抖音号："+String.valueOf(i)+"）");
        searchAdapter.notifyItems(allEntries);

        EditText editText = findViewById(R.id.edittext);
        editText.setBackgroundColor(0x0f000000+(int)(Math.random()*(0xffffff)));
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                editText.setText("");
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                if(string.isEmpty())
                    button.setVisibility(View.GONE);
                else
                    button.setVisibility(View.VISIBLE);
                List<String> searchResults = new ArrayList<>();
                for(int i = 0;i < 14; i++)
                    if(allEntries.get(i).contains(string))
                        searchResults.add(allEntries.get(i));
                searchAdapter.notifyItems(searchResults);
            }
        });



    }
    class TextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTextView;
        private ImageView mImageView;
        private int[]imageRes = {
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
        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text);
            mImageView = itemView.findViewById(R.id.listImageView);
            itemView.setOnClickListener(this);
            itemView.setBackgroundColor( 0x1f000000+(int)(Math.random()*(0xffffff)));
        }

        public void bind(String text){
            mTextView.setText(text);
            int number = 0;
            for(int i = 14; i > 0; i--)
            {
                if(text.contains(Integer.toString(i))) {
                    number = i;
                    break;
                }
            }
            mImageView.setImageResource(imageRes[number]);
        }
        @Override
        public void onClick(View v) {
            itemView.setBackgroundColor( 0x3fff0000+(int)(Math.random()*(0xffff)));
            Intent intent = new Intent(v.getContext(), PersonalHomeActivity.class);
            intent.putExtra("extra",mTextView.getText().toString());
            v.getContext().startActivity(intent);
        }
    }

    class SearchAdapter extends RecyclerView.Adapter<TextViewHolder>{

        @NonNull
        private List<String> mItems = new ArrayList<>();

        @NonNull
        @Override
        public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
            holder.bind(mItems.get(position));
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public void notifyItems(@NonNull List<String> items){
            mItems.clear();
            mItems.addAll(items);
            notifyDataSetChanged();
        }
    }
    String[]names = {
            "Alfie", "Lily", "Edward", "Ella", "Georgia", "Juliet", "Archer", "Brooks", "Carter", "Fletcher", "Graham", "Huxley",
            "Mason", "Reed", "Sawyer", "Wilder"
    };
}
