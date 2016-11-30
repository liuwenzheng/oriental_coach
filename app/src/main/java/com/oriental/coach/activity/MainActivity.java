package com.oriental.coach.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.widget.Button;

import com.oriental.coach.R;
import com.oriental.coach.base.BaseActivity;
import com.oriental.coach.entity.Teacher;
import com.oriental.coach.fragment.MainCoachFragment;
import com.oriental.coach.fragment.MainMyFragment;
import com.oriental.coach.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    FragmentManager mFragmentManager;
    Fragment mMainCoachFrament;
    Fragment mMainMyFragment;
    Fragment mCurrentFragment;
    @Bind(R.id.btn_main_home)
    Button btnMainHome;
    @Bind(R.id.btn_main_my)
    Button btnMainMy;
    private Bundle mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            return;
        }
        if (getIntent() != null && getIntent().getExtras() != null) {
            Teacher teacher = getIntent().getParcelableExtra("teacher");
            mDatas = new Bundle();
            mDatas.putParcelable("teacher", teacher);
        } else {
            finish();
            return;
        }
        mFragmentManager = getSupportFragmentManager();
        initFragment();
        mCurrentFragment = mMainCoachFrament;
        mFragmentManager.beginTransaction().add(R.id.fl_fragment_container, mMainCoachFrament).commit();
        selectedIcon();
    }

    private void selectedIcon() {
        if (mCurrentFragment == mMainCoachFrament) {
            Drawable homeTopDrawable = ContextCompat.getDrawable(this, R.drawable.home_selected);
            homeTopDrawable.setBounds(0, 0, homeTopDrawable.getMinimumWidth(), homeTopDrawable.getMinimumHeight());
            btnMainHome.setCompoundDrawables(null, homeTopDrawable, null, null);
            Drawable myTopDrawable = ContextCompat.getDrawable(this, R.drawable.my_unselected);
            myTopDrawable.setBounds(0, 0, myTopDrawable.getMinimumWidth(), myTopDrawable.getMinimumHeight());
            btnMainMy.setCompoundDrawables(null, myTopDrawable, null, null);
            btnMainHome.setTextColor(ContextCompat.getColor(this, R.color.blue_4c6f98));
            btnMainMy.setTextColor(ContextCompat.getColor(this, R.color.grey_9a9a9a));
        } else {
            Drawable homeTopDrawable = ContextCompat.getDrawable(this, R.drawable.home_unselected);
            homeTopDrawable.setBounds(0, 0, homeTopDrawable.getMinimumWidth(), homeTopDrawable.getMinimumHeight());
            btnMainHome.setCompoundDrawables(null, homeTopDrawable, null, null);
            Drawable myTopDrawable = ContextCompat.getDrawable(this, R.drawable.my_selected);
            myTopDrawable.setBounds(0, 0, myTopDrawable.getMinimumWidth(), myTopDrawable.getMinimumHeight());
            btnMainMy.setCompoundDrawables(null, myTopDrawable, null, null);
            btnMainHome.setTextColor(ContextCompat.getColor(this, R.color.grey_9a9a9a));
            btnMainMy.setTextColor(ContextCompat.getColor(this, R.color.blue_4c6f98));
        }
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
        selectedIcon();
    }

    private void initFragment() {
        mMainCoachFrament = new MainCoachFragment();
        mMainCoachFrament.setArguments(mDatas);
        mMainMyFragment = new MainMyFragment();
        mMainMyFragment.setArguments(mDatas);
    }

    @OnClick(R.id.btn_main_home)
    void onBtnMainLeftClick() {
        switchFragement(mMainCoachFrament, mFragmentManager.beginTransaction());
    }


    @OnClick(R.id.btn_main_my)
    void onBtnMainRightClick() {
        switchFragement(mMainMyFragment, mFragmentManager.beginTransaction());
    }

    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    //如果两次按键时间间隔大于2秒，则不退出
                    ToastUtils.showToast(this, "再按一次退出程序");
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {
                    //两次按键小于2秒时，退出应用
                    finish();
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}
