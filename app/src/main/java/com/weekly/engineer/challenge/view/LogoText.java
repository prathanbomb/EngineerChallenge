package com.weekly.engineer.challenge.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

public class LogoText extends TextView {
    public LogoText(Context context) {
        super(context);
        initial();
    }

    public LogoText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial();
    }

    public LogoText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initial();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LogoText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initial();
    }

    private void initial() {
        Typeface tf = Typeface.createFromAsset(getResources().getAssets(), "font/RSU_BOLD.ttf");
        setTypeface(tf);
        setShadowLayer(3, 5, 5, Color.parseColor("#000000"));
        setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
    }
}
