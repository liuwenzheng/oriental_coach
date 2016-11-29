package com.oriental.coach.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oriental.coach.R;
import com.oriental.coach.activity.DailyPlanActivity;
import com.oriental.coach.activity.OrderManagermentActivity;
import com.oriental.coach.activity.StatisticManagermentActivity;
import com.oriental.coach.activity.StudentManagermentActivity;
import com.oriental.coach.entity.Teacher;
import com.oriental.coach.net.urls.Urls;
import com.oriental.coach.utils.LogModule;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

public class MainCoachFragment extends Fragment {
    public static final String TAG = MainCoachFragment.class.getSimpleName();
    @Bind(R.id.civ_my_header)
    CircleImageView civMyHeader;
    @Bind(R.id.tv_my_name)
    TextView tvMyName;
    @Bind(R.id.tv_coach_driving_years)
    TextView tvCoachDrivingYears;
    @Bind(R.id.tv_coach_school)
    TextView tvCoachSchool;
    @Bind(R.id.tv_coach_title)
    TextView tv_coach_title;

    private Teacher mTeacher;


    @Override
    public void onAttach(Context context) {
        LogModule.i(TAG, "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogModule.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogModule.i(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_main_coach, null);
        ButterKnife.bind(this, view);
        // 从activity传过来的Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTeacher = bundle.getParcelable("teacher");
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogModule.i(TAG, "onViewCreated");
        ButterKnife.bind(this, view);
        initView();
    }

    private void initView() {
        if (mTeacher != null) {
            tvMyName.setText(mTeacher.name);
            tvCoachSchool.setText(mTeacher.school);
            tvCoachDrivingYears.setText(getString(R.string.driving_years, String.valueOf(mTeacher.carAge)));
            if (!TextUtils.isEmpty(mTeacher.logo)) {
                ImageLoader loader = ImageLoader.getInstance();
                loader.
            }
        }
    }

    @Override
    public void onStart() {
        LogModule.i(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        LogModule.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        LogModule.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        LogModule.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogModule.i(TAG, "onDestroyView");
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        LogModule.i(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        LogModule.i(TAG, "onDetach");
        super.onDetach();
    }

    @OnClick({R.id.fl_student, R.id.fl_daily, R.id.fl_orders, R.id.fl_statistic})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.fl_student:
                intent = new Intent(getActivity(), StudentManagermentActivity.class);
                startActivity(intent);
                break;
            case R.id.fl_daily:
                intent = new Intent(getActivity(), DailyPlanActivity.class);
                startActivity(intent);
                break;
            case R.id.fl_orders:
                intent = new Intent(getActivity(), OrderManagermentActivity.class);
                startActivity(intent);
                break;
            case R.id.fl_statistic:
                intent = new Intent(getActivity(), StatisticManagermentActivity.class);
                startActivity(intent);
                break;
        }
    }
}
