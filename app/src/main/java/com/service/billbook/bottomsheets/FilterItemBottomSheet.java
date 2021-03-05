package com.service.billbook.bottomsheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.service.billbook.R;

public class FilterItemBottomSheet extends BottomSheetDialogFragment {
    private FilterItemSheet mListener;

    RelativeLayout a_to_z_relative, z_to_a_relative, low_to_high_relative, higth_to_low_relative;
    RadioButton a_to_z_radio, z_to_a_radio, low_to_high_radio, high_to_low_radio, low_stock_radio, not_in_stock_radio, in_stock_radio;
    RadioGroup radio_group;
    Button apply_btn;
    TextView clear_textview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.filter_items_layout, container, false);

        a_to_z_relative = v.findViewById(R.id.a_to_z_relative);
        z_to_a_relative = v.findViewById(R.id.z_to_a_relative);
        low_to_high_relative = v.findViewById(R.id.low_to_high_relative);
        higth_to_low_relative = v.findViewById(R.id.higth_to_low_relative);
        a_to_z_radio = v.findViewById(R.id.a_to_z_radio);
        z_to_a_radio = v.findViewById(R.id.z_to_a_radio);
        low_to_high_radio = v.findViewById(R.id.low_to_high_radio);
        high_to_low_radio = v.findViewById(R.id.high_to_low_radio);
        low_stock_radio = v.findViewById(R.id.low_stock_radio);
        not_in_stock_radio = v.findViewById(R.id.not_in_stock_radio);
        in_stock_radio = v.findViewById(R.id.in_stock_radio);
        radio_group = v.findViewById(R.id.radio_group);
        apply_btn = v.findViewById(R.id.apply_btn);
        clear_textview = v.findViewById(R.id.clear_textview);

        a_to_z_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mListener.onButtonClicked("Button1");
                a_to_z_radio.setChecked(true);
                z_to_a_radio.setChecked(false);
                low_to_high_radio.setChecked(false);
                high_to_low_radio.setChecked(false);
//                dismiss();
            }
        });
        z_to_a_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mListener.onButtonClicked("Button2");
                a_to_z_radio.setChecked(false);
                z_to_a_radio.setChecked(true);
                low_to_high_radio.setChecked(false);
                high_to_low_radio.setChecked(false);
//                dismiss();
            }
        });
        low_to_high_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        a_to_z_radio.setChecked(false);
                        z_to_a_radio.setChecked(false);
                        low_to_high_radio.setChecked(true);
                        high_to_low_radio.setChecked(false);

                    }
                }
        );
        higth_to_low_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        a_to_z_radio.setChecked(false);
                        z_to_a_radio.setChecked(false);
                        low_to_high_radio.setChecked(false);
                        high_to_low_radio.setChecked(true);

                    }
                }
        );
        a_to_z_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        a_to_z_radio.setChecked(true);
                        z_to_a_radio.setChecked(false);
                        low_to_high_radio.setChecked(false);
                        high_to_low_radio.setChecked(false);
                    }
                }
        );
        z_to_a_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        a_to_z_radio.setChecked(false);
                        z_to_a_radio.setChecked(true);
                        low_to_high_radio.setChecked(false);
                        high_to_low_radio.setChecked(false);
                    }
                }
        );
        low_to_high_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        a_to_z_radio.setChecked(false);
                        z_to_a_radio.setChecked(false);
                        low_to_high_radio.setChecked(true);
                        high_to_low_radio.setChecked(false);
                    }
                }
        );
        high_to_low_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        a_to_z_radio.setChecked(false);
                        z_to_a_radio.setChecked(false);
                        low_to_high_radio.setChecked(false);
                        high_to_low_radio.setChecked(true);
                    }
                }
        );
        clear_textview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        low_stock_radio.setChecked(false);
                        not_in_stock_radio.setChecked(false);
                        in_stock_radio.setChecked(false);
                    }
                }
        );
        apply_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (low_stock_radio.isChecked()) {
                            mListener.onFilterItemClicked("low_stock");
                        } else if (not_in_stock_radio.isChecked()) {
                            mListener.onFilterItemClicked("not_in_stock");
                        } else if (in_stock_radio.isChecked()) {
                            mListener.onFilterItemClicked("in_stock");
                        }

                        if (high_to_low_radio.isChecked()) {
                            mListener.onFilterItemClicked("high_to_low_qty");
                        } else if (low_to_high_radio.isChecked()) {
                            mListener.onFilterItemClicked("qty_low_to_high");
                        } else if (a_to_z_radio.isChecked()) {
                            mListener.onFilterItemClicked("a_to_z");
                        } else if (z_to_a_radio.isChecked()) {
                            mListener.onFilterItemClicked("z_to_a");
                        }
                        dismiss();
                    }
                }
        );
        return v;
    }

    public interface FilterItemSheet {
        void onFilterItemClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (FilterItemSheet) context;
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
