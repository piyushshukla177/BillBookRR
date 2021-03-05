package com.service.billbook.bottomsheets;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.service.billbook.R;

public class AddExpenseSheet extends BottomSheetDialogFragment {
    private AddExpenseSheet.CreateExpenseCategoryListener mListener;
    ImageView close_imageview;
    Button create_expense_category_btn;
    EditText expense_category_name_et;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_expense_sheet_layout, container, false);

        close_imageview = v.findViewById(R.id.close_imageview);
        create_expense_category_btn = v.findViewById(R.id.create_expense_category_btn);
        expense_category_name_et = v.findViewById(R.id.expense_category_name_et);

        close_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }

    public interface CreateExpenseCategoryListener {
        void onCreateExpenseCategory(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (AddExpenseSheet.CreateExpenseCategoryListener) context;
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