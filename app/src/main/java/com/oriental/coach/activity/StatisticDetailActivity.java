package com.oriental.coach.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.oriental.coach.Constants;
import com.oriental.coach.R;
import com.oriental.coach.base.BaseActivity;
import com.oriental.coach.fragment.StatisticDailyFragment;
import com.oriental.coach.fragment.StatisticMonthFragment;
import com.oriental.coach.fragment.StatisticYearFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author lwz
 * @Date 2016/11/12 0012
 * @Describe 统计管理详情
 */

public class StatisticDetailActivity extends BaseActivity {
    @Bind(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @Bind(R.id.rb_statistic_detail_daily)
    RadioButton rbStatisticDetailDaily;
    @Bind(R.id.rb_statistic_detail_month)
    RadioButton rbStatisticDetailMonth;
    @Bind(R.id.rb_statistic_detail_year)
    RadioButton rbStatisticDetailYear;
    @Bind(R.id.rg_statistic_detail)
    RadioGroup rgStatisticDetail;
    @Bind(R.id.fl_fragment_container)
    FrameLayout flFragmentContainer;
    private int mCurrentTab;
    FragmentManager mFragmentManager;
    Fragment mStatisticDailyFrament;
    Fragment mStatisticMonthFragment;
    Fragment mStatisticYearFragment;
    Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_detail);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("统计管理");
        if (getIntent() != null && getIntent().getExtras() != null) {
            mCurrentTab = getIntent().getIntExtra(
                    Constants.EXTRA_KEY_STATISTIC, StatisticManagermentActivity.TYPE_STATISTIC_DAILY);
        }
        mFragmentManager = getSupportFragmentManager();
        initFragment();
        if (mCurrentTab == StatisticManagermentActivity.TYPE_STATISTIC_DAILY) {
            rbStatisticDetailDaily.setChecked(true);
            mCurrentFragment = mStatisticDailyFrament;
            mFragmentManager.beginTransaction().add(R.id.fl_fragment_container, mStatisticDailyFrament).commit();
        }
        if (mCurrentTab == StatisticManagermentActivity.TYPE_STATISTIC_MONTH) {
            rbStatisticDetailMonth.setChecked(true);
            mCurrentFragment = mStatisticMonthFragment;
            mFragmentManager.beginTransaction().add(R.id.fl_fragment_container, mStatisticMonthFragment).commit();
        }
        if (mCurrentTab == StatisticManagermentActivity.TYPE_STATISTIC_YEAR) {
            rbStatisticDetailYear.setChecked(true);
            mCurrentFragment = mStatisticYearFragment;
            mFragmentManager.beginTransaction().add(R.id.fl_fragment_container, mStatisticYearFragment).commit();
        }
        rgStatisticDetail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_statistic_detail_daily:
                        switchFragement(mStatisticDailyFrament);
                        break;
                    case R.id.rb_statistic_detail_month:
                        switchFragement(mStatisticMonthFragment);
                        break;
                    case R.id.rb_statistic_detail_year:
                        switchFragement(mStatisticYearFragment);
                        break;
                }
            }
        });
    }

    private void initFragment() {
        mStatisticDailyFrament = new StatisticDailyFragment();
        mStatisticMonthFragment = new StatisticMonthFragment();
        mStatisticYearFragment = new StatisticYearFragment();
    }

    private void switchFragement(Fragment fragment) {
        if (fragment == null || mCurrentFragment == fragment) {
            return;
        }
        if (!fragment.isAdded()) {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.fl_fragment_container, fragment).commit();
        } else {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(fragment).commit();
        }
        mCurrentFragment = fragment;
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
