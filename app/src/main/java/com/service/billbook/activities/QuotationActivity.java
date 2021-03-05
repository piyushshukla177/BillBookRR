package com.service.billbook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.service.billbook.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

public class QuotationActivity extends AppCompatActivity {

    ImageView back_image;
    MaterialBetterSpinner select_party_spinner;
    ArrayList partyList = new ArrayList();
    Button add_items_btn;
    String intent_status = "";
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);
        init();
    }

    void init() {
        context=this;
        back_image = findViewById(R.id.back_image);
        back_image.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        QuotationActivity.super.onBackPressed();
                    }
                }
        );
        select_party_spinner = findViewById(R.id.select_party_spinner);
        add_items_btn = findViewById(R.id.add_items_btn);
        add_items_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent_status = "";
                        if (!select_party_spinner.getText().toString().isEmpty()) {
                            intent_status = "counter";
                            Intent intent = new Intent(QuotationActivity.this, CounterPosActivity.class);
                            intent.putExtra("from_activity","QuotationActivity");
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(context, "Please Select Party", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        partyList.add("Party A");
        partyList.add("Party B");
        ArrayAdapter<String> incomeTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, partyList);
        select_party_spinner.setAdapter(incomeTypeAdapter);
    }
}