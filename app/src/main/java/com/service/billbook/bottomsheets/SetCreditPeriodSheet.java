package com.service.billbook.bottomsheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.service.billbook.R;

public class SetCreditPeriodSheet extends BottomSheetDialogFragment {
    TextInputLayout custom_duration_input_layout;
    private SetCreditPeriodSheet.SetCreditPeriodListener mListener;
    RadioGroup radio_group1, radio_group2, parent_radio_group;
    TextView select_credit_period_tv, set_credit_period_tv;
    int chkId1;
    int chkId2;
    Button set_credit_btn;
    EditText custom_days_et;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.set_credit_period_sheet_layout, container, false);
        radio_group1 = v.findViewById(R.id.radio_group1);
        radio_group2 = v.findViewById(R.id.radio_group2);
        parent_radio_group = v.findViewById(R.id.parent_radio_group);
        select_credit_period_tv = v.findViewById(R.id.select_credit_period_tv);
        set_credit_period_tv = v.findViewById(R.id.set_credit_period_tv);
        custom_duration_input_layout = v.findViewById(R.id.custom_duration_input_layout);
        set_credit_btn = v.findViewById(R.id.set_credit_btn);
        custom_days_et = v.findViewById(R.id.custom_days_et);
        radio_group1.clearCheck(); // this is so we can start fresh, with no selection on both RadioGroups
        radio_group2.clearCheck();
        radio_group1.setOnCheckedChangeListener(listener1);
        radio_group2.setOnCheckedChangeListener(listener2);
        set_credit_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int checkedRadioButtonId1 = radio_group1.getCheckedRadioButtonId();
                        String days = "";
                        if (checkedRadioButtonId1 == -1) {
                            // No item selected
                        } else {
                            if (checkedRadioButtonId1 == R.id.fifteen_days_radio_btn) {
                                // Do something with the button
                                days = String.valueOf(15);
                            } else if (checkedRadioButtonId1 == R.id.thirty_days_radio_btn) {
                                days = String.valueOf(30);
                            } else if (checkedRadioButtonId1 == R.id.forty_five_days_radio_btn) {
                                days = String.valueOf(45);
                            }
                        }
                        int checkedRadioButtonId2 = radio_group2.getCheckedRadioButtonId();

                        if (checkedRadioButtonId2 == -1) {
                            // No item selected
                        } else {
                            if (checkedRadioButtonId2 == R.id.sixty_days_radio_btn) {
                                // Do something with the button
                                days = String.valueOf(60);
                            } else if (checkedRadioButtonId2 == R.id.ninty_days_radio_btn) {
                                days = String.valueOf(90);
                            } else if (checkedRadioButtonId2 == R.id.custom_radio_btn) {
                                days = custom_days_et.getText().toString();
                            }
                        }
                      mListener.onCreditPeriosSelected(days);
                        dismiss();
                    }
                }
        );
        return v;
    }

    public interface SetCreditPeriodListener {
        void onCreditPeriosSelected(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (SetCreditPeriodSheet.SetCreditPeriodListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private RadioGroup.OnCheckedChangeListener listener1 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                radio_group2.setOnCheckedChangeListener(null); // remove the listener before clearing so we don't throw that stackoverflow exception(like Vladimir Volodin pointed out)
                radio_group2.clearCheck(); // clear the second RadioGroup!
                radio_group2.setOnCheckedChangeListener(listener2); //reset the listener
                chkId1 = radio_group1.getCheckedRadioButtonId();
            }
            int realCheck = chkId1 == -1 ? chkId2 : chkId1;
        }
    };

    private RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                radio_group1.setOnCheckedChangeListener(null);
                radio_group1.clearCheck();
                radio_group1.setOnCheckedChangeListener(listener1);
                chkId2 = radio_group2.getCheckedRadioButtonId();
//              int realCheck = chkId1 == -1 ? chkId2 : chkId1;
                if (checkedId == R.id.custom_radio_btn) {
                    parent_radio_group.setVisibility(View.GONE);
                    custom_duration_input_layout.setVisibility(View.VISIBLE);
                    select_credit_period_tv.setText("Select custom credit period");
                    set_credit_period_tv.setText("Custom Credit Period");
                }
            }
        }
    };
}