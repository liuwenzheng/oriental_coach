package com.oriental.coach.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author lwz
 * @Date 2016/8/16 0016
 * @Describe 保险记录
 */
public class InsuranceRecord implements Parcelable {
    // 公司
    public String company;
    // 金额
    public String price;
    // 投保日期
    public String startDate;
    // 结束日期
    public String endDate;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.company);
        dest.writeString(this.price);
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
    }

    public InsuranceRecord() {
    }

    protected InsuranceRecord(Parcel in) {
        this.company = in.readString();
        this.price = in.readString();
        this.startDate = in.readString();
        this.endDate = in.readString();
    }

    public static final Parcelable.Creator<InsuranceRecord> CREATOR = new Parcelable.Creator<InsuranceRecord>() {
        @Override
        public InsuranceRecord createFromParcel(Parcel source) {
            return new InsuranceRecord(source);
        }

        @Override
        public InsuranceRecord[] newArray(int size) {
            return new InsuranceRecord[size];
        }
    };
}
