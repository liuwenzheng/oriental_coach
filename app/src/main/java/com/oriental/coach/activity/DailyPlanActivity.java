package com.oriental.coach.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.oriental.coach.Constants;
import com.oriental.coach.R;
import com.oriental.coach.adapter.DailyPlanAdapter;
import com.oriental.coach.base.BaseActivity;
import com.oriental.coach.entity.DailyPlan;
import com.oriental.coach.entity.Teacher;
import com.oriental.coach.net.callback.DialogCallback;
import com.oriental.coach.net.resp.AppointmentResult;
import com.oriental.coach.net.resp.BaseResponse;
import com.oriental.coach.net.urls.Urls;
import com.oriental.coach.utils.ToastUtils;
import com.oriental.coach.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @Author lwz
 * @Date 2016/11/15 0012
 * @Describe 日程安排
 */

public class DailyPlanActivity extends BaseActivity {
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
    @Bind(R.id.rv_daily_plan)
    RecyclerView rvDailyPlan;
    @Bind(R.id.rg_daily_plan_day)
    RadioGroup rg_daily_plan_day;
    private List<DailyPlan> mDatas;
    private DailyPlanAdapter mAdapter;
    private Teacher mTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_plan);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("日程安排");
        if (getIntent() != null && getIntent().getExtras() != null) {
            mTeacher = getIntent().getParcelableExtra("teacher");
        } else {
            finish();
            return;
        }
        initView();
        creatDate();
    }

    private void initView() {
        mDatas = new ArrayList<>();
        mAdapter = new DailyPlanAdapter(this, mDatas);
        rvDailyPlan.setLayoutManager(new LinearLayoutManager(this));
        rvDailyPlan.setAdapter(mAdapter);
        rg_daily_plan_day.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 切换日期
                createDatas(group.findViewById(checkedId).getTag().toString());
            }
        });
    }

    private void creatDate() {
        Calendar calendar = Calendar.getInstance();
        rbDailyPlanDay1.setText(Utils.calendar2strDate(calendar, Constants.PATTERN_MM_DD_2));
        rbDailyPlanDay1.setTag(Utils.calendar2strDate(calendar, Constants.PATTERN_YYYY_MM_DD));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        rbDailyPlanDay2.setText(Utils.calendar2strDate(calendar, Constants.PATTERN_MM_DD_2));
        rbDailyPlanDay2.setTag(Utils.calendar2strDate(calendar, Constants.PATTERN_YYYY_MM_DD));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        rbDailyPlanDay3.setText(Utils.calendar2strDate(calendar, Constants.PATTERN_MM_DD_2));
        rbDailyPlanDay3.setTag(Utils.calendar2strDate(calendar, Constants.PATTERN_YYYY_MM_DD));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        rbDailyPlanDay4.setText(Utils.calendar2strDate(calendar, Constants.PATTERN_MM_DD_2));
        rbDailyPlanDay4.setTag(Utils.calendar2strDate(calendar, Constants.PATTERN_YYYY_MM_DD));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        rbDailyPlanDay5.setText(Utils.calendar2strDate(calendar, Constants.PATTERN_MM_DD_2));
        rbDailyPlanDay5.setTag(Utils.calendar2strDate(calendar, Constants.PATTERN_YYYY_MM_DD));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        rbDailyPlanDay6.setText(Utils.calendar2strDate(calendar, Constants.PATTERN_MM_DD_2));
        rbDailyPlanDay6.setTag(Utils.calendar2strDate(calendar, Constants.PATTERN_YYYY_MM_DD));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        rbDailyPlanDay7.setText(Utils.calendar2strDate(calendar, Constants.PATTERN_MM_DD_2));
        rbDailyPlanDay7.setTag(Utils.calendar2strDate(calendar, Constants.PATTERN_YYYY_MM_DD));
        // 默认选中第一个
        rbDailyPlanDay1.setChecked(true);
    }

    private void createDatas(String time) {
        mDatas.clear();
//        // 构造数据
//        for (int i = 0; i < 20; i++) {
//            DailyPlan plan = new DailyPlan();
//            plan.duration = time;
//            plan.licenseLevel = "C1";
//            plan.subject = "科目一";
//            plan.price = "130.0";
//            plan.status = "可预约";
//            mDatas.add(plan);
//        }
        Map<String, String> map = new HashMap<>();
        map.put("teacharId", mTeacher.teacharId);
        map.put("timeDate", time);
        requestGet(Urls.GET_APPOINTMENT, map, new DialogCallback<BaseResponse<List<AppointmentResult>>>(this) {
            @Override
            public void onSuccess(BaseResponse<List<AppointmentResult>> listBaseResponse, Call call, Response response) {
                List<AppointmentResult> results = listBaseResponse.resObject;
                if (results != null && !results.isEmpty()) {
                    for (AppointmentResult result : results) {
                        DailyPlan plan = new DailyPlan();
                        plan.duration = String.format("%s-%s", result.timeBeginTime, result.timeEndTime);
                        plan.licenseLevel = result.timeLicense;
                        String subject;
                        if ("1".equals(result.timeSourse)) {
                            subject = "科目二";
                        } else if ("2".equals(result.timeSourse)) {
                            subject = "科目二 场地";
                        } else {
                            subject = "科目三";
                        }
                        plan.subject = subject;
                        plan.price = String.valueOf(result.timeMondy);
                        plan.status = "0".equals(result.timeOff) ? "不可预约" : "可预约";
                        mDatas.add(plan);
                    }
                    mAdapter.setDatas(mDatas);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (e instanceof IllegalStateException) {
                    ToastUtils.showToast(DailyPlanActivity.this, e.getMessage());
                }
                mAdapter.setDatas(mDatas);
                mAdapter.notifyDataSetChanged();
            }
        });

    }


    @OnClick({R.id.iv_header_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;
        }
    }
}
