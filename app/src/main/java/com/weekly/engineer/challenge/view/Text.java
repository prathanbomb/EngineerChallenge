package com.weekly.engineer.challenge.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

public class Text extends TextView {
    public Text(Context context) {
        super(context);
        init();
    }

    public Text(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Text(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Text(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getResources().getAssets(), "font/RSU_BOLD.ttf");
        setTypeface(tf);
        setShadowLayer(1, 2, 2, Color.parseColor("#96000000"));
    }
}
