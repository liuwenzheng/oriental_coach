package com.oriental.coach.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oriental.coach.Constants;
import com.oriental.coach.R;
import com.oriental.coach.adapter.OrderMessageAdapter;
import com.oriental.coach.base.BaseActivity;
import com.oriental.coach.entity.OrderMessage;
import com.oriental.coach.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author lwz
 * @Date 2016/11/15 0012
 * @Describe 登录
 */

public class OrderMessageActivity extends BaseActivity {


    @Bind(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @Bind(R.id.rv_list)
    RecyclerView rvList;
    @Bind(R.id.tv_header_right)
    TextView tvHeaderRight;
    @Bind(R.id.rl_delete)
    RelativeLayout rl_delete;
    private boolean mCanSelect;
    private OrderMessageAdapter mAdapter;
    private List<OrderMessage> mMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_message);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("订单消息");
        tvHeaderRight.setText("删除");
        mMessages = new ArrayList<>();
        mAdapter = new OrderMessageAdapter(this, mMessages);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(mAdapter);
        createData();
    }

    private void createData() {
        mMessages.clear();
        for (int i = 0; i < 20; i++) {
            OrderMessage message = new OrderMessage();
            message.content = "这是测试数据，这是测试数据，这是测试数据，这是测试数据，这是测试数据，这是测试数据，这是测试数据，这是测试数据";
            message.time = Utils.calendar2strDate(Calendar.getInstance(), Constants.PATTERN_YYYY_MM_DD_HH_MM_SS);
            message.isSelected = false;
            mMessages.add(message);
        }
        mAdapter.setDatas(mMessages);
        mAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.iv_header_back, R.id.tv_header_right, R.id.rl_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;
            case R.id.tv_header_right:
                mCanSelect = !mCanSelect;
                mAdapter.setCanSelect(mCanSelect);
                if (mCanSelect) {
                    tvHeaderRight.setText("取消");
                    rl_delete.setVisibility(View.VISIBLE);
                } else {
                    tvHeaderRight.setText("删除");
                    rl_delete.setVisibility(View.GONE);
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.rl_delete:
                Iterator<OrderMessage> iterator = mMessages.iterator();
                while (iterator.hasNext()) {
                    OrderMessage message = iterator.next();
                    if (message.isSelected) {
                        iterator.remove();
                    }
                }
                mAdapter.setDatas(mMessages);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }
}
