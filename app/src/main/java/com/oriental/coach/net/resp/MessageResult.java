package com.oriental.coach.net.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wenzheng.liu on 2016/12/8.
 */

public class MessageResult implements Serializable {
    /**
     * messageId : 1
     * userId : 1bb8e001ef9b464e90f503151dae30a0
     * messageContent : 预约课时成功消息。
     * messageCreate : Dec 5, 2016 12:00:00 AM
     * messageType : 4_1
     * messageState : 1
     * messageUseDate : Dec 5, 2016 12:00:00 AM
     * messageDelete : 1
     */
    public String messageId;
    public String userId;
    public String messageContent;
    public Date messageCreate;
    public String messageType;
    public String messageState;
    public Date messageUseDate;
    public String messageDelete;
}
