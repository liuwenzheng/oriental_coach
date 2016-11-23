package com.oriental.coach.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.oriental.coach.R;
import com.oriental.coach.adapter.InsuranceRecordAdapter;
import com.oriental.coach.entity.InsuranceRecord;

import java.util.ArrayList;

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
    @Bind(R.id.rv_list)
    RecyclerView rvList;
    private InsuranceRecordAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_record);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("保险记录");
        if (getIntent() != null && getIntent().getExtras() != null) {
            ArrayList<InsuranceRecord> records = getIntent().getParcelableArrayListExtra(EXTRA_KEY_INSURANCE_RECORD);
            rvList.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new InsuranceRecordAdapter(this, records);
            rvList.setAdapter(mAdapter);
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
