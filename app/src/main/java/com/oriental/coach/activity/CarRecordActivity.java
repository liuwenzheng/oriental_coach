package com.oriental.coach.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.oriental.coach.R;
import com.oriental.coach.adapter.InsuranceRecordAdapter;
import com.oriental.coach.base.BaseActivity;
import com.oriental.coach.entity.CarRecord;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author lwz
 * @Date 2016/11/15 0012
 * @Describe 车辆记录
 */

public class CarRecordActivity extends BaseActivity {

    public static final String EXTRA_KEY_INSURANCE_RECORD = "insurance_record";
    @Bind(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @Bind(R.id.rv_list)
    RecyclerView rvList;
    private InsuranceRecordAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_record);
        ButterKnife.bind(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            String title = getIntent().getStringExtra("recordType");
            if (CarTypeActivity.CAR_RECORD_TYPE_INSURANCE.equals(title)) {
                tvHeaderTitle.setText("保险记录");
            }
            if (CarTypeActivity.CAR_RECORD_TYPE_EXAMINE.equals(title)) {
                tvHeaderTitle.setText("年检记录");
            }
            if (CarTypeActivity.CAR_RECORD_TYPE_MAINTENANCE.equals(title)) {
                tvHeaderTitle.setText("保养记录");
            }
            if (CarTypeActivity.CAR_RECORD_TYPE_MAINTENANCE_TWICE.equals(title)) {
                tvHeaderTitle.setText("二维记录");
            }
            ArrayList<CarRecord> records = getIntent().getParcelableArrayListExtra(EXTRA_KEY_INSURANCE_RECORD);
            rvList.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new InsuranceRecordAdapter(this, records);
            rvList.setAdapter(mAdapter);
        } else {
            finish();
            return;
        }
    }


    @OnClick({R.id.iv_header_back, R.id.tv_header_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;
        }
    }
}
