package com.oriental.coach.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.oriental.coach.R;
import com.oriental.coach.activity.DailyPlanActivity;
import com.oriental.coach.activity.StatisticManagermentActivity;
import com.oriental.coach.utils.LogModule;
import com.zcw.togglebutton.ToggleButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainCoachFragment extends Fragment {
    public static final String TAG = MainCoachFragment.class.getSimpleName();
    @Bind(R.id.fl_student)
    FrameLayout fl_tab1;
    @Bind(R.id.fl_daily)
    FrameLayout fl_tab2;
    @Bind(R.id.fl_orders)
    FrameLayout fl_tab3;
    @Bind(R.id.fl_statistic)
    FrameLayout fl_statistic;
    @Bind(R.id.tb_switch)
    ToggleButton tb_switch;

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
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogModule.i(TAG, "onViewCreated");
        ButterKnife.bind(this, view);
        tb_switch.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                LogModule.i("toggle:" + on);
            }
        });

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
                break;
            case R.id.fl_daily:
                intent = new Intent(getActivity(), DailyPlanActivity.class);
                startActivity(intent);
                break;
            case R.id.fl_orders:
                break;
            case R.id.fl_statistic:
                intent = new Intent(getActivity(), StatisticManagermentActivity.class);
                startActivity(intent);
                break;
        }
    }
}
