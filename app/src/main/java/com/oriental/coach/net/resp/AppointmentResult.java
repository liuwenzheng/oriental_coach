package com.oriental.coach.net.resp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by wenzheng.liu on 2016/11/30.
 */

public class AppointmentResult implements Serializable {

    /**
     * timeId : 9c03f359a2024645a4bb8b3310fc3f4a
     * teacharId : 01bf6a606277445a9cfbdcd48120af1d
     * timePm : PM
     * timeBeginTime : 12:00
     * timeEndTime : 14:00
     * timeDate : Feb 20, 2017 12:00:00 AM
     * timeMondy : null
     * timeSourse : null
     * timeLicense : null
     * timeState : 0
     * timeOff : 1
     * timeJson : [{'timeLicense':'C1','timeSourse':'1','timeMondy':'0.01','timeText':'C1科目二普通(￥0.01)'},{'timeLicense':'C1','timeSourse':'2','timeMondy':'0.01','timeText':'C1科目二场内(￥0.01)'}]
     * timeSourdeType : 2
     * teacharName : null
     * teacharPhone : null
     * beginTime : null
     * endTime : null
     * timeTypeList : null
     */

    public String timeId;
    public String teacharId;
    public String timePm;
    public String timeBeginTime;
    public String timeEndTime;
    public Date timeDate;
    public double timeMondy;
    public String timeSourse;
    public String timeLicense;
    public String timeState;
    public String timeOff;
    public String timeJson;
    public String timeSourdeType;
    public String teacharName;
    public String teacharPhone;
    public String beginTime;
    public String endTime;
    public String timeTypeList;

    public static class TimeJson implements Serializable {

        /**
         * timeLicense : C1
         * timeSourse : 2
         * timeMondy : 0.01
         * timeText : C1科目二场内(￥0.01)
         */

        public String timeLicense;
        public String timeSourse;
        public String timeMondy;
        public String timeText;
    }
}
