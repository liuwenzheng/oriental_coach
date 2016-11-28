package com.oriental.coach.net.callback;

import android.app.Activity;
import android.content.Context;

import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.oriental.coach.Constants;
import com.oriental.coach.net.Convert;
import com.oriental.coach.net.resp.BaseResponse;
import com.oriental.coach.net.resp.SimpleResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;


/**
 * Created by wenzheng.liu on 2016/11/28.
 */

public abstract class JsonCallback<T> extends AbsCallback<T> {

    @Override
    public T convertSuccess(Response response) throws Exception {
        // 得到类的泛型，包括了泛型参数
        Type genType = getClass().getGenericSuperclass();
        //从上述的类中取出真实的泛型参数，有些类可能有多个泛型，所以是数值
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        // 只有一个，取第一个
        Type type = params[0];
        // 此时，rawType的类型实际上是 class，但 Class 实现了 Type 接口，所以我们用 Type 接收没有问题
        Type rawType = ((ParameterizedType) type).getRawType();

        // 这里我们既然都已经拿到了泛型的真实类型，即对应的 class ，那么当然可以开始解析数据了，我们采用 Gson 解析
        // 以下代码是根据泛型解析数据，返回对象，返回的对象自动以参数的形式传递到 onSuccess 中，可以直接使用
        JsonReader jsonReader = new JsonReader(response.body().charStream());
        if (rawType == Void.class) {
            //无数据类型,表示没有data数据的情况（以  new DialogCallback<LzyResponse<Void>>(this)  以这种形式传递的泛型)
            SimpleResponse simpleResponse = Convert.fromJson(jsonReader, SimpleResponse.class);
            response.close();
            return (T) simpleResponse.toBaseResponse();
        } else if (rawType == BaseResponse.class) {
            // 有数据类型，表示有data
            BaseResponse baseResponse = Convert.fromJson(jsonReader, type);
            response.close();
            String code = baseResponse.stateCode;
            String mess = baseResponse.stateMess;
            if (Constants.NET_STATE_CODE_OK.equals(code)) {
                // 成功返回实体bean
                return (T) baseResponse;
            } else {
                // 失败返回错误信息，抛出错误，会在onError中回调。
                throw new IllegalStateException(mess);
            }
        } else {
            response.close();
            throw new IllegalStateException("基类错误无法解析!");
        }
    }
}