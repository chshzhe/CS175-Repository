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
        addImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562328963756&di=9c0c6c839381c8314a3ce8e7db61deb2&imgtype=0&src=http%3A%2F%2Fpic13.nipic.com%2F20110316%2F5961966_124313527122_2.jpg");
        ViewAdapter adapter = new ViewAdapter();
        adapter.setDatas(pages);
        pager.setAdapter(adapter);
    }

    private void addImage(int resId) {
        ImageView imageView = (ImageView) layoutInflater.inflate(R.layout.activity_image_item, null);
        Glide.with(this)
            .load(resId)
            .error(R.drawable.error)
            .into(imageView);
        pages.add(imageView);
    }

    private void addImage(String path) {
        ImageView imageView = (ImageView) layoutInflater.inflate(R.layout.activity_image_item, null);
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
