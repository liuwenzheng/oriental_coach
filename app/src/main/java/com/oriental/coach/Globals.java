package com.oriental.coach;

import android.app.Application;
import android.graphics.Bitmap;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.HashMap;
import java.util.Map;
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
                    .addCommonHeaders(new HttpHeaders("Connection", "close"))
                    // cookie使用内存缓存（app退出后，cookie消失）
//                    .setCookieStore(new MemoryCookieStore())
                    // cookie持久化存储，如果cookie不过期，则一直有效
                    .setCookieStore(new PersistentCookieStore())
                    .setConnectTimeout(10000L)  //全局的连接超时时间
                    .setReadTimeOut(10000L)     //全局的读取超时时间
                    .setWriteTimeOut(10000L);   //全局的写入超时时间
        } catch (Exception e) {
            e.printStackTrace();
        }
        //  java.io.EOFException
        Map<String, String> headers = new HashMap<>();
        headers.put("Connection", "close");
        // imageloader默认全局配置项
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .extraForDownloader(headers)
                .showImageForEmptyUri(R.drawable.default_image)
                .showImageOnLoading(R.drawable.default_image)
                .showImageOnFail(R.drawable.default_image)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }
}
