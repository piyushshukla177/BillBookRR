package com.service.billbook.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import android.widget.TextView;
import android.widget.Toast;
import com.service.billbook.R;
import com.service.billbook.servicemodels.EncodeModel;
import com.service.billbook.servicemodels.VerifyOTPModel;
import com.service.billbook.network.ApiHelper;
import com.service.billbook.util.LocaleHelper;
import com.service.billbook.util.PrefsHelper;
import com.service.billbook.network.RetrofitClient;
import java.util.concurrent.TimeUnit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyMobileActivity extends AppCompatActivity {

    Context context;
    ImageView back_image;
    Button verify_btn;
    EditText otp_et;
    TextView seconds_tv, resend_otp;
    LinearLayoutCompat timer_linear;
    private ApiHelper apiHelper;

    String encoded_mobile_number, encoded_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set content view AFTER ABOVE sequence (to avoid crash)
        setContentView(R.layout.activity_verify);
        init();
    }

    String otp_txt, mobile_number;

    void init() {
        context = this;
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        otp_et = findViewById(R.id.otp_et);
        verify_btn = findViewById(R.id.verify_btn);
        back_image = findViewById(R.id.back_image);
        seconds_tv = findViewById(R.id.seconds_tv);
        timer_linear = findViewById(R.id.timer_linear);
        resend_otp = findViewById(R.id.resend_otp);
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(VerifyMobileActivity.this, SelectMobileActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        verify_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (otp_et.getText().toString().equalsIgnoreCase("123456")) {
                            EncodeOTP(otp_et.getText().toString(), "false");
                        } else {
                            Toast.makeText(VerifyMobileActivity.this, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        otp_txt = getIntent().getStringExtra("otp");
        mobile_number = getIntent().getStringExtra("mobile");
        encoded_mobile_number = EncodeOTP(mobile_number, "true");

//        Timer T=new Timer();
//        T.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
//                        seconds_tv.setText("count="+count);
//                        count++;
//                    }
//                });
//            }
//        }, 1000, 1000);
        resend_otp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timer_linear.setVisibility(View.VISIBLE);
                        startTimer();
                        resend_otp.setVisibility(View.GONE);
                    }
                }
        );
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    private void startTimer() {
        CountDownTimer startTimer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {

                long sec = (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
//              Log.e("Timer", "onTick: " + sec);
                seconds_tv.setText(String.format(" %02d ", sec));
                if (sec == 1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            seconds_tv.setText(" 00 ");
                        }
                    }, 1000);
                }
            }
            public void onFinish() {
                timer_linear.setVisibility(View.GONE);
                resend_otp.setVisibility(View.VISIBLE);
//              seconds_tv.setText("Timer finish");
            }
        }.start();
    }

    String encoded_value;

    private String EncodeOTP(String str, String mobile) {
        encoded_value = "";
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnCancelListener(new Dialog.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                // DO SOME STUFF HERE
            }
        });
        mProgressDialog.show();
        Call<EncodeModel> loginCall = apiHelper.Encode(str);
        loginCall.enqueue(new Callback<EncodeModel>() {
            @Override
            public void onResponse(@NonNull Call<EncodeModel> call,
                                   @NonNull Response<EncodeModel> response) {
                mProgressDialog.hide();
                if (response.isSuccessful()) {

                    if (response != null) {
                        EncodeModel m = response.body();
                        if (m.getResponse().equalsIgnoreCase("true")) {

                            if (mobile.equalsIgnoreCase("true")) {
                                encoded_mobile_number = m.getBody().getText();
                                startTimer();
                            } else {
                                encoded_otp = m.getBody().getText();
                                VerifyOTP();
                            }
                        } else {
                            Toast.makeText(VerifyMobileActivity.this, "Enter valid Mobile Number", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<EncodeModel> call,
                                  @NonNull Throwable t) {
                mProgressDialog.hide();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
        return encoded_value;
    }

    private void VerifyOTP() {
        encoded_value = "";
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnCancelListener(new Dialog.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                // DO SOME STUFF HERE
            }
        });
        mProgressDialog.show();
        Call<VerifyOTPModel> loginCall = apiHelper.VerifyOTP(encoded_otp, encoded_mobile_number);
        loginCall.enqueue(new Callback<VerifyOTPModel>() {
            @Override
            public void onResponse(@NonNull Call<VerifyOTPModel> call,
                                   @NonNull Response<VerifyOTPModel> response) {
                mProgressDialog.hide();
                if (response.isSuccessful()) {

                    if (response != null) {
                        VerifyOTPModel m = response.body();
                        if (m.getResponse().equalsIgnoreCase("true")) {
                            PrefsHelper.putString(context,"user_id",m.getBody().getItem4());
                            Intent intent = new Intent(VerifyMobileActivity.this, SelectIndustryActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(VerifyMobileActivity.this, "Enter valid Mobile Number", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<VerifyOTPModel> call,
                                  @NonNull Throwable t) {
                mProgressDialog.hide();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }
}
