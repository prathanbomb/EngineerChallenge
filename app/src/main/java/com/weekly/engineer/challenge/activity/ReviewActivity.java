package com.weekly.engineer.challenge.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.antonyt.infiniteviewpager.InfinitePagerAdapter;
import com.weekly.engineer.challenge.R;
import com.weekly.engineer.challenge.data.QuestionData;
import com.weekly.engineer.challenge.fragment.ReviewFragment;
import com.weekly.engineer.challenge.view.Text;

import java.util.ArrayList;

public class ReviewActivity extends FragmentActivity {

    private int max_question;
    private String dir;
    private ArrayList<QuestionData> data;
    private Text page;
    private ViewPager mPager;
    PagerAdapter wrappedAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        getData();
        mPager = (ViewPager) findViewById(R.id.review_pager);

        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        wrappedAdapter = new InfinitePagerAdapter(mPagerAdapter);
        mPager.setAdapter(wrappedAdapter);

        page = (Text) findViewById(R.id.review_question_total);

    }

    private void getData() {
        data = new ArrayList<>();
        Intent getResult = getIntent();
        max_question = getResult.getIntExtra(MainActivity.TAG_MAX_Q, 0);
        dir = getResult.getStringExtra(MainActivity.TAG_DIR);
        data = getResult.getParcelableArrayListExtra(MainActivity.TAG_Q);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
    int i =0 ;

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            page.setText((mPager.getCurrentItem() + 1) + "/" + max_question);
            return ReviewFragment.newInstance(data.get(i++).getData(), dir);
        }

        @Override
        public int getCount() {
            return max_question;
        }
    }


}
