package com.weekly.engineer.challenge.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.widget.ShareDialog;
import com.github.clans.fab.FloatingActionButton;
import com.weekly.engineer.challenge.R;
import com.weekly.engineer.challenge.data.AchievementData;
import com.weekly.engineer.challenge.data.MyDbHelper;
import com.weekly.engineer.challenge.fragment.AchievementTabFragment;
import com.weekly.engineer.challenge.view.CustomFacebookDialog;
import com.weekly.engineer.challenge.view.Text;

import java.util.ArrayList;

public class AchievementActivity extends AppCompatActivity implements MainActivity.onEventAchiListener, View.OnClickListener {
    ViewPager viewPager;
    TabLayout tabLayout;
    private MyDbHelper db;
    private Text name, detail;
    private ImageView img;
    FloatingActionButton share;
    boolean click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_achievement);

        viewPager = (ViewPager) findViewById(R.id.achievement_viewPager);

        tabLayout = (TabLayout) findViewById(R.id.achievement_tab);
        name = (Text) findViewById(R.id.achievement_name);
        detail = (Text) findViewById(R.id.achievement_detail);
        img = (ImageView) findViewById(R.id.achievement_img);
        share = (FloatingActionButton) findViewById(R.id.achievement_share);

        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        Typeface tf = Typeface.createFromAsset(getResources().getAssets(), "font/RSU_BOLD.ttf");

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof AppCompatTextView) {
                    ((TextView) tabViewChild).setTypeface(tf, Typeface.NORMAL);
                }
            }
        }

        db = new MyDbHelper(getBaseContext());
        click = false;
        share.setOnClickListener(this);

        shareDialog = new ShareDialog(this);
        callbackManager = CallbackManager.Factory.create();
    }

    boolean complete = false;
    String title = "";
    String point = "";
    String des = "";
    int imgId;
    int position;

    private void update() {
        this.name.setText(title + " " + point);
        this.detail.setText(des);
        this.img.setImageResource(imgId);
    }

    @Override
    public void chooseEvent(String name, int condition, int goal, String detail, int img_id, boolean isComplete, int position) {
        title = name;
        if (goal >= condition)
            point = "(" + goal + ")";
        else
            point = "(" + goal + "/" + condition + ")";
        this.position = position;
        des = detail;
        imgId = img_id;
        this.complete = isComplete;
        this.click = true;
        update();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View v) {
        if (click) {
            if (complete) {
                if (isNetworkConnected()) {
                    share();
                } else {
                    Toast.makeText(AchievementActivity.this, getResources().getString(R.string.text_network), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AchievementActivity.this, getResources().getString(R.string.text_not_complete), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AchievementActivity.this, getResources().getString(R.string.text_select), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    CallbackManager callbackManager;
    ShareDialog shareDialog;
    CustomFacebookDialog newFragment;
    boolean isShare = false;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (isShare) {
            newFragment.RequestData();
            isShare = false;
        }
    }


    private void showCustomDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        newFragment = new CustomFacebookDialog();
        newFragment.show(fragmentManager, "dialog");
    }

    private void share() {
        if (AccessToken.getCurrentAccessToken() != null) {
            ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                    .putString("og:type", "enginnerchallenge:achievement")
                    .putString("og:title", title)
                    .putString("og:image", tabLayout.getSelectedTabPosition() == 0 ? MainActivity.game_achieve[position] : MainActivity.test_achieve[position])
                    .putString("og:description", des)
                    .build();

            ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                    .setActionType("enginnerchallenge:reach")
                    .putObject("achievement", object)
                    .build();


            ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
                    .setPreviewPropertyName("achievement")
                    .setAction(action)
                    .build();

            shareDialog.show(content);
        } else {
            isShare = true;
            Toast.makeText(AchievementActivity.this, getResources().getString(R.string.text_connect_facebook), Toast.LENGTH_SHORT).show();
            showCustomDialog();
        }
    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;
        private String tabTitles[] = new String[]{"Game", "Test",};

        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            ArrayList<AchievementData> data = db.selectAllAchData(position == 0);
            return AchievementTabFragment.newInstance(data);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
