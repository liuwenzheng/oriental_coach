package com.oriental.coach.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.oriental.coach.R;
import com.oriental.coach.adapter.StudentManagermentAdapter;
import com.oriental.coach.base.BaseActivity;
import com.oriental.coach.entity.Student;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.android.segmented.SegmentedGroup;

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
        mStudents = new ArrayList<>();
        mAdapter = new StudentManagermentAdapter(this, mStudents);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(mAdapter);
//        rbSubjectTwo.setChecked(true);
        creatData();
    }

    private void creatData() {
        mStudents.clear();
        for (int i = 0; i < 20; i++) {
            Student student = new Student();
            student.gender = i % 2 == 0 ? 0 : 1;
            student.name = "测试数据";
            student.phonenumber = "13201002093";
            student.identityCard = "410181192033021024";
            mStudents.add(student);
        }
        mAdapter.setDatas(mStudents);
        mAdapter.notifyDataSetChanged();
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
