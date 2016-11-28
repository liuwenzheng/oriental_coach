package com.oriental.coach.net.resp;

import java.io.Serializable;

/**
 * Created by wenzheng.liu on 2016/11/28.
 */

public class LoginResult implements Serializable {
    /**
     * userId : a6f47466846b4425956a60dd0c056025
     * account : 18700001111
     * password : 70352f41061eda4ff3c322094af068ba70c3b38b
     * userName : 张无忌
     * sex : 0
     * telphone : 18700001111
     * mail : null
     * cardNum : null
     * idcardNum : 510105199010017999
     * birthday : null
     * registerTime : Nov 24, 2016 12:00:00 AM
     * userState : 2_1
     * userFlag : 3
     * tableId : 5b7b081cc5e74a33916706ebaadd0125
     * levelAccumulate : 0
     */

    public String userId;
    public String account;
    public String password;
    public String userName;
    public String sex;
    public String telphone;
    public Object mail;
    public Object cardNum;
    public String idcardNum;
    public Object birthday;
    public String registerTime;
    public String userState;
    public String userFlag;
    public String tableId;
    public int levelAccumulate;
}
