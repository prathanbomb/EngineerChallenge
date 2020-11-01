package com.weekly.engineer.challenge.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.widget.ShareDialog;
import com.github.clans.fab.FloatingActionButton;
import com.weekly.engineer.challenge.R;
import com.weekly.engineer.challenge.data.AchievementData;
import com.weekly.engineer.challenge.data.DataContract;
import com.weekly.engineer.challenge.data.MyDbHelper;
import com.weekly.engineer.challenge.data.QuestionData;
import com.weekly.engineer.challenge.view.CustomFacebookDialog;
import com.weekly.engineer.challenge.view.Text;

import java.util.ArrayList;

public class GameResultActivity extends AppCompatActivity implements View.OnClickListener {
    Text game_score, game_your_score, game_rate, get_coin;
    private ImageView star1, star2, star3;
    FloatingActionButton  play_again, menu, share;
    int coin, coin_bonus, coin_get, score_y, score_max;
    private String dir;
    private ArrayList<QuestionData> data;
    private MyDbHelper db;
    private int[] p_data;
    ImageButton review,analysis;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_game_result);
        game_your_score = (Text) findViewById(R.id.game_your_score);
        game_score = (Text) findViewById(R.id.game_total_score);
        game_rate = (Text) findViewById(R.id.game_rate_score);
        get_coin = (Text) findViewById(R.id.game_coin_result);
        db = new MyDbHelper(getBaseContext());

        getData();
        //game_score.setText("/" + String.valueOf(score_max));
        game_your_score.setText(String.valueOf(score_y));
        MainActivity.SCORE = score_y;

        review = (ImageButton) findViewById(R.id.game_review);
        analysis = (ImageButton) findViewById(R.id.analysis_buton_game);
        menu = (FloatingActionButton) findViewById(R.id.game_return);
        play_again = (FloatingActionButton) findViewById(R.id.game_replay);
        share = (FloatingActionButton) findViewById(R.id.game_share);
        review.setOnClickListener(this);
        analysis.setOnClickListener(this);
        menu.setOnClickListener(this);
        play_again.setOnClickListener(this);
        share.setOnClickListener(this);

        star1 = (ImageView) findViewById(R.id.game_star_1);
        star2 = (ImageView) findViewById(R.id.game_star_2);
        star3 = (ImageView) findViewById(R.id.game_star_3);

        shareDialog = new ShareDialog(this);
        callbackManager = CallbackManager.Factory.create();

        p_data = MainActivity.get_p_data(getBaseContext());
        setStar(score_y, score_max);
        checkAchievement(getBaseContext(), db);
    }

    private void getData() {
        Intent itn = getIntent();
        data = new ArrayList<>();
        score_max = itn.getIntExtra(MainActivity.TAG_MAX_Q, 0);
        dir = itn.getStringExtra(MainActivity.TAG_DIR);
        score_y = itn.getIntExtra(MainActivity.TAG_CORRECT, 0);
        data = itn.getParcelableArrayListExtra(MainActivity.TAG_Q);
    }

    private void goReview() {
        Intent itn = new Intent(getApplicationContext(), ReviewActivity.class);
        itn.putParcelableArrayListExtra(MainActivity.TAG_Q, data);
        itn.putExtra(MainActivity.TAG_MAX_Q, score_max);
        itn.putExtra(MainActivity.TAG_DIR, dir);

        startActivity(itn);
    }

    private void goAnalysis() {
        Intent itn = new Intent(getApplicationContext(), AnalysisActivity.class);
        startActivity(itn);
    }

    public void setStar(float c, float max) {
        float rate = score_y;
        coin_get = score_max;

        if (score_y == score_max) {
            db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.GAME_CORRECT_PERFECT, p_data[5] + 1);
        }

        if (rate >= 9) {
            game_rate.setText(getString(R.string.text_rate_perfect));
            star1.setImageResource(R.drawable.game_star_full);
            star2.setImageResource(R.drawable.game_star_full);
            star3.setImageResource(R.drawable.game_star_full);

            db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.GAME_PERFECT, p_data[0] + 1);
            db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.GAME_STAR, p_data[6] + 3);

            coin_bonus = score_max;
        } else if (rate > 6) {
            game_rate.setText(getString(R.string.text_rate_excellent));
            star1.setImageResource(R.drawable.game_star_full);
            star3.setImageResource(R.drawable.game_star_full);
            star2.setImageResource(R.drawable.game_star_full);

            db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.GAME_EX, p_data[1] + 1);
            db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.GAME_STAR, p_data[6] + 2);

            coin_bonus = score_max * 80 / 100;
        } else if (rate > 3) {
            game_rate.setText(getString(R.string.text_rate_good));
            star1.setImageResource(R.drawable.game_star_full);
            star2.setImageResource(R.drawable.game_star_full);

            star3.setImageResource(R.drawable.game_star_blank);

            db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.GAME_GOOD, p_data[2] + 1);
            db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.GAME_STAR, p_data[6] + 1);

            coin_bonus = score_max * 60 / 100;
        } else if (rate > 0) {
            game_rate.setText(getString(R.string.text_rate_normal));
            star1.setImageResource(R.drawable.game_star_full);
            star2.setImageResource(R.drawable.game_star_blank);
            star3.setImageResource(R.drawable.game_star_blank);

            coin_bonus = 0;
        } else {
            star1.setImageResource(R.drawable.game_star_blank);
            star2.setImageResource(R.drawable.game_star_blank);
            star3.setImageResource(R.drawable.game_star_blank);

            db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.GAME_TRY, p_data[3] + 1);

            coin_bonus = 0;
        }

        String temp = (coin_bonus != 0) ? " Bonus " + String.valueOf(coin_bonus) : "";
        get_coin.setText(String.valueOf(coin_get) + temp);

        coin = db.select(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.COIN);
        coin += coin_get + coin_bonus;

        db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.COIN, coin);

        db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.PLAY_GAME, p_data[7] + 1);

        switch (dir) {
            case "com":
                db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.Q_GAME_SUB1, p_data[8] + score_max);
                break;
            case "mat":
                db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.Q_GAME_SUB2, p_data[9] + score_max);
                break;
            case "m&s":
                db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.Q_GAME_SUB3, p_data[10] + score_max);
                break;
            case "draw":
                db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.Q_GAME_SUB4, p_data[11] + score_max);
                break;
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.game_review:
                v.setClickable(false);
                goReview();
                v.setClickable(true);
                break;
            case R.id.analysis_buton_game:
                v.setClickable(false);
                goAnalysis();
                v.setClickable(true);
                break;
            case R.id.game_return:
                finish();
                Intent goMenu = new Intent(this, MainActivity.class);
                startActivity(goMenu);
                break;
            case R.id.game_replay:
                finish();
                break;
            case R.id.game_share:
                v.setClickable(false);
                if (isNetworkConnected()) {
                    share();
                } else {
                    Toast.makeText(GameResultActivity.this, getResources().getString(R.string.text_network), Toast.LENGTH_SHORT).show();
                }
                v.setClickable(true);
                break;
        }
    }

    boolean isShare = false;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (isShare) {
            newFragment.RequestData();
            isShare = false;
        }
    }

    CustomFacebookDialog newFragment;

    private void showCustomDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        newFragment = new CustomFacebookDialog();
        newFragment.show(fragmentManager, "dialog");
    }

    private void share() {
        String temp = "";
        switch (dir) {
            case "com":
                temp = getResources().getString(R.string.text_com);
                break;
            case "mat":
                temp = getResources().getString(R.string.text_mat);
                break;
            case "m&s":
                temp = getResources().getString(R.string.text_mech);
                break;
            case "draw":
                temp = getResources().getString(R.string.text_draw);
                break;
        }

        if (AccessToken.getCurrentAccessToken() != null) {
            ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                    .putString("og:type", "enginnerchallenge:score")
                    .putString("og:title", game_rate.getText().toString() + " (" + score_y + "/" + score_max + ")")
                    //.putString("og:image", MainActivity.app_icon)
                    .putString("og:description", "Subject: " + temp)
                    .build();

            ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                    .setActionType("enginnerchallenge:get")
                    .putObject("score", object)
                    .build();


            ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
                    .setPreviewPropertyName("score")
                    .setAction(action)
                    .build();

            shareDialog.show(content);
        } else {
            isShare = true;
            Toast.makeText(GameResultActivity.this, getResources().getString(R.string.text_connect_facebook), Toast.LENGTH_SHORT).show();
            showCustomDialog();
        }
    }

    public void checkAchievement(Context context, MyDbHelper db) {
        int[] p_data = MainActivity.get_p_data(context);
        ArrayList<AchievementData> achi = db.selectAllAchData();
        for (int i = 0; i < achi.size(); i++) {
            if (!achi.get(i).isHave()) {
                if (p_data[i] >= achi.get(i).getCondition()) {
                    achi.get(i).setGoal(achi.get(i).getCondition());
                    achi.get(i).setHave(1);
                    showUnlock(achi.get(i).getName(), achi.get(i).getImgID());
                }
                db.update(DataContract.AchievementEntry.TABLE_NAME, DataContract.AchievementEntry.GOAL, i + 1, p_data[i]);
                db.update(DataContract.AchievementEntry.TABLE_NAME, DataContract.AchievementEntry.HAVE, i + 1, (achi.get(i).isHave() ? 1 : 0));
            }
        }
    }

    private void showUnlock(String name, int imgID) {
        Toast toast = new Toast(getBaseContext());
        toast.setDuration(Toast.LENGTH_LONG);
        LayoutInflater inflater = getLayoutInflater();
        View toastLayout = inflater.inflate(R.layout.toast_achievement, (ViewGroup) findViewById(R.id.toast_achievement));
        ImageView image = (ImageView) toastLayout.findViewById(R.id.toast_image);
        image.setImageResource(imgID);
        Text text = (Text) toastLayout.findViewById(R.id.toast_message);
        text.setText(name);
        toast.setView(toastLayout);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
