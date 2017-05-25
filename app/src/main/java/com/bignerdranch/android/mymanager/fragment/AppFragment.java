package com.bignerdranch.android.mymanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.mymanager.Adapter.AppAdapter;
import com.bignerdranch.android.mymanager.AppInfo;
import com.bignerdranch.android.mymanager.R;
import com.bignerdranch.android.mymanager.activites.DetailActivity;
import com.bignerdranch.android.mymanager.activites.ManagerActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liubin on 2017/5/18.
 */

public class AppFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private List<AppInfo> appLists;
    private static final String ARG_APPINFO_LIST = "appinfo_list";

    private AppAdapter mAppAdapter;


    public static AppFragment newInstance(List<AppInfo> appLists) {


        Bundle args = new Bundle();
        args.putSerializable(ARG_APPINFO_LIST, (Serializable) appLists);

        AppFragment fragment = new AppFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        appLists = (List<AppInfo>) bundle.getSerializable(ARG_APPINFO_LIST);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.app_list, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.app_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);



        mAppAdapter = new AppAdapter(appLists ,getActivity());

        mRecyclerView.setAdapter(mAppAdapter);

        return v;
    }

}
