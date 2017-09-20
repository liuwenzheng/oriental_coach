package com.oriental.coach.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.oriental.coach.Constants;
import com.oriental.coach.R;
import com.oriental.coach.adapter.AssembleTrainAdapter;
import com.oriental.coach.base.BaseActivity;
import com.oriental.coach.entity.AssembleTrain;
import com.oriental.coach.entity.Teacher;
import com.oriental.coach.net.callback.DialogCallback;
import com.oriental.coach.net.resp.AssembleTrainResult;
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
 * @Date 2017/8/31
 * @Author wenzheng.liu
 * @Description
 * @ClassPath com.oriental.coach.activity.AssembleTrainActivity
 */
public class AssembleTrainActivity extends BaseActivity {
    @Bind(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @Bind(R.id.rv_assemble_train)
    RecyclerView rvAssembleTrain;
    private List<AssembleTrain> mDatas;
    private AssembleTrainAdapter mAdapter;
    private Teacher mTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assemble_train);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("集训日程");
        if (getIntent() != null && getIntent().getExtras() != null) {
            mTeacher = getIntent().getParcelableExtra("teacher");
        } else {
            finish();
            return;
        }
        initView();
        createDatas();
    }

    private void initView() {
        mDatas = new ArrayList<>();
        mAdapter = new AssembleTrainAdapter(this, mDatas);
        rvAssembleTrain.setLayoutManager(new LinearLayoutManager(this));
        rvAssembleTrain.setAdapter(mAdapter);
    }

    private void createDatas() {
        mDatas.clear();
        Map<String, String> map = new HashMap<>();
        map.put("teacherId", mTeacher.teacharId);
        requestGet(Urls.GET_ASSEMBLE_TRAIN, map, new DialogCallback<BaseResponse<List<AssembleTrainResult>>>(this) {
            @Override
            public void onSuccess(BaseResponse<List<AssembleTrainResult>> listBaseResponse, Call call, Response response) {
                List<AssembleTrainResult> results = listBaseResponse.resObject;
                if (results != null && !results.isEmpty()) {
                    for (AssembleTrainResult result : results) {
                        AssembleTrain assembleTrain = new AssembleTrain();
                        Calendar beginCalendar = Calendar.getInstance();
                        beginCalendar.setTime(result.timeBeginTime);
                        assembleTrain.timeBeginTime = Utils.calendar2strDate(beginCalendar, Constants.PATTERN_MM_DD_3);
                        Calendar endCalendar = Calendar.getInstance();
                        endCalendar.setTime(result.timeEndTime);
                        assembleTrain.timeEndTime = Utils.calendar2strDate(endCalendar, Constants.PATTERN_MM_DD_3);
                        assembleTrain.ifStart = result.ifStart;
                        assembleTrain.studentNum = result.studentNum;
                        assembleTrain.minStudentNum = result.minStudentNum;
                        assembleTrain.maxStudentNum = result.maxStudentNum;
                        mDatas.add(assembleTrain);
                    }
                    mAdapter.setDatas(mDatas);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (e instanceof IllegalStateException) {
                    ToastUtils.showToast(AssembleTrainActivity.this, e.getMessage());
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
