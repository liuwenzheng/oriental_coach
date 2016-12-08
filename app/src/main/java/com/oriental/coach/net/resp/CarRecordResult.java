package com.oriental.coach.net.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wenzheng.liu on 2016/12/8.
 */

public class CarRecordResult implements Serializable {
    /**
     * recordId : 824de833eb2a488e9b4f4610dd9c847f
     * carId : 222579b172ac46b98728688e5cdd57ac
     * recordType : 1
     * recordBeginTime : Nov 1, 2016 2:35:00 PM
     * recordEndTime : Nov 30, 2016 2:45:00 PM
     * recordContent : 例行年检
     * recordAddtime : Nov 25, 2016 3:14:34 PM
     * recordState : 1
     * carName : 奇瑞
     * carType : 1
     * carCode : 晋A532DF
     * carLogo : /cfw/ueditUpload/news/20161201/422db11267af46c18e62faa48f5ceb76.jpg
     * schoolName : 夏县新东方驾校
     * detailList : null
     */

    public String recordId;
    public String carId;
    public String recordType;
    public Date recordBeginTime;
    public Date recordEndTime;
    public String recordContent;
    public Date recordAddtime;
    public String recordState;
    public String carName;
    public String carType;
    public String carCode;
    public String carLogo;
    public String schoolName;
    public String detailList;
}
