package com.oriental.coach.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.oriental.coach.R;
import com.oriental.coach.base.BaseActivity;
import com.oriental.coach.net.callback.DialogCallback;
import com.oriental.coach.net.resp.BaseResponse;
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
 * @Description 修改密码
 * @ClassPath com.oriental.coach.activity.UpdatePasswordActivity
 */
public class UpdatePasswordActivity extends BaseActivity {
    @Bind(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @Bind(R.id.et_password_new)
    EditText etPasswordNew;
    @Bind(R.id.et_password_new_again)
    EditText etPasswordNewAgain;
    String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("重置密码");
        if (getIntent() != null && getIntent().getExtras() != null) {
            mUserId = getIntent().getStringExtra("userId");
        } else {
            finish();
            return;
        }
    }

    @OnClick({R.id.iv_header_back, R.id.btn_confirm})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;
            case R.id.btn_confirm:
                if (TextUtils.isEmpty(etPasswordNew.getText().toString()) || TextUtils.isEmpty(etPasswordNewAgain.getText().toString())) {
                    ToastUtils.showToast(this, "新密码不能为空");
                    return;
                }
                if (!etPasswordNew.getText().toString().equals(etPasswordNewAgain.getText().toString())) {
                    ToastUtils.showToast(this, "新密码不一致");
                    return;
                }
                updatePassword();
                break;
        }
    }

    private void updatePassword() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", mUserId);
        map.put("password", etPasswordNew.getText().toString());
        requestGet(Urls.GET_VERIFICATY_UPDATE, map, new DialogCallback<BaseResponse<Object>>(this) {
            @Override
            public void onSuccess(BaseResponse<Object> baseResponse, Call call, Response response) {
                Intent intent = new Intent(UpdatePasswordActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (e instanceof IllegalStateException) {
                    ToastUtils.showToast(UpdatePasswordActivity.this, e.getMessage());
                }
            }
        });
    }


}
