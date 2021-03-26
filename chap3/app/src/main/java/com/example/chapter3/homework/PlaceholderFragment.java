package com.example.chapter3.homework;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlaceholderFragment extends Fragment {
    private LottieAnimationView animationView;
    private AnimatorSet animatorSet;
    private List<People> peopleList = new ArrayList<People>();
    private View square;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        View square = inflater.inflate(R.layout.fragment_placeholder, container, false);

        initpeople();
        RecyclerView recyclerView = square.findViewById(R.id.recycler_view);
        Log.e("TEST", (recyclerView == null) + "");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        PeopleAdapter adapter = new PeopleAdapter(peopleList);
        recyclerView.setAdapter(adapter);
        return square;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        animationView = getView().findViewById(R.id.animationView);
        final RecyclerView recyclerView = getView().findViewById(R.id.recycler_view);
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(animationView, "alpha", 1, 0);
                animator1.setDuration(1000);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(recyclerView, "alpha", 0, 1);
                animator2.setDuration(1000);
                animatorSet = new AnimatorSet();
                animatorSet.playTogether(animator1, animator2);
                animatorSet.start();
            }
        }, 5000);
    }

    private void initpeople() {
        String[] str = {"十秩", "烧心", "Topological", "Chrimwriu", "麓山道", "南山月初瑕", "飞得更高", "和森。"
                , "夹", "Yuno", "sjtuzjl", "三山", "NO.11", "Negroni"};
        for (int i = 0; i < 14; i++) {
            String name = "mini_pic" + (i + 1);
            int imgid = getResources().getIdentifier(name, "drawable", "com.example.chapter3.homework");
            People temp = new People(getRandomLengthName(str[i]), imgid);
            peopleList.add(temp);
        }
    }


    private String getRandomLengthName(String name) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1; i++) {
            builder.append(name);
        }
        return builder.toString();
    }
}
