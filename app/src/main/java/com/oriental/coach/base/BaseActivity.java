package com.oriental.coach.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;

import java.util.Map;

public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public <T> void requestGet(String url, Map<String, String> params, AbsCallback<T> callback) {
        OkGo.get(url).tag(this).params(params).execute(callback);
    }

    public <T> void requestPost(String url, String jsonReq, AbsCallback<T> callback) {
        OkGo.post(url).tag(this).upJson(jsonReq).execute(callback);
    }

    public <T> void requestPost(String url, Map<String, String> params, AbsCallback<T> callback) {
        OkGo.post(url).tag(this).params(params).execute(callback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消所有请求
        OkGo.getInstance().cancelAll();
    }
}
