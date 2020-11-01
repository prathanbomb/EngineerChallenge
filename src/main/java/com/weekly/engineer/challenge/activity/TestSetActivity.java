package com.weekly.engineer.challenge.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.weekly.engineer.challenge.R;
import com.weekly.engineer.challenge.data.MyDbHelper;
import com.weekly.engineer.challenge.data.QuestionData;
import com.weekly.engineer.challenge.view.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class TestSetActivity extends AppCompatActivity implements View.OnClickListener {

    private Text tv_sub, tv_best;
    private String dir, file_name;
    private int sub, sub_index, amount;
    private MyDbHelper db;
    FloatingActionButton sub_left,sub_right,start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_set);

        sub_left = (FloatingActionButton) findViewById(R.id.test_sub_left);
        sub_right = (FloatingActionButton) findViewById(R.id.test_sub_right);
//        his = (FloatingActionButton) findViewById(R.id.test_history);
        start = (FloatingActionButton) findViewById(R.id.test_start);

        sub_index = 0;
        sub = MainActivity.subject[sub_index];
        dir = MainActivity.directory[sub_index];
        file_name = MainActivity.file_name[sub_index];

        amount = 100;
        tv_sub = (Text) findViewById(R.id.test_sub);
        tv_best = (Text) findViewById(R.id.test_best_score);
        db = new MyDbHelper(getBaseContext());


        sub_left.setOnClickListener(this);
        sub_right.setOnClickListener(this);
//        his.setOnClickListener(this);
        start.setOnClickListener(this);

        update();
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void selectSub(boolean side) {
        if (side) {
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
        dir = MainActivity.directory[sub_index];
        file_name = MainActivity.file_name[sub_index];
        update();
    }

    private void update() {
        tv_sub.setText(getString(sub));
        int[] data = db.selectTest();
        tv_best.setText(String.valueOf(data[sub_index]));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_sub_left:
                selectSub(false);
                break;
            case R.id.test_sub_right:
                selectSub(true);
                break;
            case R.id.test_start:
                new Progress().execute();
                break;
            /*case R.id.test_history:
                Intent goHistory = new Intent(getApplicationContext(), TestHistoryActivity.class);
                startActivity(goHistory);
                break;*/
        }
    }


    public class Progress extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        Intent goPlay;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(TestSetActivity.this);
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
            goPlay = new Intent(getApplicationContext(), TestPlayActivity.class);
            InputStream input;
            ArrayList<QuestionData> data = new ArrayList<>();

            try {
                input = getAssets().open(dir + "/" + file_name);
                data = MainActivity.readData(sub_index, amount, input);
                Thread.sleep(1000);
            } catch (IOException | InterruptedException e) {
                finish();
            }

            goPlay.putParcelableArrayListExtra(MainActivity.TAG_Q, data);
            goPlay.putExtra(MainActivity.TAG_MAX_Q, amount);
            goPlay.putExtra(MainActivity.TAG_DIR, dir);
            return null;
        }
    }

}
