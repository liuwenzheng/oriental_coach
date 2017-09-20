package com.oriental.coach.net.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * @Date 2017/8/31
 * @Author wenzheng.liu
 * @Description 集训
 * @ClassPath com.oriental.coach.net.resp.AssembleTrainResult
 */
public class AssembleTrainResult implements Serializable{

    /**
     * timeId : b904e655677a4af4a88e7cda0853239f
     * teacherId : fb2b247592f34134a362c83055707903
     * timeBeginTime : Aug 23, 2017 12:00:00 AM
     * timeEndTime : Aug 25, 2017 12:00:00 AM
     * timeMoney : 1200
     * timeCourse : 2
     * timeLicense : A1
     * maxStudentNum : 6
     * minStudentNum : 4
     * studentNum : 0
     * timeOff : 1
     * ifStart : 0
     * teacharName : 陈晓军
     * teacharSex : 1
     * teacharIdcard : 142701197304216038
     * teacharPhone : 18295902887
     * schoolId : af8fd49c34e345799a8269110bc93038
     * schoolName : 新东方驾驶学院
     */

    public String timeId;
    public String teacherId;
    public Date timeBeginTime;
    public Date timeEndTime;
    public double timeMoney;
    public String timeCourse;
    public String timeLicense;
    public int maxStudentNum;
    public int minStudentNum;
    public int studentNum;
    public String timeOff;
    public String ifStart;
    public String teacharName;
    public String teacharSex;
    public String teacharIdcard;
    public String teacharPhone;
    public String schoolId;
    public String schoolName;
}
