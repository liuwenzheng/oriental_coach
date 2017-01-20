package com.oriental.coach.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.oriental.coach.R;
import com.oriental.coach.activity.CarTypeActivity;
import com.oriental.coach.activity.SettingActivity;
import com.oriental.coach.entity.Teacher;
import com.oriental.coach.net.urls.Urls;
import com.oriental.coach.utils.LogModule;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainMyFragment extends Fragment {

    public static final String TAG = MainMyFragment.class.getSimpleName();
    @Bind(R.id.civ_my_header)
    CircleImageView civ_my_header;
    @Bind(R.id.tv_my_name)
    TextView tv_my_name;
    @Bind(R.id.tv_my_gender)
    TextView tv_my_gender;
    @Bind(R.id.tv_my_phonenumber)
    TextView tv_my_phonenumber;
    @Bind(R.id.rb_my_rating)
    RatingBar rb_my_rating;
    @Bind(R.id.tv_my_driving_years)
    TextView tv_my_driving_years;
    @Bind(R.id.tv_my_student_total)
    TextView tv_my_student_total;
    @Bind(R.id.tv_my_feedback_rate)
    TextView tv_my_feedback_rate;
    @Bind(R.id.tv_my_training_field)
    TextView tv_my_training_field;
    @Bind(R.id.tv_my_car_type)
    TextView tv_my_car_type;
    @Bind(R.id.tv_my_training_subject)
    TextView tv_my_training_subject;

    private PopupWindow mSharePopupWindow;
    private Teacher mTeacher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_my, null);
        ButterKnife.bind(this, view);
        // 从activity传过来的Bundle
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            mTeacher = bundle.getParcelable("teacher");
//        }
        return view;
    }

    public void setTeacher(Teacher teacher) {
        mTeacher = teacher;
    }

    public void refreshView() {
        initView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView();
    }

    private void initView() {
        if (mTeacher != null) {
            tv_my_name.setText(mTeacher.name);
            tv_my_gender.setText("2".equals(mTeacher.gender) ? "女" : "男");
            tv_my_phonenumber.setText(getString(R.string.phonenumber, mTeacher.phoneNo));
//            if (mTeacher.goodCommPro > 0 && mTeacher.goodCommPro <= 20) {
            rb_my_rating.setRating((float) mTeacher.teacharGrade);
//            } else if (mTeacher.goodCommPro > 20 && mTeacher.goodCommPro <= 40) {
//                rb_my_rating.setRating(2);
//            } else if (mTeacher.goodCommPro > 40 && mTeacher.goodCommPro <= 60) {
//                rb_my_rating.setRating(3);
//            } else if (mTeacher.goodCommPro > 60 && mTeacher.goodCommPro <= 80) {
//                rb_my_rating.setRating(4);
//            } else if (mTeacher.goodCommPro > 80 && mTeacher.goodCommPro <= 100) {
//                rb_my_rating.setRating(5);
//            } else {
//                rb_my_rating.setRating(0);
//            }
            tv_my_driving_years.setText(mTeacher.carAge + "");
            tv_my_student_total.setText(mTeacher.studentCnt + "");
            tv_my_feedback_rate.setText(mTeacher.goodCommPro + "");
            if (!TextUtils.isEmpty(mTeacher.courseType)) {
                String[] s = mTeacher.courseType.split(",");
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < s.length; i++) {
                    if ("1".equals(s[i])) {
                        builder.append("科目二普通");
                    } else if ("2".equals(s[i])) {
                        builder.append("科目二场内");
                    } else if ("3".equals(s[i])) {
                        builder.append("科目三");
                    }
                    if (i < s.length - 1) {
                        builder.append("、");
                    }
                }
                tv_my_training_subject.setText(builder.toString());
            }
            tv_my_training_field.setText(mTeacher.address);
            if (!TextUtils.isEmpty(mTeacher.logo)) {
                ImageLoader.getInstance().displayImage(Urls.SERVER_IMAGE + mTeacher.logo, civ_my_header, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        civ_my_header.setImageBitmap(ImageCrop(loadedImage));
                    }
                });
            }
            if (mTeacher.carResults != null && !mTeacher.carResults.isEmpty()) {
                tv_my_car_type.setText(mTeacher.carResults.get(0).carName);
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ll_my_car_type, R.id.ll_my_order_message, R.id.ll_my_share, R.id.ll_my_setting})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_my_car_type:
                intent = new Intent(getActivity(), CarTypeActivity.class);
                intent.putExtra("teacher", mTeacher);
                startActivity(intent);
                break;
//            case R.id.ll_my_order_message:
//                intent = new Intent(getActivity(), OrderMessageActivity.class);
//                intent.putExtra("teacher", mTeacher);
//                startActivity(intent);
//                break;
            case R.id.ll_my_share:
//                if (!getActivity().isFinishing() && mSharePopupWindow != null && mSharePopupWindow.isShowing()) {
//                    showSharePopup(true);
//                } else {
//                    showSharePopup(false);
//                }
                String msgTitle = "新东方驾校";
                String msgText = "东方驾驶学院约车系统，最好的预约学车管理平台，进入网站下载：http://www.ycxdfjy.com";
                String activityTitle = "新东方驾校";

                String imgPath = "";
                shareMsg(activityTitle, msgTitle, msgText, imgPath);
                break;
            case R.id.ll_my_setting:
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 分享功能
     *
     * @param activityTitle Activity的名字
     * @param msgTitle      消息标题
     * @param msgText       消息内容
     * @param imgPath       图片路径，不分享图片则传null
     */
    public void shareMsg(String activityTitle, String msgTitle, String msgText, String imgPath) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            LogModule.e("获取图片的路径为：", imgPath);
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/*");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra("Kdescription", msgTitle);
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, activityTitle));
    }


    private void showSharePopup(boolean isShow) {
        if (isShow) {
            mSharePopupWindow.dismiss();
        } else {
            final View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_share, null);
            mSharePopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, false);
            TextView tv_share_weixin = ButterKnife.findById(view, R.id.tv_share_weixin);
            TextView tv_share_pengyouquan = ButterKnife.findById(view, R.id.tv_share_pengyouquan);
            TextView tv_share_qq = ButterKnife.findById(view, R.id.tv_share_qq);
            TextView tv_share_cancel = ButterKnife.findById(view, R.id.tv_share_cancel);
            tv_share_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSharePopupWindow.dismiss();
                }
            });
            mSharePopupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.TOP, 0, 0);
        }
    }
}
