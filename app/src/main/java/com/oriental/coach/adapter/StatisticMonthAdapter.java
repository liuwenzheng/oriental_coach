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
import com.oriental.coach.entity.StatisticMonth;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StatisticMonthAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;

    public void setDatas(List<StatisticMonth> mMonths) {
        this.mMonths = mMonths;
    }

    private List<StatisticMonth> mMonths;

    public StatisticMonthAdapter(Context context, List<StatisticMonth> months) {
        this.mContext = context;
        this.mMonths = months;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_statistic_month_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        initializeItemView((MyViewHolder) holder, mMonths.get(position), position);
    }

    private void initializeItemView(MyViewHolder holder, StatisticMonth month, int position) {
        if (position % 2 == 0) {
            holder.llParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white_ffffff));
        } else {
            holder.llParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey_f8f8f8));

        }
        holder.tvDate.setText(month.date);
        holder.tvCount.setText(month.count + "");
        holder.tvPrice.setText(month.price + "");
        holder.tvIncome.setText(month.income + "");
    }

    @Override
    public int getItemCount() {
        return mMonths == null ? 0 : mMonths.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_date)
        TextView tvDate;
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
