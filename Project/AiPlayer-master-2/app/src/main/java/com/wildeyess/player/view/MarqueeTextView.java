package com.wildeyess.player.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * create by wildeyess
 * create on 2020-10-21
 * description 跑马灯textview
 */
public class MarqueeTextView extends TextView {

    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused(){
        return true;
    }

}
