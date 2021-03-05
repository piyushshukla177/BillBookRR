package com.service.billbook.activities;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import com.service.billbook.R;
import com.service.billbook.bottomsheets.AddExpenseSheet;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ExpenseActivity extends AppCompatActivity implements AddExpenseSheet.CreateExpenseCategoryListener {

    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    String todate = null, fromdate = null;

    ImageView back_image,add_new_catgeory_imageview;
    TextView date_picker_tv;
    MaterialBetterSpinner expense_spinner;
    ArrayList expense_category_list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        init();
    }

    void init() {
        back_image = findViewById(R.id.back_image);
        add_new_catgeory_imageview = findViewById(R.id.add_new_catgeory_imageview);
        date_picker_tv = findViewById(R.id.date_picker_tv);
        expense_spinner = findViewById(R.id.expense_spinner);
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExpenseActivity.super.onBackPressed();
                    }
                }
        );
        date_picker_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePicker(date_picker_tv);
                    }
                }
        );  add_new_catgeory_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddExpenseSheet bottomSheet = new AddExpenseSheet();
                        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                    }
                }
        );

        expense_category_list.add("Transportation & Travel Expenses");
        expense_category_list.add("Telephone & Internet Expenses");
        expense_category_list.add("Employee Salaries & Advance");
        expense_category_list.add("Repair & Maintenance");
        expense_category_list.add("Rent Expense");
        expense_category_list.add("Raw Material");
//        expense_category_list.add("Raw Material");
        expense_spinner = findViewById(R.id.expense_spinner);

        ArrayAdapter<String> incomeTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, expense_category_list);
        expense_spinner.setAdapter(incomeTypeAdapter);
    }

    public void datePicker(final TextView date) {
        calendar = Calendar.getInstance();
//        year = calendar.get(Calendar.YEAR);
//        month = calendar.get(Calendar.MONTH);
//        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(ExpenseActivity.this,
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
                            date_picker_tv.setText(day + "-" + value + "-" + year);

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

    @Override
    public void onCreateExpenseCategory(String text) {

    }
}