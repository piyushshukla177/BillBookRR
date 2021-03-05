package com.service.billbook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.service.billbook.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

public class PurchaseOrderActivity extends AppCompatActivity {

    ImageView back_image;
    Button add_items_btn;
    MaterialBetterSpinner select_party_spinner;
    ArrayList partyList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_order);
        init();
    }

    void init() {
        back_image = findViewById(R.id.back_image);
        add_items_btn = findViewById(R.id.add_items_btn);
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PurchaseOrderActivity.super.onBackPressed();
                    }
                }
        );  add_items_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Intent intent = new Intent(PurchaseOrderActivity.this,CounterPosActivity.class);
                       startActivity(intent);
                    }
                }
        );
        select_party_spinner = findViewById(R.id.select_party_spinner);
        partyList.add("Party A");
        partyList.add("Party B");
        ArrayAdapter<String> incomeTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, partyList);
        select_party_spinner.setAdapter(incomeTypeAdapter);
    }
}