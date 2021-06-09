package com.example.demo.search;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    private RecyclerView mRecyclerView;
    private SearchAdapter mSearchAdapter = new SearchAdapter();

    private SearchLayout mSearchLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mRecyclerView = findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mSearchAdapter);

        final List<String> items = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            items.add("这是第 " + i + " 行");
        }
        mSearchAdapter.notifyItems(items);

        mSearchLayout = findViewById(R.id.search);
        mSearchLayout.setOnSearchTextChangedListener(new SearchLayout.OnSearchTextChangedListener() {
            @Override
            public void afterChanged(String text) {
                Log.i(TAG, "afterChanged: " + text);
                List<String> filters = new ArrayList<>();
                for (String item : items) {
                    if (item.contains(text)) {
                        filters.add(item);
                    }
                }
                mSearchAdapter.notifyItems(filters);
            }
        });

    }
}