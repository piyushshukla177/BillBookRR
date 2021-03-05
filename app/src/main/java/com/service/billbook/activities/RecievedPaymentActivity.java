package com.service.billbook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.service.billbook.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecievedPaymentActivity extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    String todate = null, fromdate = null;
    TextInputLayout note_textinput_layout;
    ImageView back_image, plus_imageview;
    MaterialBetterSpinner select_party_spinner;
    ArrayList partyList = new ArrayList();
    LinearLayout date_picker_linear, add_note_linear;
    TextView date_tv,add_note_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieved_payment);

        init();
    }

    void init() {
        date_tv = findViewById(R.id.date_tv);
        add_note_tv = findViewById(R.id.add_note_tv);
        back_image = findViewById(R.id.back_image);
        note_textinput_layout = findViewById(R.id.note_textinput_layout);
        select_party_spinner = findViewById(R.id.select_party_spinner);
        date_picker_linear = findViewById(R.id.date_picker_linear);
        add_note_linear = findViewById(R.id.add_note_linear);
        plus_imageview = findViewById(R.id.plus_imageview);
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RecievedPaymentActivity.super.onBackPressed();
                    }
                }
        );
        partyList.add("Party A");
        partyList.add("Party B");
        ArrayAdapter<String> incomeTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, partyList);
        select_party_spinner.setAdapter(incomeTypeAdapter);

        date_picker_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePicker(date_picker_linear);
                    }
                }
        );
        add_note_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (note_textinput_layout.getVisibility() == View.GONE) {
                            note_textinput_layout.setVisibility(View.VISIBLE);
                            plus_imageview.setImageResource(R.drawable.cross_circular);
                            add_note_tv.setTextColor(getResources().getColor(R.color.black));
                        } else {
                            note_textinput_layout.setVisibility(View.GONE);
                            plus_imageview.setImageResource(R.drawable.circular_plus);
                            add_note_tv.setTextColor(getResources().getColor(R.color.btn_color));
                        }
                    }
                }
        );
    }

    public void datePicker(final LinearLayout date) {
        calendar = Calendar.getInstance();
//        year = calendar.get(Calendar.YEAR);
//        month = calendar.get(Calendar.MONTH);
//        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(RecievedPaymentActivity.this,
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
                            date_tv.setText(day + "-" + value + "-" + year);

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