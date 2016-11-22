package com.oriental.coach.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.oriental.coach.R;
import com.oriental.coach.entity.InsuranceRecord;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author lwz
 * @Date 2016/11/15 0012
 * @Describe 登录
 */

public class InsuranceRecordActivity extends Activity {

    public static final String EXTRA_KEY_INSURANCE_RECORD = "insurance_record";
    @Bind(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @Bind(R.id.tv_company)
    TextView tvCompany;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_start_date)
    TextView tvStartDate;
    @Bind(R.id.tv_end_date)
    TextView tvEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_record);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("保险记录");
        if (getIntent() != null && getIntent().getExtras() != null) {
            InsuranceRecord record = getIntent().getParcelableExtra(EXTRA_KEY_INSURANCE_RECORD);
            tvCompany.setText(record.company);
            tvPrice.setText(record.price);
            tvStartDate.setText(record.startDate);
            tvEndDate.setText(record.endDate);
        } else {
            finish();
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
