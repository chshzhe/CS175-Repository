package com.domker.study.androidstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.List;

public class GlideActivity extends AppCompatActivity {
    ViewPager pager = null;
    LayoutInflater layoutInflater = null;
    List<View> pages = new ArrayList<View>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        layoutInflater = getLayoutInflater();
        pager = (ViewPager) findViewById(R.id.view_pager);
        addImage(R.drawable.drawableimage);
        addImage(R.drawable.ic_markunread);
        addImage("/sdcard/fileimage.jpg");
        addImage("file:///android_asset/assetsimage.jpg");
        addImage(R.raw.rawimage);
        addImage("https://pic2.zhimg.com/v2-7b6e010dfb9343f803cf9c11f3163055_r.jpg");
        ViewAdapter adapter = new ViewAdapter();
        adapter.setDatas(pages);
        pager.setAdapter(adapter);
    }

    private void addImage(int resId) {
        //ImageView imageView = (ImageView) layoutInflater.inflate(R.layout.activity_image_item, null);
        ZoomImageView imageView = (ZoomImageView) layoutInflater.inflate(R.layout.activity_image_item, null);
        Glide.with(this)
            .load(resId)
            .error(R.drawable.error)
            .into(imageView);
        pages.add(imageView);
    }

    private void addImage(String path) {
        //ImageView imageView = (ImageView) layoutInflater.inflate(R.layout.activity_image_item, null);
        ZoomImageView imageView = (ZoomImageView) layoutInflater.inflate(R.layout.activity_image_item, null);
        Glide.with(this)
            .load(path)
            .apply(new RequestOptions().circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
            .error(R.drawable.error)
            //.transition(withCrossFade(4000))
            //.override(100, 100)
            .into(imageView);
        pages.add(imageView);
    }
}
