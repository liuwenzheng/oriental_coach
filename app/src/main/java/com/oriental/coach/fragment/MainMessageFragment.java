package com.oriental.coach.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oriental.coach.Constants;
import com.oriental.coach.R;
import com.oriental.coach.activity.MainActivity;
import com.oriental.coach.adapter.OrderMessageAdapter;
import com.oriental.coach.entity.OrderMessage;
import com.oriental.coach.net.callback.DialogCallback;
import com.oriental.coach.net.callback.JsonCallback;
import com.oriental.coach.net.resp.BaseResponse;
import com.oriental.coach.net.resp.MessageResult;
import com.oriental.coach.net.urls.Urls;
import com.oriental.coach.utils.LogModule;
import com.oriental.coach.utils.PreferencesUtil;
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


public class MainMessageFragment extends Fragment implements OrderMessageAdapter.OnChangeMsgStatusListener {

    public static final String TAG = MainMessageFragment.class.getSimpleName();
    @Bind(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @Bind(R.id.rv_list)
    RecyclerView rvList;
    @Bind(R.id.tv_header_right)
    TextView tvHeaderRight;
    @Bind(R.id.rl_delete)
    RelativeLayout rl_delete;
    @Bind(R.id.iv_header_back)
    ImageView ivHeaderBack;
    private boolean mCanSelect;
    private OrderMessageAdapter mAdapter;
    private List<OrderMessage> mMessages;
    private MainActivity mActivity;
    private String mTeacherId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_message, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView();
        initData();
    }

    private void initData() {
        mActivity = (MainActivity) getActivity();
        mMessages = new ArrayList<>();
        mAdapter = new OrderMessageAdapter(getActivity(), mMessages, this);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setAdapter(mAdapter);
        createData();
    }

    private void initView() {
        ivHeaderBack.setVisibility(View.GONE);
        tvHeaderTitle.setText("订单消息");
        tvHeaderRight.setText("删除");
    }


    public void refreshData() {
        createData();
    }

    private void createData() {
        mTeacherId = PreferencesUtil.getStringByName(getActivity(), "teacherId", "");
        Map<String, String> map = new HashMap<>();
        map.put("userId", mTeacherId);
        map.put("likeMessageType", "3");
        ((MainActivity) getActivity()).requestGet(Urls.GET_MESSAGE, map, new DialogCallback<BaseResponse<List<MessageResult>>>(getActivity()) {
            @Override
            public void onSuccess(BaseResponse<List<MessageResult>> listBaseResponse, Call call, Response response) {
                mMessages.clear();
                List<MessageResult> results = listBaseResponse.resObject;
                if (results != null && !results.isEmpty()) {
                    for (int i = 0; i < results.size(); i++) {
                        MessageResult result = results.get(i);
                        OrderMessage message = new OrderMessage();
                        message.content = result.messageContent;
                        message.messageId = result.messageId;
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(result.messageCreate);
                        message.time = Utils.calendar2strDate(calendar, Constants.PATTERN_YYYY_MM_DD_HH_MM_SS);
                        message.isSelected = false;
                        message.msgStatus = result.messageState;
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
                    ToastUtils.showToast(getActivity(), e.getMessage());
                }
                mAdapter.setDatas(mMessages);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_header_right, R.id.rl_delete})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
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
                    ((MainActivity) getActivity()).requestGet(Urls.DELETE_MESSAGE, map, new DialogCallback<BaseResponse<Object>>(getActivity()) {
                        @Override
                        public void onSuccess(BaseResponse baseResponse, Call call, Response response) {
                            ToastUtils.showToast(getActivity(), baseResponse.stateMess);
                            Iterator<OrderMessage> iterator = mMessages.iterator();
                            while (iterator.hasNext()) {
                                OrderMessage message = iterator.next();
                                if (message.isSelected) {
                                    iterator.remove();
                                }
                            }
                            mCanSelect = !mCanSelect;
                            mAdapter.setCanSelect(mCanSelect);
                            if (mCanSelect) {
                                tvHeaderRight.setText("取消");
                                rl_delete.setVisibility(View.VISIBLE);
                            } else {
                                tvHeaderRight.setText("删除");
                                rl_delete.setVisibility(View.GONE);
                            }
                            mAdapter.setDatas(mMessages);
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            if (e instanceof IllegalStateException) {
                                ToastUtils.showToast(getActivity(), e.getMessage());
                            }
                        }
                    });
                } else {
                    // 没有选择消息
                }
                break;
        }
    }

    @Override
    public void onStart() {
        LogModule.i(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        LogModule.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        LogModule.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        LogModule.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onClickChangeStatus(final OrderMessage message) {
        Map<String, String> map = new HashMap<>();
        map.put("messageId", message.messageId);
        map.put("messageState", "1");
        ((MainActivity) getActivity()).requestGet(Urls.UPDATE_MESSAGE, map, new DialogCallback<BaseResponse<Object>>(getActivity()) {
            @Override
            public void onSuccess(BaseResponse baseResponse, Call call, Response response) {
                ToastUtils.showToast(getActivity(), "已阅读");
                message.msgStatus = "1";
                mAdapter.notifyDataSetChanged();
                requestMessageCount();
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (e instanceof IllegalStateException) {
                    ToastUtils.showToast(getActivity(), e.getMessage());
                }
            }
        });
    }

    private void requestMessageCount() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", mTeacherId);
        mActivity.requestGet(Urls.GET_MESSAGE_COUNT, map, new JsonCallback<BaseResponse<Object>>(mActivity) {
            @Override
            public void onSuccess(BaseResponse<Object> baseResponse, Call call, Response response) {
                double count = (double) baseResponse.resObject;
                if (count > 0) {
                    mActivity.ivUnread.setVisibility(View.VISIBLE);
                } else {
                    mActivity.ivUnread.setVisibility(View.GONE);
                }
            }
        });
    }
}
