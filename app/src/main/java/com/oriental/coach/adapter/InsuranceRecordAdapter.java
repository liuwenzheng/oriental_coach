package com.oriental.coach.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oriental.coach.R;
import com.oriental.coach.entity.InsuranceRecord;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InsuranceRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<InsuranceRecord> mEntities;

    public InsuranceRecordAdapter(Context context, List<InsuranceRecord> entities) {
        this.mContext = context;
        this.mEntities = entities;
    }

    public void setDatas(List<InsuranceRecord> entities) {
        this.mEntities = entities;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_insurance_record, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        initializeItemView((MyViewHolder) holder, mEntities.get(position));
    }

    private void initializeItemView(MyViewHolder holder, InsuranceRecord record) {
        holder.tvCompany.setText(record.company);
        holder.tvPrice.setText(record.price);
        holder.tvStartDate.setText(record.startDate);
        holder.tvEndDate.setText(record.endDate);
    }

    @Override
    public int getItemCount() {
        return mEntities == null ? 0 : mEntities.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_company)
        TextView tvCompany;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_start_date)
        TextView tvStartDate;
        @Bind(R.id.tv_end_date)
        TextView tvEndDate;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
