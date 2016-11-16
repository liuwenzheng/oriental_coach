package com.oriental.coach.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oriental.coach.R;
import com.oriental.coach.entity.DailyPlan;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DailyPlanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;

    public void setDatas(List<DailyPlan> mPlans) {
        this.mPlans = mPlans;
    }

    private List<DailyPlan> mPlans;

    public DailyPlanAdapter(Context context, List<DailyPlan> plans) {
        this.mContext = context;
        this.mPlans = plans;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_daily_plan_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        initializeItemView((MyViewHolder) holder, mPlans.get(position), position);
    }

    private void initializeItemView(MyViewHolder holder, DailyPlan plan, int position) {
        if (position % 2 == 0) {
            holder.llParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white_ffffff));
        } else {
            holder.llParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey_f8f8f8));

        }
        holder.tvDuration.setText(plan.duration);
        holder.tvLicenseLevel.setText(plan.licenseLevel);
        holder.tvSubject.setText(plan.subject);
        holder.tvPrice.setText(plan.price);
        holder.tvStatus.setText(plan.status);
    }

    @Override
    public int getItemCount() {
        return mPlans == null ? 0 : mPlans.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_duration)
        TextView tvDuration;
        @Bind(R.id.tv_license_level)
        TextView tvLicenseLevel;
        @Bind(R.id.tv_subject)
        TextView tvSubject;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_status)
        TextView tvStatus;
        @Bind(R.id.ll_parent)
        LinearLayout llParent;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
