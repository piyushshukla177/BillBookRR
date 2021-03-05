package com.service.billbook.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.service.billbook.R;
import com.service.billbook.adapter.CreateBIllProductListAdapter;
import com.service.billbook.bottomsheets.EditInvoiceNumberSheet;
import com.service.billbook.model.CreateBillItemListModel;
import com.service.billbook.network.ApiHelper;
import com.service.billbook.network.RetrofitClient;
import com.service.billbook.persistent.AdditionalInfoTable;
import com.service.billbook.persistent.DatabaseClient;
import com.service.billbook.persistent.InvoiceDb;
import com.service.billbook.servicemodels.PartyListResponseModel;
import com.service.billbook.util.PrefsHelper;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePurchaseActivity extends AppCompatActivity {

    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private ApiHelper apiHelper;
    static InvoiceDb db;
    String intent_status = "";

    MaterialBetterSpinner party_name_spinner;
    ImageView back_image;
    Button add_items_btn;
    TextView add_more_items_tv, subtotal_tv, total_items_tv, invoice_number_tv, invoice_date_tv, additional_charges_tv, round_off_tv, notes_tv, discount_tv, edit_invoice_tv, balance_amount_tv;
    RelativeLayout additional_relative, discount_relative, round_off_relative, notes_relative, sub_total_relative, cash_received_relative, enter_cash_received_relative, balance_amount_relative;
    LinearLayout addition_charges_linear, round_off_linear, notes_linear, discount_linear;
    RadioButton round_plus_radio, round_minus_radio;
    RecyclerView product_list_recycle;
    EditText discount_rs_et, round_off_et, additional_charges_et, grand_total_et, notes_et, cash_received_et, charge_name_et, discount_percentage_et;
    ImageView additional_cross_imageview, roundoff_cross_imageview, notes_imageview, discount_cross_imageview;
    RadioGroup round_off_radio_group;
    Context context;
    ArrayList partyList = new ArrayList();
    String user_id;
    ArrayList<HashMap<String, String>> selected_product_list;
    ArrayList<CreateBillItemListModel> item_list = new ArrayList<>();

    private static String selected_party;
    private CreateBIllProductListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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
        intent_status = "";
        party_name_spinner = findViewById(R.id.party_name_spinner);
        back_image = findViewById(R.id.back_image);
        add_items_btn = findViewById(R.id.add_items_btn);
        add_more_items_tv = findViewById(R.id.add_more_items_tv);
        additional_relative = findViewById(R.id.additional_relative);
        discount_relative = findViewById(R.id.discount_relative);
        round_off_relative = findViewById(R.id.round_off_relative);
        notes_relative = findViewById(R.id.notes_relative);
        sub_total_relative = findViewById(R.id.sub_total_relative);
        cash_received_relative = findViewById(R.id.cash_received_relative);
        round_plus_radio = findViewById(R.id.round_plus_radio);
        product_list_recycle = findViewById(R.id.product_list_recycle);
        subtotal_tv = findViewById(R.id.subtotal_tv);
        total_items_tv = findViewById(R.id.total_items_tv);
        discount_rs_et = findViewById(R.id.discount_rs_et);
        round_off_et = findViewById(R.id.round_off_et);
        additional_charges_et = findViewById(R.id.additional_charges_et);
        grand_total_et = findViewById(R.id.grand_total_et);
        round_minus_radio = findViewById(R.id.round_minus_radio);
        notes_et = findViewById(R.id.notes_et);
        invoice_number_tv = findViewById(R.id.invoice_number_tv);
        cash_received_et = findViewById(R.id.cash_received_et);
        charge_name_et = findViewById(R.id.charge_name_et);
        discount_percentage_et = findViewById(R.id.discount_percentage_et);
        invoice_date_tv = findViewById(R.id.invoice_date_tv);
        additional_charges_tv = findViewById(R.id.additional_charges_tv);
        addition_charges_linear = findViewById(R.id.addition_charges_linear);
        additional_cross_imageview = findViewById(R.id.additional_cross_imageview);
        roundoff_cross_imageview = findViewById(R.id.roundoff_cross_imageview);
        notes_imageview = findViewById(R.id.notes_imageview);
        round_off_tv = findViewById(R.id.round_off_tv);
        round_off_linear = findViewById(R.id.round_off_linear);
        notes_tv = findViewById(R.id.notes_tv);
        notes_linear = findViewById(R.id.notes_linear);
        discount_tv = findViewById(R.id.discount_tv);
        discount_linear = findViewById(R.id.discount_linear);
        discount_cross_imageview = findViewById(R.id.discount_cross_imageview);
        enter_cash_received_relative = findViewById(R.id.enter_cash_received_relative);
        balance_amount_relative = findViewById(R.id.balance_amount_relative);
        edit_invoice_tv = findViewById(R.id.edit_invoice_tv);
        balance_amount_tv = findViewById(R.id.balance_amount_tv);
        round_off_radio_group = findViewById(R.id.round_off_radio_group);

        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!party_name_spinner.getText().toString().isEmpty()) {
                            new AlertDialog.Builder(CreatePurchaseActivity.this)
                                    .setMessage("Are you sure you want to exit ? ")
                                    .setCancelable(false)
                                    .setPositiveButton("Discard Bill", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            ClearAllData myTask = new ClearAllData();
                                            myTask.execute();
                                            CreatePurchaseActivity.this.finish();
                                        }
                                    })
                                    .setNegativeButton("Continue", null)
                                    .show();
                        } else {
                            CreatePurchaseActivity.super.onBackPressed();
                        }
                    }
                }
        );
        add_items_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent_status = "";
                        if (!party_name_spinner.getText().toString().isEmpty()) {
                            intent_status = "counter";
                            Intent intent = new Intent(CreatePurchaseActivity.this, CounterPosActivity.class);
                            intent.putExtra("from_activity", "CreatePurchaseActivity");
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(context, "Please Select Party", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        add_more_items_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent_status = "";
                        if (!party_name_spinner.getText().toString().isEmpty()) {
                            intent_status = "counter";
                            Intent intent = new Intent(CreatePurchaseActivity.this, CounterPosActivity.class);
                            intent.putExtra("arraylist", selected_product_list);
                            intent.putExtra("from_activity", "CreateBillActivity");
                            startActivity(intent);
                            finish();
                            SetAdditionalInfoDb myTask = new SetAdditionalInfoDb();
                            myTask.execute();
                        } else {
                            Toast.makeText(context, "Please Select Party", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        selected_product_list = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("arraylist");
        if (selected_product_list != null && selected_product_list.size() > 0) {
            add_items_btn.setVisibility(View.GONE);
            add_more_items_tv.setVisibility(View.VISIBLE);
            additional_relative.setVisibility(View.VISIBLE);
            discount_relative.setVisibility(View.VISIBLE);
            round_off_relative.setVisibility(View.VISIBLE);
            notes_relative.setVisibility(View.VISIBLE);
            sub_total_relative.setVisibility(View.VISIBLE);
            cash_received_relative.setVisibility(View.VISIBLE);
            setProductListRecycleView();
        } else {
            add_items_btn.setVisibility(View.VISIBLE);
        }
        party_name_spinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                selected_party = party_name_spinner.getText().toString();
            }
        });
        if (selected_party != null && !selected_party.isEmpty()) {
            party_name_spinner.setText(selected_party);
        }
        additional_charges_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (addition_charges_linear.getVisibility() == View.GONE) {
                            additional_charges_tv.setVisibility(View.GONE);
                            addition_charges_linear.setVisibility(View.VISIBLE);
                        } else {
                            additional_charges_tv.setVisibility(View.VISIBLE);
                            addition_charges_linear.setVisibility(View.GONE);
                        }
                    }
                }
        );
        additional_cross_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        additional_charges_tv.setVisibility(View.VISIBLE);
                        addition_charges_linear.setVisibility(View.GONE);
                        additional_charges_et.setText(String.valueOf(0));
                        charge_name_et.setText("");
                        SetTotal();
                    }
                }
        );
        roundoff_cross_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        round_off_tv.setVisibility(View.VISIBLE);
                        round_off_linear.setVisibility(View.GONE);
                        round_off_et.setText(String.valueOf(0));
                        discount_percentage_et.setText(String.valueOf(0));
                        SetTotal();
                    }
                }
        );
        notes_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notes_tv.setVisibility(View.VISIBLE);
                        notes_linear.setVisibility(View.GONE);
                        notes_et.setText("");
                    }
                }
        );
        discount_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (discount_linear.getVisibility() == View.GONE) {
                            discount_tv.setVisibility(View.GONE);
                            discount_linear.setVisibility(View.VISIBLE);

                        } else {
                            discount_tv.setVisibility(View.VISIBLE);
                            discount_linear.setVisibility(View.GONE);
                        }
                    }
                }
        );
        notes_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (notes_linear.getVisibility() == View.GONE) {
                            notes_tv.setVisibility(View.GONE);
                            notes_linear.setVisibility(View.VISIBLE);
                        } else {
                            notes_tv.setVisibility(View.VISIBLE);
                            notes_linear.setVisibility(View.GONE);
                        }
                    }
                }
        );
        discount_cross_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        discount_tv.setVisibility(View.VISIBLE);
                        discount_linear.setVisibility(View.GONE);
                        discount_rs_et.setText(String.valueOf(0));
                        discount_percentage_et.setText(String.valueOf(0));

                        SetTotal();
                    }
                }
        );
        round_off_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (round_off_linear.getVisibility() == View.GONE) {
                            round_off_tv.setVisibility(View.GONE);
                            round_off_linear.setVisibility(View.VISIBLE);
                        } else {
                            round_off_tv.setVisibility(View.VISIBLE);
                            round_off_linear.setVisibility(View.GONE);
                        }
                    }
                }
        );
        cash_received_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (enter_cash_received_relative.getVisibility() == View.GONE) {
                            cash_received_relative.setVisibility(View.GONE);
                            enter_cash_received_relative.setVisibility(View.VISIBLE);
                            balance_amount_relative.setVisibility(View.VISIBLE);
                            cash_received_et.setText(grand_total_et.getText());
                        } else {
                            cash_received_relative.setVisibility(View.VISIBLE);
                            enter_cash_received_relative.setVisibility(View.GONE);
                            balance_amount_relative.setVisibility(View.GONE);
                        }
                    }
                }
        );
        edit_invoice_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditInvoiceNumberSheet bottomSheet = new EditInvoiceNumberSheet();
                        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                    }
                }
        );

        additional_charges_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String edited_amt = s.toString();
                try {
                    SetTotal();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        discount_rs_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (discount_rs_et.hasFocus()) {
                    String edited_amt = s.toString();
                    try {
                        if (edited_amt == null || edited_amt.toString().isEmpty()) {
                            edited_amt = String.valueOf(0);
                        }
                        float discount_rupee = Float.parseFloat(edited_amt);
                        float total_amount = Float.parseFloat(subtotal_tv.getText().toString());
                        float discount_percent = (discount_rupee * 100) / total_amount;
                        discount_percentage_et.setText(String.valueOf(df2.format(discount_percent)));
                        SetTotal();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        discount_percentage_et.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (discount_percentage_et.hasFocus()) {
                            String edited_amt = s.toString();
                            try {
                                if (edited_amt == null || edited_amt.toString().isEmpty()) {
                                    edited_amt = String.valueOf(0);
                                }
                                float discount_percent = Float.parseFloat(edited_amt);
                                float total_amount = Float.parseFloat(subtotal_tv.getText().toString());
                                float discount_rupee = (total_amount * discount_percent) / 100;
                                discount_rs_et.setText(String.valueOf(df2.format(discount_rupee)));
                                SetTotal();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
        );
        round_off_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String edited_amt = s.toString();
                try {
                    SetTotal();
//                    if (!edited_amt.toString().isEmpty()) {

//                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        cash_received_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String edited_amt = s.toString();
                if (edited_amt == null || edited_amt.toString().isEmpty()) {
                    edited_amt = String.valueOf(0);
                }
                float cash_received_amount = Float.parseFloat(edited_amt);
                float balance_amount = Float.parseFloat(grand_total_et.getText().toString()) - cash_received_amount;
                balance_amount_tv.setText(String.valueOf(df2.format(balance_amount)));
            }
        });

        round_off_radio_group.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.round_plus_radio) {
                            SetTotal();
                        } else {
                            SetTotal();
                        }
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

    private void setProductListRecycleView() {
        int i = 0;
        round_plus_radio.setChecked(true);
        HashMap<String, String> m;
        CreateBillItemListModel model;
        float total = 0;
        int items = 0;
        while (i < selected_product_list.size()) {
            m = selected_product_list.get(i);
            model = new CreateBillItemListModel();
            model.setName(m.get("name"));
            model.setUnit(m.get("unit"));
            model.setRate(m.get("rate"));
            model.setQty(m.get("qty"));
            model.setIndex(m.get("index"));

            total = total + Integer.parseInt(m.get("qty")) * Float.parseFloat(m.get("rate"));
            items = items + Integer.parseInt(m.get("qty"));
            item_list.add(model);
            i++;
        }
        product_list_recycle.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new CreateBIllProductListAdapter(context, item_list);
        product_list_recycle.setLayoutManager(mLayoutManager);
        product_list_recycle.setAdapter(mAdapter);
        subtotal_tv.setText(String.valueOf(total));
        total_items_tv.setText("ITEMS ( " + items + " )");
        SetTotal();
    }

    public void SetTotal() {
        try {
            float sub_total = 0;
            if (!subtotal_tv.getText().toString().isEmpty()) {
                sub_total = Float.parseFloat(subtotal_tv.getText().toString());
            }
            String discount_rs = discount_rs_et.getText().toString();
            if (discount_rs == null || discount_rs.isEmpty()) {
                discount_rs = String.valueOf(0);
            }
            float discount_rupee = Float.parseFloat(discount_rs);

            String additional_charges = additional_charges_et.getText().toString();
            if (additional_charges == null || additional_charges.isEmpty()) {
                additional_charges = String.valueOf(0);
            }
            float addi_charges = Float.parseFloat(additional_charges);

            String round_off = round_off_et.getText().toString();
            if (round_off == null || round_off.isEmpty()) {
                round_off = String.valueOf(0);
            }
            float round_off_amt = Float.parseFloat(round_off);

            float final_amt = 0;
            if (round_minus_radio.isChecked()) {
                final_amt = (sub_total + addi_charges) - discount_rupee - round_off_amt;
            } else {
                final_amt = (sub_total + addi_charges + round_off_amt) - discount_rupee;
            }
            grand_total_et.setText(String.valueOf(final_amt));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private class SetAdditionalInfoDb extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {

            String round_type = "";
            if (round_minus_radio.isChecked()) {
                round_type = "minus";
            } else {
                round_type = "plus";
            }
            db.AdditionalDao().deleteAllAdditionalInfo();
            db.AdditionalDao().insert(new AdditionalInfoTable(invoice_number_tv.getText().toString(), party_name_spinner.getText().toString(), invoice_date_tv.getText().toString(), additional_charges_et.getText().toString(), charge_name_et.getText().toString(), discount_percentage_et.getText().toString(),
                    discount_rs_et.getText().toString(), round_off_et.getText().toString(), round_type, notes_et.getText().toString(), cash_received_et.getText().toString()));
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(String... text) {

        }
    }

    private class ClearAllData extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            selected_party = "";
            db.AdditionalDao().deleteAllAdditionalInfo();
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(String... text) {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (intent_status.isEmpty()) {
            ClearAllData myTask = new ClearAllData();
            myTask.execute();
        }
    }

    @Override
    public void onBackPressed() {
//      super.onBackPressed();
        if (!party_name_spinner.getText().toString().isEmpty()) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit ? ")
                    .setCancelable(false)
                    .setPositiveButton("Discard Bill", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ClearAllData myTask = new ClearAllData();
                            myTask.execute();
                            finish();
                        }
                    })
                    .setNegativeButton("Continue", null)
                    .show();
        } else {
            super.onBackPressed();
        }
    }
}
