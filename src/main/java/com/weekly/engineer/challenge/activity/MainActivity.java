package com.weekly.engineer.challenge.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.github.clans.fab.FloatingActionButton;
import com.weekly.engineer.challenge.R;
import com.weekly.engineer.challenge.data.AchievementData;
import com.weekly.engineer.challenge.data.DataContract;
import com.weekly.engineer.challenge.data.MyDbHelper;
import com.weekly.engineer.challenge.data.QuestionData;
import com.weekly.engineer.challenge.view.CustomFacebookDialog;
import com.weekly.engineer.challenge.view.CustomHelpDialog;
import com.weekly.engineer.challenge.view.MainButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public final static String TAG_MAX_Q = "MAX_Q";
    public final static String TAG_DIR = "DIR";
    public final static String TAG_CORRECT = "CORRECT";
    public final static String TAG_Q = "Q";
    public final static String TAG_ANS = "ANS";
    public final static String TAG_HELP1 = "HELP1";
    public final static String TAG_HELP2 = "HELP2";
    public final static String PREFS_NAME = "EC";
    public final static String CHECK = "CHECK";
    public final static String TAG_COUNT = "COUNT";
    public final static String TAG_DATA = "DATA";
    public static String help1 = "1";
    public static String help2 = "1";
    public static int SCORE ;
    public final static int[] help_upgrade = {50, 100, 200, 400, 800, 1000, 1600, 2000};
    public final static int[] help_num = {1, 2, 4, 6, 8, 10, 13, 16, 20};

    public final static String app_icon = "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXUFhPZnpoTnlEenM";

    public final static String[] game_achieve = {"https://drive.google.com/uc?export=download&id=0B82l_1ppleMXcXVXeWp6bGI5eFE"
            , "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXeTJqX1ljWkZYekU"
            , "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXRjJ5SVQ3QnEzUVU"
            , "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXaXdEa2k4dlZOdms"
            , "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXcE5udmtXODk2T00"
            , "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXVlY4NWRnZk1XNUE"
            , "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXbHhtMlppMUIyaWs"
            , "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXS0ZpTnY5cWtHNlU"
            , "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXVk1mVHhnU2VpYWc"
            , "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXQ0Ryanc3VGNtclk"
            , "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXQjgyQ3BUN1gtV00"
            , "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXeGYzdkRKYTI1bFk"
    };

    public final static String[] test_achieve = {"https://drive.google.com/uc?export=download&id=0B82l_1ppleMXVk1mVHhnU2VpYWc"
            , "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXM0RWRE9KWlUyenc"
            , "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXbHZkZ3BpNmNCYzA"
            , "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXZ0JOSTF4a19NcmM"
            , "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXb3JnRlY4RHkwekU"
            , "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXR3Z6dEp1a1VCZW8"
            , "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXX2lCT1JSNG93REE"
            , "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXVnhudGRQbXRYVjg"
            , "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXeUVvb2RVZUU0dmc"
            , "https://drive.google.com/uc?export=download&id=0B82l_1ppleMXNnlORzI0eHdOblU"
    };

    public final static String[] directory = {"com", "mat", "m&s", "draw"};
    public final static String[] file_name = {"com_pro.csv", "material.csv", "mech_stat.csv", "draw.csv"};

    public final static int[] question_number = {20, 30, 50, 80, 100};
    public final static int[] subject = {R.string.text_com, R.string.text_mat, R.string.text_mech, R.string.text_draw};
    public final static int[] topic_material = {R.string.text_all,R.string.text_all, R.string.text_all, R.string.text_all,R.string.text_all,R.string.text_all,R.string.text_all,R.string.text_all,R.string.text_all};
    public final static int[] topic_drawings = {R.string.text_all,R.string.text_all, R.string.text_all, R.string.text_all,R.string.text_all,R.string.text_all,R.string.text_all,R.string.text_all,R.string.text_all};
    public final static int[] topic_static = {R.string.text_all,R.string.text_all, R.string.text_all, R.string.text_all,R.string.text_all,R.string.text_all,R.string.text_all,R.string.text_all,R.string.text_all};
    public final static int[] topic_compro = {R.string.text_all,R.string.text_com_introduction, R.string.text_com_fundamental, R.string.text_com_0peration, R.string.text_com_flow_Chart, R.string.text_com_selection, R.string.text_com_interation, R.string.text_com_Function, R.string.text_com_Array};
    public final static int[] history_subject = {R.string.text_all, R.string.text_com, R.string.text_mat, R.string.text_mech, R.string.text_draw};

    private AchievementData[] achievement = {
            new AchievementData("Wow!!!", 10, 0, "Get perfect 10 times", 1, 0, R.drawable.achiev_01),
            new AchievementData("Hooray", 20, 0, "Get excellent 20 times", 1, 0, R.drawable.achiev_02),
            new AchievementData("Not bad", 40, 0, "Get good 40 times", 1, 0, R.drawable.achiev_03),
            new AchievementData("The exam is difficult", 50, 0, "Get Try again 50 times", 1, 0, R.drawable.achiev_04),
            new AchievementData("Millionaire", 10000, 0, "Use coin 10000", 1, 0, R.drawable.achiev_05),
            new AchievementData("Bravo!!!", 10, 0, "All answer correct 10 times", 1, 0, R.drawable.achiev_06),
            new AchievementData("The Shining star ", 150, 0, "Get Star 150", 1, 0, R.drawable.achiev_07),
            new AchievementData("Nerd", 100, 0, "Play game mode 100 times", 1, 0, R.drawable.achiev_08),
            new AchievementData("Welcome to programmer's world", 500, 0, "Answer questions Computer Programming 500 times", 1, 0, R.drawable.achiev_09),
            new AchievementData("Material knowledge", 500, 0, "Answer questions Engineering Materials 500 times", 1, 0, R.drawable.achiev_10),
            new AchievementData("Speedy drawing", 500, 0, "Answer questions Engineering Drawings 500 times", 1, 0, R.drawable.achiev_11),
            new AchievementData("Mechanics Tools", 500, 0, "Answer questions Engineering Mechanic : Static 500 times", 1, 0, R.drawable.achiev_12),
            new AchievementData("The Most Experience ", 10000, 0, "Answer questions 10000 times", 0, 0, R.drawable.achiev_13),
            new AchievementData("Willing to Programming", 1000, 0, "Answer questions Computer Programming 1000 times", 0, 0, R.drawable.achiev_14),
            new AchievementData("Industrious in Materials", 1000, 0, "Answer questions Engineering Materials 1000 times", 0, 0, R.drawable.achiev_15),
            new AchievementData("Devoting for Drawing", 1000, 0, "Answer questions Engineering Drawings 1000 times", 0, 0, R.drawable.achiev_16),
            new AchievementData("Dedicate to Mechanic", 1000, 0, "Answer questions Engineering Mechanic : Static 1000 times", 0, 0, R.drawable.achiev_17),
            new AchievementData("Master of Computer Programming", 20, 0, "Score more then 90 in Computer Programming 20 times", 0, 0, R.drawable.achiev_18),
            new AchievementData("Mother of Engineering Materials", 20, 0, "Score more then 90 in Engineering Materials 20 times", 0, 0, R.drawable.achiev_19),
            new AchievementData("Expert of Engineering Drawing", 20, 0, "Score more then 90 in Engineering Drawings 20 times", 0, 0, R.drawable.achiev_20),
            new AchievementData("Specialise in Engineering Mechanic", 20, 0, "Score more then 90 in Engineering Mechanic : Static 20 times", 0, 0, R.drawable.achiev_21)
    };
    //MainButton game, test;
    ImageView facebook,ach,help,game,test,progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean check = settings.getBoolean(CHECK, false);

        game = (ImageView) findViewById(R.id.game_mode);
        test = (ImageView) findViewById(R.id.test_mode);
        progress = (ImageView) findViewById(R.id.progress_temp);

        //
        //game.setTextTitle(R.string.text_game_mode);
        //test.setTextTitle(R.string.text_test_mode);
        //game.setImageView(R.drawable.button_game1);
        //test.setImageView(R.drawable.button_test);

        ach = (ImageView) findViewById(R.id.achievement);
        help = (ImageView) findViewById(R.id.help);
        facebook = (ImageView) findViewById(R.id.main_facebook);

        MyDbHelper db = new MyDbHelper(getBaseContext());

        if (!check) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(CHECK, true);
            editor.apply();
            db.init();
            db.init(achievement);
        }

        game.setOnClickListener(this);
        test.setOnClickListener(this);
        ach.setOnClickListener(this);
        help.setOnClickListener(this);
        progress.setOnClickListener(this);
        facebook.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }

    public static String[] Random(int n, int max, int min, String[] noUse) {
        String[] msg = new String[n];
        int i = 0;

        Random r = new Random();
        while (i < n) {
            if (i == 0) {
                msg[i] = String.valueOf(r.nextInt(max - min + 1) + min);
            } else {
                int t = r.nextInt(max - min) + min;
                boolean c = false;
                for (int j = 0; j < i; j++) {
                    if (msg[j].equals(String.valueOf(t))) {
                        c = false;
                        break;
                    } else {
                        c = true;
                    }
                }
                if (c) {
                    msg[i] = String.valueOf(t);
                } else {
                    continue;
                }

            }
            if (noUse.length >= 1 && !noUse[0].equals("0")) {
                boolean c = false;
                for (String aNoUse : noUse) {
                    if (aNoUse.equals(String.valueOf(msg[i]))) {
                        c = false;
                        break;
                    } else {
                        c = true;
                    }
                }
                if (c) {
                    i++;
                } else {
                    if (i != 0) {
                        i--;
                    }
                }
            } else {
                i++;
            }
        }
        return msg;
    }

    public static ArrayList<QuestionData> readData(int sub, int amount, InputStream input) throws IOException {
        int max = 0;
        int min = 1;
        String[] noUse = {"0"};
        switch (sub) {
            case 0:
                max = 399;
                break;
            case 1:
                max = 400;
                break;
            case 2:
                max = 336;
                noUse = new String[2];
                noUse[0] = "185";
                noUse[1] = "186";
                break;
            case 3:
                max = 526;
                break;
        }
        String[] rd = MainActivity.Random(amount, max + 1, min, noUse);
        ArrayList<QuestionData> data = new ArrayList<>();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(input));
        String readLine;
        int line = 0;
        boolean check = false;
        while ((readLine = br.readLine()) != null) {
            for (String aRd : rd) {
                if (line == Integer.valueOf(aRd)) {
                    check = true;
                    break;
                }
            }
            if (check) {
                String[] a = readLine.split(",");
                if (a.length > 23) {
                    String[] b = new String[23];
                    for (int i = 0; i < b.length; i++) {
                        b[i] = "";
                    }
                    boolean comma = false;
                    int count = 0;
                    for (int i = 0, j = 0; i < readLine.length(); i++) {

                        if (readLine.charAt(i) == '"') {
                            comma = true;
                            count++;
                            continue;
                        }

                        if (count == 2) {
                            count = 0;
                            comma = false;
                        }

                        if (readLine.charAt(i) == ',' && !comma) {
                            j++;
                            continue;
                        }

                        b[j] += readLine.charAt(i);

                    }
                    data.add(new QuestionData(b));
                } else {
                    data.add(new QuestionData(a));
                }
                check = false;
            }
            line++;
        }


        return data;
    }

    public static int[] get_p_data(Context context) {
        int[] p_data = new int[21];
        MyDbHelper db = new MyDbHelper(context);
        for (int i = 0; i < 21; i++) {
            p_data[i] = db.select(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.ACHIEVEMENT[i]);
        }
        return p_data;
    }

    @Override
    public void onClick(View v) {
        Intent itn;
        switch (v.getId()) {
            case R.id.game_mode:
                v.setClickable(false);
                itn = new Intent(getApplicationContext(), GameSetActivity.class);
                startActivity(itn);
                v.setClickable(true);
                break;
            case R.id.test_mode:
                v.setClickable(false);
                itn = new Intent(getApplicationContext(), TestSetActivity.class);
                startActivity(itn);
                v.setClickable(true);
                break;
            case R.id.achievement:
                v.setClickable(false);
                itn = new Intent(getApplicationContext(), AchievementActivity.class);
                startActivity(itn);
                v.setClickable(true);
                break;
            case R.id.progress_temp:
                v.setClickable(false);
                itn = new Intent(getApplicationContext(), TestHistoryActivity.class);
                startActivity(itn);
                v.setClickable(true);
                break;
            case R.id.help:
                v.setClickable(false);
                showHelpDialog();
                v.setClickable(true);
                break;
            case R.id.main_facebook:
                if (isNetworkConnected()) {
                    showCustomDialog();
                } else {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.text_network), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void showHelpDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        CustomHelpDialog newFragment = new CustomHelpDialog();
        newFragment.show(fragmentManager, "dialog");
    }

    CustomFacebookDialog newFragment;

    private void showCustomDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        newFragment = new CustomFacebookDialog();
        newFragment.show(fragmentManager, "dialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CallbackManager callbackManager = CallbackManager.Factory.create();
        callbackManager.onActivityResult(requestCode, resultCode, data);
        newFragment.RequestData();
        newFragment.setShare();
    }

    public interface onEventListener {

        void chooseEvent(String ans);
    }

    public interface onEventAchiListener {

        void chooseEvent(String name, int condition, int goal, String detail, int img_id, boolean isComplete, int position);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


}