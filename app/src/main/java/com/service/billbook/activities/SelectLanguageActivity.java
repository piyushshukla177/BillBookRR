package com.service.billbook.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import com.service.billbook.R;
import com.service.billbook.util.LocaleHelper;

public class SelectLanguageActivity extends AppCompatActivity {

    CardView english_card, hindi_cardview, hinglish_cardview, gujrati_cardview, kannad_cardview;
    RadioButton english_radio, hindi_radiobtn, hinglish_radio, gujrati_radio, kannad_radio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
        init();
    }

    void init() {
        english_card = findViewById(R.id.english_card);
        hindi_cardview = findViewById(R.id.hindi_cardview);
        english_radio = findViewById(R.id.english_radio);
        hindi_radiobtn = findViewById(R.id.hindi_radiobtn);
        hinglish_cardview = findViewById(R.id.hinglish_cardview);
        hinglish_radio = findViewById(R.id.hinglish_radio);
//        gujrati_cardview = findViewById(R.id.gujrati_cardview);
//        gujrati_radio = findViewById(R.id.gujrati_radio);
//        kannad_cardview = findViewById(R.id.kannad_cardview);
//        kannad_radio = findViewById(R.id.kannad_radio);

        english_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        english_radio.setChecked(true);
                        finish();
                        Intent intent = new Intent(SelectLanguageActivity.this, SelectMobileActivity.class);
                        startActivity(intent);
                        LocaleHelper.setLocale(SelectLanguageActivity.this, "en");
                    }
                }
        );
        english_card.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        english_radio.setChecked(true);
                        finish();
                        Intent intent = new Intent(SelectLanguageActivity.this, SelectMobileActivity.class);
                        startActivity(intent);
                        LocaleHelper.setLocale(SelectLanguageActivity.this, "en");
                    }
                }
        );
        hindi_cardview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hindi_radiobtn.setChecked(true);
                        finish();
                        Intent intent = new Intent(SelectLanguageActivity.this, SelectMobileActivity.class);
                        startActivity(intent);
                        LocaleHelper.setLocale(SelectLanguageActivity.this, "hi");
                    }
                }
        );
        hindi_radiobtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hindi_radiobtn.setChecked(true);
                        finish();
                        Intent intent = new Intent(SelectLanguageActivity.this, SelectMobileActivity.class);
                        startActivity(intent);
                        LocaleHelper.setLocale(SelectLanguageActivity.this, "hi");
                    }
                }
        );

        hinglish_cardview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hinglish_radio.setChecked(true);
                        finish();
                        Intent intent = new Intent(SelectLanguageActivity.this, SelectMobileActivity.class);
                        startActivity(intent);
                        LocaleHelper.setLocale(SelectLanguageActivity.this, "gi");
                    }
                }
        );
        hinglish_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hinglish_radio.setChecked(true);
                        finish();
                        Intent intent = new Intent(SelectLanguageActivity.this, SelectMobileActivity.class);
                        startActivity(intent);
                        LocaleHelper.setLocale(SelectLanguageActivity.this, "gi");
                    }
                }
        );
//        gujrati_cardview.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        gujrati_radio.setChecked(true);
//                        finish();
//                        Intent intent = new Intent(SelectLanguageActivity.this, SelectMobileActivity.class);
//                        startActivity(intent);
//                    }
//                }
//        );
//        gujrati_radio.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        gujrati_radio.setChecked(true);
//                        finish();
//                        Intent intent = new Intent(SelectLanguageActivity.this, SelectMobileActivity.class);
//                        startActivity(intent);
//                    }
//                }
//        );
//        kannad_cardview.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        kannad_radio.setChecked(true);
//                        finish();
//                        Intent intent = new Intent(SelectLanguageActivity.this, SelectMobileActivity.class);
//                        startActivity(intent);
//                    }
//                }
//        );
//        kannad_radio.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        kannad_radio.setChecked(true);
//                        finish();
//                        Intent intent = new Intent(SelectLanguageActivity.this, SelectMobileActivity.class);
//                        startActivity(intent);
//                    }
//                }
//        );
//     String language = PrefsHelper.getString(SelectLanguageActivity.this, "language");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}
