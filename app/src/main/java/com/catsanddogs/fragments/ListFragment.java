package com.catsanddogs.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.catsanddogs.R;
import com.catsanddogs.application.Application;
import com.catsanddogs.list.OnListItemClick;
import com.catsanddogs.list.ListAdapter;
import com.catsanddogs.network.ItemData;
import com.catsanddogs.network.Query;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment {

    public static String CATEGORY_TAG = "category_tag";
    private String category;
    private ListAdapter tabAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(CATEGORY_TAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, null);
        initList(rootView);
        return rootView;
    }

    private void initList(View rootView) {
        RecyclerView rvTabList = (RecyclerView)rootView.findViewById(R.id.rvTabList);
        rvTabList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rvTabList.setLayoutManager(layoutManager);
        tabAdapter = new ListAdapter(new OnListItemClick(){
            @Override
            public void onClick(int position, String url, String title) {
                Activity activity = getActivity();
                if (activity != null && !activity.isFinishing() &&
                        activity instanceof OnListItemClick) {
                    ((OnListItemClick) activity).onClick(position, url, title);
                }
            }
        });
        rvTabList.setAdapter(tabAdapter);

        if (getData() != null) {
            updateList();
        } else {
            Application.getApi().getData(category).enqueue(
                    new Callback<Query>() {
                        @Override
                        public void onResponse(Call<Query> call, Response<Query> response) {
                            if (response == null || response.body() == null) {
                                return;
                            }
                            Activity act = getActivity();
                            if (act != null && !act.isFinishing() ) {
                                ((Application)act.getApplication()).dataStoragePut(category, response.body().getData());
                                act.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateList();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<Query> call, Throwable t) {
                        }
                    }
            );
        }
    }

    private void updateList() {
        tabAdapter.updateList(getData());
    }

    private List<ItemData> getData() {
        Activity act = getActivity();
        if (act == null || act.isFinishing()) {
            return null;
        }
        return ((Application)act.getApplication()).dataStorageGet(category);
    }
}
