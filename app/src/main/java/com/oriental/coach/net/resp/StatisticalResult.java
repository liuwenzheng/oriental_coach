package com.oriental.coach.net.resp;

import java.io.Serializable;

/**
 * Created by wenzheng.liu on 2016/12/13.
 */

public class StatisticalResult implements Serializable {
    /**
     * num : 3
     * deductsum : 0
     * deductratio : 80
     * money : 600
     */

    public Statistical month;
    public Statistical year;
    public Statistical day;

}
