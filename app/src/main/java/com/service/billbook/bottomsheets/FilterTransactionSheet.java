package com.service.billbook.bottomsheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.service.billbook.R;

public class FilterTransactionSheet extends BottomSheetDialogFragment {
    private FilterTransactionSheet.TransactionFilterListener mListener;
    RadioGroup radio_group1, radio_group2, radio_group3, radio_group4;
    RadioButton high_to_low_radio,low_to_high_radio,sale_radio, purchase_radio, recieved_payment, payment_out_radio, quotation_radio, sale_return_radio, purchase_return_radio,
            purchase_oredr_radio, delivery_challan_radio, expense_rardio;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.transaction_sheet_layout, container, false);

        radio_group1 = v.findViewById(R.id.radio_group1);
        radio_group2 = v.findViewById(R.id.radio_group2);
        radio_group3 = v.findViewById(R.id.radio_group3);
        radio_group4 = v.findViewById(R.id.radio_group4);

        high_to_low_radio = v.findViewById(R.id.high_to_low_radio);
        low_to_high_radio = v.findViewById(R.id.low_to_high_radio);
        sale_radio = v.findViewById(R.id.sale_radio);
        purchase_radio = v.findViewById(R.id.purchase_radio);
        recieved_payment = v.findViewById(R.id.recieved_payment);
        payment_out_radio = v.findViewById(R.id.payment_out_radio);
        quotation_radio = v.findViewById(R.id.quotation_radio);
        sale_return_radio = v.findViewById(R.id.sale_return_radio);
        purchase_return_radio = v.findViewById(R.id.purchase_return_radio);
        purchase_oredr_radio = v.findViewById(R.id.purchase_oredr_radio);
        delivery_challan_radio = v.findViewById(R.id.delivery_challan_radio);
        expense_rardio = v.findViewById(R.id.expense_rardio);

        high_to_low_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        low_to_high_radio.setChecked(false);
                        high_to_low_radio.setChecked(true);
                    }
                }
        );
        low_to_high_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        high_to_low_radio.setChecked(false);
                        low_to_high_radio.setChecked(true);
                    }
                }
        );
        sale_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        purchase_radio.setChecked(false);
                        recieved_payment.setChecked(false);
                        payment_out_radio.setChecked(false);
                        quotation_radio.setChecked(false);
                        sale_return_radio.setChecked(false);
                        purchase_return_radio.setChecked(false);
                        purchase_oredr_radio.setChecked(false);
                        delivery_challan_radio.setChecked(false);
                        expense_rardio.setChecked(false);
                    }
                }
        );
        purchase_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sale_radio.setChecked(false);
                        recieved_payment.setChecked(false);
                        payment_out_radio.setChecked(false);
                        quotation_radio.setChecked(false);
                        sale_return_radio.setChecked(false);
                        purchase_return_radio.setChecked(false);
                        purchase_oredr_radio.setChecked(false);
                        delivery_challan_radio.setChecked(false);
                        expense_rardio.setChecked(false);
                    }
                }
        );
        recieved_payment.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sale_radio.setChecked(false);
                        purchase_radio.setChecked(false);
                        payment_out_radio.setChecked(false);
                        quotation_radio.setChecked(false);
                        sale_return_radio.setChecked(false);
                        purchase_return_radio.setChecked(false);
                        purchase_oredr_radio.setChecked(false);
                        delivery_challan_radio.setChecked(false);
                        expense_rardio.setChecked(false);
                    }
                }
        );
        payment_out_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sale_radio.setChecked(false);
                        purchase_radio.setChecked(false);
                        recieved_payment.setChecked(false);
                        quotation_radio.setChecked(false);
                        sale_return_radio.setChecked(false);
                        purchase_return_radio.setChecked(false);
                        purchase_oredr_radio.setChecked(false);
                        delivery_challan_radio.setChecked(false);
                        expense_rardio.setChecked(false);
                    }
                }
        );
        quotation_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sale_radio.setChecked(false);
                        purchase_radio.setChecked(false);
                        recieved_payment.setChecked(false);
                        payment_out_radio.setChecked(false);
                        sale_return_radio.setChecked(false);
                        purchase_return_radio.setChecked(false);
                        purchase_oredr_radio.setChecked(false);
                        delivery_challan_radio.setChecked(false);
                        expense_rardio.setChecked(false);
                    }
                }
        );
        sale_return_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sale_radio.setChecked(false);
                        purchase_radio.setChecked(false);
                        recieved_payment.setChecked(false);
                        payment_out_radio.setChecked(false);
                        quotation_radio.setChecked(false);
                        purchase_return_radio.setChecked(false);
                        purchase_oredr_radio.setChecked(false);
                        delivery_challan_radio.setChecked(false);
                        expense_rardio.setChecked(false);
                    }
                }
        );
        purchase_return_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sale_radio.setChecked(false);
                        purchase_radio.setChecked(false);
                        recieved_payment.setChecked(false);
                        payment_out_radio.setChecked(false);
                        quotation_radio.setChecked(false);
                        sale_return_radio.setChecked(false);
                        purchase_oredr_radio.setChecked(false);
                        delivery_challan_radio.setChecked(false);
                        expense_rardio.setChecked(false);
                    }
                }
        );
        purchase_oredr_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sale_radio.setChecked(false);
                        purchase_radio.setChecked(false);
                        recieved_payment.setChecked(false);
                        payment_out_radio.setChecked(false);
                        quotation_radio.setChecked(false);
                        sale_return_radio.setChecked(false);
                        purchase_return_radio.setChecked(false);
                        delivery_challan_radio.setChecked(false);
                        expense_rardio.setChecked(false);
                    }
                }
        );
        delivery_challan_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sale_radio.setChecked(false);
                        purchase_radio.setChecked(false);
                        recieved_payment.setChecked(false);
                        payment_out_radio.setChecked(false);
                        quotation_radio.setChecked(false);
                        sale_return_radio.setChecked(false);
                        purchase_return_radio.setChecked(false);
                        purchase_oredr_radio.setChecked(false);
                        expense_rardio.setChecked(false);
                    }
                }
        );
        expense_rardio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sale_radio.setChecked(false);
                        purchase_radio.setChecked(false);
                        recieved_payment.setChecked(false);
                        payment_out_radio.setChecked(false);
                        quotation_radio.setChecked(false);
                        sale_return_radio.setChecked(false);
                        purchase_return_radio.setChecked(false);
                        purchase_oredr_radio.setChecked(false);
                        delivery_challan_radio.setChecked(false);
                    }
                }
        );
        return v;
    }

    public interface TransactionFilterListener {
        void onTransactionFilter(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (FilterTransactionSheet.TransactionFilterListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}