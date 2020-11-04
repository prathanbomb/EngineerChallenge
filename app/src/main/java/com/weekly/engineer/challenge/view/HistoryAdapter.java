package com.weekly.engineer.challenge.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.weekly.engineer.challenge.R;
import com.weekly.engineer.challenge.data.HistoryData;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends BaseAdapter {

    private Context context;

    private List<HistoryData> listData;

    public HistoryAdapter(Context context, List<HistoryData> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public HistoryData getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HistoryData entry = listData.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_list_history, null);
        }
        Text no = (Text) convertView.findViewById(R.id.item_his_no);
        Text sub = (Text) convertView.findViewById(R.id.item_his_sub);
        Text score = (Text) convertView.findViewById(R.id.item_his_score);

        no.setText(String.valueOf(entry.getNo()));
        sub.setText(entry.getSubject());
        score.setText(entry.getScore()+" %");

        return convertView;
    }
    public void clear(){
        this.listData.clear();
    }

    public void addArrayList(ArrayList<HistoryData> data){
        this.listData = data;
        notifyDataSetChanged();
    }

}
