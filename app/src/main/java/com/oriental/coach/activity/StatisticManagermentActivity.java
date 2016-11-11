package com.oriental.coach.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.oriental.coach.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author lwz
 * @Date 2016/11/12 0012
 * @Describe 统计管理
 */

public class StatisticManagermentActivity extends Activity {
    @Bind(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @Bind(R.id.tv_statistic_daily_number)
    TextView tvStatisticDailyNumber;
    @Bind(R.id.tv_statistic_daily_money_amount)
    TextView tvStatisticDailyMoneyAmount;
    @Bind(R.id.tv_statistic_daily_money_part)
    TextView tvStatisticDailyMoneyPart;
    @Bind(R.id.tv_statistic_month_number)
    TextView tvStatisticMonthNumber;
    @Bind(R.id.tv_statistic_month_money_amount)
    TextView tvStatisticMonthMoneyAmount;
    @Bind(R.id.tv_statistic_month_money_part)
    TextView tvStatisticMonthMoneyPart;
    @Bind(R.id.tv_statistic_year_number)
    TextView tvStatisticYearNumber;
    @Bind(R.id.tv_statistic_year_money_amount)
    TextView tvStatisticYearMoneyAmount;
    @Bind(R.id.tv_statistic_year_money_part)
    TextView tvStatisticYearMoneyPart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_managerment);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("管理统计");
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
