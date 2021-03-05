package com.service.billbook.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.service.billbook.R;
import com.service.billbook.adapter.CounterAdapter;
import com.service.billbook.adapter.ItemListAdapter;
import com.service.billbook.model.CounterModel;
import com.service.billbook.bottomsheets.SelectCategorySheet;
import com.service.billbook.model.ItemListModel;
import com.service.billbook.network.ApiHelper;
import com.service.billbook.network.RetrofitClient;
import com.service.billbook.servicemodels.ItemListResponseModel;
import com.service.billbook.util.PrefsHelper;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CounterPosActivity extends AppCompatActivity implements SelectCategorySheet.CategoryListener {

    String from_activity, party_name = "";
    public static CounterPosActivity cpa = null;
    public RecyclerView counter_recyclerview;
    Context context;
    private CounterAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<CounterModel> item_list = new ArrayList<>();
    EditText search_edittext;
    ImageView back_image, close_imageview;
    Button save_btn, create_new_item_btn, select_category_btn;
    TextView items_tv, total_amount_tv;
    LinearLayout add_more_details_linear;
    private ApiHelper apiHelper;
    ArrayList<HashMap<String, String>> selected_product_list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_pos);
        init();
    }

    String user_id;

    void init() {
        cpa = this;
        context = this;
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        user_id = PrefsHelper.getString(context, "user_id");
        back_image = findViewById(R.id.back_image);
        close_imageview = findViewById(R.id.close_imageview);
        search_edittext = findViewById(R.id.search_edittext);
        counter_recyclerview = findViewById(R.id.counter_recyclerview);
        save_btn = findViewById(R.id.save_btn);
        items_tv = findViewById(R.id.items_tv);
        total_amount_tv = findViewById(R.id.total_amount_tv);
        create_new_item_btn = findViewById(R.id.create_new_item_btn);
        select_category_btn = findViewById(R.id.select_category_btn);
        add_more_details_linear = findViewById(R.id.add_more_details_linear);

        Intent intent = getIntent();
        from_activity = intent.getStringExtra("from_activity");
        party_name = intent.getStringExtra("party_name");
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CounterPosActivity.super.onBackPressed();
                    }
                }
        );
        search_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    close_imageview.setVisibility(View.GONE);
                } else {
                    close_imageview.setVisibility(View.VISIBLE);
                }
                filter(editable.toString());
            }
        });
        close_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        search_edittext.setText("");
                    }
                }
        );
        save_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );
        create_new_item_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CounterPosActivity.this, CreateItemActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        select_category_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SelectCategorySheet bottomSheet = new SelectCategorySheet();
                        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                    }
                }
        );
        add_more_details_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAdapter.getAllSelectedProducts(from_activity);
                        finish();
                    }
                }
        );
        selected_product_list = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("arraylist");
//        if (selected_product_list != null && selected_product_list.size() > 0) {
//            setSelectedItems();
//        }
        getItemList();
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                mAdapter.CalculateTotal();
            }
        };
        handler.postDelayed(r, 500);
