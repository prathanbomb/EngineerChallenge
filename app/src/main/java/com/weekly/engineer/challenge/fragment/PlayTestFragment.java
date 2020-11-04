package com.weekly.engineer.challenge.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.weekly.engineer.challenge.R;
import com.weekly.engineer.challenge.activity.MainActivity;
import com.weekly.engineer.challenge.view.ChoiceButton;
import com.weekly.engineer.challenge.view.QText;

import java.io.IOException;
import java.io.InputStream;

import uk.co.senab.photoview.PhotoViewAttacher;

public class PlayTestFragment extends Fragment implements View.OnClickListener {

    private MainActivity.onEventListener tListener;
    private ChoiceButton cha, chb, chc, chd, che;
    private QText question;
    private ImageView img_q;
    private String[] data;
    private String dir;
    private String ans;
    PhotoViewAttacher mAttacher;

    public static PlayTestFragment newInstance(String[] data, String directory, String ans) {
        PlayTestFragment fragment = new PlayTestFragment();
        Bundle args = new Bundle();

        args.putStringArray(MainActivity.TAG_DATA, data);
        args.putString(MainActivity.TAG_DIR, directory);
        args.putString(MainActivity.TAG_ANS, ans);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            data = getArguments().getStringArray(MainActivity.TAG_DATA);
            dir = getArguments().getString(MainActivity.TAG_DIR);
            ans = getArguments().getString(MainActivity.TAG_ANS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_test_play, container, false);

        question = (QText) rootView.findViewById(R.id.test_text_question);
        img_q = (ImageView) rootView.findViewById(R.id.test_img_question);

        cha = (ChoiceButton) rootView.findViewById(R.id.test_choice_a);
        chb = (ChoiceButton) rootView.findViewById(R.id.test_choice_b);
        chc = (ChoiceButton) rootView.findViewById(R.id.test_choice_c);
        chd = (ChoiceButton) rootView.findViewById(R.id.test_choice_d);
        che = (ChoiceButton) rootView.findViewById(R.id.test_choice_e);

        setQuestion(data[1], data[8]);
        setChoice(cha, data[2], data[9]);
        setChoice(chb, data[3], data[10]);
        setChoice(chc, data[4], data[11]);
        setChoice(chd, data[5], data[12]);
        setChoice(che, data[6], data[13]);

        mAttacher = new PhotoViewAttacher(img_q);

        cha.setOnClickListener(this);
        chb.setOnClickListener(this);
        chc.setOnClickListener(this);
        chd.setOnClickListener(this);
        che.setOnClickListener(this);


        if (ans.equals("0")) {
            cha.setDefaultDis();
            chb.setDefaultDis();
            chc.setDefaultDis();
            chd.setDefaultDis();
            che.setDefaultDis();
        } else {
            check(ans);
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity.onEventListener) {
            tListener = (MainActivity.onEventListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        tListener = null;
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
                case R.id.test_choice_a:
                    t = getString(R.string.text_a);
                    break;
                case R.id.test_choice_b:
                    t = getString(R.string.text_b);
                    break;
                case R.id.test_choice_c:
                    t = getString(R.string.text_c);
                    break;
                case R.id.test_choice_d:
                    t = getString(R.string.text_d);
                    break;
                case R.id.test_choice_e:
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

    private void check2(String ans) {

        String temp_ans = "0";
        switch (ans) {
            case "1":
                temp_ans = "1";
                break;
            case "2":
                temp_ans = "2";
                break;
            case "3":
                temp_ans = "3";
                break;
            case "4":
                temp_ans = "4";
                break;
            case "5":
                temp_ans = "5";
                break;
        }
        tListener.chooseEvent(temp_ans);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_choice_a:
                check("1");
                check2("1");
                break;
            case R.id.test_choice_b:
                check("2");
                check2("2");
                break;
            case R.id.test_choice_c:
                check("3");
                check2("3");
                break;
            case R.id.test_choice_d:
                check("4");
                check2("4");
                break;
            case R.id.test_choice_e:
                check("5");
                check2("5");
                break;
        }
    }
}
