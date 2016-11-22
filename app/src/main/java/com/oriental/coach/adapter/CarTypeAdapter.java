package com.oriental.coach.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oriental.coach.R;
import com.oriental.coach.entity.CarType;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CarTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private CarTypeClickListener mListener;

    public void setDatas(List<CarType> mCarType) {
        this.mCarType = mCarType;
    }

    private List<CarType> mCarType;

    public CarTypeAdapter(Context context, List<CarType> carType, CarTypeClickListener mListener) {
        this.mContext = context;
        this.mCarType = carType;
        this.mListener = mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_car_type_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        initializeItemView((MyViewHolder) holder, mCarType.get(position), position);
    }

    private void initializeItemView(MyViewHolder holder, final CarType carType, int position) {
        holder.tvCarTypeName.setText(carType.name);
        holder.tvCarTypeNumber.setText(carType.number);
        holder.tvInsuranceRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.insuranceRecordClick(carType);
            }
        });
        holder.tvExamineRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.insuranceRecordClick(carType);
            }
        });
        holder.tvMaintenanceRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.insuranceRecordClick(carType);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCarType == null ? 0 : mCarType.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_car_pic)
        ImageView ivCarPic;
        @Bind(R.id.tv_car_type_name)
        TextView tvCarTypeName;
        @Bind(R.id.tv_car_type_number)
        TextView tvCarTypeNumber;
        @Bind(R.id.tv_insurance_record)
        TextView tvInsuranceRecord;
        @Bind(R.id.tv_examine_record)
        TextView tvExamineRecord;
        @Bind(R.id.tv_maintenance_record)
        TextView tvMaintenanceRecord;
        @Bind(R.id.ll_parent)
        RelativeLayout llParent;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface CarTypeClickListener {
        public void insuranceRecordClick(CarType carType);

        public void examineRecordClick();

        public void maintenanceRecordClick();
    }
}
