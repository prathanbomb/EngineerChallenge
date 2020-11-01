package com.weekly.engineer.challenge.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.weekly.engineer.challenge.R;

public class MainButton extends LinearLayout {

    Text text;
    ImageView img;

    public MainButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.button_main, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        text = (Text) findViewById(R.id.main_bt_tv);
        img = (ImageView) findViewById(R.id.main_bt_img);
    }

    public void setImageView(int id) {
        img.setImageResource(id);
    }

    public void setTextTitle(int id) {
        text.setText(getResources().getString(id));
    }

}
