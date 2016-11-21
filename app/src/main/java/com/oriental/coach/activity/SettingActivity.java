package com.oriental.coach.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.oriental.coach.R;
import com.zcw.togglebutton.ToggleButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author lwz
 * @Date 2016/11/15 0012
 * @Describe 设置
 */

public class SettingActivity extends Activity {


    @Bind(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @Bind(R.id.tb_switch)
    ToggleButton tbSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("设置");
        tbSwitch.setToggleOn();
        tbSwitch.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {

            }
        });
    }

    @OnClick({R.id.iv_header_back, R.id.btn_logout, R.id.rl_modify_password})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;
            case R.id.btn_logout:
                break;
            case R.id.rl_modify_password:
                intent = new Intent(this, ModifyPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
