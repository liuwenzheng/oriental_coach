package com.oriental.coach.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oriental.coach.R;
import com.oriental.coach.entity.OrderEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class OrderManagermentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ORDER_STATUS_FINISHED = 0;
    public static final int ORDER_STATUS_CANCEL = 1;
    public static final int ORDER_STATUS_UNFINISHED = 2;

    private Context mContext;

    public void setEntities(List<OrderEntity> mEntities) {
        this.mEntities = mEntities;
    }

    private List<OrderEntity> mEntities;

    public OrderManagermentAdapter(Context context, List<OrderEntity> entities) {
        this.mContext = context;
        this.mEntities = entities;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_order_managerment_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        initializeItemView((MyViewHolder) holder, mEntities.get(position), position);
    }

    private void initializeItemView(MyViewHolder holder, OrderEntity entity, int position) {
        if (ORDER_STATUS_FINISHED == entity.orderStatus) {
            holder.ivOrderStatus.setVisibility(View.VISIBLE);
            holder.ivOrderStatus.setImageResource(R.drawable.order_finished_icon);
            holder.rlSurplusTime.setVisibility(View.GONE);
        }
        if (ORDER_STATUS_CANCEL == entity.orderStatus) {
            holder.ivOrderStatus.setVisibility(View.VISIBLE);
            holder.ivOrderStatus.setImageResource(R.drawable.order_cancel_icon);
            holder.rlSurplusTime.setVisibility(View.GONE);
        }
        if (ORDER_STATUS_UNFINISHED == entity.orderStatus) {
            holder.ivOrderStatus.setVisibility(View.GONE);
            holder.rlSurplusTime.setVisibility(View.VISIBLE);
            holder.tvSurplusTime.setText(entity.surplusTime);
        }
        holder.tvName.setText(entity.name);
        holder.tvSubject.setText(entity.subject);
        holder.tvPhonenumber.setText(mContext.getString(R.string.order_item_phonenumber, entity.phonenumber));
        holder.tvPrice.setText(mContext.getString(R.string.order_item_price, entity.price));
        holder.tvPayType.setText(mContext.getString(R.string.order_item_pay_type, entity.payType));
        holder.tvCreateTime.setText(entity.createTime);
        holder.tvOrderNumber.setText(entity.orderNumber);
        holder.tvCourseTime.setText(entity.courseTime);
    }

    @Override
    public int getItemCount() {
        return mEntities == null ? 0 : mEntities.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_surplus_time)
        TextView tvSurplusTime;
        @Bind(R.id.rl_surplus_time)
        RelativeLayout rlSurplusTime;
        @Bind(R.id.ci_header)
        CircleImageView ciHeader;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_subject)
        TextView tvSubject;
        @Bind(R.id.tv_phonenumber)
        TextView tvPhonenumber;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_pay_type)
        TextView tvPayType;
        @Bind(R.id.iv_order_status)
        ImageView ivOrderStatus;
        @Bind(R.id.tv_create_time)
        TextView tvCreateTime;
        @Bind(R.id.tv_order_number)
        TextView tvOrderNumber;
        @Bind(R.id.tv_course_time)
        TextView tvCourseTime;
        @Bind(R.id.ll_parent)
        LinearLayout llParent;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}