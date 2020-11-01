package com.weekly.engineer.challenge.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.antonyt.infiniteviewpager.InfinitePagerAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.weekly.engineer.challenge.R;
import com.weekly.engineer.challenge.data.QuestionData;
import com.weekly.engineer.challenge.fragment.PlayTestFragment;
import com.weekly.engineer.challenge.view.Text;

import java.util.ArrayList;

public class TestPlayActivity extends AppCompatActivity implements View.OnClickListener, MainActivity.onEventListener {

    private int max_question;
    private String dir;
    private ArrayList<QuestionData> data;
    private ViewPager mPager;
    PagerAdapter wrappedAdapter;
    private String ans[] = new String[100];
    FloatingActionButton fab;
    private Text page, time, count;
    private Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_play);

        for (int i = 0; i < 100; i++) {
            ans[i] = "0";
        }

        getData();
        mPager = (ViewPager) findViewById(R.id.test_pager);

        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        wrappedAdapter = new InfinitePagerAdapter(mPagerAdapter);
        mPager.setAdapter(wrappedAdapter);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        time = (Text) findViewById(R.id.test_play_time);
        page = (Text) findViewById(R.id.test_play_question_total);
        count = (Text) findViewById(R.id.test_play_choose_total);


        fab.setOnClickListener(this);

        count();
        Time();

    }

    private void Time() {
        t = new Thread() {
            int i = 10800;

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (i > 0) {
                                    int h = i / 3600;
                                    int m = (i % 3600) / 60;
                                    int s = (i % 3600) % 60;
                                    String temp = h + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
                                    time.setText(temp);
                                } else {
                                    new Progress().execute(count.getText().toString());
                                }
                            }
                        });
                        i--;
                    }
                } catch (InterruptedException e) {
                    finish();
                }
            }
        };
        t.start();

    }

    private void getData() {
        Intent getResult = getIntent();
        max_question = getResult.getIntExtra(MainActivity.TAG_MAX_Q, 0);
        dir = getResult.getStringExtra(MainActivity.TAG_DIR);
        data = getResult.getParcelableArrayListExtra(MainActivity.TAG_Q);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                v.setClickable(false);
                new Progress().execute(count.getText().toString());
                v.setClickable(true);
                break;
        }
    }

    @Override
    public void chooseEvent(String ans) {
        int position = mPager.getCurrentItem();
        this.ans[position] = ans;
        count();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        t.interrupt();
                        finish();
                    }
                }).create().show();
    }

    int i =0 ;
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            page.setText((mPager.getCurrentItem() + 1) + "/" + max_question);
            return PlayTestFragment.newInstance(data.get(i++).getData(), dir, ans[i++]);
        }


        @Override
        public int getCount() {
            return max_question;
        }
    }

    private void count() {
        int count = 100;
        for (String an : this.ans) {
            if (!an.equals("0")) {
                count--;
            }
        }
        this.count.setText(String.valueOf(count));
    }

    public class Progress extends AsyncTask<String, Void, Void> {
        ProgressDialog progressDialog;
        Intent itn;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(TestPlayActivity.this);
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

            t.interrupt();
            startActivity(itn);
            finish();
        }

        @Override
        protected Void doInBackground(String... params) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                finish();
            }

            int correct = 0;
            for (int i = 0; i < data.size(); i++) {
                if (ans[i].equals(data.get(i).getData()[7])) {
                    correct++;
                }
            }
            itn = new Intent(getApplicationContext(), TestResultActivity.class);
            itn.putParcelableArrayListExtra(MainActivity.TAG_Q, data);
            itn.putExtra(MainActivity.TAG_MAX_Q, max_question);
            itn.putExtra(MainActivity.TAG_CORRECT, correct);
            itn.putExtra(MainActivity.TAG_COUNT, 100 - Integer.valueOf(params[0]));
            itn.putExtra(MainActivity.TAG_DIR, dir);
            return null;
        }
    }
}
