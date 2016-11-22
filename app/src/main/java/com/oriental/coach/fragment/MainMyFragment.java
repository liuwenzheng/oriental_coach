package com.oriental.coach.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.oriental.coach.R;
import com.oriental.coach.activity.CarTypeActivity;
import com.oriental.coach.activity.SettingActivity;

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
    @Bind(R.id.tv_my_signature)
    TextView tv_my_signature;
    @Bind(R.id.tv_my_training_field)
    TextView tv_my_training_field;
    @Bind(R.id.tv_my_car_type)
    TextView tv_my_car_type;
    @Bind(R.id.tv_my_training_subject)
    TextView tv_my_training_subject;

    private PopupWindow mSharePopupWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_my, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
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
                startActivity(intent);
                break;
            case R.id.ll_my_order_message:
                break;
            case R.id.ll_my_share:
                if (!getActivity().isFinishing() && mSharePopupWindow != null && mSharePopupWindow.isShowing()) {
                    showSharePopup(true);
                } else {
                    showSharePopup(false);
                }
                break;
            case R.id.ll_my_setting:
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
        }
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
