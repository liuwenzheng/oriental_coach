package com.oriental.coach;

import android.app.Application;

import com.lzy.okgo.OkGo;

import java.util.logging.Level;

/**
 * Created by wenzheng.liu on 2016/11/28.
 */

public class Globals extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // OkGo初始化
        OkGo.init(this);
        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            OkGo.getInstance()
                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("OkGo", Level.INFO, true)
                    .setConnectTimeout(10000L)  //全局的连接超时时间
                    .setReadTimeOut(10000L)     //全局的读取超时时间
                    .setWriteTimeOut(10000L);   //全局的写入超时时间
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
