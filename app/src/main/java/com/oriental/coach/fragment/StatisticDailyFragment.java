package com.oriental.coach.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oriental.coach.Constants;
import com.oriental.coach.R;
import com.oriental.coach.activity.StatisticDetailActivity;
import com.oriental.coach.adapter.StatisticDailyAdapter;
import com.oriental.coach.entity.StatisticDaily;
import com.oriental.coach.net.callback.DialogCallback;
import com.oriental.coach.net.resp.BaseResponse;
import com.oriental.coach.net.resp.BespeakResult;
import com.oriental.coach.net.urls.Urls;
import com.oriental.coach.utils.LogModule;
import com.oriental.coach.utils.PreferencesUtil;
import com.oriental.coach.utils.ToastUtils;
import com.oriental.coach.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;


public class StatisticDailyFragment extends Fragment {

    public static final String TAG = StatisticDailyFragment.class.getSimpleName();
    @Bind(R.id.rv_list)
    RecyclerView rv_list;
    private StatisticDailyAdapter mAdapter;
    private List<StatisticDaily> mDatas;

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
        return inflater.inflate(R.layout.fragment_statistic_daily_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogModule.i(TAG, "onViewCreated");
        ButterKnife.bind(this, view);
        mDatas = new ArrayList<>();
        mAdapter = new StatisticDailyAdapter(getActivity(), mDatas);
        rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_list.setAdapter(mAdapter);
        String teacherId = PreferencesUtil.getStringByName(getActivity(), "teacherId", "");
        if (TextUtils.isEmpty(teacherId)) {
            return;
        }
        createDatas(teacherId);
    }

    private void createDatas(String teacherId) {
        mDatas.clear();
        Map<String, String> map = new HashMap<>();
        map.put("flag", "day");
        map.put("teacherId", teacherId);
        ((StatisticDetailActivity) getActivity()).requestGet(Urls.GET_APP_BESPEAK, map, new DialogCallback<BaseResponse<List<BespeakResult>>>(getActivity()) {

            @Override
            public void onSuccess(BaseResponse<List<BespeakResult>> listBaseResponse, Call call, Response response) {
                List<BespeakResult> results = listBaseResponse.resObject;
                ToastUtils.showToast(getActivity(), listBaseResponse.stateMess);
                if (results != null && !results.isEmpty()) {
                    for (BespeakResult result : results) {
                        StatisticDaily daily = new StatisticDaily();
                        daily.name = result.studentName;
                        daily.price = result.bespeakSumMoney;
                        daily.income = result.takeRatio;
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(result.bespeakAddtime);
                        daily.time = String.format("%s %s-%s",
                                Utils.calendar2strDate(calendar, Constants.PATTERN_MM_DD_2),
                                result.timeBeginTime,
                                result.timeEndTime);
                        mDatas.add(daily);
                    }
                    mAdapter.setDatas(mDatas);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (e instanceof IllegalStateException) {
                    ToastUtils.showToast(getActivity(), e.getMessage());
                }
                mAdapter.setDatas(mDatas);
                mAdapter.notifyDataSetChanged();
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

}
