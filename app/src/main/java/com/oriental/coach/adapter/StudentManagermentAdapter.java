package com.oriental.coach.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.oriental.coach.R;
import com.oriental.coach.entity.Student;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class StudentManagermentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;

    public void setDatas(List<Student> mStudents) {
        this.mStudents = mStudents;
    }

    private List<Student> mStudents;

    public StudentManagermentAdapter(Context context, List<Student> students) {
        this.mContext = context;
        this.mStudents = students;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_student_managerment_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        initializeItemView((MyViewHolder) holder, mStudents.get(position), position);
    }

    private void initializeItemView(MyViewHolder holder, Student student, int position) {
        holder.tvName.setText(student.name);
        if (student.gender == 1) {
            holder.tvGender.setText("男");
            holder.ivGender.setImageResource(R.drawable.gender_male);
        } else {
            holder.tvGender.setText("女");
            holder.ivGender.setImageResource(R.drawable.gender_female);
        }
        holder.tvPhonenumber.setText(mContext.getString(R.string.phonenumber, student.phonenumber));
        holder.tvIdentityCard.setText(mContext.getString(R.string.identity_card, student.identityCard));
        holder.tv_course_type.setText(student.subject);
        if (!TextUtils.isEmpty(student.headerUrl)) {
            ImageLoader.getInstance().displayImage(student.headerUrl, holder.ciHeader);
        }
    }

    @Override
    public int getItemCount() {
        return mStudents == null ? 0 : mStudents.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ci_header)
        CircleImageView ciHeader;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_gender)
        TextView tvGender;
        @Bind(R.id.iv_gender)
        ImageView ivGender;
        @Bind(R.id.tv_phonenumber)
        TextView tvPhonenumber;
        @Bind(R.id.tv_identity_card)
        TextView tvIdentityCard;
        @Bind(R.id.tv_course_type)
        TextView tv_course_type;
        @Bind(R.id.ll_parent)
        RelativeLayout llParent;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
