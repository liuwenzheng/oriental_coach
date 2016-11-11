package com.oriental.coach.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.oriental.coach.R;
import com.oriental.coach.base.BaseActivity;
import com.oriental.coach.fragment.MainCoachFragment;
import com.oriental.coach.fragment.MainMyFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.fl_fragment_container)
    FrameLayout mainFragment;
    FragmentManager mFragmentManager;
    Fragment mMainCoachFrament;
    Fragment mMainMyFragment;
    Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            return;
        }
        mFragmentManager = getSupportFragmentManager();
        initFragment();
        mCurrentFragment = mMainCoachFrament;
        mFragmentManager.beginTransaction().add(R.id.fl_fragment_container, mMainCoachFrament).commit();
    }

    private void switchFragement(Fragment fragment, FragmentTransaction fragmentTransaction) {
        if (fragment == null || mCurrentFragment == fragment) {
            return;
        }
        if (!fragment.isAdded()) {
            fragmentTransaction.hide(mCurrentFragment).add(R.id.fl_fragment_container, fragment).commit();
        } else {
            fragmentTransaction.hide(mCurrentFragment).show(fragment).commit();
        }
        mCurrentFragment = fragment;
    }

    private void initFragment() {
        mMainCoachFrament = new MainCoachFragment();
        mMainMyFragment = new MainMyFragment();
    }

    @OnClick(R.id.btn_main_home)
    void onBtnMainLeftClick() {
        switchFragement(mMainCoachFrament, mFragmentManager.beginTransaction());
    }


    @OnClick(R.id.btn_main_my)
    void onBtnMainRightClick() {
        switchFragement(mMainMyFragment, mFragmentManager.beginTransaction());
    }

}
