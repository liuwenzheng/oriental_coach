package com.oriental.coach.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oriental.coach.R;
import com.oriental.coach.base.BaseActivity;
import com.oriental.coach.net.callback.DialogCallback;
import com.oriental.coach.net.resp.BaseResponse;
import com.oriental.coach.net.resp.VerifyCodeResult;
import com.oriental.coach.net.urls.Urls;
import com.oriental.coach.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


/**
 * @Date 2017/2/7
 * @Author wenzheng.liu
 * @Description 获取验证码
 * @ClassPath com.oriental.coach.activity.GetVerificationCodeActivity
 */
public class GetVerificationCodeActivity extends BaseActivity {
    @Bind(R.id.tv_header_title)
    TextView tvHeaderTitle;
    private static final int COUNT_DOWN_INTERVAL = 1000;
    @Bind(R.id.et_accounts)
    EditText etAccounts;
    @Bind(R.id.et_verification_code)
    EditText etVerificationCode;
    @Bind(R.id.btn_get_verification_code)
    Button btnGetVerificationCode;

    private int mCount;
    private Runnable countDownRunnable = new Runnable() {
        @Override
        public void run() {
            mCount--;
            if (mCount <= 0) {
                resetCountDown();
                return;
            }
            btnGetVerificationCode.setText(String.format("%s秒后重新获取", mCount));
            btnGetVerificationCode.postDelayed(this, COUNT_DOWN_INTERVAL);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("验证用户");
    }

    @OnClick({R.id.iv_header_back, R.id.btn_get_verification_code, R.id.btn_next})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;
            case R.id.btn_get_verification_code:
                if (TextUtils.isEmpty(etAccounts.getText().toString())) {
                    ToastUtils.showToast(this, "请输入手机号码");
                    return;
                }
                verifyAccounts();
                break;
            case R.id.btn_next:
                if (TextUtils.isEmpty(etVerificationCode.getText().toString())) {
                    ToastUtils.showToast(this, "请输入验证码");
                    return;
                }
                verifyCode();
                break;
        }
    }

    private void verifyCode() {
        Map<String, String> map = new HashMap<>();
        map.put("account", etAccounts.getText().toString());
        map.put("code", etVerificationCode.getText().toString());
        requestGet(Urls.GET_VERIFICATY_INITRESET, map, new DialogCallback<BaseResponse<VerifyCodeResult>>(this) {
            @Override
            public void onSuccess(BaseResponse<VerifyCodeResult> baseResponse, Call call, Response response) {
                VerifyCodeResult result = baseResponse.resObject;
                if (result != null && !TextUtils.isEmpty(result.userId)) {
                    Intent intent = new Intent(GetVerificationCodeActivity.this, UpdatePasswordActivity.class);
                    intent.putExtra("userId", result.userId);
                    startActivity(intent);
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (e instanceof IllegalStateException) {
                    ToastUtils.showToast(GetVerificationCodeActivity.this, "验证码错误");
                }
            }
        });
    }

    private void verifyAccounts() {
        Map<String, String> map = new HashMap<>();
        map.put("account", etAccounts.getText().toString());
        requestGet(Urls.GET_VERIFICATY_INITFORGET, map, new DialogCallback<BaseResponse<Object>>(this) {
            @Override
            public void onSuccess(BaseResponse<Object> baseResponse, Call call, Response response) {
                getVerificationCode();
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (e instanceof IllegalStateException) {
                    ToastUtils.showToast(GetVerificationCodeActivity.this, e.getMessage());
                }
            }
        });
    }

    private void getVerificationCode() {
        Map<String, String> map = new HashMap<>();
        map.put("verMailOrphone", etAccounts.getText().toString());
        requestGet(Urls.GET_VERIFICATY_SEND, map, new DialogCallback<BaseResponse<Object>>(this) {
            @Override
            public void onSuccess(BaseResponse<Object> baseResponse, Call call, Response response) {
                startCountDown();
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (e instanceof IllegalStateException) {
                    ToastUtils.showToast(GetVerificationCodeActivity.this, e.getMessage());
                }
            }
        });
    }

    private void startCountDown() {
        mCount = 60;
        btnGetVerificationCode.setEnabled(false);
        btnGetVerificationCode.setText(String.format("%s秒后重新获取", mCount));
        btnGetVerificationCode.postDelayed(countDownRunnable, COUNT_DOWN_INTERVAL);
        btnGetVerificationCode.setTextColor(ContextCompat.getColor(this, R.color.black_666666));
        btnGetVerificationCode.setBackgroundColor(ContextCompat.getColor(this, R.color.grey_e5e5e5));
    }

    private void resetCountDown() {
        btnGetVerificationCode.removeCallbacks(countDownRunnable);
        btnGetVerificationCode.setEnabled(true);
        btnGetVerificationCode.setTextColor(ContextCompat.getColor(this, R.color.white_ffffff));
        btnGetVerificationCode.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_4c6f98));
        btnGetVerificationCode.setText("再次获取");
    }


}
