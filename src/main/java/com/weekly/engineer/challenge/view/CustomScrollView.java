package com.weekly.engineer.challenge.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initial();
    }

    public CustomScrollView(Context context) {
        super(context);
        initial();
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial();
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initial();
    }

    public void initial() {
        setSmoothScrollingEnabled(true);
        setScrollbarFadingEnabled(false);
        setScrollBarSize(10);
    }
}
