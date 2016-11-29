package com.oriental.coach.net.resp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author lwz
 * @Date 2016/11/29 0029
 * @Describe
 */

public class CarResult implements Parcelable {
    /**
     * carId : fc3551342a554f37a44304022c6c1f2c
     * schoolId : af8fd49c34e345799a8269110bc93038
     * carName : 比亚迪
     * mileage : 6000
     * carType : 1
     * carCode : 晋A45SS5
     * carLogo : /cfw/ueditUpload/news/20161124/f49d9d40c7c84684a38129c97d4539cf.jpg
     * vin : LSVAM4187C2184847
     * carEngineCode : AF9K54605
     * carVehicleLicense : LVVDC11BX9D3702
     * carRegistrationCode : 236987541203
     * addTime : Nov 24, 2016 11:21:46 AM
     * carState : 1
     * teacherId : null
     * schoolName : null
     */

    public String carId;
    public String schoolId;
    public String carName;
    public String mileage;
    public String carType;
    public String carCode;
    public String carLogo;
    public String vin;
    public String carEngineCode;
    public String carVehicleLicense;
    public String carRegistrationCode;
    public String addTime;
    public String carState;
    public String teacherId;
    public String schoolName;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.carId);
        dest.writeString(this.schoolId);
        dest.writeString(this.carName);
        dest.writeString(this.mileage);
        dest.writeString(this.carType);
        dest.writeString(this.carCode);
        dest.writeString(this.carLogo);
        dest.writeString(this.vin);
        dest.writeString(this.carEngineCode);
        dest.writeString(this.carVehicleLicense);
        dest.writeString(this.carRegistrationCode);
        dest.writeString(this.addTime);
        dest.writeString(this.carState);
        dest.writeString(this.teacherId);
        dest.writeString(this.schoolName);
    }

    public CarResult() {
    }

    protected CarResult(Parcel in) {
        this.carId = in.readString();
        this.schoolId = in.readString();
        this.carName = in.readString();
        this.mileage = in.readString();
        this.carType = in.readString();
        this.carCode = in.readString();
        this.carLogo = in.readString();
        this.vin = in.readString();
        this.carEngineCode = in.readString();
        this.carVehicleLicense = in.readString();
        this.carRegistrationCode = in.readString();
        this.addTime = in.readString();
        this.carState = in.readString();
        this.teacherId = in.readString();
        this.schoolName = in.readString();
    }

    public static final Creator<CarResult> CREATOR = new Creator<CarResult>() {
        @Override
        public CarResult createFromParcel(Parcel source) {
            return new CarResult(source);
        }

        @Override
        public CarResult[] newArray(int size) {
            return new CarResult[size];
        }
    };
}
