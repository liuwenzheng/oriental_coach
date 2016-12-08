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
import com.oriental.coach.adapter.OrderManagermentAdapter;
import com.oriental.coach.base.BaseActivity;
import com.oriental.coach.entity.OrderEntity;
import com.oriental.coach.entity.Teacher;
import com.oriental.coach.net.callback.DialogCallback;
import com.oriental.coach.net.resp.BaseResponse;
import com.oriental.coach.net.resp.BespeakResult;
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
 * @Date 2016/11/12 0012
 * @Describe 订单管理
 */

public class OrderManagermentActivity extends BaseActivity {
    @Bind(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @Bind(R.id.rb_order_managerment_finished)
    RadioButton rbOrderManagermentFinished;
    @Bind(R.id.rb_order_managerment_cancel)
    RadioButton rbOrderManagermentCancel;
    @Bind(R.id.rb_order_managerment_unfinished)
    RadioButton rbOrderManagermentUnfinished;
    @Bind(R.id.rg_order_managerment)
    RadioGroup rgOrderManagerment;
    @Bind(R.id.rv_list)
    RecyclerView rvList;
    private List<OrderEntity> mEntities;
    private OrderManagermentAdapter mAdapter;
    private Teacher mTeacher;
    public static final String STATE_BESPEAK_FINISHED = "3,4,5";
    public static final String STATE_BESPEAK_CANCEL = "1_0,2_0,2_1";
    public static final String STATE_BESPEAK_UNFINISHED = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_managerment);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("订单管理");
        rgOrderManagerment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                createDatas((String) group.findViewById(checkedId).getTag());
            }
        });
        if (getIntent() != null && getIntent().getExtras() != null) {
            mTeacher = getIntent().getParcelableExtra("teacher");
        } else {
            finish();
            return;
        }
        mEntities = new ArrayList<>();
        mAdapter = new OrderManagermentAdapter(this, mEntities);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(mAdapter);
        rbOrderManagermentFinished.setChecked(true);
    }

    private void createDatas(String status) {
        mEntities.clear();
        Map<String, String> map = new HashMap<>();
        map.put("teacharId", mTeacher.teacharId);
        map.put("bespeakState", status);
        requestGet(Urls.GET_BESPEAK, map, new DialogCallback<BaseResponse<List<BespeakResult>>>(this) {

            @Override
            public void onSuccess(BaseResponse<List<BespeakResult>> listBaseResponse, Call call, Response response) {
                List<BespeakResult> results = listBaseResponse.resObject;
                if (results != null && !results.isEmpty()) {
                    for (int i = 0; i < results.size(); i++) {
                        BespeakResult result = results.get(i);
                        OrderEntity entity = new OrderEntity();
                        entity.name = result.studentName;
                        if (STATE_BESPEAK_UNFINISHED.equals(result.bespeakState)) {
                            // 未完成订单，计算时间
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(result.timeDate);
                            String timeDay = Utils.calendar2strDate(calendar, Constants.PATTERN_YYYY_MM_DD);
                            String timeMinute = String.format("%s %s", timeDay, result.timeBeginTime);
                            calendar = Utils.strDate2Calendar(timeMinute, Constants.PATTERN_YYYY_MM_DD_HH_MM);

                            if (Utils.compareDate(calendar.getTime(), Calendar.getInstance().getTime()) != 1) {
                                entity.surplusTime = "正在完成中";
                            } else {
                                int time = Utils.getIntervalMin(Utils.calendar2strDate(Calendar.getInstance(), Constants.PATTERN_YYYY_MM_DD_HH_MM),
                                        timeMinute,
                                        Constants.PATTERN_YYYY_MM_DD_HH_MM);
                                int day = time / 60 / 24;
                                int hour = time / 60 % 24;
                                int min = time % 60;
                                entity.surplusTime = String.format("%s天%s小时%s分钟", day, hour, min);
                            }
                        }
                        entity.orderStatus = result.bespeakState;
                        String subject;
                        if ("1".equals(result.timeSourse)) {
                            subject = "科目二普通";
                        } else if ("2".equals(result.timeSourse)) {
                            subject = "科目二场地";
                        } else {
                            subject = "科目三";
                        }
                        entity.subject = subject;
                        entity.phonenumber = result.studentPhone;
                        entity.headerUrl = result.studentLogo;
                        entity.price = result.bespeakSumMoney;
                        entity.payType = result.tradeType;
                        Calendar addTime = Calendar.getInstance();
                        addTime.setTime(result.bespeakAddtime);
                        entity.createTime = Utils.calendar2strDate(addTime, Constants.PATTERN_YYYY_MM_DD_HH_MM);
                        entity.orderNumber = result.bespeakCode;
                        Calendar courseTime = Calendar.getInstance();
                        courseTime.setTime(result.timeDate);
                        entity.courseTime = String.format("%s %s-%s", Utils.calendar2strDate(courseTime, Constants.PATTERN_YYYY_MM_DD), result.timeBeginTime, result.timeEndTime);
                        mEntities.add(entity);
                    }
                    mAdapter.setEntities(mEntities);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (e instanceof IllegalStateException) {
                    ToastUtils.showToast(OrderManagermentActivity.this, e.getMessage());
                }
                mAdapter.setEntities(mEntities);
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
