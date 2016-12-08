package com.oriental.coach.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author lwz
 * @Date 2016/8/16 0016
 * @Describe 记录
 */
public class CarRecord implements Parcelable {
    // 开始日期
    public String startDate;
    // 结束日期
    public String endDate;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
    }

    public CarRecord() {
    }

    protected CarRecord(Parcel in) {
        this.startDate = in.readString();
        this.endDate = in.readString();
    }

    public static final Creator<CarRecord> CREATOR = new Creator<CarRecord>() {
        @Override
        public CarRecord createFromParcel(Parcel source) {
            return new CarRecord(source);
        }

        @Override
        public CarRecord[] newArray(int size) {
            return new CarRecord[size];
        }
    };
}
