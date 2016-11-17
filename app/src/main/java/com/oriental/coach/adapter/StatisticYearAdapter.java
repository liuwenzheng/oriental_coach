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
import com.oriental.coach.entity.StatisticYear;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StatisticYearAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    public void setDatas(List<StatisticYear> mYears) {
        this.mYears = mYears;
    }

    private List<StatisticYear> mYears;

    public StatisticYearAdapter(Context context, List<StatisticYear> years) {
        this.mContext = context;
        this.mYears = years;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_statistic_year_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        initializeItemView((MyViewHolder) holder, mYears.get(position), position);
    }

    private void initializeItemView(MyViewHolder holder, StatisticYear daily, int position) {
        if (position % 2 == 0) {
            holder.llParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white_ffffff));
        } else {
            holder.llParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey_f8f8f8));

        }
        holder.tvMonth.setText(daily.month);
        holder.tvCount.setText(daily.count);
        holder.tvPrice.setText(daily.price);
        holder.tvIncome.setText(daily.income);
    }

    @Override
    public int getItemCount() {
        return mYears == null ? 0 : mYears.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_month)
        TextView tvMonth;
        @Bind(R.id.tv_count)
        TextView tvCount;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_income)
        TextView tvIncome;
        @Bind(R.id.ll_parent)
        LinearLayout llParent;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
