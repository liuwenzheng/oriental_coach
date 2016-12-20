package com.oriental.coach.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.oriental.coach.R;
import com.oriental.coach.base.BaseActivity;
import com.oriental.coach.base.BaseHandler;
import com.oriental.coach.entity.Teacher;
import com.oriental.coach.fragment.MainCoachFragment;
import com.oriental.coach.fragment.MainMessageFragment;
import com.oriental.coach.fragment.MainMyFragment;
import com.oriental.coach.net.callback.DialogCallback;
import com.oriental.coach.net.resp.BaseResponse;
import com.oriental.coach.net.resp.CarResult;
import com.oriental.coach.net.resp.TeacherResult;
import com.oriental.coach.net.urls.Urls;
import com.oriental.coach.utils.PreferencesUtil;
import com.oriental.coach.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    FragmentManager mFragmentManager;
    Fragment mMainCoachFrament;
    Fragment mMainMyFragment;
    Fragment mMainMessageFragment;
    Fragment mCurrentFragment;
    @Bind(R.id.rb_main_home)
    RadioButton rbMainHome;
    @Bind(R.id.rb_main_message)
    RadioButton rbMainMessage;
    @Bind(R.id.rb_main_my)
    RadioButton rbMainMy;
    @Bind(R.id.rg_main)
    RadioGroup rgMain;
    // private Bundle mDatas;

    class MyHandler extends BaseHandler<MainActivity> {


        public MyHandler(MainActivity mainActivity) {
            super(mainActivity);
        }

        @Override
        protected void handleMessage(MainActivity mainActivity, Message msg) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        if (savedInstanceState != null) {
            return;
        }
//        mDatas = new Bundle();
//        mDatas.putParcelable("teacher", mTeacher);
        mFragmentManager = getSupportFragmentManager();
        initFragment();
        mCurrentFragment = mMainCoachFrament;
        mFragmentManager.beginTransaction().add(R.id.fl_fragment_container, mMainCoachFrament).commit();
        // selectedIcon();
        rbMainHome.setChecked(true);
        final String teacherId = PreferencesUtil.getStringByName(this, "teacherId", "");
        if (!TextUtils.isEmpty(teacherId)) {
            if (mTeacher == null) {
                new MyHandler(this).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestTeacher(teacherId);
                    }
                }, 800);
            }
        } else {
            finish();
            return;
        }
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_main_home:
                        switchFragement(mMainCoachFrament, mFragmentManager.beginTransaction());
                        break;
                    case R.id.rb_main_message:
                        switchFragement(mMainMessageFragment, mFragmentManager.beginTransaction());
                        break;
                    case R.id.rb_main_my:
                        switchFragement(mMainMyFragment, mFragmentManager.beginTransaction());
                        break;
                }
            }
        });
    }

    private Teacher mTeacher;

    private void requestTeacher(final String tableId) {
        Map<String, String> req = new HashMap<>();
        req.put("teacharId", tableId);
        requestGet(Urls.GET_TEACHER, req, new DialogCallback<BaseResponse<TeacherResult>>(this, false) {
            @Override
            public void onSuccess(BaseResponse<TeacherResult> teacherResult, Call call, Response response) {
                TeacherResult result = teacherResult.resObject;
                mTeacher = new Teacher();
                mTeacher.name = result.teacharName;
                mTeacher.carAge = result.teacharCarAge;
                mTeacher.school = result.schoolName;
                mTeacher.courseType = result.courseType;
                mTeacher.gender = result.teacharSex;
                mTeacher.goodCommPro = result.goodCommPro;
                mTeacher.logo = result.teacharLogo;
                mTeacher.phoneNo = result.teacharPhone;
                mTeacher.studentCnt = result.studentCnt;
                mTeacher.teacharId = tableId;
                StringBuilder builder = new StringBuilder();
                builder.append(result.proviceName)
                        .append(result.citeName)
                        .append(result.countyName)
                        .append(result.areaName)
                        .append(result.addressDetail);
                mTeacher.address = builder.toString();
                requestCar(tableId);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (e instanceof IllegalStateException) {
                    ToastUtils.showToast(MainActivity.this, e.getMessage());
                } else {
                    ToastUtils.showToast(MainActivity.this, "网络错误");
                    finish();
                }
            }
        });
    }

    private void requestCar(String tableId) {
        Map<String, String> req = new HashMap<>();
        req.put("teacherId", tableId);
        requestGet(Urls.GET_TEACHER_CAR, req, new DialogCallback<BaseResponse<List<CarResult>>>(this, false) {
            @Override
            public void onSuccess(BaseResponse<List<CarResult>> carResult, Call call, Response response) {
                List<CarResult> results = carResult.resObject;
                mTeacher.carResults = results;
                ((MainCoachFragment) mMainCoachFrament).setTeacher(mTeacher);
                ((MainCoachFragment) mMainCoachFrament).refreshView();
                ((MainMyFragment) mMainMyFragment).setTeacher(mTeacher);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (e instanceof IllegalStateException) {
                    ToastUtils.showToast(MainActivity.this, e.getMessage());
                } else {
                    ToastUtils.showToast(MainActivity.this, "网络错误");
                    finish();
                }
            }
        });
    }

