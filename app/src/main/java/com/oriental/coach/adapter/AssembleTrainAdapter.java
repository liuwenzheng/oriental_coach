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
import com.oriental.coach.entity.AssembleTrain;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AssembleTrainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;

    public void setDatas(List<AssembleTrain> assembleTrains) {
        this.assembleTrains = assembleTrains;
    }

    private List<AssembleTrain> assembleTrains;

    public AssembleTrainAdapter(Context context, List<AssembleTrain> plans) {
        this.mContext = context;
        this.assembleTrains = plans;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_assemble_train_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        initializeItemView((MyViewHolder) holder, assembleTrains.get(position), position);
    }

    private void initializeItemView(MyViewHolder holder, AssembleTrain assembleTrain, int position) {
        if (position % 2 == 0) {
            holder.llParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white_ffffff));
        } else {
            holder.llParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey_f8f8f8));

        }
        holder.tvDate.setText(String.format("%s-%s", assembleTrain.timeBeginTime, assembleTrain.timeEndTime));
        holder.tvNum.setText(String.format("%d/%d", assembleTrain.minStudentNum, assembleTrain.maxStudentNum));
        holder.tvActualNum.setText(assembleTrain.studentNum + "");
        holder.tvStatus.setText("0".equals(assembleTrain.ifStart) ? "未开课" : "已开课");
    }

    @Override
    public int getItemCount() {
        return assembleTrains == null ? 0 : assembleTrains.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.tv_num)
        TextView tvNum;
        @Bind(R.id.tv_actual_num)
        TextView tvActualNum;
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
