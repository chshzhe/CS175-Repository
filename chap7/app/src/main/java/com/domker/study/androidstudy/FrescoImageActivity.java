package com.domker.study.androidstudy;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FrescoImageActivity extends AppCompatActivity {
    ViewPager pager = null;
    LayoutInflater layoutInflater = null;
    List<View> pages = new ArrayList<View>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_image);
        layoutInflater = getLayoutInflater();
        pager = (ViewPager) findViewById(R.id.view_pager);
        addImage("res:/" + R.drawable.drawableimage);

        Uri sdcardUri = Uri.fromFile(new File("/sdcard/fileimage.jpg"));// For files on device
        addImage(sdcardUri);

        addImage("asset:/assetsimage.jpg");

        addRawImage(R.raw.rawimage);
        addImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562328963756&di=9c0c6c839381c8314a3ce8e7db61deb2&imgtype=0&src=http%3A%2F%2Fpic13.nipic.com%2F20110316%2F5961966_124313527122_2.jpg");
        ViewAdapter adapter = new ViewAdapter();
        adapter.setDatas(pages);
        pager.setAdapter(adapter);
    }

    private void addImage(Uri uri) {
        SimpleDraweeView imageView = (SimpleDraweeView) layoutInflater.inflate(R.layout.activity_fresco_item, null);
        imageView.setImageURI(uri);
        imageView
                .getHierarchy()
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
        pages.add(imageView);
    }

    private void addImage(String path) {
        addImage(Uri.parse(path));
    }

    private void addRawImage(int resId) {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(resId).build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(imageRequest.getSourceUri())
                .setAutoPlayAnimations(true)
                .build();
        SimpleDraweeView imageView = (SimpleDraweeView) layoutInflater.inflate(R.layout.activity_fresco_item, null);
        imageView
                .getHierarchy()
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
        imageView.setController(controller);
        pages.add(imageView);
    }

}
