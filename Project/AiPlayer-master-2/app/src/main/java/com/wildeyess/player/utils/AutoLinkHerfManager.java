package com.wildeyess.player.utils;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.wildeyess.player.utils.autolinktextview.AutoLinkMode;
import com.wildeyess.player.utils.autolinktextview.AutoLinkTextView;

/**
 * create by wildeyess
 * create on 2020-10-21
 * description
 */
public class AutoLinkHerfManager {
    /**
     * 设置正文内容
     *
     * @param content
     */
    public static void setContent(String content, AutoLinkTextView autoLinkTextView) {

        if (TextUtils.isEmpty(content)) return;

        autoLinkTextView.setVisibility(View.VISIBLE);
        autoLinkTextView.addAutoLinkMode(AutoLinkMode.MODE_HASHTAG, AutoLinkMode.MODE_MENTION, AutoLinkMode.MODE_URL);  //设置需要富文本的模式
        autoLinkTextView.setText(content);
        autoLinkTextView.setAutoLinkOnClickListener((autoLinkMode, matchedText) -> {

            switch (autoLinkMode) {
                case MODE_HASHTAG:
                    Log.i("minfo", "话题 " + matchedText);
                    break;
                case MODE_MENTION:
                    Log.i("minfo", "at " + matchedText);
                    break;
            }
        });
    }

}
