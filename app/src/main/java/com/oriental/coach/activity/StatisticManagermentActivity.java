package com.oriental.coach.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.oriental.coach.Constants;
import com.oriental.coach.R;
import com.oriental.coach.base.BaseActivity;
import com.oriental.coach.net.callback.DialogCallback;
import com.oriental.coach.net.resp.BaseResponse;
import com.oriental.coach.net.resp.StatisticalResult;
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
 * @Date 2016/11/12 0012
 * @Describe 统计管理
 */

public class StatisticManagermentActivity extends BaseActivity {
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
    public static final int TYPE_STATISTIC_DAILY = 0;
    public static final int TYPE_STATISTIC_MONTH = 1;
    public static final int TYPE_STATISTIC_YEAR = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_managerment);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("统计管理");
        String teacherId = PreferencesUtil.getStringByName(this, "teacherId", "");
        if (!TextUtils.isEmpty(teacherId)) {
            Map<String, String> map = new HashMap<>();
            map.put("teacherId", teacherId);
            requestGet(Urls.GET_STATISTICAL, map, new DialogCallback<BaseResponse<StatisticalResult>>(this) {
                @Override
                public void onSuccess(BaseResponse<StatisticalResult> statisticalResultBaseResponse, Call call, Response response) {
                    StatisticalResult result = statisticalResultBaseResponse.resObject;
                    if (result != null) {
                        if (result.day != null) {
                            tvStatisticDailyNumber.setText(result.day.num + "");
                            tvStatisticDailyMoneyAmount.setText(result.day.money + "");
                            tvStatisticDailyMoneyPart.setText(result.day.deductratio + "");
                        }
                        if (result.month != null) {
                            tvStatisticMonthNumber.setText(result.month.num + "");
                            tvStatisticMonthMoneyAmount.setText(result.month.money + "");
                            tvStatisticMonthMoneyPart.setText(result.month.deductratio + "");
                        }
                        if (result.year != null) {
                            tvStatisticYearNumber.setText(result.year.num + "");
                            tvStatisticYearMoneyAmount.setText(result.year.money + "");
                            tvStatisticYearMoneyPart.setText(result.year.deductratio + "");
                        }
                    }
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                    if (e instanceof IllegalStateException) {
                        ToastUtils.showToast(StatisticManagermentActivity.this, e.getMessage());
                    }
                }
            });
        } else {
            finish();
            return;
        }
    }


    @OnClick({R.id.iv_header_back, R.id.rl_statistic_daily, R.id.rl_statistic_month, R.id.rl_statistic_year})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;
            case R.id.rl_statistic_daily:
                intent = new Intent(this, StatisticDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_STATISTIC, TYPE_STATISTIC_DAILY);
                startActivity(intent);
                break;
            case R.id.rl_statistic_month:
                intent = new Intent(this, StatisticDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_STATISTIC, TYPE_STATISTIC_MONTH);
                startActivity(intent);
                break;
            case R.id.rl_statistic_year:
                intent = new Intent(this, StatisticDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_STATISTIC, TYPE_STATISTIC_YEAR);
                startActivity(intent);
                break;
        }
    }
}
