package com.weekly.engineer.challenge.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.weekly.engineer.challenge.R;

public class ProgressActivity extends AppCompatActivity implements View.OnClickListener{

    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        double y,x;
        x=-5.0;

        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        for(int i = 0; i<500; i++){
            x = x + 0.1;
            y = Math.sin(x);
            series.appendData(new DataPoint(x,y), true, 500);
        }
        graph.addSeries(series);

    }

    @Override
    public void onClick(View v) {

    }
}
