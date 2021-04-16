package com.domker.study.androidstudy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CustomView extends View {

    public CustomView(Context context) {
        super(context);
    }


    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        Bitmap iconbit = BitmapFactory.decodeResource(getResources(), R.drawable.image2);

        canvas.drawBitmap(iconbit, 20, 20, null);

        //Bitmap bitmap = Bitmap.createBitmap(600, 800, Bitmap.Config.ARGB_8888);

    }

}