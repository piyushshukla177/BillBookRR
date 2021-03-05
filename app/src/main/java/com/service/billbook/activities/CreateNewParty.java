package com.service.billbook.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.service.billbook.R;
import com.service.billbook.network.ApiHelper;
import com.service.billbook.network.RetrofitClient;
import com.service.billbook.servicemodels.CreatePartyModel;
import com.service.billbook.servicemodels.UpdatePartyModel;
import com.service.billbook.util.PrefsHelper;
import com.service.billbook.bottomsheets.SetCreditPeriodSheet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNewParty extends AppCompatActivity implements SetCreditPeriodSheet.SetCreditPeriodListener {

    Context context;
    TextView gst_details_tv, adress_details_tv, balance_details_tv, credit_period_tv;
    LinearLayout gst_details_linear, address_details_linear, balance_details_linear, address_linear, balance_linear;
    ImageView back_image, gst_details_imageview, address_details_imageview, balance_details_imageview;
    TextInputLayout gstin_input_layout, party_billing_address_input_layout, party_shipping_address_input_layout;
    CheckBox shipping_checkbox;
    RelativeLayout set_credit_period_relative;

    EditText party_name_et, contact_number_et, gstin_et, party_billing_address_et, party_shipping_address_et, credit_limit_et;
    RadioButton customer_radio, supplier_radio;
    CardView save_party_btn;
    private ApiHelper apiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_party);
        init();
    }

    String user_id;
    String update_status = "false";
    String party_id;

    void init() {
        context = this;
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        user_id = PrefsHelper.getString(context, "user_id");

        back_image = findViewById(R.id.back_image);
        party_name_et = findViewById(R.id.party_name_et);
        contact_number_et = findViewById(R.id.contact_number_et);
        gstin_et = findViewById(R.id.gstin_et);
        party_billing_address_et = findViewById(R.id.party_billing_address_et);
        party_shipping_address_et = findViewById(R.id.party_shipping_address_et);
        customer_radio = findViewById(R.id.customer_radio);
        supplier_radio = findViewById(R.id.supplier_radio);
        gst_details_imageview = findViewById(R.id.gst_details_imageview);
        credit_period_tv = findViewById(R.id.credit_period_tv);
        credit_limit_et = findViewById(R.id.credit_limit_et);

        balance_details_imageview = findViewById(R.id.balance_details_imageview);
        address_details_imageview = findViewById(R.id.address_details_imageview);
        address_linear = findViewById(R.id.address_linear);
        gst_details_linear = findViewById(R.id.gst_details_linear);
        address_details_linear = findViewById(R.id.address_details_linear);
        balance_details_linear = findViewById(R.id.balance_details_linear);
        gstin_input_layout = findViewById(R.id.gstin_input_layout);
        party_billing_address_input_layout = findViewById(R.id.party_billing_address_input_layout);
        party_shipping_address_input_layout = findViewById(R.id.party_shipping_address_input_layout);
        shipping_checkbox = findViewById(R.id.shipping_checkbox);
        gst_details_tv = findViewById(R.id.gst_details_tv);
        adress_details_tv = findViewById(R.id.adress_details_tv);
        balance_linear = findViewById(R.id.balance_linear);
        balance_details_tv = findViewById(R.id.balance_details_tv);
        set_credit_period_relative = findViewById(R.id.set_credit_period_relative);
        save_party_btn = findViewById(R.id.save_party_btn);
        shipping_checkbox.setChecked(true);
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CreateNewParty.super.onBackPressed();
                    }
                }
        );
        gst_details_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (gstin_input_layout.getVisibility() == View.GONE) {
                            gstin_input_layout.setVisibility(View.VISIBLE);
                            gst_details_imageview.setImageResource(R.drawable.cross_circular);
                            gst_details_tv.setTextColor(getResources().getColor(R.color.black));
                        } else {
                            gstin_input_layout.setVisibility(View.GONE);
                            gst_details_imageview.setImageResource(R.drawable.circular_plus);
                            gst_details_tv.setTextColor(getResources().getColor(R.color.btn_color));
                        }

                    }
                }
        );
        address_details_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (address_linear.getVisibility() == View.GONE) {
                            address_linear.setVisibility(View.VISIBLE);
                            address_details_imageview.setImageResource(R.drawable.cross_circular);
                            adress_details_tv.setTextColor(getResources().getColor(R.color.black));
                        } else {
                            address_linear.setVisibility(View.GONE);
                            address_details_imageview.setImageResource(R.drawable.circular_plus);
                            adress_details_tv.setTextColor(getResources().getColor(R.color.btn_color));
                        }
                    }
                }
        );
        balance_details_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (balance_linear.getVisibility() == View.GONE) {
                            balance_linear.setVisibility(View.VISIBLE);
                            balance_details_imageview.setImageResource(R.drawable.cross_circular);
                            balance_details_tv.setTextColor(getResources().getColor(R.color.black));
                        } else {
                            balance_linear.setVisibility(View.GONE);
                            balance_details_imageview.setImageResource(R.drawable.circular_plus);
                            balance_details_tv.setTextColor(getResources().getColor(R.color.btn_color));
                        }
                    }
                }
        );
        shipping_checkbox.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (shipping_checkbox.isChecked()) {
                            party_shipping_address_input_layout.setVisibility(View.GONE);
                        } else {
                            party_shipping_address_input_layout.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );
        set_credit_period_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetCreditPeriodSheet bottomSheet = new SetCreditPeriodSheet();
                        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                    }
                }
        );
        save_party_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (check()) {
                            if (update_status.equals("true")) {
                                UpdateParty();
                            } else {
                                CreateParty();
                            }
                        }
                    }
                }
        );

        Intent intent = getIntent();
        if (intent.hasExtra("party_id")) {
            update_status = "true";
            party_id = intent.getStringExtra("party_id");
            party_name_et.setText(intent.getStringExtra("party_name"));
            contact_number_et.setText(intent.getStringExtra("party_mobile"));
            String party_type = intent.getStringExtra("party_type");
            if (party_type != null && party_type.equals("1")) {
                customer_radio.setSelected(true);
            } else {
                supplier_radio.setSelected(true);
            }
            gstin_et.setText(intent.getStringExtra("gstin_number"));
            party_billing_address_et.setText(intent.getStringExtra("billing_address"));
            party_shipping_address_et.setText(intent.getStringExtra("shipping_address"));
            if (intent.getStringExtra("credit_period") != null) {
                credit_period_tv.setText(intent.getStringExtra("credit_period") + " Day(s)");
            }
            if (intent.getStringExtra("credit_limit") != null) {
                credit_limit_et.setText(intent.getStringExtra("credit_limit"));
            }
        }
    }

    @Override
    public void onCreditPeriosSelected(String text) {
        credit_period_days = text;
        credit_period_tv.setText(text + " day(s)");
    }

    String credit_period_days = String.valueOf(7);

    void CreateParty() {
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

        String party_type = "";
        if (customer_radio.isSelected()) {
            party_type = String.valueOf(1);
        } else {
            party_type = String.valueOf(2);
        }

        String billingAdrSame = "false";
        if (shipping_checkbox.isChecked()) {
            billingAdrSame = "true";
        }
        String credit_limit = String.valueOf(0);
        if (!credit_limit_et.getText().toString().isEmpty()) {
            credit_limit = credit_limit_et.getText().toString();
        }
        Call<CreatePartyModel> createPartyCall = apiHelper.CreateParty(user_id, party_name_et.getText().toString(), contact_number_et.getText().toString(),
                party_billing_address_et.getText().toString(), billingAdrSame, party_shipping_address_et.getText().toString(),
                gstin_et.getText().toString(), party_type, credit_period_days, credit_limit);
        createPartyCall.enqueue(new Callback<CreatePartyModel>() {
            @Override
            public void onResponse(@NonNull Call<CreatePartyModel> call,
                                   @NonNull Response<CreatePartyModel> response) {
                mProgressDialog.hide();
                if (response.isSuccessful()) {

                    if (response != null) {
                        CreatePartyModel m = response.body();
                        if (m.getResponse().equalsIgnoreCase("true")) {
                            Toast.makeText(context, "Party Created Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CreatePartyModel> call,
                                  @NonNull Throwable t) {
                mProgressDialog.hide();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

    void UpdateParty() {
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

        String party_type = "";
        if (customer_radio.isSelected()) {
            party_type = String.valueOf(1);
        } else {
            party_type = String.valueOf(2);
        }

        String billingAdrSame = "false";
        if (shipping_checkbox.isChecked()) {
            billingAdrSame = "true";
        }
        String credit_limit = String.valueOf(0);
        if (credit_limit_et.getText() != null && !credit_limit_et.getText().toString().isEmpty() && !credit_limit_et.getText().toString().equals("null")) {
            credit_limit = credit_limit_et.getText().toString();
        }
        if (credit_period_days == null || credit_period_days.isEmpty()) {
            credit_period_days = String.valueOf(7);
        }

//        Log.e("parameters ", user_id + ", " + party_id + ", " + party_name_et.getText().toString() + ",  " + contact_number_et.getText().toString() + " , " + party_billing_address_et.getText().toString()
//                + " , " + billingAdrSame + " , " + party_shipping_address_et.getText().toString() + " , " + gstin_et.getText().toString() + " , " + party_type + " , " +
//                credit_period_days + " , " + credit_limit);

        Call<UpdatePartyModel> createPartyCall = apiHelper.UpdateParty(user_id, party_id, party_name_et.getText().toString(), contact_number_et.getText().toString(),
                party_billing_address_et.getText().toString(), billingAdrSame, party_shipping_address_et.getText().toString(),
                gstin_et.getText().toString(), party_type, credit_period_days, credit_limit);

        createPartyCall.enqueue(new Callback<UpdatePartyModel>() {
            @Override
            public void onResponse(@NonNull Call<UpdatePartyModel> call,
                                   @NonNull Response<UpdatePartyModel> response) {
                mProgressDialog.hide();
                if (response.isSuccessful()) {
                    if (response != null) {
                        UpdatePartyModel m = response.body();
                        if (m.getResponse().equalsIgnoreCase("true")) {
                            Toast.makeText(context, "Party Updated Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdatePartyModel> call,
                                  @NonNull Throwable t) {
                mProgressDialog.hide();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

    boolean check() {
        boolean b = true;
        if (party_name_et.getText().toString().isEmpty()) {
            party_name_et.requestFocus();
            Toast.makeText(context, "Enter Party Name", Toast.LENGTH_SHORT).show();
            b = false;
            return b;
        } else if (contact_number_et.getText().toString().isEmpty()) {
            contact_number_et.requestFocus();
            Toast.makeText(context, "Enter Contact Number", Toast.LENGTH_SHORT).show();
            b = false;
            return b;
        }
        return b;
    }
}
