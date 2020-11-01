package com.weekly.engineer.challenge.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.weekly.engineer.challenge.R;
import com.weekly.engineer.challenge.activity.MainActivity;
import com.weekly.engineer.challenge.data.AchievementData;
import com.weekly.engineer.challenge.view.AchievementAdapter;

import java.util.ArrayList;

public class AchievementTabFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String TAG_COUNT = "COUNT";
    public static final String TAG_NAME = "NAME";
    public static final String TAG_CONDITION = "CONDITION";
    public static final String TAG_GOAL = "GOAL";
    public static final String TAG_DETAIL = "DETAIL";
    public static final String TAG_HAVE = "HAVE";
    public static final String TAG_IMG_ID = "IMG_ID";

    private int count;
    private int[] condition, goal, img_id;
    private String[] name, detail;
    private boolean[] have;

    MainActivity.onEventAchiListener tListener;
    ArrayList<AchievementData> data;

    public static AchievementTabFragment newInstance(ArrayList<AchievementData> data) {
        Bundle args = new Bundle();
        String[] n = new String[data.size()];
        int[] c = new int[data.size()];
        int[] g = new int[data.size()];
        String[] d = new String[data.size()];
        boolean[] have = new boolean[data.size()];
        int[] img = new int[data.size()];

        for (int i = 0; i < data.size(); i++) {
            n[i] = data.get(i).getName();
            c[i] = data.get(i).getCondition();
            g[i] = data.get(i).getGoal();
            d[i] = data.get(i).getDetail();
            have[i] = data.get(i).isHave();
            img[i] = data.get(i).getImgID();
        }
        args.putInt(TAG_COUNT, data.size());
        args.putStringArray(TAG_NAME, n);
        args.putIntArray(TAG_CONDITION, c);
        args.putIntArray(TAG_GOAL, g);
        args.putStringArray(TAG_DETAIL, d);
        args.putBooleanArray(TAG_HAVE, have);
        args.putIntArray(TAG_IMG_ID, img);

        AchievementTabFragment fragment = new AchievementTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        count = getArguments().getInt(TAG_COUNT);
        name = getArguments().getStringArray(TAG_NAME);
        condition = getArguments().getIntArray(TAG_CONDITION);
        goal = getArguments().getIntArray(TAG_GOAL);
        img_id = getArguments().getIntArray(TAG_IMG_ID);
        detail = getArguments().getStringArray(TAG_DETAIL);
        have = getArguments().getBooleanArray(TAG_HAVE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_achievement_tab, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.achievement_tab_list);
        data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            data.add(new AchievementData(name[i], condition[i], goal[i], detail[i], have[i], img_id[i]));
        }

        AchievementAdapter adapter = new AchievementAdapter(getActivity().getBaseContext(), data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setSelection(0);
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity.onEventAchiListener) {
            tListener = (MainActivity.onEventAchiListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        tListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.setSelected(true);
        view.setActivated(true);
        tListener.chooseEvent(name[position],condition[position],goal[position],detail[position],img_id[position],have[position],position);
    }
}