package com.oriental.coach.activity;

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
 * @Describe 修改密码
 */

public class ModifyPasswordActivity extends BaseActivity {


    @Bind(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @Bind(R.id.et_password_old)
    EditText etPasswordOld;
    @Bind(R.id.et_password_new)
    EditText etPasswordNew;
    @Bind(R.id.et_password_new_again)
    EditText etPasswordNewAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("修改密码");
    }


    @OnClick({R.id.iv_header_back, R.id.btn_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;
            case R.id.btn_finish:
                if (TextUtils.isEmpty(etPasswordOld.getText().toString())) {
                    ToastUtils.showToast(this, "旧密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(etPasswordNew.getText().toString()) || TextUtils.isEmpty(etPasswordNewAgain.getText().toString())) {
                    ToastUtils.showToast(this, "新密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(etPasswordNew.getText().toString()) || TextUtils.isEmpty(etPasswordNewAgain.getText().toString())) {
                    ToastUtils.showToast(this, "新密码不能为空");
                    return;
                }
                if (!etPasswordNew.getText().toString().equals(etPasswordNewAgain.getText().toString())) {
                    ToastUtils.showToast(this, "新密码不一致");
                    return;
                }
                String oldPassword = etPasswordOld.getText().toString();
                String newPassword = etPasswordNew.getText().toString();
                requestUpdatePassword(oldPassword, newPassword);
                break;
        }
    }

    private void requestUpdatePassword(String oldPassword, String newPassword) {

        Map<String, String> map = new HashMap<>();
        map.put("userId", PreferencesUtil.getStringByName(this, "userId", ""));
        map.put("password", newPassword);
        map.put("oldPassword", oldPassword);
        requestGet(Urls.UPDATE_PASSWORD, map, new DialogCallback<BaseResponse<Object>>(this) {
            @Override
            public void onSuccess(BaseResponse baseResponse, Call call, Response response) {
                ToastUtils.showToast(ModifyPasswordActivity.this, "修改成功");
                finish();
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (e instanceof IllegalStateException) {
                    ToastUtils.showToast(ModifyPasswordActivity.this, e.getMessage());
                }
            }
        });

    }
}
