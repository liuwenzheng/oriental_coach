package com.oriental.coach.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.oriental.coach.R;
import com.oriental.coach.adapter.StudentManagermentAdapter;
import com.oriental.coach.base.BaseActivity;
import com.oriental.coach.entity.DailyPlan;
import com.oriental.coach.entity.Student;
import com.oriental.coach.entity.Teacher;
import com.oriental.coach.net.callback.DialogCallback;
import com.oriental.coach.net.resp.AppointmentResult;
import com.oriental.coach.net.resp.BaseResponse;
import com.oriental.coach.net.resp.StudentResult;
import com.oriental.coach.net.urls.Urls;
import com.oriental.coach.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.android.segmented.SegmentedGroup;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @Author lwz
 * @Date 2016/11/12 0012
 * @Describe 学生管理
 */

public class StudentManagermentActivity extends BaseActivity {
    @Bind(R.id.tv_header_title)
    TextView tvHeaderTitle;
    //    @Bind(R.id.sg_student_header)
//    SegmentedGroup sgStudentHeader;
//    @Bind(R.id.rb_subject_two)
//    RadioButton rbSubjectTwo;
//    @Bind(R.id.rb_subject_three)
//    RadioButton rbSubjectThree;
    @Bind(R.id.rv_list)
    RecyclerView rvList;
    private StudentManagermentAdapter mAdapter;
    private List<Student> mStudents;
    private Teacher mTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_managerment);
        ButterKnife.bind(this);
//        tvHeaderTitle.setVisibility(View.GONE);
        tvHeaderTitle.setText("学员管理");
//        sgStudentHeader.setVisibility(View.VISIBLE);
//        sgStudentHeader.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                creatData(((RadioButton) group.findViewById(checkedId)).getText().toString());
//            }
//        });
        if (getIntent() != null && getIntent().getExtras() != null) {
            mTeacher = getIntent().getParcelableExtra("teacher");
            mStudents = new ArrayList<>();
            mAdapter = new StudentManagermentAdapter(this, mStudents);
            rvList.setLayoutManager(new LinearLayoutManager(this));
            rvList.setAdapter(mAdapter);
//        rbSubjectTwo.setChecked(true);
            creatData();
        } else {
            finish();
            return;
        }
    }

    private void creatData() {
        mStudents.clear();
        Map<String, String> map = new HashMap<>();
        map.put("teacharId", mTeacher.teacharId);
        requestGet(Urls.GET_STUDENT, map, new DialogCallback<BaseResponse<List<StudentResult>>>(this) {
            @Override
            public void onSuccess(BaseResponse<List<StudentResult>> listBaseResponse, Call call, Response response) {
                List<StudentResult> results = listBaseResponse.resObject;
                if (results != null && !results.isEmpty()) {
                    for (StudentResult result : results) {
                        Student student = new Student();
                        student.name = result.studentName;
                        student.gender = result.studentSex;
                        student.phonenumber = result.studentPhone;
                        student.identityCard = result.studentIdcard;
                        String subject;
                        if ("1".equals(result.courseType)) {
                            subject = "科目二普通";
                        } else if ("2".equals(result.courseType)) {
                            subject = "科目二场内";
                        } else if ("3".equals(result.courseType)) {
                            subject = "科目三";
                        } else {
                            subject = "";
                        }
                        student.subject = subject;
                        if (!TextUtils.isEmpty(result.studentLogo)) {
                            student.headerUrl = Urls.SERVER_IMAGE + result.studentLogo;
                        }
                        mStudents.add(student);
                    }
                    mAdapter.setDatas(mStudents);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (e instanceof IllegalStateException) {
                    ToastUtils.showToast(StudentManagermentActivity.this, e.getMessage());
                }
                mAdapter.setDatas(mStudents);
                mAdapter.notifyDataSetChanged();
            }
        });
//        for (int i = 0; i < 20; i++) {
//            Student student = new Student();
//            student.gender = i % 2 == 0 ? 0 : 1;
//            student.name = "测试数据";
//            student.phonenumber = "13201002093";
//            student.identityCard = "410181192033021024";
//            mStudents.add(student);
//        }
//        mAdapter.setDatas(mStudents);
//        mAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.iv_header_back})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;

        }
    }
}
