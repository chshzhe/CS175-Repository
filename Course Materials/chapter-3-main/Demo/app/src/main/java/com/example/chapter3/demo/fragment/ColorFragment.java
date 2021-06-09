package com.example.chapter3.demo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chapter3.demo.R;

public final class ColorFragment extends Fragment {
    private static final String KEY_EXTRA_COLOR = "extra_color";

    public static ColorFragment newInstance(int color) {
        ColorFragment cf = new ColorFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_EXTRA_COLOR, color);
        cf.setArguments(args);
        return cf;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int color = Color.BLUE;
        Bundle args = getArguments();
        if (args != null) {
            color = args.getInt(KEY_EXTRA_COLOR, Color.BLUE);
        }
        View view = inflater.inflate(R.layout.fragment_color, container, false);
        view.setBackgroundColor(color);
        return view;
    }
}
