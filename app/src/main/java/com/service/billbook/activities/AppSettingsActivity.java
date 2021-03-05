package com.service.billbook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.service.billbook.R;

public class AppSettingsActivity extends AppCompatActivity {

    LinearLayout payment_reminder_linear, reminder_linear, payment_reminder_Self_linear, bill_self_linear, purchase_self_reminder;
    ImageView payment_reminder_imageview, back_image, payment_reminder_self_imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);
        init();
    }

    private void init() {
        payment_reminder_linear = findViewById(R.id.payment_reminder_linear);
        payment_reminder_imageview = findViewById(R.id.payment_reminder_imageview);
        reminder_linear = findViewById(R.id.reminder_linear);
        payment_reminder_Self_linear = findViewById(R.id.payment_reminder_Self_linear);
        bill_self_linear = findViewById(R.id.bill_self_linear);
        purchase_self_reminder = findViewById(R.id.purchase_self_reminder);
        back_image = findViewById(R.id.back_image);
        payment_reminder_self_imageview = findViewById(R.id.payment_reminder_self_imageview);
        payment_reminder_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (reminder_linear.getVisibility() == View.GONE) {
                            payment_reminder_imageview.setImageResource(R.drawable.up_arrow);
                            reminder_linear.setVisibility(View.VISIBLE);
                        } else {
                            payment_reminder_imageview.setImageResource(R.drawable.down_arrow);
                            reminder_linear.setVisibility(View.GONE);
                        }
                    }
                }
        );
        payment_reminder_Self_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bill_self_linear.getVisibility() == View.GONE) {
                            payment_reminder_self_imageview.setImageResource(R.drawable.up_arrow);
                            bill_self_linear.setVisibility(View.VISIBLE);
                            purchase_self_reminder.setVisibility(View.VISIBLE);
                        } else {
                            payment_reminder_self_imageview.setImageResource(R.drawable.down_arrow);
                            bill_self_linear.setVisibility(View.GONE);
                            purchase_self_reminder.setVisibility(View.GONE);
                        }
                    }
                }
        );
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppSettingsActivity.super.onBackPressed();
                    }
                }
        );
    }
}