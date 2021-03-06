package com.service.billbook.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.service.billbook.R;
import com.service.billbook.adapter.CreateBIllProductListAdapter;
import com.service.billbook.adapter.PartyListAdapter;
import com.service.billbook.model.CreateBillItemListModel;
import com.service.billbook.model.PartyListModel;
import com.service.billbook.network.ApiHelper;
import com.service.billbook.network.RetrofitClient;
import com.service.billbook.persistent.AdditionalInfoTable;
import com.service.billbook.persistent.DatabaseClient;
import com.service.billbook.persistent.InvoiceDb;
import com.service.billbook.servicemodels.CreateInvoiceModel;
import com.service.billbook.bottomsheets.EditInvoiceNumberSheet;
import com.service.billbook.bottomsheets.EditItemSheet;
import com.service.billbook.servicemodels.PartyListResponseModel;
import com.service.billbook.util.PrefsHelper;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import android.os.AsyncTask;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBillActivity extends AppCompatActivity implements EditInvoiceNumberSheet.EditInvoiceNumberSheetListener, EditItemSheet.EditItemListener {

    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private ApiHelper apiHelper;
    static InvoiceDb db;
    private static String selected_party;
    RecyclerView product_list_recycle;
    private CreateBIllProductListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<CreateBillItemListModel> item_list = new ArrayList<>();

    Context context;
    ImageView back_image;
    Button add_items_btn;
    MaterialBetterSpinner select_party_spinner;
    ArrayList partyList = new ArrayList();
    ArrayList<HashMap<String, String>> selected_product_list;
    LinearLayout addition_charges_linear, discount_linear, round_off_linear, notes_linear;
    TextView title_tv, additional_charges_tv, discount_tv, round_off_tv, notes_tv, add_more_items_tv, edit_invoice_tv, subtotal_tv, grand_total_et, total_items_tv, balance_amount_tv, invoice_number_tv, invoice_date_tv;
    ImageView additional_cross_imageview, discount_cross_imageview, roundoff_cross_imageview, notes_imageview;
    RelativeLayout cash_received_relative, enter_cash_received_relative, balance_amount_relative, additional_relative, discount_relative, round_off_relative, notes_relative, sub_total_relative;
    EditText additional_charges_et, discount_percentage_et, discount_rs_et, round_off_et, cash_received_et, notes_et, charge_name_et;
    RadioGroup round_off_radio_group;
    RadioButton round_minus_radio, round_plus_radio;
    String intent_status = "";

    CardView save_cardview;
    String user_id;
    String bill_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bill);
        intent_status = "";
        db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        AsyncTaskRunner myTask = new AsyncTaskRunner();
        myTask.execute();
        init();
    }

    void init() {
        context = this;
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        user_id = PrefsHelper.getString(context, "user_id");
        back_image = findViewById(R.id.back_image);
        add_items_btn = findViewById(R.id.add_items_btn);
        product_list_recycle = findViewById(R.id.product_list_recycle);
        addition_charges_linear = findViewById(R.id.addition_charges_linear);
        discount_linear = findViewById(R.id.discount_linear);
        additional_charges_tv = findViewById(R.id.additional_charges_tv);
        edit_invoice_tv = findViewById(R.id.edit_invoice_tv);
        discount_tv = findViewById(R.id.discount_tv);
        additional_cross_imageview = findViewById(R.id.additional_cross_imageview);
        discount_cross_imageview = findViewById(R.id.discount_cross_imageview);
        round_off_tv = findViewById(R.id.round_off_tv);
        round_off_linear = findViewById(R.id.round_off_linear);
        roundoff_cross_imageview = findViewById(R.id.roundoff_cross_imageview);
        notes_imageview = findViewById(R.id.notes_imageview);
        notes_tv = findViewById(R.id.notes_tv);
        notes_linear = findViewById(R.id.notes_linear);
        cash_received_relative = findViewById(R.id.cash_received_relative);
        enter_cash_received_relative = findViewById(R.id.enter_cash_received_relative);
        balance_amount_relative = findViewById(R.id.balance_amount_relative);
        additional_relative = findViewById(R.id.additional_relative);
        discount_relative = findViewById(R.id.discount_relative);
        round_off_relative = findViewById(R.id.round_off_relative);
        notes_relative = findViewById(R.id.notes_relative);
        sub_total_relative = findViewById(R.id.sub_total_relative);
        add_more_items_tv = findViewById(R.id.add_more_items_tv);
        subtotal_tv = findViewById(R.id.subtotal_tv);
        grand_total_et = findViewById(R.id.grand_total_et);
        total_items_tv = findViewById(R.id.total_items_tv);
        additional_charges_et = findViewById(R.id.additional_charges_et);
        discount_percentage_et = findViewById(R.id.discount_percentage_et);
        discount_rs_et = findViewById(R.id.discount_rs_et);
        round_off_radio_group = findViewById(R.id.round_off_radio_group);
        round_minus_radio = findViewById(R.id.round_minus_radio);
        round_plus_radio = findViewById(R.id.round_plus_radio);
        round_off_et = findViewById(R.id.round_off_et);
        cash_received_et = findViewById(R.id.cash_received_et);
        balance_amount_tv = findViewById(R.id.balance_amount_tv);
        invoice_number_tv = findViewById(R.id.invoice_number_tv);
        invoice_date_tv = findViewById(R.id.invoice_date_tv);
        notes_et = findViewById(R.id.notes_et);
        charge_name_et = findViewById(R.id.charge_name_et);
        save_cardview = findViewById(R.id.save_cardview);
        title_tv = findViewById(R.id.title_tv);

        Intent intent = getIntent();
        bill_type = intent.getStringExtra("bill_type");
        if (bill_type.equals("purchase")) {
            title_tv.setText("Create Purchase");
            invoice_number_tv.setText("Purchase #1");
        } else if (bill_type.equals("quotation")) {
            title_tv.setText(getString(R.string.Proforma_Quotation));
            invoice_number_tv.setText("Quotation #1");
        } else if (bill_type.equals("delivery_challan")) {
            title_tv.setText(getString(R.string.Delivery_Challan));
            invoice_number_tv.setText("Delivery Challan #1");
        } else if (bill_type.equals("purchase_order")) {
            title_tv.setText(getString(R.string.Purchase_Order));
            invoice_number_tv.setText("Purchase Order #1");
        } else if (bill_type.equals("sale_return")) {
            title_tv.setText(getString(R.string.Sale_Return));
            invoice_number_tv.setText("Sale Return #1");
        } else if (bill_type.equals("purchase_return")) {
            title_tv.setText("Purchase Return");
            invoice_number_tv.setText("Purchase Return #1");
        }
        save_cardview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (check()) {
                            SaveInvoice();
                        }
                    }
                }
        );
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!select_party_spinner.getText().toString().isEmpty()) {
                            new AlertDialog.Builder(CreateBillActivity.this)
                                    .setMessage("Are you sure you want to exit ? ")
                                    .setCancelable(false)
                                    .setPositiveButton("Discard Bill", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            ClearAllData myTask = new ClearAllData();
                                            myTask.execute();
                                            CreateBillActivity.this.finish();
                                        }
                                    })
                                    .setNegativeButton("Continue", null)
                                    .show();
                        } else {
                            CreateBillActivity.super.onBackPressed();
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
        add_items_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent_status = "";
                        if (!select_party_spinner.getText().toString().isEmpty()) {
                            intent_status = "counter";
                            Intent intent = new Intent(CreateBillActivity.this, CounterPosActivity.class);
                            intent.putExtra("bill_type", bill_type);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(context, "Please Select Party", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
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
        select_party_spinner = findViewById(R.id.select_party_spinner);

        select_party_spinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                selected_party = select_party_spinner.getText().toString();
            }
        });
        if (selected_party != null && !selected_party.isEmpty()) {
            select_party_spinner.setText(selected_party);
        }
        add_more_items_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent_status = "";
                        if (!select_party_spinner.getText().toString().isEmpty()) {
                            intent_status = "counter";
                            Intent intent = new Intent(CreateBillActivity.this, CounterPosActivity.class);
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

    @Override
    public void onEditInvoiceNumber(String text) {

    }

    //method to set total ater delete product from array list(recyclerview)
    public void setTotal(ArrayList<CreateBillItemListModel> list, int position) {
//      HashMap<String, String> map = selected_product_list.get(position);
//      map.put("qty", String.valueOf(0));
        selected_product_list.get(position).put("qty", String.valueOf(0));
        CreateBillItemListModel m;
        int i = 0;
        int total_qty = 0;
        float subtotal = 0;
        while (i < list.size()) {
            try {
                m = new CreateBillItemListModel();
                m = list.get(i);
                int qty = Integer.parseInt(m.getQty());
                float rate = Float.parseFloat(m.getRate());
                total_qty = total_qty + qty;
                subtotal = subtotal + (rate * qty);
                i++;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        subtotal_tv.setText(String.valueOf(subtotal));
        try {
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
                final_amt = (subtotal + addi_charges) - discount_rupee - round_off_amt;
            } else {
                final_amt = (subtotal + addi_charges + round_off_amt) - discount_rupee;
            }
            grand_total_et.setText(String.valueOf(final_amt));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {

            List<AdditionalInfoTable> list = db.AdditionalDao().getAllAdditionalInfo();
            if (list != null && list.size() > 0) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String party = list.get(0).getParty_name();
                        String addition_charges = list.get(0).getAdditional_charges();
                        String discount_rupee = list.get(0).getDiscount_rupee();
                        String discount_percent = list.get(0).getDiscount_percent();
                        String round_amount = list.get(0).getRound_off();
                        String round_type = list.get(0).getRound_type();
                        String addi_charge_name = list.get(0).getAdditional_charges_name();
                        String notes = list.get(0).getNotes();

                        if (round_type.equals("plus")) {
                            round_plus_radio.setChecked(true);
                        } else {
                            round_minus_radio.setChecked(true);
                        }
                        if (party != null && !party.isEmpty()) {
                            select_party_spinner.setText(party);
                        }
                        if (addition_charges != null && !addition_charges.isEmpty()) {
                            additional_charges_et.setText(addition_charges);
                            addition_charges_linear.setVisibility(View.VISIBLE);
                            additional_charges_tv.setVisibility(View.GONE);
                            charge_name_et.setText(addi_charge_name);
                        }

                        if (round_amount != null && !round_amount.isEmpty()) {
                            round_off_et.setText(round_amount);
                            round_off_linear.setVisibility(View.VISIBLE);
                            round_off_tv.setVisibility(View.GONE);
                        }
                        if (discount_rupee != null && !discount_rupee.isEmpty()) {
                            discount_rs_et.setText(discount_rupee);
                            discount_percentage_et.setText(discount_percent);
                            discount_linear.setVisibility(View.VISIBLE);
                            discount_tv.setVisibility(View.GONE);
                        }
                        if (notes != null && !notes.isEmpty()) {
                            notes_et.setText(notes);
                            notes_linear.setVisibility(View.VISIBLE);
                            notes_tv.setVisibility(View.GONE);
                        }
                    }
                });
            }
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
            db.AdditionalDao().insert(new AdditionalInfoTable(invoice_number_tv.getText().toString(), select_party_spinner.getText().toString(), invoice_date_tv.getText().toString(), additional_charges_et.getText().toString(), charge_name_et.getText().toString(), discount_percentage_et.getText().toString(),
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
    public void onEditItem(String text) {

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
        if (!select_party_spinner.getText().toString().isEmpty()) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit ? ")
                    .setCancelable(false)
                    .setPositiveButton("Discard Bill", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ClearAllData myTask = new ClearAllData();
                            myTask.execute();
                            CreateBillActivity.this.finish();
                        }
                    })
                    .setNegativeButton("Continue", null)
                    .show();
        } else {
            super.onBackPressed();
        }
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

    void SaveInvoice() {
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
//      HashMap<String, String> map = new HashMap();
//      map.put("strCategoryName", category_name);
        Call<CreateInvoiceModel> loginCall = apiHelper.CreateInvoice(user_id, "5fmPFyI75dnQUIGTEL84wg==", select_party_spinner.getText().toString(),
                String.valueOf(1), invoice_date_tv.getText().toString().substring(0, 10), String.valueOf(0), String.valueOf(0)
                , String.valueOf(0), String.valueOf(0), String.valueOf(0));
        loginCall.enqueue(new Callback<CreateInvoiceModel>() {
            @Override
            public void onResponse(@NonNull Call<CreateInvoiceModel> call,
                                   @NonNull Response<CreateInvoiceModel> response) {
                mProgressDialog.hide();
                if (response.isSuccessful()) {

                    if (response != null) {
                        CreateInvoiceModel m = response.body();
                        if (m.getResponse().equalsIgnoreCase("true")) {
                            Toast.makeText(context, "Invoice Created Successfully", Toast.LENGTH_SHORT).show();
//                            bottomSheet.dismiss();
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CreateInvoiceModel> call,
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
        if (select_party_spinner.getText().toString().isEmpty()) {
            b = false;
            select_party_spinner.requestFocus();
            return b;
        }
        return b;
    }

    HashMap<String, String> party_id_map = new HashMap<>();

    private void getPartyList() {
        partyList.clear();
        party_id_map.clear();
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
                                    party_id_map.put(m.getBody().get(i).getStrPartyName(), m.getBody().get(i).getBigintPartyId());
                                }
                            }
                            ArrayAdapter<String> incomeTypeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, partyList);
                            select_party_spinner.setAdapter(incomeTypeAdapter);
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
