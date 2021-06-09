package com.example.chapter3.demo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chapter3.demo.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerCommunicationActivity extends AppCompatActivity
        implements ColorPlusFragment.Listener {
    private ViewPager mViewPager;
    private RecyclerView mCollectView;
    private CollectColorAdapter mCollectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_communication);
        mViewPager = findViewById(R.id.view_pager);
        mCollectView = findViewById(R.id.collect_view);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            private final static int TOTAL = 10;

            @Override
            public Fragment getItem(int i) {
                return ColorPlusFragment.newInstance(Color.HSVToColor(new float[]{i * 360.f / TOTAL, 1.0f, 1.0f}));
            }

            @Override
            public int getCount() {
                return TOTAL;
            }
        });

        mCollectAdapter = new CollectColorAdapter();
        mCollectView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mCollectView.setAdapter(mCollectAdapter);
    }

    @Override
    public void onCollectColor(int color) {
        mCollectAdapter.addColor(color);
    }

    private static class ColorViewHolder extends RecyclerView.ViewHolder {
        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static class CollectColorAdapter extends RecyclerView.Adapter<ColorViewHolder> {

        private List<Integer> mColorList = new ArrayList<>();

        @NonNull
        @Override
        public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ColorViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_color, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ColorViewHolder colorViewHolder, int i) {
            int color = mColorList.get(i);
            colorViewHolder.itemView.setBackgroundColor(color);
        }

        @Override
        public int getItemCount() {
            return mColorList.size();
        }

        public void addColor(int color) {
            mColorList.add(color);
            notifyDataSetChanged();
        }
    }
}
