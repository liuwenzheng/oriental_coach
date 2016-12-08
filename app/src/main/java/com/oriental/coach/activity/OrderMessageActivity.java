package com.oriental.coach.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oriental.coach.Constants;
import com.oriental.coach.R;
import com.oriental.coach.adapter.OrderMessageAdapter;
import com.oriental.coach.base.BaseActivity;
import com.oriental.coach.entity.OrderMessage;
import com.oriental.coach.entity.Teacher;
import com.oriental.coach.net.callback.DialogCallback;
import com.oriental.coach.net.resp.BaseResponse;
import com.oriental.coach.net.resp.MessageResult;
import com.oriental.coach.net.urls.Urls;
import com.oriental.coach.utils.ToastUtils;
import com.oriental.coach.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @Author lwz
 * @Date 2016/11/15 0012
 * @Describe 消息列表
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
    private Teacher mTeacher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_message);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("订单消息");
        tvHeaderRight.setText("删除");
        if (getIntent() != null && getIntent().getExtras() != null) {
            mTeacher = getIntent().getParcelableExtra("teacher");
        } else {
            finish();
            return;
        }
        mMessages = new ArrayList<>();
        mAdapter = new OrderMessageAdapter(this, mMessages);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(mAdapter);
        createData();
    }

    private void createData() {
        mMessages.clear();
        Map<String, String> map = new HashMap<>();
        map.put("userId", mTeacher.teacharId);
        map.put("messageTypes", "3_1");
        requestGet(Urls.GET_MESSAGE, map, new DialogCallback<BaseResponse<List<MessageResult>>>(this) {
            @Override
            public void onSuccess(BaseResponse<List<MessageResult>> listBaseResponse, Call call, Response response) {
                List<MessageResult> results = listBaseResponse.resObject;
                if (results != null && !results.isEmpty()) {
                    for (int i = 0; i < results.size(); i++) {
                        MessageResult result = results.get(0);
                        OrderMessage message = new OrderMessage();
                        message.content = result.messageContent;
                        message.messageId = result.messageId;
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(result.messageCreate);
                        message.time = Utils.calendar2strDate(calendar, Constants.PATTERN_YYYY_MM_DD_HH_MM_SS);
                        message.isSelected = false;
                        mMessages.add(message);
                    }
                    mAdapter.setDatas(mMessages);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (e instanceof IllegalStateException) {
                    ToastUtils.showToast(OrderMessageActivity.this, e.getMessage());
                }
                mAdapter.setDatas(mMessages);
                mAdapter.notifyDataSetChanged();
            }
        });


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
                StringBuilder sb = new StringBuilder();
                Iterator<OrderMessage> iterator = mMessages.iterator();
                while (iterator.hasNext()) {
                    OrderMessage message = iterator.next();
                    if (message.isSelected) {
                        sb.append(message.messageId);
                        sb.append(",");
                    }
                }
                if (!TextUtils.isEmpty(sb.toString())) {
                    String requestParams = sb.toString();
                    requestParams = requestParams.substring(0, requestParams.lastIndexOf(","));
                    Map<String, String> map = new HashMap<>();
                    map.put("messageIds", requestParams);
                    requestGet(Urls.DELETE_MESSAGE, map, new DialogCallback<BaseResponse>(this) {
                        @Override
                        public void onSuccess(BaseResponse baseResponse, Call call, Response response) {
                            ToastUtils.showToast(OrderMessageActivity.this, baseResponse.stateMess);
                            Iterator<OrderMessage> iterator = mMessages.iterator();
                            while (iterator.hasNext()) {
                                OrderMessage message = iterator.next();
                                if (message.isSelected) {
                                    iterator.remove();
                                }
                            }
                            mAdapter.setDatas(mMessages);
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            if (e instanceof IllegalStateException) {
                                ToastUtils.showToast(OrderMessageActivity.this, e.getMessage());
                            }
                        }
                    });
                } else {
                    // 没有选择消息
                }
                break;
        }
    }
}
