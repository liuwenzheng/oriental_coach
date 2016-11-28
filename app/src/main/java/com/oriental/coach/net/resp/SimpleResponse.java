package com.oriental.coach.net.resp;

import java.io.Serializable;

public class SimpleResponse implements Serializable {


    public String stateCode;
    public String stateMess;

    public BaseResponse toBaseResponse() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.stateCode = stateCode;
        baseResponse.stateMess = stateMess;
        return baseResponse;
    }
}