package com.oriental.coach.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.oriental.coach.Constants;
import com.oriental.coach.R;
import com.oriental.coach.adapter.CarTypeAdapter;
import com.oriental.coach.base.BaseActivity;
import com.oriental.coach.entity.CarRecord;
import com.oriental.coach.entity.CarType;
import com.oriental.coach.entity.Teacher;
import com.oriental.coach.net.callback.DialogCallback;
import com.oriental.coach.net.resp.BaseResponse;
import com.oriental.coach.net.resp.CarRecordResult;
import com.oriental.coach.net.resp.CarResult;
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
 * @Describe 车型
 */

public class CarTypeActivity extends BaseActivity implements CarTypeAdapter.CarTypeClickListener {
    public static final String CAR_RECORD_TYPE_INSURANCE = "3";
    public static final String CAR_RECORD_TYPE_EXAMINE = "1";
    public static final String CAR_RECORD_TYPE_MAINTENANCE = "2";
    public static final String CAR_RECORD_TYPE_MAINTENANCE_TWICE = "4";

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
                    carType.carId = result.carId;
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

    @OnClick({R.id.iv_header_back, R.id.tv_header_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;
        }
    }

    @Override
    public void insuranceRecordClick(final CarType carType) {
        getRecord(carType, CAR_RECORD_TYPE_INSURANCE);
    }

    @Override
    public void examineRecordClick(CarType carType) {
        getRecord(carType, CAR_RECORD_TYPE_EXAMINE);
    }

    @Override
    public void maintenanceRecordClick(CarType carType) {
        getRecord(carType, CAR_RECORD_TYPE_MAINTENANCE);
    }

    @Override
    public void maintenanceTwiceRecordClick(CarType carType) {
        getRecord(carType, CAR_RECORD_TYPE_MAINTENANCE_TWICE);
    }

    private void getRecord(CarType carType, final String type) {
        Map<String, String> map = new HashMap<>();
        map.put("carId", carType.carId);
        map.put("recordType", type);
        requestGet(Urls.GET_CARRECORD, map, new DialogCallback<BaseResponse<List<CarRecordResult>>>(this) {
            @Override
            public void onSuccess(BaseResponse<List<CarRecordResult>> listBaseResponse, Call call, Response response) {
                List<CarRecordResult> results = listBaseResponse.resObject;
                if (results != null && !results.isEmpty()) {
                    ArrayList<CarRecord> records = new ArrayList<>();
                    for (int i = 0; i < results.size(); i++) {
                        CarRecordResult result = results.get(i);
                        CarRecord record = new CarRecord();
                        Calendar start = Calendar.getInstance();
                        start.setTime(result.recordBeginTime);
                        record.startDate = Utils.calendar2strDate(start, Constants.PATTERN_YYYY_MM_DD);
                        Calendar end = Calendar.getInstance();
                        end.setTime(result.recordEndTime);
                        record.endDate = Utils.calendar2strDate(end, Constants.PATTERN_YYYY_MM_DD);
                        records.add(record);
                    }
                    Intent intent = new Intent(CarTypeActivity.this, CarRecordActivity.class);
                    intent.putParcelableArrayListExtra(CarRecordActivity.EXTRA_KEY_INSURANCE_RECORD, records);
                    intent.putExtra("recordType", type);
                    startActivity(intent);
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (e instanceof IllegalStateException) {
                    ToastUtils.showToast(CarTypeActivity.this, e.getMessage());
                }
            }
        });
    }

}
