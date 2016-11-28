package com.oriental.coach.activity;

import android.app.Activity;
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
import com.oriental.coach.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_managerment);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("订单管理");
        rgOrderManagerment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                createDatas(Integer.parseInt((String) group.findViewById(checkedId).getTag()));
            }
        });
        mEntities = new ArrayList<>();
        mAdapter = new OrderManagermentAdapter(this, mEntities);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(mAdapter);
        rbOrderManagermentFinished.setChecked(true);
    }

    private void createDatas(int status) {
        mEntities.clear();
        for (int i = 0; i < 20; i++) {
            OrderEntity entity = new OrderEntity();
            entity.name = "测试" + i;
            if (OrderManagermentAdapter.ORDER_STATUS_UNFINISHED == status) {
                entity.surplusTime = "1天12小时32分钟";
            } else {
                entity.surplusTime = "";
            }
            entity.orderStatus = status;
            entity.subject = "科目三";
            entity.phonenumber = "13200293821";
            entity.price = "130.0";
            entity.payType = "支付宝";
            entity.createTime = Utils.calendar2strDate(Calendar.getInstance(), Constants.PATTERN_YYYY_MM_DD_HH_MM);
            entity.orderNumber = "123456789";
            entity.courseTime = "2016-09-13 07:00-18:00";
            mEntities.add(entity);
        }
        mAdapter.setEntities(mEntities);
        mAdapter.notifyDataSetChanged();
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
