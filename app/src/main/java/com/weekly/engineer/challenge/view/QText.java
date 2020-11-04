package com.weekly.engineer.challenge.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

public class QText extends TextView {
    public QText(Context context) {
        super(context);
        init();
    }

    public QText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public QText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getResources().getAssets(), "font/SuperspaceBold.ttf");
        setTypeface(tf);
    }
}
