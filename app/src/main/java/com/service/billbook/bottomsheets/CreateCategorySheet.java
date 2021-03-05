package com.service.billbook.bottomsheets;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.service.billbook.R;

public class CreateCategorySheet extends BottomSheetDialogFragment {

    private CreateCategoryListener mListener;
    ImageView close_imageview;
    EditText category_name_et;
    Button create_catgeory_btn;
    String type, categoryName;
    TextView title_tv;

    public CreateCategorySheet(String type, String categoryName) {
        this.type = type;
        this.categoryName = categoryName;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_category_sheet_layout, container, false);

        close_imageview = v.findViewById(R.id.close_imageview);
        category_name_et = v.findViewById(R.id.category_name_et);
        create_catgeory_btn = v.findViewById(R.id.create_catgeory_btn);
        title_tv = v.findViewById(R.id.title_tv);

        close_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        create_catgeory_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!category_name_et.getText().toString().isEmpty()) {
                            mListener.onCreateCategory(category_name_et.getText().toString());
                        } else {
                            Toast.makeText(getActivity(), "Enter Category Name", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        if (type != null && type.equals("edit")) {
            title_tv.setText("Edit Category");
            create_catgeory_btn.setText("Edit");
            category_name_et.setText(categoryName);
        }
        return v;
    }

    public interface CreateCategoryListener {
        void onCreateCategory(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (CreateCategoryListener) context;
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
