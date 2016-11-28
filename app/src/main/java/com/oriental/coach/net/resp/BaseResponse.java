package com.oriental.coach.net.resp;

import java.io.Serializable;

/**
 * Created by wenzheng.liu on 2016/11/28.
 * 返回实体bean基类（所有返回实体共有参数）
 */

public class BaseResponse<T> implements Serializable {
    public String stateCode;
    public String stateMess;
    public T resObject;
}
