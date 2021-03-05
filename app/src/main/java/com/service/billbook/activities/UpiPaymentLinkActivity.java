package com.service.billbook.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.service.billbook.R;

public class UpiPaymentLinkActivity extends AppCompatActivity {

    ImageView back_image;
    CardView add_upi_id_cardview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upi_payment_link);
        back_image = findViewById(R.id.back_image);
        add_upi_id_cardview = findViewById(R.id.add_upi_id_cardview);
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UpiPaymentLinkActivity.super.onBackPressed();
                    }
                }
        );
        add_upi_id_cardview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UpiPaymentLinkActivity.this, AddUPI_IDActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }
}