//      setData();
    }

    HashMap<String, String> m = new HashMap<>();

    private void setSelectedItems() {
        int i = 0;
        while (i < selected_product_list.size()) {
            m = selected_product_list.get(i);
//          int index = Integer.parseInt(m.get("index"));
            String qty = m.get("qty");
//          int j = 0;
            item_list.get(i).setSelected_qty(qty);
            i++;
        }
        mAdapter.notifyDataSetChanged();
    }

    private void filter(String text) {
        ArrayList<CounterModel> filteredList = new ArrayList<>();
        for (CounterModel item : item_list) {
            if (item.getItem_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

    CounterModel model;

    void setData() {

        model = new CounterModel();
        model.setItem_name("Milk");
        model.setItem_rate(String.valueOf(73));
        model.setItem_stock("STOCK 3 PCS");
        model.setItem_purchase_price("₹ 50");
        model.setUnit("PCS");
        model.setBarcode("1");
        model.setSelected_qty(String.valueOf(0));
        model.setItem_sale_price(String.valueOf(73));
        item_list.add(model);

        model = new CounterModel();
        model.setItem_name("Cow Ghee 200 Ml Jar");
        model.setItem_rate(String.valueOf(73));
        model.setItem_stock("STOCK 3 PCS");
        model.setItem_purchase_price("₹ 50");
        model.setSelected_qty(String.valueOf(0));
        model.setBarcode("2");
        model.setUnit("PCS");
        model.setItem_sale_price(String.valueOf(73));
        item_list.add(model);

        model = new CounterModel();
        model.setItem_name("Cow Ghee 200 Ml Jar");
        model.setItem_rate(String.valueOf(73));
        model.setItem_stock("STOCK 3 PCS");
        model.setItem_purchase_price("₹ 50");
        model.setUnit("PCS");
        model.setBarcode("3");
        model.setSelected_qty(String.valueOf(0));
        model.setItem_sale_price(String.valueOf(73));
        item_list.add(model);

        counter_recyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new CounterAdapter(CounterPosActivity.this, item_list);
        counter_recyclerview.setLayoutManager(mLayoutManager);
        counter_recyclerview.setAdapter(mAdapter);
    }

    public void setTotal(int items, float total) {
        items_tv.setText(String.valueOf(items) + " Items");
        total_amount_tv.setText("₹ " + String.valueOf(total));

    }

    @Override
    public void onCategorySelected(String text) {

    }

    private void getItemList() {

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
        Call<ItemListResponseModel> loginCall = apiHelper.getItemList(user_id);
        loginCall.enqueue(new Callback<ItemListResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ItemListResponseModel> call,
                                   @NonNull Response<ItemListResponseModel> response) {
                mProgressDialog.hide();
                if (response.isSuccessful()) {
                    if (response != null) {
                        ItemListResponseModel m = response.body();
                        if (m.getResponse().equalsIgnoreCase("true")) {

                            CounterModel model;
                            if (m.getBody() != null && m.getBody().size() > 0) {
                                for (int i = 0; i < m.getBody().size(); i++) {
                                    model = new CounterModel();
                                    model.setItem_name(m.getBody().get(i).getStrItemName());
                                    model.setItem_purchase_price(String.valueOf(m.getBody().get(i).getDcPurchasePrice()));
                                    model.setItem_sale_price(String.valueOf(m.getBody().get(i).getDcSalePrice()));
                                    model.setItem_rate(String.valueOf(m.getBody().get(i).getDcSalePrice()));
                                    model.setItem_stock(String.valueOf(m.getBody().get(i).getDcOpeningStock()));
                                    model.setUnit(String.valueOf(m.getBody().get(i).getStrUnitName()));
                                    model.setSelected_qty(String.valueOf(0));

//                                    model.setIntLowStock(String.valueOf(m.getBody().get(i).getIntLowStock()));
//                                    model.setIntItemType(String.valueOf(m.getBody().get(i).getIntItemType()));
//                                    model.setBigintItemId(String.valueOf(m.getBody().get(i).getBigintItemId()));
//                                    model.setBigintUserId(String.valueOf(m.getBody().get(i).getBigintUserId()));
//                                    model.setDcMRP(String.valueOf(m.getBody().get(i).getDcMRP()));
//                                    model.setStrHSNCode(String.valueOf(m.getBody().get(i).getStrHSNCode()));
//                                    model.setIntGst(String.valueOf(m.getBody().get(i).getIntGst()));
//                                      model.setBitisPurchaseTaxInclude(String.valueOf(m.getBody().get(i).getBitisPurchaseTaxInclude()));
//                                    model.setBitisSaleTaxInclude(String.valueOf(m.getBody().get(i).getBitisSaleTaxInclude()));
//                                    model.setStrItemCode(String.valueOf(m.getBody().get(i).getStrItemCode()));
//                                    model.setBitIsAdditionalColumn(String.valueOf(m.getBody().get(i).getBitIsAdditionalColumn()));
//                                    model.setBigintCategoryId(String.valueOf(m.getBody().get(i).getBigintCategoryId()));
//                                    model.setNumber_of_items(String.valueOf(m.getBody().get(i).getNumber_of_items()));
//                                  model.setCategoryName(m.getBody().get(i).getStrCategoryName());
                                    model.setItem_stock(String.valueOf(m.getBody().get(i).getStockvalue()));
//                                  model.setItem_description(m.getBody().get(i).getStrDescription());
                                    item_list.add(model);
                                }
                                counter_recyclerview.setHasFixedSize(true);
                                mLayoutManager = new LinearLayoutManager(context);
                                mAdapter = new CounterAdapter(context, item_list);
                                counter_recyclerview.setLayoutManager(mLayoutManager);
                                counter_recyclerview.setAdapter(mAdapter);
                                if (selected_product_list != null && selected_product_list.size() > 0) {
                                    setSelectedItems();
                                }
                            }
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ItemListResponseModel> call,
                                  @NonNull Throwable t) {
                mProgressDialog.hide();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }
}