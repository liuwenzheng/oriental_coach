package com.oriental.coach.net.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wenzheng.liu on 2016/11/30.
 */

public class AppointmentResult implements Serializable {
    /**
     * timeId : 20bf75ba435041f49f2785d480cbea0b
     * teacharId : ee053a703a1243eb9e24f6b1729393be
     * timePm : AM
     * timeBeginTime : 08:00
     * timeEndTime : 10:00
     * timeDate : Dec 2, 2016 12:00:00 AM
     * timeMondy : 100
     * timeSourse : 1
     * timeLicense : C2
     * timeState : 0
     * timeOff : 0
     * beginTime : null
     * endTime : null
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
    public String beginTime;
    public String endTime;
}
