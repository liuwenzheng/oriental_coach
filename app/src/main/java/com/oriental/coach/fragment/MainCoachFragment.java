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

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.oriental.coach.R;
import com.oriental.coach.activity.AssembleTrainActivity;
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
    @Bind(R.id.tv_daily)
    TextView tvDaily;

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
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            mTeacher = bundle.getParcelable("teacher");
//        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogModule.i(TAG, "onViewCreated");
        ButterKnife.bind(this, view);
        initView();
    }

    public void setTeacher(Teacher teacher) {
        mTeacher = teacher;
    }

    public void refreshView() {
        initView();
    }

    private void initView() {
        if (mTeacher != null) {
            tvMyName.setText(mTeacher.name);
            tvCoachSchool.setText(mTeacher.school);
            tvCoachDrivingYears.setText(getString(R.string.driving_years, String.valueOf(mTeacher.carAge)));
            tvDaily.setText(mTeacher.teacharJobType == 2 ? "集训日程" : "记时日程");
            if (!TextUtils.isEmpty(mTeacher.logo)) {
                ImageLoader.getInstance().displayImage(Urls.SERVER_IMAGE + mTeacher.logo, civMyHeader, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        civMyHeader.setImageBitmap(ImageCrop(loadedImage));
                    }


                });
            }
        }
    }

    /**
     * 按正方形裁切图片
     */
    public static Bitmap ImageCrop(Bitmap bitmap) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        int wh = w > h ? h : w;// 裁切后所取的正方形区域边长

        //下面这句是关键
        return Bitmap.createBitmap(bitmap, 0, 10, wh, wh, null, false);
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
                intent.putExtra("teacher", mTeacher);
                startActivity(intent);
                break;
            case R.id.fl_daily:
                if (mTeacher.teacharJobType == 2) {
                    intent = new Intent(getActivity(), AssembleTrainActivity.class);
                    intent.putExtra("teacher", mTeacher);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), DailyPlanActivity.class);
                    intent.putExtra("teacher", mTeacher);
                    startActivity(intent);
                }
                break;
            case R.id.fl_orders:
                intent = new Intent(getActivity(), OrderManagermentActivity.class);
                intent.putExtra("teacher", mTeacher);
                startActivity(intent);
                break;
            case R.id.fl_statistic:
                intent = new Intent(getActivity(), StatisticManagermentActivity.class);
                startActivity(intent);
                break;
        }
    }
}
