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
import com.oriental.coach.entity.OrderMessage;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private boolean mCanSelect;
    private OnChangeMsgStatusListener mListener;

    public void setDatas(List<OrderMessage> mMessages) {
        this.mMessages = mMessages;
    }

    private List<OrderMessage> mMessages;

    public OrderMessageAdapter(Context context, List<OrderMessage> mMessages, OnChangeMsgStatusListener listener) {
        this.mContext = context;
        this.mMessages = mMessages;
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_order_message_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        initializeItemView((MyViewHolder) holder, mMessages.get(position), position);
    }

    private void initializeItemView(MyViewHolder holder, final OrderMessage message, int position) {
        holder.tvContent.setText(message.content);
        holder.tvTime.setText(message.time);
        if ("0".equals(message.msgStatus)) {
            holder.ivMsgStatus.setImageResource(R.drawable.msg_close);
        } else {
            holder.ivMsgStatus.setImageResource(R.drawable.msg_open);
        }
        if (mCanSelect) {
            holder.ivSelected.setVisibility(View.VISIBLE);
            holder.ivSelected.setImageResource(message.isSelected ? R.drawable.checked : R.drawable.unchecked);
            holder.rlParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCanSelect) {
                        message.isSelected = !message.isSelected;
                        notifyDataSetChanged();
                    }
                }
            });
        } else {
            message.isSelected = false;
            holder.ivSelected.setVisibility(View.GONE);
            if ("0".equals(message.msgStatus)) {
                holder.rlParent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onClickChangeStatus(message);
                    }
                });
            } else {
                holder.rlParent.setOnClickListener(null);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mMessages == null ? 0 : mMessages.size();
    }

    public void setCanSelect(boolean mCanSelect) {
        this.mCanSelect = mCanSelect;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.iv_selected)
        ImageView ivSelected;
        @Bind(R.id.iv_msg_status)
        ImageView ivMsgStatus;
        @Bind(R.id.rl_prarent)
        RelativeLayout rlParent;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnChangeMsgStatusListener {
        void onClickChangeStatus(OrderMessage message);
    }
}
