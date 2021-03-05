package com.service.billbook.bottomsheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.service.billbook.R;

public class AddStockSheet extends BottomSheetDialogFragment {
    private AddStockListener mListener;
    ImageView back_arrow;
    LinearLayout add_remark_linear;
    TextInputLayout add_remark_input_layout;
    TextView add_stock_tv;
    public String stock_type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_stock_sheet_layout, container, false);

        back_arrow = v.findViewById(R.id.back_arrow);
        add_remark_linear = v.findViewById(R.id.add_remark_linear);
        add_remark_input_layout = v.findViewById(R.id.add_remark_input_layout);
        add_stock_tv = v.findViewById(R.id.add_stock_tv);
        add_stock_tv.setText(stock_type);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        add_remark_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_remark_linear.setVisibility(View.GONE);
                add_remark_input_layout.setVisibility(View.VISIBLE);
            }
        });
        return v;
    }

    public interface AddStockListener {
        void onAddStock(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (AddStockListener) context;
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
