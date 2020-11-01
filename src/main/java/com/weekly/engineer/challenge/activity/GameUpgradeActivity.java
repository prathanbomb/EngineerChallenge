package com.weekly.engineer.challenge.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.weekly.engineer.challenge.R;
import com.weekly.engineer.challenge.data.AchievementData;
import com.weekly.engineer.challenge.data.DataContract;
import com.weekly.engineer.challenge.data.MyDbHelper;
import com.weekly.engineer.challenge.view.Text;
import com.weekly.engineer.challenge.view.UpgradeButton;

import java.util.ArrayList;

public class GameUpgradeActivity extends AppCompatActivity implements View.OnClickListener {

    private Text h1,h2, c ;
    private UpgradeButton bh1, bh2;
    private int current_h1, current_h2, coin;
    private MyDbHelper db;
    private int num_help1 = Integer.parseInt(MainActivity.help1);
    private int num_help2 = Integer.parseInt(MainActivity.help2);
    FloatingActionButton img_h1, img_h2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_upgrade);

        img_h1 = (FloatingActionButton) findViewById(R.id.u_h1);
        img_h2 = (FloatingActionButton) findViewById(R.id.u_h2);
        h1 = (Text) findViewById(R.id.upgrade_help1);
        h2 = (Text) findViewById(R.id.upgrade_help2);
        c = (Text) findViewById(R.id.upgrade_coin);
        bh1 = (UpgradeButton) findViewById(R.id.upgrade_bt_help1);
        bh2 = (UpgradeButton) findViewById(R.id.upgrade_bt_help2);

        img_h1.setClickable(false);
        img_h2.setClickable(false);
        db = new MyDbHelper(getBaseContext());
        int[] data = db.selectGame();
        current_h1 = data[1];
        current_h2 = data[2];
        coin = data[0];

        bh1.setImageView(R.drawable.game_coin);
        bh2.setImageView(R.drawable.game_coin);

        bh1.setOnClickListener(this);
        bh2.setOnClickListener(this);

        update();
    }

    private void update() {
        c.setText(String.valueOf(coin));
        h1.setText(getString(R.string.text_use) + " " + String.valueOf(MainActivity.help_num[current_h1]) + " " + getString(R.string.text_times));
        h2.setText(getString(R.string.text_use) + " " + String.valueOf(MainActivity.help_num[current_h2]) + " " + getString(R.string.text_times));

        if (current_h1 != MainActivity.help_upgrade.length)
            bh1.setTextTitle(String.valueOf(MainActivity.help_upgrade[current_h1]));
        else
            bh1.setTextTitle(getString(R.string.text_max));

        if (current_h2 != MainActivity.help_upgrade.length)
            bh2.setTextTitle(String.valueOf(MainActivity.help_upgrade[current_h2]));
        else
            bh2.setTextTitle(getString(R.string.text_max));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upgrade_bt_help1:
                v.setClickable(false);
                if (coin > MainActivity.help_upgrade[current_h1]) {
                    num_help1++;
                    MainActivity.help1 = String.valueOf(num_help1);
                    coin = coin - MainActivity.help_upgrade[current_h1];
                    current_h1++;
                    db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.COIN, coin);
                    db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.USE_COIN, db.select(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.USE_COIN) + MainActivity.help_upgrade[current_h1 - 1]);
                    db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.HELP_1, current_h1);
                    update();
                    checkAchievement(getBaseContext(), db);
                } else {
                    new AlertDialog.Builder(this)
                            .setMessage("Not enough coins")
                            .setNegativeButton(android.R.string.ok, null).create().show();
                }
                v.setClickable(true);
                break;
            case R.id.upgrade_bt_help2:
                v.setClickable(false);
                if (coin > MainActivity.help_upgrade[current_h2]) {
                    num_help2++;
                    MainActivity.help2 = String.valueOf(num_help2);
                    coin = coin - MainActivity.help_upgrade[current_h2];
                    current_h2++;
                    db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.COIN, coin);
                    db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.USE_COIN, db.select(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.USE_COIN) + MainActivity.help_upgrade[current_h2 - 1]);
                    db.update(DataContract.PersonalEntry.TABLE_NAME, DataContract.PersonalEntry.HELP_2, current_h2);
                    update();
                    checkAchievement(getBaseContext(), db);
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("Not enough coins")
                            .setNegativeButton(android.R.string.ok, null).create().show();
                }
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
        toast.setDuration(Toast.LENGTH_LONG);
        LayoutInflater inflater = getLayoutInflater();
        View toastLayout = inflater.inflate(R.layout.toast_achievement, (ViewGroup) findViewById(R.id.toast_achievement));
        ImageView image = (ImageView) toastLayout.findViewById(R.id.toast_image);
        image.setImageResource(imgID);
        Text text = (Text) toastLayout.findViewById(R.id.toast_message);
        text.setText(name);
        toast.setView(toastLayout);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
