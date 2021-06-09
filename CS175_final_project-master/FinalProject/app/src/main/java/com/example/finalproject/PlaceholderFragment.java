package com.example.finalproject;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.*;

public class PlaceholderFragment extends Fragment {

    private LottieAnimationView animationView;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_placeholder, container, false);
        FrameLayout frameLayout = fragmentView.findViewById(R.id.frame);

        animationView = new LottieAnimationView(getContext());
        animationView.setAnimation(R.raw.material_wave_loading);
        animationView.setRepeatCount(ValueAnimator.INFINITE);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        animationView.setLayoutParams(layoutParams);
        frameLayout.addView(animationView);
        animationView.playAnimation();

        recyclerView = new RecyclerView(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        MyAdapter myAdapter = new MyAdapter();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        List<String> allEntries = new ArrayList<>();
        for(int i = 0;i < 14; i++)
            allEntries.add("你成功添加"+names[i]+"(抖音号："+String.valueOf(i)+")为好友，现在可以一起聊天了");
        myAdapter.notifyItems(allEntries);
        layoutParams.width=LayoutParams.MATCH_PARENT;
        layoutParams.height=LayoutParams.MATCH_PARENT;
        recyclerView.setLayoutParams(layoutParams);
        recyclerView.setAlpha(0);
        frameLayout.addView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                int mShortAnimationDuration = getResources().getInteger(
                        android.R.integer.config_shortAnimTime);
                recyclerView.animate()
                        .alpha(1f)
                        .setDuration(mShortAnimationDuration)
                        .setListener(null);
                animationView.animate()
                        .alpha(0f)
                        .setDuration(mShortAnimationDuration)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                animationView.setVisibility(View.GONE);
                            }
                        });
            }
        }, 5000);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private ImageView mImageView;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.textView);
            mImageView = itemView.findViewById(R.id.imageView);

            itemView.setBackgroundColor( 0x1f000000+(int)(Math.random()*(0xffffff)));
        }

        public void bind(String text,int res){
            mTextView.setText(text);
            mImageView.setImageResource(res);
            }

    }

    class MyAdapter extends RecyclerView.Adapter<ItemViewHolder>{

        @NonNull
        private List<String> mItems = new ArrayList<>();
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
                R.drawable.image14
        };

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_holder,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            holder.bind(mItems.get(position),imageRes[position]);
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
