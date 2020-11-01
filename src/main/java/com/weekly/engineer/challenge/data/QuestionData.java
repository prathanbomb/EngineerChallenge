package com.weekly.engineer.challenge.data;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestionData implements Parcelable {
    String[] data;

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public QuestionData(String[] data) {
        this.data = data;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(this.data);
    }

    protected QuestionData(Parcel in) {
        this.data = in.createStringArray();
    }

    public static final Creator<QuestionData> CREATOR = new Creator<QuestionData>() {
        @Override
        public QuestionData createFromParcel(Parcel source) {
            return new QuestionData(source);
        }

        @Override
        public QuestionData[] newArray(int size) {
            return new QuestionData[size];
        }
    };
}
