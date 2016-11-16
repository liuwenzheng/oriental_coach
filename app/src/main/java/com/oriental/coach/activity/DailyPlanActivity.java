package com.oriental.coach.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.oriental.coach.Constants;
import com.oriental.coach.R;
import com.oriental.coach.utils.Utils;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author lwz
 * @Date 2016/11/15 0012
 * @Describe 日程安排
 */

public class DailyPlanActivity extends Activity {
    @Bind(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @Bind(R.id.rb_daily_plan_day_1)
    RadioButton rbDailyPlanDay1;
    @Bind(R.id.rb_daily_plan_day_2)
    RadioButton rbDailyPlanDay2;
    @Bind(R.id.rb_daily_plan_day_3)
    RadioButton rbDailyPlanDay3;
    @Bind(R.id.rb_daily_plan_day_4)
    RadioButton rbDailyPlanDay4;
    @Bind(R.id.rb_daily_plan_day_5)
    RadioButton rbDailyPlanDay5;
    @Bind(R.id.rb_daily_plan_day_6)
    RadioButton rbDailyPlanDay6;
    @Bind(R.id.rb_daily_plan_day_7)
    RadioButton rbDailyPlanDay7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_plan);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("日程安排");
        creatDate();
    }

    private void creatDate() {
        Calendar calendar = Calendar.getInstance();
        rbDailyPlanDay1.setText(Utils.calendar2strDate(calendar, Constants.PATTERN_MM_DD_2));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        rbDailyPlanDay2.setText(Utils.calendar2strDate(calendar, Constants.PATTERN_MM_DD_2));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        rbDailyPlanDay3.setText(Utils.calendar2strDate(calendar, Constants.PATTERN_MM_DD_2));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        rbDailyPlanDay4.setText(Utils.calendar2strDate(calendar, Constants.PATTERN_MM_DD_2));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        rbDailyPlanDay5.setText(Utils.calendar2strDate(calendar, Constants.PATTERN_MM_DD_2));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        rbDailyPlanDay6.setText(Utils.calendar2strDate(calendar, Constants.PATTERN_MM_DD_2));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        rbDailyPlanDay7.setText(Utils.calendar2strDate(calendar, Constants.PATTERN_MM_DD_2));
        // 默认选中第一个
        rbDailyPlanDay1.setChecked(true);
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
