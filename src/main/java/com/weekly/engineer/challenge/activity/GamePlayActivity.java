package com.weekly.engineer.challenge.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.weekly.engineer.challenge.R;
import com.weekly.engineer.challenge.data.QuestionData;
import com.weekly.engineer.challenge.view.ChoiceButton;
import com.weekly.engineer.challenge.view.CustomScrollView;
import com.weekly.engineer.challenge.view.QText;
import com.weekly.engineer.challenge.view.Text;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import uk.co.senab.photoview.PhotoViewAttacher;

public class GamePlayActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<QuestionData> data;
    private ChoiceButton cha, chb, chc, chd, che;
    private QText question;
    private Text total_q ,tv_help1, tv_help2;
    private ImageView img_q;
    private FloatingActionButton fab_h1, fab_h2;
    FloatingActionMenu menu;
    private CustomScrollView scrollView;
    private Toast toast;
    private String dir;
    private int num_question;
    private int max_question = 0;
    private int correct,incorrect;
    private ImageView tvCorrect1,tvCorrect2,tvCorrect3,tvCorrect4,tvCorrect5,tvCorrect6,tvCorrect7,tvCorrect8,tvCorrect9,tvCorrect10;
    private int total_help1 = Integer.parseInt(MainActivity.help1);
    private int total_help2 = Integer.parseInt(MainActivity.help2);
    private int count_h1;
    private boolean help;
    ChoiceButton my;
    CountDownTimer cdt;
    private PhotoViewAttacher mAttacher;
    private static final String FORMAT = "%02d:%02d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        help = false;
        count_h1 = 0;
        num_question = 0;
        correct = 0;
        incorrect = 0;
        getData();

        fab_h1 = (FloatingActionButton) findViewById(R.id.game_play_help1);
        fab_h2 = (FloatingActionButton) findViewById(R.id.game_play_help2);

        question = (QText) findViewById(R.id.game_play_text_question);
        //tx_incorrect = (Text) findViewById(R.id.game_play_incorrect);
        total_q = (Text) findViewById(R.id.game_play_question_total);
        img_q = (ImageView) findViewById(R.id.game_play_img_question);
        tv_help1 = (Text) findViewById(R.id.tv_help1);
        tv_help2 = (Text) findViewById(R.id.tv_help2);
        tvCorrect1 = (ImageView) findViewById(R.id.correct_state1);
        tvCorrect2 = (ImageView) findViewById(R.id.correct_state2);
        tvCorrect3 = (ImageView) findViewById(R.id.correct_state3);
        tvCorrect4 = (ImageView) findViewById(R.id.correct_state4);
        tvCorrect5 = (ImageView) findViewById(R.id.correct_state5);
        tvCorrect6 = (ImageView) findViewById(R.id.correct_state6);
        tvCorrect7 = (ImageView) findViewById(R.id.correct_state7);
        tvCorrect8 = (ImageView) findViewById(R.id.correct_state8);
        tvCorrect9 = (ImageView) findViewById(R.id.correct_state9);
        tvCorrect10 = (ImageView) findViewById(R.id.correct_state10);

        menu = (FloatingActionMenu) findViewById(R.id.game_help);

        scrollView = (CustomScrollView) findViewById(R.id.game_scroll);

        mAttacher = new PhotoViewAttacher(img_q);

        cha = (ChoiceButton) findViewById(R.id.game_play_choice_a);
        chb = (ChoiceButton) findViewById(R.id.game_play_choice_b);
        chc = (ChoiceButton) findViewById(R.id.game_play_choice_c);
        chd = (ChoiceButton) findViewById(R.id.game_play_choice_d);
        che = (ChoiceButton) findViewById(R.id.game_play_choice_e);


        cha.setOnClickListener(this);
        chb.setOnClickListener(this);
        chc.setOnClickListener(this);
        chd.setOnClickListener(this);
        che.setOnClickListener(this);

        menu.setClosedOnTouchOutside(true);
        fab_h1.setOnClickListener(this);
        fab_h2.setOnClickListener(this);
        update();
        cdt = new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                total_q.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                new Progress().execute();
            }
        }.start();
    }
    int intro,funda,operation,FlowChart,select,interation,function,array;
