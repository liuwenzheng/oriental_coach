package com.oriental.coach.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
import com.oriental.coach.utils.LogModule;
import com.oriental.coach.utils.PreferencesUtil;
import com.oriental.coach.utils.ToastUtils;
import com.oriental.coach.utils.Utils;
import com.oriental.coach.version.DownLoadAsyncTask;
import com.oriental.coach.version.GetServerUrl;
import com.oriental.coach.version.UpdateInfo;
import com.oriental.coach.version.UpdateInfoService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    private MyHandler handler;

    class MyHandler extends BaseHandler<MainActivity> {


        public MyHandler(MainActivity mainActivity) {
            super(mainActivity);
        }

        @Override
        protected void handleMessage(MainActivity mainActivity, Message msg) {
            switch (msg.what) {
                case 1:
                    //不提示对话框通知用户升级程序
                    showUpdateDialog();
                    break;

                case 2://安装新版本
                    update();
                    break;
                case 3:
                    final String teacherId = PreferencesUtil.getStringByName(MainActivity.this, "teacherId", "");
                    if (!TextUtils.isEmpty(teacherId)) {
                        if (mTeacher == null) {
                            requestTeacher(teacherId);
                        }
                    } else {
                        MainActivity.this.finish();
                        return;
                    }
                    break;
                case 500:
                    //服务器超时
                    Toast.makeText(getApplicationContext(), "获取服务器更新信息失败", Toast.LENGTH_LONG).show();
                    break;
            }
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
        handler = new MyHandler(this);
        /**
         * 比对版本号/读取更新信息/下载APK/安装
         */
        CheckVersionTask();

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

    private UpdateInfo info;

    /*
    * 从服务器获取xml解析并进行比对版本号
    */
    private void CheckVersionTask() {
        new Thread() {
            public void run() {
                try {
                    //获取服务器保存版本信息的路径
                    String path = GetServerUrl.url;
                    //包装成url的对象
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    InputStream is = conn.getInputStream();
                    //解析xml文件封装成对象
                    info = UpdateInfoService.getUpdataInfo(is);
                    if (info.getVersion().equals(Utils.getVersionName(MainActivity.this))) {
                        LogModule.i("版本号相同无需升级");
                        handler.sendEmptyMessageDelayed(3, 800);
                    } else {
                        LogModule.i("版本号不同 ,提示用户升级 ");
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    // 待处理
                    Message msg = new Message();
                    msg.what = 500;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /**
     * 安装apk
     */
    private void update() {
        /**
         * 程序的安装请注意：默认是不支持安装非市场程序的 因此判断一下
         * 下面是界面设置变动修改的settings信息。1是允许 0是不允许
         */
        Intent intent = new Intent(Intent.ACTION_VIEW);
//    	 	 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "DrivingTeacher.apk")),
                "application/vnd.android.package-archive");
        startActivity(intent);
        finish();
    }

    /**
     * 弹出版本升级提示框
     */
    private void showUpdateDialog() {
        AlertDialog.Builder builer = new AlertDialog.Builder(this);
        builer.setTitle("版本升级");
        builer.setMessage(info.getDescription());
        //当点确定按钮时从服务器上下载 新的apk 然后安装
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //开始下载最新APK
                downFile();
            }
        });
        //当点取消按钮时进行登录
        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MainActivity.this.finish();
            }
        });

        AlertDialog dialog = builer.create();
        dialog.setCancelable(false);//点击屏幕和物理返回键dialog不消失
        dialog.show();
    }


    /*
     * 从服务器中下载APK
     */
    private void downFile() {
        /**使用异步下载更新进度条下载进度*/
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("正在下载");
        progressDialog.setMax(100);//进度条最大值
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//水平样式
        progressDialog.setIndeterminate(false);//进度条的动画效果（有动画则无进度值）
        progressDialog.setCanceledOnTouchOutside(false);//点击屏幕dialog不消失
        //开始下载
        DownLoadAsyncTask downLoad = new DownLoadAsyncTask(MainActivity.this, handler, progressDialog);
        downLoad.execute(info.getUrl());
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
                mTeacher.teacharGrade = result.teacharGrade;
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
