package com.weekly.engineer.challenge.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.weekly.engineer.challenge.R;
import com.weekly.engineer.challenge.data.AchievementData;
import com.weekly.engineer.challenge.data.DataContract;
import com.weekly.engineer.challenge.data.HistoryData;
import com.weekly.engineer.challenge.data.MyDbHelper;
import com.weekly.engineer.challenge.data.QuestionData;
import com.weekly.engineer.challenge.view.Text;

import java.text.NumberFormat;
import java.util.ArrayList;

public class TestResultActivity extends AppCompatActivity implements View.OnClickListener {

    Text test_your_score;
    FloatingActionButton review, play_again, menu, history;
    private PointsGraphSeries<DataPoint> series1, series2, series3, series4;
    private ArrayList<HistoryData> data_sub_1, data_sub_2, data_sub_3, data_sub_4;
    private int score_y, score_max, count;
    private String dir;
    private MyDbHelper db;
    private ArrayList<QuestionData> data;
    private GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        db = new MyDbHelper(getBaseContext());

        test_your_score = (Text) findViewById(R.id.test_your_score);

        getData();

        test_your_score.setText(String.valueOf(score_y));

        review = (FloatingActionButton) findViewById(R.id.test_review);
        menu = (FloatingActionButton) findViewById(R.id.test_return);
        play_again = (FloatingActionButton) findViewById(R.id.test_replay);
        history = (FloatingActionButton) findViewById(R.id.test_result_history);
        graph = (GraphView) findViewById(R.id.test_result_graph);
        review.setOnClickListener(this);
        menu.setOnClickListener(this);
        play_again.setOnClickListener(this);
        history.setOnClickListener(this);
        saveData();
        checkAchievement(getBaseContext(), db);
        initGraph();

