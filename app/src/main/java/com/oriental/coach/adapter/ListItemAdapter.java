package com.oriental.coach.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oriental.coach.R;
import com.oriental.coach.entity.ListEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ListEntity> mEntities;

    public ListItemAdapter(Context context, List<ListEntity> entities) {
        this.mContext = context;
        this.mEntities = entities;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        initializeItemView((MyViewHolder) holder, mEntities.get(position));
    }

    private void initializeItemView(MyViewHolder holder, ListEntity listEntity) {
        holder.tvKey.setText(listEntity.key);
        holder.tvValue.setText(listEntity.value);
    }

    @Override
    public int getItemCount() {
        return mEntities == null ? 0 : mEntities.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_key)
        TextView tvKey;
        @Bind(R.id.tv_value)
        TextView tvValue;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
