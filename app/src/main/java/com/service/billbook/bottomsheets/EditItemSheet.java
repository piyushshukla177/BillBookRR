package com.service.billbook.bottomsheets;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.service.billbook.R;
import java.util.ArrayList;

public class EditItemSheet extends BottomSheetDialogFragment {

//  RadioButton discount_percentage_radio, disocunt_rupee_radio;
    RadioGroup discount_radio_group;
    public String product_name_txt;
    public String unit_txt;
    public String qty_txt;
    public String price_txt;
    private EditItemSheet.EditItemListener mListener;
    ImageView delete_imageview, back_imageview;
    Spinner gst_type_spinner;
    TextView item_name_tv;
    EditText price_et, unit_et, quantity_et;
    TextInputLayout discount_rupee_inputlayout, dicount_percentage_inputlayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_item_sheet_layout, container, false);

        delete_imageview = v.findViewById(R.id.delete_imageview);
        gst_type_spinner = v.findViewById(R.id.gst_type_spinner);
        item_name_tv = v.findViewById(R.id.item_name_tv);
        price_et = v.findViewById(R.id.price_et);
        unit_et = v.findViewById(R.id.unit_et);
        quantity_et = v.findViewById(R.id.quantity_et);
        discount_radio_group = v.findViewById(R.id.discount_radio_group);
        discount_rupee_inputlayout = v.findViewById(R.id.discount_rupee_inputlayout);
        dicount_percentage_inputlayout = v.findViewById(R.id.dicount_percentage_inputlayout);
        back_imageview = v.findViewById(R.id.back_imageview);

        item_name_tv.setText(product_name_txt);
        price_et.setText(price_txt);
        unit_et.setText(unit_txt);
        quantity_et.setText(qty_txt);
        delete_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        back_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ArrayList gst_tax_list = new ArrayList();
        gst_tax_list.add("GST @ 0%");
        gst_tax_list.add("GST @ 5%");
        gst_tax_list.add("GST @ 12%");
        gst_tax_list.add("GST @ 18%");
        gst_tax_list.add("GST @ 28%");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_layout, gst_tax_list);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_layout);
        gst_type_spinner.setAdapter(adapter);
        discount_radio_group.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.disocunt_rupee_radio) {
                            dicount_percentage_inputlayout.setVisibility(View.GONE);
                            discount_rupee_inputlayout.setVisibility(View.VISIBLE);
                        } else {
                            dicount_percentage_inputlayout.setVisibility(View.VISIBLE);
                            discount_rupee_inputlayout.setVisibility(View.GONE);
                        }

                    }
                }
        );
        return v;
    }

    public interface EditItemListener {
        void onEditItem(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (EditItemSheet.EditItemListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setValues(String product_name) {
        item_name_tv.setText(product_name);
    }
}