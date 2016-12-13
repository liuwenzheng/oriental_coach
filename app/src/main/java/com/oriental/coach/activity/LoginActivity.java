package com.oriental.coach.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.oriental.coach.R;
import com.oriental.coach.base.BaseActivity;
import com.oriental.coach.net.callback.DialogCallback;
import com.oriental.coach.net.resp.BaseResponse;
import com.oriental.coach.net.resp.LoginResult;
import com.oriental.coach.net.urls.Urls;
import com.oriental.coach.utils.PreferencesUtil;
import com.oriental.coach.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @Author lwz
 * @Date 2016/11/15 0012
 * @Describe 登录
 */

public class LoginActivity extends BaseActivity {


    @Bind(R.id.et_accounts)
    EditText etAccounts;
    @Bind(R.id.et_password)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        String teacherId = PreferencesUtil.getStringByName(LoginActivity.this, "teacherId", "");
        if (TextUtils.isEmpty(teacherId)) {
            etAccounts.setText(PreferencesUtil.getStringByName(LoginActivity.this, "account", ""));
            // TODO: 2016/11/28 0028 test
            // etPassword.setText("00000000");
        } else {
            gotoMainActivity();
        }
    }


    @OnClick({R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                if (TextUtils.isEmpty(etAccounts.getText().toString())) {
                    ToastUtils.showToast(this, "帐号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(etPassword.getText().toString())) {
                    ToastUtils.showToast(this, "密码不能为空");
                    return;
                }
                requestLogin();
                break;
        }
    }

    private void requestLogin() {
        Map<String, String> req = new HashMap<>();
        req.put("account", etAccounts.getText().toString());
        req.put("password", etPassword.getText().toString());
        requestGet(Urls.LOGIN, req, new DialogCallback<BaseResponse<LoginResult>>(this) {
            @Override
            public void onSuccess(BaseResponse<LoginResult> loginResult, Call call, Response response) {

                ToastUtils.showToast(LoginActivity.this, loginResult.stateMess);
                LoginResult result = loginResult.resObject;
                if (result != null) {
                    PreferencesUtil.setStringByName(LoginActivity.this, "teacherId", result.tableId);
                    PreferencesUtil.setStringByName(LoginActivity.this, "account", etAccounts.getText().toString());
                    PreferencesUtil.setStringByName(LoginActivity.this, "userId", result.userId);
                    gotoMainActivity();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (e instanceof IllegalStateException) {
                    ToastUtils.showToast(LoginActivity.this, e.getMessage());
                } else {
                    ToastUtils.showToast(LoginActivity.this, "网络错误");
                }
            }
        });
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
