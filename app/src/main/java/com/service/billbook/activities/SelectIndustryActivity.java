package com.service.billbook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.service.billbook.R;
import com.service.billbook.bottomsheets.AddReferalCodeSheet;

import java.util.ArrayList;

public class SelectIndustryActivity extends AppCompatActivity implements AddReferalCodeSheet.ReferalCodeInterface {

    TextInputLayout gstin_layout;
    Spinner industry_type_spinner;
    ArrayList industry_type_list = new ArrayList();
    RadioButton gst_reg_radio, gst_not_reg_radio;
    Button continue_button;
    TextView referal_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_industry);

        industry_type_list.add(getString(R.string.Select_Industry_Type));
        industry_type_list.add("General Store (Kirana)");
        industry_type_list.add("Hardware");
        industry_type_list.add("Stationary");
        industry_type_list.add("Travel");
        industry_type_list.add("Medicine(pharma)");
        industry_type_list.add("Mobile and Accessories");
        industry_type_list.add("Agriculture");
        industry_type_list.add("Art and Design");
        industry_type_list.add("Automotive");
        industry_type_list.add("Construction");
        industry_type_list.add("Consulting");
        industry_type_list.add("Dairy(Milk)");
        industry_type_list.add("Education");
        industry_type_list.add("Electronics");
        industry_type_list.add("Engineering");
        industry_type_list.add("Financial Services");
        industry_type_list.add("Food Services(Restaurant,Cafe)");
        industry_type_list.add("Fruit And Vegitables");
        industry_type_list.add("Garment (Textile)");
        industry_type_list.add("Giftshop");
        industry_type_list.add("Government");
        industry_type_list.add("Healthcare");
        industry_type_list.add("Information Technology");
        industry_type_list.add("Meat");
        industry_type_list.add("Services");
        industry_type_list.add("Other");
        industry_type_spinner = findViewById(R.id.industry_type_spinner);
        gst_reg_radio = findViewById(R.id.gst_reg_radio);
        gst_not_reg_radio = findViewById(R.id.gst_not_reg_radio);
        gstin_layout = findViewById(R.id.gstin_layout);
        continue_button = findViewById(R.id.continue_button);
        referal_tv = findViewById(R.id.referal_tv);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.spinner_layout, industry_type_list);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_layout);
        industry_type_spinner.setAdapter(adapter);

        gst_reg_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gstin_layout.setVisibility(View.VISIBLE);
                } else {
                    gstin_layout.setVisibility(View.GONE);
                }
            }
        });
        continue_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SelectIndustryActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        referal_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddReferalCodeSheet bottomSheet = new AddReferalCodeSheet();
                        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                    }
                }
        );
    }

    @Override
    public void onReferalCodeAdded(String text) {

    }
}