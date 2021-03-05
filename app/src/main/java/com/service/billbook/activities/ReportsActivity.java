package com.service.billbook.activities;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.service.billbook.R;

public class ReportsActivity extends AppCompatActivity {

    RelativeLayout transactions_reports_relative, item_reports_relative, party_reports_relative,expense_reports_relative;
    ImageView back_image, solid_up_transactions_imageview, solid_up_item_imageview, solid_up_party_imageview,solid_up_expense_imageview;
    LinearLayout transactions_reports_linear, item_reports_linear, party_reports_linear,expense_reports_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        init();
    }

    void init() {
        back_image = findViewById(R.id.back_image);
        solid_up_transactions_imageview = findViewById(R.id.solid_up_transactions_imageview);
        solid_up_item_imageview = findViewById(R.id.solid_up_item_imageview);
        solid_up_party_imageview = findViewById(R.id.solid_up_party_imageview);
        solid_up_expense_imageview = findViewById(R.id.solid_up_expense_imageview);
        transactions_reports_linear = findViewById(R.id.transactions_reports_linear);
        item_reports_linear = findViewById(R.id.item_reports_linear);
        party_reports_linear = findViewById(R.id.party_reports_linear);
        expense_reports_linear = findViewById(R.id.expense_reports_linear);
        transactions_reports_relative = findViewById(R.id.transactions_reports_relative);
        item_reports_relative = findViewById(R.id.item_reports_relative);
        party_reports_relative = findViewById(R.id.party_reports_relative);
        expense_reports_relative = findViewById(R.id.expense_reports_relative);
        transactions_reports_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (transactions_reports_linear.getVisibility() == View.VISIBLE) {
                            transactions_reports_linear.setVisibility(View.GONE);
                            solid_up_transactions_imageview.setImageResource(R.drawable.down_solid_black);
                        } else {
                            transactions_reports_linear.setVisibility(View.VISIBLE);
                            solid_up_transactions_imageview.setImageResource(R.drawable.up_solid_black);
                        }
                    }
                }
        );
        item_reports_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item_reports_linear.getVisibility() == View.VISIBLE) {
                            item_reports_linear.setVisibility(View.GONE);
                            solid_up_item_imageview.setImageResource(R.drawable.down_solid_black);
                        } else {
                            item_reports_linear.setVisibility(View.VISIBLE);
                            solid_up_item_imageview.setImageResource(R.drawable.up_solid_black);
                        }
                    }
                }
        );
        party_reports_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (party_reports_linear.getVisibility() == View.VISIBLE) {
                            party_reports_linear.setVisibility(View.GONE);
                            solid_up_party_imageview.setImageResource(R.drawable.down_solid_black);
                        } else {
                            party_reports_linear.setVisibility(View.VISIBLE);
                            solid_up_party_imageview.setImageResource(R.drawable.up_solid_black);
                        }
                    }
                }
        );
        expense_reports_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (expense_reports_linear.getVisibility() == View.VISIBLE) {
                            expense_reports_linear.setVisibility(View.GONE);
                            solid_up_expense_imageview.setImageResource(R.drawable.down_solid_black);
                        } else {
                            expense_reports_linear.setVisibility(View.VISIBLE);
                            solid_up_expense_imageview.setImageResource(R.drawable.up_solid_black);
                        }
                    }
                }
        );
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ReportsActivity.super.onBackPressed();
                    }
                }
        );
    }
}