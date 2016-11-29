package com.oriental.coach.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.oriental.coach.R;
import com.oriental.coach.adapter.CarTypeAdapter;
import com.oriental.coach.base.BaseActivity;
import com.oriental.coach.entity.CarType;
import com.oriental.coach.entity.InsuranceRecord;
import com.oriental.coach.entity.Teacher;
import com.oriental.coach.net.resp.CarResult;
import com.oriental.coach.net.urls.Urls;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author lwz
 * @Date 2016/11/15 0012
 * @Describe 车型
 */

public class CarTypeActivity extends BaseActivity implements CarTypeAdapter.CarTypeClickListener {


    @Bind(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @Bind(R.id.rv_list)
    RecyclerView rvList;
    private CarTypeAdapter mAdapter;
    private List<CarType> mEntities;
    private Teacher mTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_type);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("车型");
        mEntities = new ArrayList<>();
        if (getIntent() != null && getIntent().getExtras() != null) {
            mTeacher = getIntent().getParcelableExtra("teacher");
            if (!mTeacher.carResults.isEmpty()) {
                for (CarResult result : mTeacher.carResults) {
                    CarType carType = new CarType();
                    carType.name = result.carName;
                    carType.number = result.carCode;
                    if (!TextUtils.isEmpty(result.carLogo)) {
                        carType.url = Urls.SERVER_IMAGE + result.carLogo;
                    }
                    mEntities.add(carType);
                }
            }
        }
        mAdapter = new CarTypeAdapter(this, mEntities, this);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(mAdapter);
    }

    private void createData() {
        mEntities.clear();
        for (int i = 0; i < 20; i++) {
            CarType carType = new CarType();
            carType.name = "雪弗兰";
            carType.number = "晋121" + i;
            List<InsuranceRecord> records = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                InsuranceRecord record = new InsuranceRecord();
                record.company = "中国平安" + j;
                record.price = "5000";
                record.startDate = "2016-01-01";
                record.endDate = "2018-01-01";
                records.add(record);
            }
            carType.insuranceRecords = records;
            mEntities.add(carType);
        }
        mAdapter.setDatas(mEntities);
        mAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.iv_header_back, R.id.tv_header_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;
        }
    }

    @Override
    public void insuranceRecordClick(CarType carType) {
        Intent intent = new Intent(this, InsuranceRecordActivity.class);
        ArrayList<InsuranceRecord> records = new ArrayList<>(carType.insuranceRecords);
        intent.putParcelableArrayListExtra(InsuranceRecordActivity.EXTRA_KEY_INSURANCE_RECORD, records);
        startActivity(intent);
    }

    @Override
    public void examineRecordClick() {

    }

    @Override
    public void maintenanceRecordClick() {

    }
}
