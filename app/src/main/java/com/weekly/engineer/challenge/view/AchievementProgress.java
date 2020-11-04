package com.weekly.engineer.challenge.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.weekly.engineer.challenge.R;

public class AchievementProgress extends View {
    private int condition, goal;
    Paint p;

    public AchievementProgress(Context context) {
        super(context);
        init();
    }

    public AchievementProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AchievementProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AchievementProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        condition = 0;
        goal = 0;
        p = new Paint();
    }

    public void setData(int condition, int goal) {
        this.condition = condition;
        this.goal = goal;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p.setColor(getResources().getColor(R.color.red_darken));
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        p.setAntiAlias(true);
        RectF rectf = new RectF(5, 5, getMeasuredWidth() - 5, getMeasuredHeight() - 5);
        canvas.drawRect(rectf, p);

        p.setStyle(Paint.Style.FILL);
        p.setColor(getResources().getColor(R.color.red_lighten));
        p.setAntiAlias(true);
        float width = 0;
        if (condition != 0) {
            width = ((float) (getMeasuredWidth() - 7.5) / 100) * ((float) goal * 100 / (float) condition);
        }

        RectF rectf2 = new RectF((float) 7.5, (float) 7.5, width, (float) (getMeasuredHeight() - 7.5));
        canvas.drawRect(rectf2, p);
    }
}