        switch (dir) {
            case "com":
                setGraph(1);
                break;
            case "mat":
                setGraph(2);
                break;
            case "m&s":
                setGraph(3);
                break;
            case "draw":
                setGraph(4);
                break;
        }
    }

    private void initGraph() {
        data_sub_1 = db.selectSubData(getResources().getString(MainActivity.subject[0]));
        data_sub_2 = db.selectSubData(getResources().getString(MainActivity.subject[1]));
        data_sub_3 = db.selectSubData(getResources().getString(MainActivity.subject[2]));
        data_sub_4 = db.selectSubData(getResources().getString(MainActivity.subject[3]));

        if (data_sub_1.size() != 0) {
            DataPoint[] data1 = new DataPoint[data_sub_1.size()];
            for (int i = 0; i < data1.length; i++) {
                data1[i] = new DataPoint(data_sub_1.get(data_sub_1.size() - 1 - i).getNo(), data_sub_1.get(data_sub_1.size() - 1 - i).getScore());
            }
            series1 = new PointsGraphSeries<>(data1);
        } else {
            series1 = new PointsGraphSeries<>();
        }
        if (data_sub_2.size() != 0) {
            DataPoint[] data2 = new DataPoint[data_sub_2.size()];
            for (int i = 0; i < data2.length; i++) {
                data2[i] = new DataPoint(data_sub_2.get(data_sub_2.size() - 1 - i).getNo(), data_sub_2.get(data_sub_2.size() - 1 - i).getScore());
            }
            series2 = new PointsGraphSeries<>(data2);
        } else {
            series2 = new PointsGraphSeries<>();
        }
        if (data_sub_3.size() != 0) {
            DataPoint[] data3 = new DataPoint[data_sub_3.size()];
            for (int i = 0; i < data3.length; i++) {
                data3[i] = new DataPoint(data_sub_3.get(data_sub_3.size() - 1 - i).getNo(), data_sub_3.get(data_sub_3.size() - 1 - i).getScore());
            }
            series3 = new PointsGraphSeries<>(data3);
        } else {
            series3 = new PointsGraphSeries<>();
        }
        if (data_sub_4.size() != 0) {
            DataPoint[] data4 = new DataPoint[data_sub_4.size()];
            for (int i = 0; i < data4.length; i++) {
                data4[i] = new DataPoint(data_sub_4.get(data_sub_4.size() - 1 - i).getNo(), data_sub_4.get(data_sub_4.size() - 1 - i).getScore());
            }
            series4 = new PointsGraphSeries<>(data4);
        } else {
            series4 = new PointsGraphSeries<>();
        }

        series1.setTitle("Programming");
        series2.setTitle("Materials");
        series3.setTitle("Mechanic");
        series4.setTitle("Drawings");

        series1.setColor(getResources().getColor(R.color.sub_1));
        series2.setColor(getResources().getColor(R.color.sub_2));
        series3.setColor(getResources().getColor(R.color.sub_3));
        series4.setColor(getResources().getColor(R.color.sub_4));

        series1.setSize(10);
        series2.setSize(10);
        series3.setSize(10);
        series4.setSize(10);

        graph.getGridLabelRenderer().setGridColor(Color.BLUE);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);

        NumberFormat nf = NumberFormat.getInstance();
        nf.setParseIntegerOnly(true);
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf, nf));
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
    }

    private void setGraph(int sub) {
        graph.removeAllSeries();
        switch (sub) {
            case 0:
                if (data_sub_1.size() != 0) {
                    graph.addSeries(series1);
                }
                if (data_sub_2.size() != 0) {
                    graph.addSeries(series2);
                }
                if (data_sub_3.size() != 0) {
                    graph.addSeries(series3);
                }
                if (data_sub_4.size() != 0) {
                    graph.addSeries(series4);
                }
                break;
            case 1:
                if (data_sub_1.size() != 0) {
                    graph.addSeries(series1);
                }
                break;
            case 2:

                if (data_sub_2.size() != 0) {
                    graph.addSeries(series2);
                }
                break;
            case 3:
                if (data_sub_3.size() != 0) {
                    graph.addSeries(series3);
                }
                break;
            case 4:
                if (data_sub_4.size() != 0) {
                    graph.addSeries(series4);
                }
                break;
        }
    }


    private void saveData() {
        String sub = "";
        String temp = "";
        String temp2 = "";
        String temp3 = "";
        switch (dir) {
            case "com":
                sub = getString(R.string.text_com);
                temp = DataContract.PersonalEntry.HI_SUB1;
                temp2 = DataContract.PersonalEntry.Q_TEST_SUB1;
                if (score_y >= 90) {
                    temp3 = DataContract.PersonalEntry.Q_TEST_MORE_SUB1;
                }
                break;
            case "mat":
                sub = getString(R.string.text_mat);
                temp = DataContract.PersonalEntry.HI_SUB2;
                temp2 = DataContract.PersonalEntry.Q_TEST_SUB2;
                if (score_y >= 90) {
                    temp3 = DataContract.PersonalEntry.Q_TEST_MORE_SUB2;
                }
                break;
            case "m&s":
                sub = getString(R.string.text_mech);
                temp = DataContract.PersonalEntry.HI_SUB3;
                temp2 = DataContract.PersonalEntry.Q_TEST_SUB3;
                if (score_y >= 90) {
                    temp3 = DataContract.PersonalEntry.Q_TEST_MORE_SUB3;
                }
                break;
            case "draw":
                sub = getString(R.string.text_draw);
                temp = DataContract.PersonalEntry.HI_SUB4;
                temp2 = DataContract.PersonalEntry.Q_TEST_SUB4;
                if (score_y >= 90) {
                    temp3 = DataContract.PersonalEntry.Q_TEST_MORE_SUB4;
                }
                break;
        }
        if (db.select(DataContract.PersonalEntry.TABLE_NAME, temp) < score_y)
            db.update(DataContract.PersonalEntry.TABLE_NAME, temp, score_y);

        db.insert(new HistoryData(sub, score_y));
        db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.Q_TEST_ALL, db.select(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.Q_TEST_ALL) + count);
        db.update(DataContract.PersonalEntry.TABLE_NAME, temp2, db.select(DataContract.PersonalEntry.TABLE_NAME, temp2) + count);
        if (!temp3.equals(""))
            db.update(DataContract.PersonalEntry.TABLE_NAME, temp3, db.select(DataContract.PersonalEntry.TABLE_NAME, temp3) + 1);
    }

    private void getData() {
        Intent itn = getIntent();
        score_max = itn.getIntExtra(MainActivity.TAG_MAX_Q, 0);
        dir = itn.getStringExtra(MainActivity.TAG_DIR);
        score_y = itn.getIntExtra(MainActivity.TAG_CORRECT, 0);
        count = itn.getIntExtra(MainActivity.TAG_COUNT, 0);
        data = itn.getParcelableArrayListExtra(MainActivity.TAG_Q);
    }

    private void goReview() {
        Intent itn = new Intent(getApplicationContext(), ReviewActivity.class);
        itn.putParcelableArrayListExtra(MainActivity.TAG_Q, data);
        itn.putExtra(MainActivity.TAG_MAX_Q, score_max);
        itn.putExtra(MainActivity.TAG_DIR, dir);

        startActivity(itn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_review:
                v.setClickable(false);
                goReview();
                v.setClickable(true);
                break;
            case R.id.test_return:
                finish();
                Intent goMenu = new Intent(this, MainActivity.class);
                startActivity(goMenu);
                break;
            case R.id.test_replay:
                finish();
                break;
            case R.id.test_result_history:
                v.setClickable(false);
                Intent gohistory = new Intent(this, TestHistoryActivity.class);
                startActivity(gohistory);
                v.setClickable(true);
                break;
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
        toast.setDuration(Toast.LENGTH_SHORT);
        LayoutInflater inflater = getLayoutInflater();
        View toastLayout = inflater.inflate(R.layout.toast_achievement, (ViewGroup) findViewById(R.id.toast_achievement));
        ImageView image = (ImageView) toastLayout.findViewById(R.id.image);
        image.setImageResource(imgID);
        Text text = (Text) toastLayout.findViewById(R.id.text);
        text.setText(name);
        toast.setView(toastLayout);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
