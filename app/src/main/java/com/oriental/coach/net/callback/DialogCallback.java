package com.oriental.coach.net.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.view.Window;

import com.lzy.okgo.request.BaseRequest;

import okhttp3.Response;

/**
 * 对于网络请求是否需要弹出进度对话框/
 */
public abstract class DialogCallback<T> extends JsonCallback<T> {

    private ProgressDialog dialog;

    private void initDialog(Activity activity) {
        initDialog(activity, true);
    }

    private void initDialog(Activity activity, boolean cancelable) {
        dialog = new ProgressDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(cancelable);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
    }

    public DialogCallback(Activity activity) {
        super(activity);
        initDialog(activity);
    }

    public DialogCallback(Activity activity, boolean cancelable) {
        super(activity);
        initDialog(activity, cancelable);
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        // 网络请求前显示对话框
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onAfter(@Nullable T t, @Nullable Exception e) {
        super.onAfter(t, e);
        // 网络请求结束后关闭对话框
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
