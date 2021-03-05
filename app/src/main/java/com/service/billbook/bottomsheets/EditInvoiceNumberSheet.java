package com.service.billbook.bottomsheets;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.service.billbook.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditInvoiceNumberSheet extends BottomSheetDialogFragment {

    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    String todate = null, fromdate = null;
    private EditInvoiceNumberSheetListener mListener;
    ImageView close_imageview, plus_image;
    LinearLayout add_prefix_linear, prefix_linear;
    TextInputLayout invoice_number_layout;
    TextView add_prefix_tv;
    EditText contact_number_et, bill_date_et, due_date_et;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_invoice_sheet_layout, container, false);

        close_imageview = v.findViewById(R.id.close_imageview);
        add_prefix_linear = v.findViewById(R.id.add_prefix_linear);
        prefix_linear = v.findViewById(R.id.prefix_linear);
        invoice_number_layout = v.findViewById(R.id.invoice_number_layout);
        plus_image = v.findViewById(R.id.plus_image);
        add_prefix_tv = v.findViewById(R.id.add_prefix_tv);
        contact_number_et = v.findViewById(R.id.contact_number_et);
        bill_date_et = v.findViewById(R.id.bill_date_et);
        due_date_et = v.findViewById(R.id.due_date_et);
        contact_number_et.setText(String.valueOf(1));
        close_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        add_prefix_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (prefix_linear != null && prefix_linear.getVisibility() == View.GONE) {
                            prefix_linear.setVisibility(View.VISIBLE);
                            invoice_number_layout.setVisibility(View.GONE);
                            plus_image.setImageResource(R.drawable.cross_circular);
                            add_prefix_tv.setText("Remove Prefix");
                            add_prefix_tv.setTextColor(getResources().getColor(R.color.black));
                        } else {
                            prefix_linear.setVisibility(View.GONE);
                            invoice_number_layout.setVisibility(View.VISIBLE);
                            plus_image.setImageResource(R.drawable.circular_plus);
                            add_prefix_tv.setText("Add Prefix");
                            add_prefix_tv.setTextColor(getResources().getColor(R.color.btn_color));
                        }

                    }
                }
        );
        bill_date_et.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePicker(bill_date_et);
                    }
                }
        );
        due_date_et.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePicker(due_date_et);
                    }
                }
        );

        return v;
    }

    public interface EditInvoiceNumberSheetListener {
        void onEditInvoiceNumber(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (EditInvoiceNumberSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void datePicker(final EditText date) {
        calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String value = convertMonth(month);
                        if (value != (null)) {
                            String edit = String.valueOf(date);

                            String[] separated = edit.split("/");
                            String variable = separated[1];

                            if (variable.equals("edt_to_date}")) {
                                todate = (year + "-" + month + "-" + day);
                            } else {
                                fromdate = (year + "-" + month + "-" + day);
                            }
                            //date.setText(day + "-" + month + 1  + "-" + year);
                            date.setText(day + "-" + value + "-" + year);

                            if ((fromdate != null) && (todate != null)) {
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                                Date date1 = null;
                                Date date2 = null;
                                try {
                                    date1 = format.parse(fromdate);
                                    date2 = format.parse(todate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public String convertMonth(int month) {
        String value = null;
        if (month == 1) {
            value = "Jan";
        } else if (month == 2) {
            value = "Feb";
        } else if (month == 3) {
            value = "Mar";
        } else if (month == 4) {
            value = "Apr";
        } else if (month == 5) {
            value = "May";
        } else if (month == 6) {
            value = "Jun";
        } else if (month == 7) {
            value = "Jul";
        } else if (month == 8) {
            value = "Aug";
        } else if (month == 9) {
            value = "Sep";
        } else if (month == 10) {
            value = "Oct";
        } else if (month == 11) {
            value = "Nov";
        } else if (month == 12) {
            value = "Dec";
        }
        return value;
    }
}