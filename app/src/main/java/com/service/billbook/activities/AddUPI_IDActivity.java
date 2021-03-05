package com.service.billbook.activities;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.service.billbook.R;

public class AddUPI_IDActivity extends AppCompatActivity {

    ImageView back_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_upi_id);

        back_image = findViewById(R.id.back_image);
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AddUPI_IDActivity.this, UpiPaymentLinkActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
