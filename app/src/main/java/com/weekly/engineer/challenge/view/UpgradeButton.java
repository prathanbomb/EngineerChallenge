package com.weekly.engineer.challenge.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.weekly.engineer.challenge.R;

public class UpgradeButton extends LinearLayout {

    Text text;
    ImageView img;

    public UpgradeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.button_upgrade, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        text = (Text) findViewById(R.id.up_bt_tv);
        img = (ImageView) findViewById(R.id.up_bt_img);
    }

    public void setImageView(int id) {
        img.setImageResource(id);
    }

    public void setTextTitle(String msg) {
        text.setText(msg);
    }
}
