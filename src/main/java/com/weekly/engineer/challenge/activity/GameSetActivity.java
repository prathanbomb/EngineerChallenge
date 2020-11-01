package com.weekly.engineer.challenge.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.github.clans.fab.FloatingActionButton;
import com.weekly.engineer.challenge.R;
import com.weekly.engineer.challenge.data.MyDbHelper;
import com.weekly.engineer.challenge.data.QuestionData;
import com.weekly.engineer.challenge.view.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GameSetActivity extends AppCompatActivity implements View.OnClickListener {

    private String time;
    private int help1, help2, sub, amount, sub_index, amount_index, topic_compro,topic_static, topic_index, topic;
    private Text tv_help1, tv_help2, tv_amount, tv_sub, tv_topic;
    private MyDbHelper db;
    private String dir, file_name;
    FloatingActionButton sub_left,sub_right,am_left,am_right,start,game_help1,game_help2,topic_left,topic_right;
    Button upgrade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_set);
        sub_index = 0;
        topic_index = 0;
        amount_index = 0;
        time = getString(R.string.text_time);
        sub = MainActivity.subject[sub_index];
        topic_compro = MainActivity.topic_compro[topic_index];
        topic_static = MainActivity.topic_static[topic_index];
        amount = 100;
        dir = MainActivity.directory[sub_index];
        file_name = MainActivity.file_name[sub_index];

        tv_sub = (Text) findViewById(R.id.game_sub);
        tv_topic = (Text) findViewById(R.id.game_topic);
        tv_amount = (Text) findViewById(R.id.game_amount);
        tv_help1 = (Text) findViewById(R.id.game_help1);
        tv_help2 = (Text) findViewById(R.id.game_help2);

        db = new MyDbHelper(getBaseContext());


        sub_left = (FloatingActionButton) findViewById(R.id.game_sub_left);
        sub_right = (FloatingActionButton)findViewById(R.id.game_sub_right);
       /* am_left= (FloatingActionButton) findViewById(R.id.game_amount_left);
        am_right = (FloatingActionButton)findViewById(R.id.game_amount_right);*/
        topic_left = (FloatingActionButton) findViewById(R.id.game_topic_left);
        topic_right = (FloatingActionButton) findViewById(R.id.game_topic_right);
        upgrade= (Button) findViewById(R.id.game_upgrade);
        start= (FloatingActionButton) findViewById(R.id.game_start);

        game_help1= (FloatingActionButton) findViewById(R.id.game_set_help1);
        game_help2= (FloatingActionButton) findViewById(R.id.game_set_help2);

        sub_left.setOnClickListener(this);
        sub_right.setOnClickListener(this);
        /*am_left.setOnClickListener(this);
        am_right.setOnClickListener(this);*/
        topic_left.setOnClickListener(this);
        topic_right.setOnClickListener(this);
        upgrade.setOnClickListener(this);
        start.setOnClickListener(this);

        game_help1.setClickable(false);
        game_help2.setClickable(false);
        update();
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    private void selectSub(boolean some) {
        if (some) {
            sub_index++;
        } else {
            sub_index--;
        }
        if (sub_index > 3) {
            sub_index = 0;
        }
        if (sub_index < 0) {
            sub_index = 3;
        }
        sub = MainActivity.subject[sub_index];
        topic_compro = MainActivity.topic_compro[0];
        dir = MainActivity.directory[sub_index];
        file_name = MainActivity.file_name[sub_index];
        update();
    }

    private void selectTopic(boolean side) {
        if (side) {
            topic_index++;
        } else {
            topic_index--;
        }
        if (topic_index > 8) {
            topic_index = 0;
        }
        if (topic_index < 0) {
            topic_index = 8;
        }
        if (sub_index == 0){
            topic_compro = MainActivity.topic_compro[topic_index];
        } else if (sub_index == 1){
            topic_compro = MainActivity.topic_material[topic_index];
        } else if (sub_index == 2){
            topic_compro = MainActivity.topic_static[topic_index];
        }else {
            topic_compro = MainActivity.topic_drawings[topic_index];
        }
        update();
    }

    private void update() {

        int[] data = db.selectGame();

        int c_h1 = data[1];
        int c_h2 = data[2];


        //help1 = MainActivity.help_num[c_h1];
        //help2 = MainActivity.help_num[c_h2];
        //MainActivity.help1 = "1";
        help1 = Integer.parseInt(MainActivity.help1);
        help2 = Integer.parseInt(MainActivity.help2);

        tv_sub.setText(getString(sub));
        tv_topic.setText(getText(topic_compro));
        tv_amount.setText("02:00");
        String help1 = time + this.help1;
        String help2 = time + this.help2;
        tv_help1.setText(help1);
        tv_help2.setText(help2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.game_sub_left:
                selectSub(false);
                break;
            case R.id.game_sub_right:
                selectSub(true);
                break;
            case R.id.game_topic_left:
                selectTopic(false);
                break;
            case R.id.game_topic_right:
                selectTopic(true);
                break;
            case R.id.game_start:
                v.setClickable(false);
                new Progress().execute();
                v.setClickable(true);
                break;
            case R.id.game_upgrade:
                v.setClickable(false);
                goUpgrade();
                v.setClickable(true);
                break;
        }
    }

    private void goUpgrade() {
        Intent goUpgrade = new Intent(getApplicationContext(), GameUpgradeActivity.class);
        startActivity(goUpgrade);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public class Progress extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        Intent goPlay;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(GameSetActivity.this);
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
            startActivity(goPlay);
        }

        @Override
        protected Void doInBackground(Void... params) {
            goPlay = new Intent(getApplicationContext(), GamePlayActivity.class);
            InputStream input;
            ArrayList<QuestionData> data = new ArrayList<>();

            try {
                input = getAssets().open(dir + "/" + file_name);
                data = MainActivity.readData(sub_index, amount, input);
                Thread.sleep(1000);
            } catch (IOException | InterruptedException e) {
                finish();
            }

            goPlay.putParcelableArrayListExtra(MainActivity.TAG_Q,data);
            goPlay.putExtra(MainActivity.TAG_HELP1, help1);
            goPlay.putExtra(MainActivity.TAG_HELP2, help2);
            goPlay.putExtra(MainActivity.TAG_MAX_Q, amount);
            goPlay.putExtra(MainActivity.TAG_DIR, dir);
            return null;
        }
    }

}