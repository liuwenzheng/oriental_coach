package com.oriental.coach.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oriental.coach.R;
import com.oriental.coach.adapter.ListItemAdapter;
import com.oriental.coach.entity.ListEntity;
import com.oriental.coach.utils.DividerItemDecoration;
import com.oriental.coach.utils.LogModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainMyListFragment extends Fragment {

    public static final String TAG = MainMyListFragment.class.getSimpleName();
    @Bind(R.id.rv_list)
    RecyclerView rv_list;
    private List<ListEntity> mEntities;
    private ListItemAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        LogModule.i(TAG, "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogModule.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogModule.i(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_main_my_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogModule.i(TAG, "onViewCreated");
        ButterKnife.bind(this, view);
        mEntities = new ArrayList<>();
        mEntities.add(new ListEntity("key1", "value1"));
        mEntities.add(new ListEntity("key2", "value2"));
        mEntities.add(new ListEntity("key3", "value3"));
        mEntities.add(new ListEntity("key4", "value4"));
        mEntities.add(new ListEntity("key5", "value5"));
        mEntities.add(new ListEntity("key6", "value6"));
        mEntities.add(new ListEntity("key7", "value7"));
        mEntities.add(new ListEntity("key8", "value8"));
        mEntities.add(new ListEntity("key9", "value9"));
        mEntities.add(new ListEntity("key10", "value10"));
        mEntities.add(new ListEntity("key11", "value11"));
        mEntities.add(new ListEntity("key12", "value12"));
        mEntities.add(new ListEntity("key13", "value13"));
        mAdapter = new ListItemAdapter(getActivity(), mEntities);
        rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_list.setAdapter(mAdapter);
        rv_list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
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
    public void onDestroyView() {
        super.onDestroyView();
        LogModule.i(TAG, "onDestroyView");
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        LogModule.i(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        LogModule.i(TAG, "onDetach");
        super.onDetach();
    }

}
