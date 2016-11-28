package com.oriental.coach.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.oriental.coach.R;
import com.oriental.coach.base.BaseActivity;
import com.oriental.coach.entity.Teacher;
import com.oriental.coach.net.callback.DialogCallback;
import com.oriental.coach.net.resp.BaseResponse;
import com.oriental.coach.net.resp.LoginResult;
import com.oriental.coach.net.resp.TeacherResult;
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
        // TODO: 2016/11/28 0028 test
        etAccounts.setText("18009186157");
        etPassword.setText("00000000");
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
                    requestTeacher(result.tableId);
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                ToastUtils.showToast(LoginActivity.this, e.getMessage());
            }
        });
    }

    private void requestTeacher(final String tableId) {
        Map<String, String> req = new HashMap<>();
        req.put("teacharId", tableId);
        requestGet(Urls.GET_TEACHER, req, new DialogCallback<BaseResponse<TeacherResult>>(this) {
            @Override
            public void onSuccess(BaseResponse<TeacherResult> teacherResult, Call call, Response response) {
                TeacherResult result = teacherResult.resObject;
                Teacher teacher = new Teacher();
                teacher.name = result.teacharName;
                teacher.carAge = result.teacharCarAge;
                teacher.school = result.schoolId;
                teacher.courseType = result.courseType;
                teacher.gender = result.teacharSex;
                teacher.goodCommPro = result.goodCommPro;
                teacher.logo = result.teacharLogo;
                teacher.phoneNo = result.teacharPhone;
                teacher.studentCnt = result.studentCnt;
                StringBuilder builder = new StringBuilder();
                builder.append(result.proviceName).append(result.citeName).append(result.countyName).append(result.areaName);
                teacher.address = builder.toString();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("teacher", teacher);
                startActivity(intent);
                finish();
            }
        });
    }
}
