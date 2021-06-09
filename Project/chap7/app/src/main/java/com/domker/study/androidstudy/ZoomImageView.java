package com.domker.study.androidstudy;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class ZoomImageView extends ImageView implements OnScaleGestureListener, OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener {

    public final float SCALE_MAX = 8.0f;
    private final int mTouchSlop;
    private float initScale = 1.0f;
    private final float[] matrixValues = new float[9];
    private boolean once = true;
    private  ScaleGestureDetector mScaleGestureDetector = null;
    private final Matrix mScaleMatrix = new Matrix();
    private boolean isCanDrag;

    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;

    private int lastPointerCount;
    private float mLastX;
    private float mLastY;

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setScaleType(ScaleType.MATRIX);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScaleGestureDetector = new ScaleGestureDetector(context,this);
        this.setOnTouchListener(this);
    }
    public final float getScale(){
        mScaleMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();

        if (getDrawable() == null)
            return true;
        if ((scale<SCALE_MAX && scaleFactor>1.0f)
                ||(scale>initScale && scaleFactor<1.0f))
        {

            if (scaleFactor * scale < initScale)
            {
                scaleFactor = initScale / scale;
            }
            if (scaleFactor * scale > SCALE_MAX)
            {
                scaleFactor = SCALE_MAX / scale;
            }
            mScaleMatrix.postScale(scaleFactor, scaleFactor,
                    detector.getFocusX(), detector.getFocusX());
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
        }
        return true;
    }
    private void checkBorderAndCenterWhenScale()
    {

        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();
        if (rect.width() >= width)
        {
            if (rect.left > 0)
            {
                deltaX = -rect.left;
            }
            if (rect.right < width)
            {
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height)
        {
            if (rect.top > 0)
            {
                deltaY = -rect.top;
            }
            if (rect.bottom < height)
            {
                deltaY = height - rect.bottom;
            }
        }
        if (rect.width() < width)
        {
            deltaX = width * 0.5f - rect.right + 0.5f * rect.width();
        }
        if (rect.height() < height)
        {
            deltaY = height * 0.5f - rect.bottom + 0.5f * rect.height();
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);

    }
    private RectF getMatrixRectF()
    {
        Matrix matrix = mScaleMatrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d)
        {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }


    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    protected void onAttachedToWindow(){
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        mScaleGestureDetector.onTouchEvent(event);

        float x = 0, y = 0;
        final int pointerCount = event.getPointerCount();
        for (int i = 0; i < pointerCount; i++)
        {
            x += event.getX(i);
            y += event.getY(i);
        }
        x = x / pointerCount;
        y = y / pointerCount;
        if (pointerCount != lastPointerCount)
        {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }


        lastPointerCount = pointerCount;
        RectF rectF = getMatrixRectF();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (rectF.width() > getWidth() || rectF.height() > getHeight())
                {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
            case MotionEvent.ACTION_MOVE:
                float dx = x - mLastX;
                float dy = y - mLastY;

                if (!isCanDrag)
                {
                    isCanDrag = isCanDrag(dx, dy);
                }
                if (isCanDrag)
                {
                    if (getDrawable() != null)
                    {
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        if (rectF.width() < getWidth())
                        {
                            dx = 0;
                            isCheckLeftAndRight = false;
                        }
                        if (rectF.height() < getHeight())
                        {
                            dy = 0;
                            isCheckTopAndBottom = false;
                        }
                        mScaleMatrix.postTranslate(dx, dy);
                        checkMatrixBounds();
                        setImageMatrix(mScaleMatrix);

                    }
                }
                mLastX = x;
                mLastY = y;
                if (rectF.width() > getWidth() || rectF.height() > getHeight())
                {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (isCanDrag) {

                    if (getDrawable() != null) {
                        if (rectF.left == 0 && dx > 0) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }

                        if (rectF.right == getWidth() && dx < 0) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                lastPointerCount = 0;
                break;
        }



        return true;
    }

    private void checkMatrixBounds()
    {
        RectF rect = getMatrixRectF();

        float deltaX = 0, deltaY = 0;
        final float viewWidth = getWidth();
        final float viewHeight = getHeight();
        if (rect.top > 0 && isCheckTopAndBottom)
        {
            deltaY = -rect.top;
        }
        if (rect.bottom < viewHeight && isCheckTopAndBottom)
        {
            deltaY = viewHeight - rect.bottom;
        }
        if (rect.left > 0 && isCheckLeftAndRight)
        {
            deltaX = -rect.left;
        }
        if (rect.right < viewWidth && isCheckLeftAndRight)
        {
            deltaX = viewWidth - rect.right;
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }


    private boolean isCanDrag(float dx, float dy)
    {
        return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
    }

    @Override
    public void onGlobalLayout() {
        if(once)
        {
            Drawable d = getDrawable();
            if (d == null)
                return;
            int width = getWidth();
            int height = getHeight();
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();
            float scale = 1.0f;
            if (dw > width && dh<= height){
                scale = width*1.0f/dw;
            }
            if (dh > height && dw<= width){
                scale = width*1.0f/dh;
            }
            if (dw > width && dh > height){
                scale = Math.min(dw * 1.0f / width, dh * 1.0f / height);
            }
            initScale = scale;
            mScaleMatrix.postTranslate((width - dw) / 2, (height - dh) / 2);
            mScaleMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
            setImageMatrix(mScaleMatrix);
            once = false;
        }

    }
}
