package com.weekly.engineer.challenge.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.weekly.engineer.challenge.R;
import com.weekly.engineer.challenge.data.AchievementData;

import java.util.List;

public class AchievementAdapter extends BaseAdapter {

    private Context context;

    private List<AchievementData> listData;

    public AchievementAdapter(Context context, List<AchievementData> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public AchievementData getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AchievementData entry = listData.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_list_achievement, null);
        }

        ImageView img = (ImageView) convertView.findViewById(R.id.achievement_item_img);
        ImageView lock = (ImageView) convertView.findViewById(R.id.achievement_item_unlock);
        ImageView lock_two = (ImageView) convertView.findViewById(R.id.achievement_item_unlock_two);
        ImageView lock_three = (ImageView) convertView.findViewById(R.id.achievement_item_unlock_three);
        Text name = (Text) convertView.findViewById(R.id.achievement_item_name);
        Text bar = (Text) convertView.findViewById(R.id.achievement_item_bar);
        AchievementProgress progress = (AchievementProgress) convertView.findViewById(R.id.achievement_item_progress);


        name.setText(entry.getName());
        if(entry.getGoal()<entry.getCondition()){
            bar.setText(entry.getGoal() + "/" + entry.getCondition());
        }else{
            bar.setText(entry.getGoal()+"");
        }
        img.setImageResource(entry.getImgID());
        progress.setData(entry.getCondition(), entry.getGoal());

        if (entry.isHave()) {
            lock.setImageResource(R.drawable.game_star_full);
            lock_two.setImageResource(R.drawable.game_star_full);
            lock_three.setImageResource(R.drawable.game_star_full);
        } else {
            lock.setImageResource(R.drawable.game_star_blank);
            lock_two.setImageResource(R.drawable.game_star_blank);
            lock_three.setImageResource(R.drawable.game_star_blank);
        }

        return convertView;
    }
}