//    private void selectedIcon() {
//        if (mCurrentFragment == mMainCoachFrament) {
//            Drawable homeTopDrawable = ContextCompat.getDrawable(this, R.drawable.home_selected);
//            homeTopDrawable.setBounds(0, 0, homeTopDrawable.getMinimumWidth(), homeTopDrawable.getMinimumHeight());
//            btnMainHome.setCompoundDrawables(null, homeTopDrawable, null, null);
//            Drawable myTopDrawable = ContextCompat.getDrawable(this, R.drawable.my_unselected);
//            myTopDrawable.setBounds(0, 0, myTopDrawable.getMinimumWidth(), myTopDrawable.getMinimumHeight());
//            btnMainMy.setCompoundDrawables(null, myTopDrawable, null, null);
//            btnMainHome.setTextColor(ContextCompat.getColor(this, R.color.blue_4c6f98));
//            btnMainMy.setTextColor(ContextCompat.getColor(this, R.color.grey_9a9a9a));
//        } else if (mCurrentFragment == mMainMyFragment) {
//            Drawable homeTopDrawable = ContextCompat.getDrawable(this, R.drawable.home_unselected);
//            homeTopDrawable.setBounds(0, 0, homeTopDrawable.getMinimumWidth(), homeTopDrawable.getMinimumHeight());
//            btnMainHome.setCompoundDrawables(null, homeTopDrawable, null, null);
//            Drawable myTopDrawable = ContextCompat.getDrawable(this, R.drawable.my_selected);
//            myTopDrawable.setBounds(0, 0, myTopDrawable.getMinimumWidth(), myTopDrawable.getMinimumHeight());
//            btnMainMy.setCompoundDrawables(null, myTopDrawable, null, null);
//            btnMainHome.setTextColor(ContextCompat.getColor(this, R.color.grey_9a9a9a));
//            btnMainMy.setTextColor(ContextCompat.getColor(this, R.color.blue_4c6f98));
//        } else {
//            Drawable homeTopDrawable = ContextCompat.getDrawable(this, R.drawable.message_unselected);
//            homeTopDrawable.setBounds(0, 0, homeTopDrawable.getMinimumWidth(), homeTopDrawable.getMinimumHeight());
//            btnMainHome.setCompoundDrawables(null, homeTopDrawable, null, null);
//            Drawable myTopDrawable = ContextCompat.getDrawable(this, R.drawable.message_selected);
//            myTopDrawable.setBounds(0, 0, myTopDrawable.getMinimumWidth(), myTopDrawable.getMinimumHeight());
//            btnMainMy.setCompoundDrawables(null, myTopDrawable, null, null);
//            btnMainHome.setTextColor(ContextCompat.getColor(this, R.color.grey_9a9a9a));
//            btnMainMy.setTextColor(ContextCompat.getColor(this, R.color.blue_4c6f98));
//        }
//    }

    private void switchFragement(Fragment fragment, FragmentTransaction fragmentTransaction) {
        if (fragment == null || mCurrentFragment == fragment) {
            return;
        }
        if (!fragment.isAdded()) {
            fragmentTransaction.hide(mCurrentFragment).add(R.id.fl_fragment_container, fragment).commit();
        } else {
            fragmentTransaction.hide(mCurrentFragment).show(fragment).commit();
            if (fragment == mMainMessageFragment) {
                ((MainMessageFragment) fragment).refreshData();
            }
        }
        mCurrentFragment = fragment;
        // selectedIcon();

    }

    private void initFragment() {
        mMainCoachFrament = new MainCoachFragment();
        mMainMyFragment = new MainMyFragment();
        mMainMessageFragment = new MainMessageFragment();
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
