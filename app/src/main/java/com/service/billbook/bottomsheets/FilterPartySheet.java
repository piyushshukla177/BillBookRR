package com.service.billbook.bottomsheets;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.service.billbook.R;
import androidx.annotation.Nullable;

public class FilterPartySheet extends BottomSheetDialogFragment {
    private FilterPartySheetListener mListener;
    Button apply_btn;
    RelativeLayout high_to_low_relative, low_to_high_relative;
    RadioButton high_to_low_radio, low_to_high_radio;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.choose_mobile_bottom_layout, container, false);

        apply_btn = v.findViewById(R.id.apply_btn);
        high_to_low_relative = v.findViewById(R.id.high_to_low_relative);
        low_to_high_relative = v.findViewById(R.id.low_to_high_relative);
        high_to_low_radio = v.findViewById(R.id.high_to_low_radio);
        low_to_high_radio = v.findViewById(R.id.low_to_high_radio);
        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (high_to_low_radio.isChecked()) {
                    mListener.onFilterParty("high_to_low");
                } else {
                    mListener.onFilterParty("low_to_high");
                }
                dismiss();
            }
        });
        high_to_low_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        high_to_low_radio.setChecked(true);
                        low_to_high_radio.setChecked(false);
                    }
                }
        );
        low_to_high_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        high_to_low_radio.setChecked(false);
                        low_to_high_radio.setChecked(true);
                    }
                }
        );
        high_to_low_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        high_to_low_radio.setChecked(true);
                        low_to_high_radio.setChecked(false);
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
        return v;
    }

    public interface FilterPartySheetListener {
        void onFilterParty(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (FilterPartySheetListener) context;
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
