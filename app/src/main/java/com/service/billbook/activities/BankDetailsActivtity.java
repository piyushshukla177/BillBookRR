package com.service.billbook.activities;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.service.billbook.R;

public class BankDetailsActivtity extends AppCompatActivity {

    ImageView back_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details_activtity);
        back_image=findViewById(R.id.back_image);
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BankDetailsActivtity.super.onBackPressed();
                    }
                }
        );
    }
}