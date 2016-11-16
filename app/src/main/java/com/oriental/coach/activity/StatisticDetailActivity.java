package com.oriental.coach.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.oriental.coach.Constants;
import com.oriental.coach.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author lwz
 * @Date 2016/11/12 0012
 * @Describe 统计管理详情
 */

public class StatisticDetailActivity extends Activity {
    @Bind(R.id.tv_header_title)
    TextView tvHeaderTitle;
    private int mCurrentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_managerment);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("统计管理");
        if (getIntent() != null && getIntent().getExtras() != null) {
            mCurrentTab = getIntent().getIntExtra(
                    Constants.EXTRA_KEY_STATISTIC, StatisticManagermentActivity.TYPE_STATISTIC_DAILY);
        }
    }


    @OnClick({R.id.iv_header_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;
            case R.id.tv_header_title:
                break;
        }
    }
}
