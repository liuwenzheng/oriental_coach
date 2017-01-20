package com.oriental.coach.net.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author lwz
 * @Date 2016/12/7 0007
 * @Describe
 */

public class BespeakResult implements Serializable {

    /**
     * bespeakId : 2fd0fc71a42243818e801335925cff5d
     * timeId : aae310bce59b496b81488799d87dd95b
     * bespeakCode : XYYC20161207144745521619213
     * bespeakAddtime : Dec 7, 2016 2:47:45 PM
     * bespeakSumTime : 1
     * bespeakSumMoney : 100
     * bespeakTime : 10:00~11:00
     * bespeakState : 1
     * bespeakIsdel : 1
     * studentId : 1bb8e001ef9b464e90f503151dae30a0
     * studentName : 王晓明
     * studentIdcard : 610521198001010075
     * studentPhone : 18991433008
     * studentLogo : null
     * teacharId : ee053a703a1243eb9e24f6b1729393be
     * teacharName : 张三
     * teacharPhone : 18009186157
     * teacharLogo : /cfw/ueditUpload/img/20161124/103d6aa850274edebf8a2b5c82c577d6.jpg
     * timePm : AM
     * timeBeginTime : 10:00
     * timeEndTime : 11:00
     * timeDate : Dec 8, 2016 12:00:00 AM
     * timeSourse : 3
     * timeLicense : C1
     * tradeType : 5
     * tradeCode : null
     * takeMoney : null
     * takeRatio : null
     * schoolId : af8fd49c34e345799a8269110bc93038
     * schoolName : 新东方驾驶学院
     */

    public String bespeakId;
    public String timeId;
    public String bespeakCode;
    public Date bespeakAddtime;
    public String bespeakSumTime;
    public double bespeakSumMoney;
    public String bespeakTime;
    public String bespeakState;
    public String bespeakIsdel;
    public String studentId;
    public String studentName;
    public String studentIdcard;
    public String studentPhone;
    public String studentLogo;
    public String teacharId;
    public String teacharName;
    public String teacharPhone;
    public String teacharLogo;
    public String timePm;
    public String timeBeginTime;
    public String timeEndTime;
    public Date timeDate;
    public String timeSourse;
    public String timeLicense;
    public String tradeType;
    public String tradeCode;
    public String takeMoney;
    public double takeRatio;
    public String schoolId;
    public String schoolName;
}
