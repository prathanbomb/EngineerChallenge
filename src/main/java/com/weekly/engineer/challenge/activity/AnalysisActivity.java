package com.weekly.engineer.challenge.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.weekly.engineer.challenge.R;
import com.weekly.engineer.challenge.data.HistoryData;
import com.weekly.engineer.challenge.data.MyDbHelper;
import com.weekly.engineer.challenge.view.HistoryAdapter;

import java.util.ArrayList;

public class AnalysisActivity extends AppCompatActivity {
    ListView lv;
    private HistoryAdapter adapter;
    private MyDbHelper db;
    private ArrayList<HistoryData> listOfData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        lv = (ListView) findViewById(R.id.analysis_listview);

       /* listOfData = new ArrayList<>();
        listOfData = db.selectAllData();
        lv.setAdapter(adapter);*/
    }
}
