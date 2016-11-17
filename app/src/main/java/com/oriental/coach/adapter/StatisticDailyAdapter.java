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
import com.oriental.coach.entity.StatisticDaily;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StatisticDailyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;

    public void setDatas(List<StatisticDaily> mDailys) {
        this.mDailys = mDailys;
    }

    private List<StatisticDaily> mDailys;

    public StatisticDailyAdapter(Context context, List<StatisticDaily> dailys) {
        this.mContext = context;
        this.mDailys = dailys;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_statistic_daily_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        initializeItemView((MyViewHolder) holder, mDailys.get(position), position);
    }

    private void initializeItemView(MyViewHolder holder, StatisticDaily daily, int position) {
        if (position % 2 == 0) {
            holder.llParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white_ffffff));
        } else {
            holder.llParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey_f8f8f8));

        }
        holder.tvName.setText(daily.name);
        holder.tvPrice.setText(daily.price);
        holder.tvIncome.setText(daily.income);
        holder.tvTime.setText(daily.time);
    }

    @Override
    public int getItemCount() {
        return mDailys == null ? 0 : mDailys.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_income)
        TextView tvIncome;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.ll_parent)
        LinearLayout llParent;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
