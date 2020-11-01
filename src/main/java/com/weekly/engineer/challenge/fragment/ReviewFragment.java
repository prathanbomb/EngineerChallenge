package com.weekly.engineer.challenge.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weekly.engineer.challenge.R;
import com.weekly.engineer.challenge.activity.MainActivity;
import com.weekly.engineer.challenge.view.ChoiceButton;

import java.io.IOException;
import java.io.InputStream;

public class ReviewFragment extends Fragment {

    private ChoiceButton cha, chb, chc, chd, che;
    private TextView question;
    private ImageView img_q;
    private String[] data;
    private String dir;

    public static ReviewFragment newInstance(String[] param1, String param2) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();

        args.putStringArray(MainActivity.TAG_DATA, param1);
        args.putString(MainActivity.TAG_DIR, param2);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            data = getArguments().getStringArray(MainActivity.TAG_DATA);
            dir = getArguments().getString(MainActivity.TAG_DIR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_review, container, false);

        question = (TextView) rootView.findViewById(R.id.review_text_question);
        img_q = (ImageView) rootView.findViewById(R.id.review_img_question);

        cha = (ChoiceButton) rootView.findViewById(R.id.review_choice_a);
        chb = (ChoiceButton) rootView.findViewById(R.id.review_choice_b);
        chc = (ChoiceButton) rootView.findViewById(R.id.review_choice_c);
        chd = (ChoiceButton) rootView.findViewById(R.id.review_choice_d);
        che = (ChoiceButton) rootView.findViewById(R.id.review_choice_e);

        cha.setClickable(false);
        chb.setClickable(false);
        chc.setClickable(false);
        chd.setClickable(false);
        che.setClickable(false);

        setQuestion(data[1], data[8]);
        setChoice(cha, data[2], data[9]);
        setChoice(chb, data[3], data[10]);
        setChoice(chc, data[4], data[11]);
        setChoice(chd, data[5], data[12]);
        setChoice(che, data[6], data[13]);

        check(data[7]);
        return rootView;
    }

    private void check(String ans) {
        cha.setDefaultDis();
        chb.setDefaultDis();
        chc.setDefaultDis();
        chd.setDefaultDis();
        che.setDefaultDis();
        switch (ans) {
            case "1":
                cha.setDefault();
                break;
            case "2":
                chb.setDefault();
                break;
            case "3":
                chc.setDefault();
                break;
            case "4":
                chd.setDefault();
                break;
            case "5":
                che.setDefault();
                break;
        }
    }

    private void setQuestion(String qtext, final String qimg) {
        if (qtext.equals("none")) {
            question.setVisibility(View.INVISIBLE);
            question.setText("");
        } else {
            question.setVisibility(View.VISIBLE);
            question.setText(qtext);
        }

        if (qimg.equals("none")) {
            img_q.setVisibility(View.INVISIBLE);
            img_q.setImageBitmap(null);
        } else {
            img_q.setVisibility(View.VISIBLE);
            img_q.post(new Runnable() {
                @Override
                public void run() {
                    img_q.setImageBitmap(loadImage(qimg, 1, null));
                }
            });

        }

    }

    private void setChoice(final ChoiceButton choice, String ctext, final String cimg) {
        if (ctext.equals("none") && cimg.equals("none")) {
            choice.setVisibility(View.INVISIBLE);
            ViewGroup.LayoutParams lp = choice.getLayoutParams();
            lp.height = 0;
            lp.width = 0;
            choice.setLayoutParams(lp);
            choice.setText1("");
            choice.setText2("");
        } else {
            choice.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams lp = choice.getLayoutParams();
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            choice.setLayoutParams(lp);
            String t = "";
            switch (choice.getId()) {
                case R.id.review_choice_a:
                    t = getString(R.string.text_a);
                    break;
                case R.id.review_choice_b:
                    t = getString(R.string.text_b);
                    break;
                case R.id.review_choice_c:
                    t = getString(R.string.text_c);
                    break;
                case R.id.review_choice_d:
                    t = getString(R.string.text_d);
                    break;
                case R.id.review_choice_e:
                    t = getString(R.string.text_e);
                    break;
            }
            if (ctext.equals("none")) {
                choice.setText1(t);
                choice.setText2("");
            } else {
                choice.setText1(t);
                choice.setText2(ctext);
            }

            if (cimg.equals("none")) {
                choice.setImage(null);
            } else {
                choice.post(new Runnable() {
                    @Override
                    public void run() {
                        choice.setImage(loadImage(cimg, 2, choice));
                    }
                });
            }
        }
    }

    private Bitmap loadImage(String filename, int type, View v) {
        InputStream ins = null;
        Bitmap bitmap = null;
        try {
            ins = getActivity().getAssets().open(dir + "/" + filename);
            bitmap = BitmapFactory.decodeStream(ins);
        } catch (final IOException e) {
            getActivity().finish();
        } finally {
            if (ins != null)
                try {
                    ins.close();
                } catch (IOException e) {
                    getActivity().finish();
                }
        }
        Point size = new Point();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        display.getSize(size);

        float x = (float) (size.x * 0.8);
        if (type == 2) {
            x = (float) (v.getWidth() * 0.75);
        }

        int width;
        int height;
        Bitmap resizedBitmap = null;
        if (bitmap != null) {
            width = bitmap.getWidth();
            height = bitmap.getHeight();
            float scale = x / width;
            resizedBitmap = Bitmap.createScaledBitmap(bitmap, (int) (width * scale), (int) (height * scale), false);
        }
        return resizedBitmap;
    }
}
