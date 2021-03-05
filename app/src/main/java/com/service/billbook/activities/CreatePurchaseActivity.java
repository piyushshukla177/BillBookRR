package com.service.billbook.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.service.billbook.R;
import com.service.billbook.network.ApiHelper;
import com.service.billbook.network.RetrofitClient;
import com.service.billbook.persistent.DatabaseClient;
import com.service.billbook.persistent.InvoiceDb;
import com.service.billbook.servicemodels.PartyListResponseModel;
import com.service.billbook.util.PrefsHelper;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePurchaseActivity extends AppCompatActivity {

    private ApiHelper apiHelper;
    static InvoiceDb db;

    MaterialBetterSpinner party_name_spinner;
    ImageView back_image;
    Button add_items_btn;
    Context context;
    ArrayList partyList = new ArrayList();
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_purchase);
        init();
    }

    void init() {
        context = this;
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        user_id = PrefsHelper.getString(context, "user_id");

        party_name_spinner = findViewById(R.id.party_name_spinner);
        back_image = findViewById(R.id.back_image);
        add_items_btn = findViewById(R.id.add_items_btn);
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CreatePurchaseActivity.super.onBackPressed();
                    }
                }
        );
        add_items_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CreatePurchaseActivity.this, CounterPosActivity.class);
                        intent.putExtra("from_activity", "CreatePurchaseActivity");
                        startActivity(intent);
                        finish();
                    }
                }
        );
        getPartyList();
    }

    private void getPartyList() {
        partyList.clear();
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
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
        Call<PartyListResponseModel> partyListCall = apiHelper.getPartyList(user_id);
        partyListCall.enqueue(new Callback<PartyListResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<PartyListResponseModel> call,
                                   @NonNull Response<PartyListResponseModel> response) {
                mProgressDialog.hide();
                if (response.isSuccessful()) {
                    if (response != null) {
                        PartyListResponseModel m = response.body();
                        if (m.getResponse().equalsIgnoreCase("true")) {

                            if (m.getBody() != null && m.getBody().size() > 0) {
                                for (int i = 0; i < m.getBody().size(); i++) {

                                    partyList.add(m.getBody().get(i).getStrPartyName());
                                }
                            }
                            ArrayAdapter<String> incomeTypeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, partyList);
                            party_name_spinner.setAdapter(incomeTypeAdapter);
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PartyListResponseModel> call,
                                  @NonNull Throwable t) {
                mProgressDialog.hide();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }
}
