package com.service.billbook.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.service.billbook.R;

public class ItemDetailsTabFragment extends Fragment {

    TextView item_code_tv, unit_tv, low_stock_tv, tax_rate_tv, hsn_code_tv, item_type_tv,remark_tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_item_details_tab, container, false);
        init(root);
        return root;
    }

    private Bundle bundle;
    String item_code, unit, low_stock, tax_rate, hsn_code, item_type, description;

    void init(View root) {
        item_code_tv = root.findViewById(R.id.item_code_tv);
        unit_tv = root.findViewById(R.id.unit_tv);
        low_stock_tv = root.findViewById(R.id.low_stock_tv);
        tax_rate_tv = root.findViewById(R.id.tax_rate_tv);
        hsn_code_tv = root.findViewById(R.id.hsn_code_tv);
        item_type_tv = root.findViewById(R.id.item_type_tv);
        remark_tv = root.findViewById(R.id.remark_tv);

        //within OncreateView
        bundle = this.getArguments();
        item_code = bundle.getString("item_code");
        unit = bundle.getString("unit");
        low_stock = bundle.getString("low_stock");
        tax_rate = bundle.getString("gst");
        hsn_code = bundle.getString("hsn_code");
        item_type = bundle.getString("item_type");
        description = bundle.getString("description");

        if (item_code != null && !item_code.isEmpty()) {
            item_code_tv.setText(item_code);
        } else {
            item_code_tv.setText("--");
        }
        if (unit != null && !unit.isEmpty()) {
            unit_tv.setText(unit);
        } else {
            unit_tv.setText("--");
        }
        if (low_stock != null && !low_stock.isEmpty()) {
            low_stock_tv.setText(low_stock);
        } else {
            low_stock_tv.setText("--");
        }

        if (tax_rate != null && !tax_rate.isEmpty()) {
            tax_rate_tv.setText(tax_rate);
        } else {
            tax_rate_tv.setText("--");
        }

        if (hsn_code != null && !hsn_code.isEmpty()) {
            hsn_code_tv.setText(hsn_code);
        } else {
            hsn_code_tv.setText("--");
        }
        if (item_type != null && !item_type.isEmpty()) {
            if(item_type.equals("1"))
            {
                item_type_tv.setText("Product");
            }else
            {
                item_type_tv.setText("Service");
            }

        } else {
            item_type_tv.setText("--");
        }
        if (description != null && !description.isEmpty()) {
            remark_tv.setText(description);
        } else {
            remark_tv.setText("--");
        }
    }
}