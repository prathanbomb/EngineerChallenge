package com.weekly.engineer.challenge.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.weekly.engineer.challenge.R;
import com.weekly.engineer.challenge.data.HistoryData;
import com.weekly.engineer.challenge.data.MyDbHelper;
import com.weekly.engineer.challenge.view.HistoryAdapter;
import com.weekly.engineer.challenge.view.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class TestHistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private HistoryAdapter adapter;
    private MyDbHelper db;
    ViewPager viewPager;
    ListView lv;
    TabLayout tabLayout;
    private GraphView graph;
    private ArrayList<HistoryData> listOfData;
    private PointsGraphSeries<DataPoint> series1, series2, series3, series4;
    private ArrayList<HistoryData> data_sub_1, data_sub_2, data_sub_3, data_sub_4;
    FloatingActionButton left, right;
    private int sub_index;
    private Text sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lv = (ListView) findViewById(R.id.history_listView);
        graph = (GraphView) findViewById(R.id.test_history_graph);
        sub = (Text) findViewById(R.id.test_history_sub);
        left = (FloatingActionButton) findViewById(R.id.test_history_left);
        right = (FloatingActionButton) findViewById(R.id.test_history_right);
        tabLayout = (TabLayout) findViewById(R.id.history_tab);
        tabLayout.setupWithViewPager(viewPager);
        viewPager = (ViewPager) findViewById(R.id.history_viewPager);
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
        Typeface tf = Typeface.createFromAsset(getResources().getAssets(), "font/RSU_BOLD.ttf");
        sub_index = 0;
        db = new MyDbHelper(getBaseContext());

        listOfData = new ArrayList<>();
        listOfData = db.selectAllData();

        adapter = new HistoryAdapter(getBaseContext(), listOfData);
        lv.setAdapter(adapter);

        initGraph();
        setGraph(sub_index);

        sub.setText(MainActivity.history_subject[sub_index]);

        left.setOnClickListener(this);
        right.setOnClickListener(this);
    }

    private void selectSub(boolean side) {
        if (side) {
            sub_index++;
        } else {
            sub_index--;
        }
        if (sub_index > 4) {
            sub_index = 0;
        }
        if (sub_index < 0) {
            sub_index = 4;
        }
        sub.setText(MainActivity.history_subject[sub_index]);
        setGraph(sub_index);

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
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(100);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        if (data_sub_1.size()> data_sub_2.size() && data_sub_1.size()> data_sub_3.size() && data_sub_1.size() > data_sub_4.size() ){
            graph.getViewport().setMaxX(data_sub_1.size());
        } else if (data_sub_2.size() >data_sub_3.size() && data_sub_2.size() > data_sub_4.size()){
            graph.getViewport().setMaxX(data_sub_2.size());
        } else if (data_sub_3.size() > data_sub_4.size()){
            graph.getViewport().setMaxX(data_sub_3.size());
        } else {
            graph.getViewport().setMaxX(data_sub_4.size());
        }
        graph.getViewport().setMaxX(data_sub_1.size());
        graph.getViewport().setScrollable(true);

        NumberFormat nf = NumberFormat.getInstance();
        nf.setParseIntegerOnly(true);
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf, nf));
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
    }

    private void setGraph(int sub_index) {
        graph.removeAllSeries();
        initGraph();
        switch (sub_index) {
            case 0:
                //if (data_sub_1.size() != 0) {
                    graph.addSeries(series1);
               // }
               // if (data_sub_2.size() != 0) {
                    graph.addSeries(series2);
               // }
              //  if (data_sub_3.size() != 0) {
                    graph.addSeries(series3);
                //}
                //if (data_sub_4.size() != 0) {
                    graph.addSeries(series4);
               // }
                adapter.clear();
                adapter.addArrayList(db.selectAllData());
                break;
            case 1:
              //  if (data_sub_1.size() != 0) {
                    graph.addSeries(series1);
               // }
                adapter.clear();
                adapter.addArrayList(data_sub_1);
                break;
            case 2:
               // if (data_sub_2.size() != 0) {
                    graph.addSeries(series2);
               // }
                adapter.clear();
                adapter.addArrayList(data_sub_2);
                break;
            case 3:
               // if (data_sub_3.size() != 0) {
                    graph.addSeries(series3);
               // }
                adapter.clear();
                adapter.addArrayList(data_sub_3);
                break;
            case 4:
               // if (data_sub_4.size() != 0) {
                    graph.addSeries(series4);
               // }
                adapter.clear();
                adapter.addArrayList(data_sub_4);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_history_left:
                selectSub(true);
                break;
            case R.id.test_history_right:
                selectSub(false);
                break;
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "Game");
        adapter.addFragment(new HistoryTest(), "Test");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
