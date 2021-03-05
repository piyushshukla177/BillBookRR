package com.service.billbook.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.service.billbook.R;
import com.service.billbook.activities.CreateBillActivity;

public class PartyTransactionsTabFragment extends Fragment {

    Context context;
    CardView create_invoice_cardview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_transactions_tab, container, false);
        init(root);
        return root;
    }

    void init(View root) {
        context = getActivity();
        create_invoice_cardview = root.findViewById(R.id.create_invoice_cardview);
        create_invoice_cardview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, CreateBillActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
