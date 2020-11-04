package com.weekly.engineer.challenge.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.weekly.engineer.challenge.R;
import com.weekly.engineer.challenge.data.HelpData;

import java.util.List;

public class HelpAdapter extends BaseAdapter {

    private Context context;

    private List<HelpData> listData;

    public HelpAdapter(Context context, List<HelpData> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public HelpData getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    LinearLayout layout_detail;
    ImageView img;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HelpData entry = listData.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_list_help, null);
        }

        Text title = (Text) convertView.findViewById(R.id.help_title);
        Text detail = (Text) convertView.findViewById(R.id.help_detail);
        img = (ImageView) convertView.findViewById(R.id.help_plus);
        layout_detail = (LinearLayout) convertView.findViewById(R.id.help_layout_detail);

        title.setText(entry.getTitle());
        detail.setText(entry.getDetail());

        img.setImageResource(R.drawable.main_plus);
        layout_detail.setVisibility(View.GONE);

        return convertView;
    }
}
