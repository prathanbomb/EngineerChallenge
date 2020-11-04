package com.weekly.engineer.challenge.view;

import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.weekly.engineer.challenge.R;
import com.weekly.engineer.challenge.data.HelpData;

import java.util.ArrayList;

public class CustomHelpDialog extends DialogFragment implements AdapterView.OnItemClickListener {

    ArrayList<HelpData> data;
    HelpAdapter adapter;
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_help_dialog, container, false);
        listView = (ListView) rootView.findViewById(R.id.help_listView);
        data = new ArrayList<>();

        data.add(new HelpData("Game mode", "โหมดเกมจะประกอบด้วยฟังก์ชันดังนี้ \n" +
                "\tฟังก์ชันสำหรับเลือกวิชาประกอบด้วย วิชาการเขียนโปรแกรมเบื้องต้น วิชาการเขียนแบบเชิงวิศวกรรม วิชาวัสดุศาสตร์ และวิชากลศาสตร์วิศวกรรม\n" +
                "\tฟังก์ชันสำหรับเลือกจำนวนข้อโดยขั้นต่ำต้องเลือก 20 ข้อ มากสุด 100 ข้อ\n" +
                "\tฟังก์ชันสำหรับการอัปเกรดตัวช่วย โดยฟังก์ชันนี้จะสามารถอัปเกรดเพื่อเพิ่มจำนวนตัวช่วยโดยการใช้จำนวนเหรียญที่ได้รับจากการเล่น การที่จะอัปเกรดได้ต้องมีจำนวนเหรียญเพียงพอต่อการอัปเกรด โดยตัวช่วยนั้นจะมีสองแบบ คือ การตัด 2 คำตอบ และ ตอบได้ 2 ครั้ง\n" +
                "\tในโหมดเกมเมื่อกดปุ่มเริ่มเล่นเกมแล้ว จะสามารถใช้ตัวช่วยได้ในขณะเล่น ได้ตามจำนวนตัวช่วยที่สามรถใช้อยู่ในปัจจุบัน และโหมดเกมจะทำการตรวจคำตอบหลังจากกดเลือกคำตอบทันที\n" +
                "\tเมื่อเล่นโหมดเกมเสร็จจะแสดงหน้าผลคะแนน โดยในหน้าผลคะแนนจะประกอบด้วยจำวนวนคะแนนที่ได้ ระดับคะแนน และเหรียญที่ได้จากการเล่นเกม และสามารถดูผลเฉลยได้จากการกดฟังก์ชันดูผลเฉลย" ));
        data.add(new HelpData("Test mode", "โหมดเกมจะประกอบด้วยฟังก์ชันดังนี้ \n" +
                "\tฟังก์ชันสำหรับเลือกวิชาประกอบด้วย วิชาการเขียนโปรแกรมเบื้องต้น วิชาการเขียนแบบเชิงวิศวกรรม วิชาวัสดุศาสตร์ และวิชากลศาสตร์วิศวกรรม\n" +
                "\tฟังก์ชันสำหรับการดูประวัติย้อนหลังโดยจะเป็นการกราฟแสดงจำนวนคำแนนของแต่ละครั้งของการสอบ และรายละเอียดของการสอบแต่ละครั้ง\n" +
                "\tในโหมดเกมนั้นจำนวนข้อสอบจะกำหนดสำหรับการทำแต่ละครั้งคือ 100 ข้อ และมีเวลาในการทำข้อสอบทั้งหมด 3 ชั่วโมง และจะมีการแสดงคะแนนสูงสุดของแต่ละวิชา\n" +
                "\tเมื่อกดปุ่มเริ่มทำการทดสอบในโหดมทดสอบนั้นจะทำการตรวจคำตอบรอบเดียว จะทำการตรวจเมื่อหมดเวลาในการทำข้อสอบหรือ กดปุ่มส่งข้อสอบ\n" +
                "\tหลังจากทำการตรวจคำตอบแล้วจะแสดงหน้าคะแนน โดยในหน้าแสดงผลคะแนนนั้นจะประกอบไปด้วยคะแนนที่ได้ กราฟประวัติของผลการทดสอบของครั้งก่อนหน้า และสามารถดูผลเฉลยได้จากการกดฟังก์ชันดูผลเฉลย\t"));
        data.add(new HelpData("Achievement", "โหมดความสำเร็จสำหรับโหมดนี้จะประกอบด้วย รายการความสำเร็จต่างๆที่มีโดย จะบอกเงื่อนไขต่างๆของแต่ละรายการความสำเร็จ และหลังจากผ่านเงื่อนไขแล้วจะปลดล๊อคความสำเร็จนั้นๆ และสามารถแชร์ความสำเร็จนั้นลงบนเฟซบุ๊คได้"));

        data.add(new HelpData("Credit", "ข้อสอบจากสภาวิศวะกร : http://www.coe.or.th/coe/main/coeHome.php?aMenu=70101\n"));

        adapter = new HelpAdapter(getActivity().getBaseContext(), data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView  img = (ImageView) view.findViewById(R.id.help_plus);
        LinearLayout layout_detail = (LinearLayout) view.findViewById(R.id.help_layout_detail);

        if(layout_detail.getVisibility() == View.GONE){
            img.setImageResource(R.drawable.main_minus);
            layout_detail.setVisibility(View.VISIBLE);
        }else{
            img.setImageResource(R.drawable.main_plus);
            layout_detail.setVisibility(View.GONE);
        }
    }
}