int intro_score = 0,funda_score = 0,operation_score = 0,FlowChart_score = 0,select_score = 0,interation_score = 0,function_score = 0,array_score = 0;

    private void update() {
        setQuestion(data.get(num_question).getData()[1], data.get(num_question).getData()[8]);
        setChoice(cha, data.get(num_question).getData()[2], data.get(num_question).getData()[9]);
        setChoice(chb, data.get(num_question).getData()[3], data.get(num_question).getData()[10]);
        setChoice(chc, data.get(num_question).getData()[4], data.get(num_question).getData()[11]);
        setChoice(chd, data.get(num_question).getData()[5], data.get(num_question).getData()[12]);
        setChoice(che, data.get(num_question).getData()[6], data.get(num_question).getData()[13]);
        intro = Integer.parseInt(data.get(num_question).getData()[14]);
        funda = Integer.parseInt(data.get(num_question).getData()[15]);
        operation = Integer.parseInt(data.get(num_question).getData()[16]);
        FlowChart = Integer.parseInt(data.get(num_question).getData()[17]);
        select = Integer.parseInt(data.get(num_question).getData()[18]);
        interation = Integer.parseInt(data.get(num_question).getData()[19]);
        function = Integer.parseInt(data.get(num_question).getData()[20]);
        array = Integer.parseInt(data.get(num_question).getData()[21]);

        cha.setDefault();
        chb.setDefault();
        chc.setDefault();
        chd.setDefault();
        che.setDefault();

        tv_help1.setText("x" + total_help1);
        tv_help2.setText("x" + total_help2);

        scrollView.smoothScrollTo(0, 0);

        //tx_correct.setText(String.valueOf(correct));
        //tx_incorrect.setText(String.valueOf(incorrect));
        //total_q.setText(String.valueOf(num_question + 1) + ":" + String.valueOf(max_question));
        if (total_help1 == 0) {
            fab_h1.setEnabled(false);
        } else {
            fab_h1.setEnabled(true);
        }
        if (total_help2 == 0) {
            fab_h2.setEnabled(false);
        } else {
            fab_h2.setEnabled(true);
        }

        mAttacher.update();

    }

    private void setQuestion(String qtext, String qimg) {
        if (qtext.equals("none")) {
            question.setVisibility(View.GONE);
            question.setText("");
        } else {
            question.setVisibility(View.VISIBLE);
            question.setText(qtext);
        }

        if (qimg.equals("none")) {
            img_q.setVisibility(View.GONE);
            img_q.setImageBitmap(null);
        } else {
            img_q.setVisibility(View.VISIBLE);
            img_q.setImageBitmap(loadImage(qimg, 1, null));

        }

    }

    private void setChoice(final ChoiceButton choice, String ctext, final String cimg) {
        if (ctext.equals("none") && cimg.equals("none")) {
            choice.setVisibility(View.GONE);
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
                case R.id.game_play_choice_a:
                    t = getString(R.string.text_a);
                    break;
                case R.id.game_play_choice_b:
                    t = getString(R.string.text_b);
                    break;
                case R.id.game_play_choice_c:
                    t = getString(R.string.text_c);
                    break;
                case R.id.game_play_choice_d:
                    t = getString(R.string.text_d);
                    break;
                case R.id.game_play_choice_e:
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
            ins = getAssets().open(dir + "/" + filename);
            bitmap = BitmapFactory.decodeStream(ins);
        } catch (final IOException e) {
            finish();
        } finally {
            if (ins != null)
                try {
                    ins.close();
                } catch (IOException e) {
                    finish();
                }

        }
        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
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

    private void check(String s) {
        cha.setClickable(false);
        chb.setClickable(false);
        chc.setClickable(false);
        chd.setClickable(false);
        che.setClickable(false);

        toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        int temp = 0;
        switch (s) {
            case "1":
                temp = R.id.game_play_choice_a;
                break;
            case "2":
                temp = R.id.game_play_choice_b;
                break;
            case "3":
                temp = R.id.game_play_choice_c;
                break;
            case "4":
                temp = R.id.game_play_choice_d;
                break;
            case "5":
                temp = R.id.game_play_choice_e;
                break;
        }
        my = (ChoiceButton) findViewById(temp);


        if (s.equals(data.get(num_question).getData()[7])) {

            correct++;
            if(intro == 1){
                intro_score ++;
            }
            if (funda == 1){
                funda_score ++;
            }
            if (operation == 1){
                operation_score++;
            }
            if (FlowChart == 1){
                FlowChart_score++;
            }
            if (select == 1){
                select_score++;
            }
            if (interation == 1){
                interation_score++;
            }
            if (function == 1){
                funda_score++;
            }
            if (array == 1){
                array_score++;
            }
            switch (correct){
                case 1: tvCorrect1.setImageResource(R.drawable.view_score_game_one_normal);
                    break;
                case 2: tvCorrect2.setImageResource(R.drawable.view_score_game_two_normal);
                    break;
                case 3: tvCorrect3.setImageResource(R.drawable.view_score_game_three_normal);
                    break;
                case 4: tvCorrect4.setImageResource(R.drawable.view_score_game_four_normal);
                    break;
                case 5: tvCorrect5.setImageResource(R.drawable.view_score_game_five_normal);
                    break;
                case 6: tvCorrect6.setImageResource(R.drawable.view_score_game_six_normal);
                    break;
                case 7: tvCorrect7.setImageResource(R.drawable.view_score_game_seven_normal);
                    break;
                case 8: tvCorrect8.setImageResource(R.drawable.view_score_game_eight_normal);
                    break;
                case 9: tvCorrect9.setImageResource(R.drawable.view_score_game_nine_normal);
                    break;
                case 10: tvCorrect10.setImageResource(R.drawable.view_score_game_ten_normal);
                    break;
            }
            max_question++ ;
            LayoutInflater inflater = getLayoutInflater();
            View toastLayout = inflater.inflate(R.layout.toast_correct, (ViewGroup) findViewById(R.id.correct_toast_layout));
            toast.setView(toastLayout);
            toast.show();
            deleteToast(600, true);
        } else {
            switch (correct){
                case 0: correct = 0;
                    break;
                case 1: tvCorrect1.setImageResource(R.drawable.view_score_game_one_currect);
                    correct = 0;
                    break;
                case 2: tvCorrect1.setImageResource(R.drawable.view_score_game_one_currect);
                    tvCorrect2.setImageResource(R.drawable.view_score_game_two_currect);
                    correct = 0;
                    break;
                case 3:
                    correct = 3;
                    break;
                case 4: tvCorrect4.setImageResource(R.drawable.view_score_game_four_currect);
                    correct = 3;
                    break;
                case 5: tvCorrect4.setImageResource(R.drawable.view_score_game_four_currect);
                    tvCorrect5.setImageResource(R.drawable.view_score_game_five_currect);
                    correct = 3;
                    break;
                case 6:
                    correct = 6;
                    break;
                case 7: tvCorrect7.setImageResource(R.drawable.view_score_game_seven_currect);
                    correct = 6;
                    break;
                case 8: tvCorrect7.setImageResource(R.drawable.view_score_game_seven_currect);
                    tvCorrect8.setImageResource(R.drawable.view_score_game_eight_currect);
                    correct = 6;
                    break;
                case 9: correct = 9;
                    break;
                case 10: correct = 10;
                    break;
            }
            max_question++ ;
            LayoutInflater inflater = getLayoutInflater();
            View toastLayout = inflater.inflate(R.layout.toast_incorrect, (ViewGroup) findViewById(R.id.incorrect_toast_layout));
            toast.setView(toastLayout);
            if (help) {
                if (count_h1 == 2) {
                    my.setDefaultDis();
                    my.setClickable(false);
                    fab_h1.setImageResource(R.drawable.game_help1_1);
                    toastLayout = inflater.inflate(R.layout.toast_help_1, (ViewGroup) findViewById(R.id.help_1_toast_layout));
                    toast.setView(toastLayout);
                    toast.show();
                    deleteToast(400, false);

                } else {
                    incorrect++;
                    toast.show();
                    deleteToast(600, true);

                }
                count_h1--;
            } else {
                incorrect++;
                toast.show();
                deleteToast(600, true);

            }


        }
    }

    private void getData() {
        data = new ArrayList<>();
        Intent getPlay = getIntent();
        //max_question = getPlay.getIntExtra(MainActivity.TAG_MAX_Q, 0);
        dir = getPlay.getStringExtra(MainActivity.TAG_DIR);
        total_help1 = getPlay.getIntExtra(MainActivity.TAG_HELP1, 0);
        total_help2 = getPlay.getIntExtra(MainActivity.TAG_HELP2, 0);
        data = getPlay.getParcelableArrayListExtra(MainActivity.TAG_Q);
    }

    private void deleteToast(int dur, final boolean t) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
                if (t) {
                    next();
                }
                cha.setClickable(true);
                chb.setClickable(true);
                chc.setClickable(true);
                chd.setClickable(true);
                che.setClickable(true);
            }
        }, dur);

    }

    private void next() {
            num_question++;
            help = false;
            count_h1 = 0;
            fab_h1.setImageResource(R.drawable.game_help1);
            fab_h1.setClickable(true);
            fab_h1.setEnabled(false);
            update();
    }

    private void helpUpdate(int id) {
        switch (id) {
            case R.id.game_play_help1:
                help = true;
                total_help1--;
                fab_h1.setClickable(false);
                tv_help1.setText("x" + String.valueOf(total_help1));
                fab_h1.setImageResource(R.drawable.game_help1_2);
                count_h1 = 2;
                toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                LayoutInflater inflater = getLayoutInflater();
                View toastLayout = inflater.inflate(R.layout.toast_help_2, (ViewGroup) findViewById(R.id.help_2_toast_layout));
                toast.setView(toastLayout);
                toast.show();
                deleteToast(400, false);
                break;
            case R.id.game_play_help2:
                total_help2--;
                fab_h2.setEnabled(false);
                tv_help2.setText("x" + String.valueOf(total_help2));
                String[] ou = {data.get(num_question).getData()[7]};
                String[] temp = MainActivity.Random(2, (!data.get(num_question).getData()[6].equals("none") || !data.get(num_question).getData()[13].equals("none")) ? 5 : 4, 1, ou);
                for (String aTemp : temp) {
                    switch (aTemp) {
                        case "1":
                            cha.setDefaultDis();
                            cha.setClickable(false);
                            break;
                        case "2":
                            chb.setDefaultDis();
                            chb.setClickable(false);
                            break;
                        case "3":
                            chc.setDefaultDis();
                            chc.setClickable(false);
                            break;
                        case "4":
                            chd.setDefaultDis();
                            chd.setClickable(false);
                            break;
                        case "5":
                            che.setDefaultDis();
                            che.setClickable(false);
                            break;
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.game_play_choice_a:
                check("1");
                break;
            case R.id.game_play_choice_b:
                check("2");
                break;
            case R.id.game_play_choice_c:
                check("3");
                break;
            case R.id.game_play_choice_d:
                check("4");
                break;
            case R.id.game_play_choice_e:
                check("5");
                break;
            case R.id.game_play_help1:
                helpUpdate(R.id.game_play_help1);
                break;
            case R.id.game_play_help2:
                helpUpdate(R.id.game_play_help2);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        MainActivity.help1 = String.valueOf(total_help1);
        MainActivity.help2 = String.valueOf(total_help2);
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                        cdt.cancel();
                    }
                }).create().show();
    }

    public class Progress extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        Intent itn;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(GamePlayActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress));
            progressDialog.setMessage(getString(R.string.text_progress));
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            startActivity(itn);
            finish();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                finish();
            }
            itn = new Intent(getApplicationContext(), GameResultActivity.class);
            itn.putParcelableArrayListExtra(MainActivity.TAG_Q, data);
            itn.putExtra(MainActivity.TAG_MAX_Q, max_question);
            itn.putExtra(MainActivity.TAG_CORRECT, correct);
            itn.putExtra(MainActivity.TAG_DIR, dir);
            itn.putExtra(MainActivity.help1, total_help1);
            itn.putExtra(MainActivity.help2, total_help2);
            return null;
        }
    }


}
