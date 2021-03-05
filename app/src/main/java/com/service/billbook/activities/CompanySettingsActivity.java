package com.service.billbook.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.service.billbook.R;

import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class CompanySettingsActivity extends AppCompatActivity {

    Spinner industry_type_spinner;
    ImageView back_image, company_logo;
    ArrayList industry_type_list = new ArrayList();
    LinearLayout business_types_linear;
    ImageView select_business_type_imageview;
    RelativeLayout select_business_type_relative, bank_details_relative;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_settings);
        String[] appPermissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        company_logo = findViewById(R.id.company_logo);
        bank_details_relative = findViewById(R.id.bank_details_relative);
        business_types_linear = findViewById(R.id.business_types_linear);
        industry_type_spinner = findViewById(R.id.industry_type_spinner);
        back_image = findViewById(R.id.back_image);
        select_business_type_relative = findViewById(R.id.select_business_type_relative);
        select_business_type_imageview = findViewById(R.id.select_business_type_imageview);
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompanySettingsActivity.super.onBackPressed();
            }
        });

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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.spinner_layout, industry_type_list);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_layout);
        industry_type_spinner.setAdapter(adapter);

        select_business_type_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (business_types_linear.getVisibility() == business_types_linear.VISIBLE) {
                            select_business_type_imageview.setImageResource(R.drawable.down_arrow);
                            business_types_linear.setVisibility(View.GONE);
                        } else {
                            select_business_type_imageview.setImageResource(R.drawable.up_arrow);
                            business_types_linear.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );

        bank_details_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CompanySettingsActivity.this, BankDetailsActivtity.class);
                        startActivity(intent);
                    }
                }
        );

        company_logo.setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {

                        if (checkPermissions()) {
                            TedBottomPicker.with(CompanySettingsActivity.this)
                                    .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                                        @Override
                                        public void onImageSelected(Uri uri) {
                                            // here is selected image uri
                                            company_logo.setImageURI(uri);
                                        }
                                    });
                        }
                    }
                }
        );
        checkPermissions();
    }

    String[] appPermissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public int PERMISSION_CODE = 100;

    public boolean checkPermissions() {

        List<String> lsitPermissionsNeeded = new ArrayList<>();
        for (String perm : appPermissions) {

            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                lsitPermissionsNeeded.add(perm);
            }
        }

        //check for non granted permissions
        if (!lsitPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, lsitPermissionsNeeded.toArray(new String[lsitPermissionsNeeded.size()]), PERMISSION_CODE);
            return false;
        }
        //app ha all permissions proceed ahead
        return true;
    }

//    private void requestForSpecificPermission() {
//        ActivityCompat.requestPermissions(CompanySettingsActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}