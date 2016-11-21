package com.oriental.coach.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.oriental.coach.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author lwz
 * @Date 2016/11/15 0012
 * @Describe 修改密码
 */

public class ModifyPasswordActivity extends Activity {


    @Bind(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @Bind(R.id.et_accounts)
    EditText etAccounts;
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
                break;
        }
    }
}
