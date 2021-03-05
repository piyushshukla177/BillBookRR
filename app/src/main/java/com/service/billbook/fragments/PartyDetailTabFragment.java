package com.service.billbook.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.service.billbook.R;

public class PartyDetailTabFragment extends Fragment {

    Context context;
    TextView party_contact_number_tv, party_billing_address_tv, party_shipping_address_tv, party_place_of_supply_tv, party_gst_registration_type_tv,
            credit_period_days, party_gstin_tv, party_credit_limit_tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_party_detail_tab, container, false);
        init(root);
        return root;
    }

    private Bundle bundle;
    String party_id, party_name, party_mobile, credit_limit, party_amount, party_type, credit_period, billing_address, shipping_address, gstin_number, place_of_supply;

    void init(View root) {
        context = getActivity();

        //within OncreateView
        bundle = this.getArguments();
        party_id = bundle.getString("party_id");
        party_name = bundle.getString("party_name");
        party_mobile = bundle.getString("party_mobile");
        credit_limit = bundle.getString("credit_limit");
        party_amount = bundle.getString("party_amount");
        party_type = bundle.getString("party_type");
        credit_period = bundle.getString("credit_period");
        gstin_number = bundle.getString("gstin_number");
        place_of_supply = bundle.getString("place_of_supply");
        billing_address = bundle.getString("billing_address");
        shipping_address = bundle.getString("shipping_address");
        place_of_supply = bundle.getString("place_of_supply");

        party_contact_number_tv = root.findViewById(R.id.party_contact_number_tv);
        party_billing_address_tv = root.findViewById(R.id.party_billing_address_tv);
        party_shipping_address_tv = root.findViewById(R.id.party_shipping_address_tv);
        party_place_of_supply_tv = root.findViewById(R.id.party_place_of_supply_tv);
        party_gst_registration_type_tv = root.findViewById(R.id.party_gst_registration_type_tv);
        credit_period_days = root.findViewById(R.id.credit_period_days);
        party_gstin_tv = root.findViewById(R.id.party_gstin_tv);
        party_credit_limit_tv = root.findViewById(R.id.party_credit_limit_tv);

        party_contact_number_tv.setText(party_mobile);
        if (billing_address != null) {
            party_billing_address_tv.setText(billing_address);
        } else {
            party_billing_address_tv.setText("--");
        }
        if (shipping_address != null) {
            party_shipping_address_tv.setText(shipping_address);
        } else {
            party_shipping_address_tv.setText("--");
        }
        if (place_of_supply != null) {
            party_place_of_supply_tv.setText(place_of_supply);
        } else {
            party_place_of_supply_tv.setText("--");
        }
        if (gstin_number != null && !gstin_number.isEmpty()) {
            party_gstin_tv.setText(gstin_number);
            party_gst_registration_type_tv.setText("Registered");
        } else {
            party_gstin_tv.setText("--");
            party_gst_registration_type_tv.setText("--");
        }

        if (credit_period != null && !credit_period.isEmpty()&& !credit_limit.equals("null")) {
            credit_period_days.setText(credit_period);
        } else {
            credit_period_days.setText("--");
        }
        if (credit_limit != null && !credit_limit.isEmpty() && !credit_limit.equals("null")) {
            party_credit_limit_tv.setText(credit_limit);
        } else {
            party_credit_limit_tv.setText("--");
        }
    }
}