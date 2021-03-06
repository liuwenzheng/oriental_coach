package com.oriental.coach.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.oriental.coach.net.resp.CarResult;

import java.util.List;

/**
 * @Author lwz
 * @Date 2016/11/28 0028
 * @Describe
 */

public class Teacher implements Parcelable {
    // 名字
    public String name;
    // 驾龄
    public int carAge;
    // 驾校
    public String school;
    // 电话号码
    public String phoneNo;
    // 性别
    public String gender;
    // 头像url
    public String logo;
    // 学生数量
    public int studentCnt;
    // 好评率
    public String goodCommPro;
    // 培训科目
    public String courseType;
    // 教练地址
    public String address;
    // 教练用车
    public List<CarResult> carResults;
    // 教练id
    public String teacharId;
    // 教练评分
    public double teacharGrade;
    // 教练类型
    public int teacharJobType;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.carAge);
        dest.writeString(this.school);
        dest.writeString(this.phoneNo);
        dest.writeString(this.gender);
        dest.writeString(this.logo);
        dest.writeInt(this.studentCnt);
        dest.writeString(this.goodCommPro);
        dest.writeString(this.courseType);
        dest.writeString(this.address);
        dest.writeTypedList(this.carResults);
        dest.writeString(this.teacharId);
        dest.writeDouble(this.teacharGrade);
        dest.writeInt(this.teacharJobType);
    }

    public Teacher() {
    }

    protected Teacher(Parcel in) {
        this.name = in.readString();
        this.carAge = in.readInt();
        this.school = in.readString();
        this.phoneNo = in.readString();
        this.gender = in.readString();
        this.logo = in.readString();
        this.studentCnt = in.readInt();
        this.goodCommPro = in.readString();
        this.courseType = in.readString();
        this.address = in.readString();
        this.carResults = in.createTypedArrayList(CarResult.CREATOR);
        this.teacharId = in.readString();
        this.teacharGrade = in.readDouble();
        this.teacharJobType = in.readInt();
    }

    public static final Creator<Teacher> CREATOR = new Creator<Teacher>() {
        @Override
        public Teacher createFromParcel(Parcel source) {
            return new Teacher(source);
        }

        @Override
        public Teacher[] newArray(int size) {
            return new Teacher[size];
        }
    };
}
