package com.service.billbook.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.service.billbook.R;

public class StockTimelineTabFragment extends Fragment {

    Context context;
    RecyclerView stock_timeline_recyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_stock_timeline, container, false);
        init(root);
        return root;
    }

    void init(View root) {
        context = getActivity();
        stock_timeline_recyclerview = root.findViewById(R.id.stock_timeline_recyclerview);
    }
}