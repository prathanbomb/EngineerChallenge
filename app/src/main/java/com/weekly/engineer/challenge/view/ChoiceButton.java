package com.weekly.engineer.challenge.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weekly.engineer.challenge.R;

public class ChoiceButton extends LinearLayout {
    private TextView text1, text2;
    private ImageView imageView,incorrect;

    public ChoiceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.button_choice, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        text1 = (TextView) findViewById(R.id.bt_tv_1);
        text2 = (TextView) findViewById(R.id.bt_tv_2);
        imageView = (ImageView) findViewById(R.id.bt_img);
    }

    public void setText1(String txt) {
        text1.setText(txt);
    }

    public void setText2(String txt) {
        text2.setText(txt);
    }


    public void setDefault() {
        setBackground(getResources().getDrawable(R.drawable.btn_choice));
        setClickable(true);
    }

    public void setDefaultDis() {
        setBackground(getResources().getDrawable(R.drawable.btn_choice_disable));
        setClickable(true);
    }

    public void setCorrectState() {
        //imageView.setImageBitmap(R.drawable.game_correct_1);
    }

    public void setImage(Bitmap img) {
        imageView.setImageBitmap(img);
    }


}