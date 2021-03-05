package com.service.billbook.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.service.billbook.R;
import com.service.billbook.activities.BulkUploadItemActivity;
import com.service.billbook.activities.CreateItemActivity;
import com.service.billbook.activities.OnlineStoreActivity;
import com.service.billbook.adapter.ItemListAdapter;
import com.service.billbook.model.ItemListModel;
import com.service.billbook.network.ApiHelper;
import com.service.billbook.network.RetrofitClient;
import com.service.billbook.servicemodels.GetAdditionalSettingsModel;
import com.service.billbook.servicemodels.ItemListResponseModel;
import com.service.billbook.servicemodels.SetAdditionalSettingsModel;
import com.service.billbook.bottomsheets.FilterItemBottomSheet;
import com.service.billbook.util.PrefsHelper;
import com.service.billbook.bottomsheets.SelectCategorySheet;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsFragment extends Fragment {

    Context context;
    RecyclerView items_recyclerview;
    private ItemListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<ItemListModel> item_list = new ArrayList<>();
    CardView create_new_item_card;
    RadioButton low_stock_radio, select_category_radio, purchase_price_radio, sale_price_radio;
    RadioGroup radio_group;
    EditText search_edittext;
    ImageView close_imageview, settings_imageview, close_drawer_imageview;
    RelativeLayout items_filter_relative;
    LinearLayout additional_items_column_linear, online_store_linear, bulk_upload_linear;
    private ApiHelper apiHelper;
    Switch add_mrp_to_invoice_switch, set_wholesale_price_switch, scan_barcode_switch;
    public static ItemsFragment ii = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_items, container, false);
        init(root);
        return root;
    }

    String user_id;

    void init(View root) {
        ii = this;
        context = getActivity();
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        user_id = PrefsHelper.getString(context, "user_id");
        radio_group = root.findViewById(R.id.radio_group);
        low_stock_radio = root.findViewById(R.id.low_stock_radio);
        select_category_radio = root.findViewById(R.id.select_category_radio);
        items_recyclerview = root.findViewById(R.id.items_recyclerview);
        create_new_item_card = root.findViewById(R.id.create_new_item_card);
        search_edittext = root.findViewById(R.id.search_edittext);
        close_imageview = root.findViewById(R.id.close_imageview);
        items_filter_relative = root.findViewById(R.id.items_filter_relative);
        online_store_linear = root.findViewById(R.id.online_store_linear);
        DrawerLayout drawer = root.findViewById(R.id.drawer_layout);
        settings_imageview = root.findViewById(R.id.settings_imageview);
        bulk_upload_linear = root.findViewById(R.id.bulk_upload_linear);
        additional_items_column_linear = root.findViewById(R.id.additional_items_column_linear);
        close_drawer_imageview = root.findViewById(R.id.close_drawer_imageview);
        purchase_price_radio = root.findViewById(R.id.purchase_price_radio);
        sale_price_radio = root.findViewById(R.id.sale_price_radio);
        add_mrp_to_invoice_switch = root.findViewById(R.id.add_mrp_to_invoice_switch);
        set_wholesale_price_switch = root.findViewById(R.id.set_wholesale_price_switch);
        scan_barcode_switch = root.findViewById(R.id.scan_barcode_switch);
        additional_items_column_linear.setVisibility(View.GONE);
        create_new_item_card.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), CreateItemActivity.class);
                        startActivity(intent);
                    }
                }
        );
        getItemList();

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.low_stock_radio:
                        FilterByLowStock();
                        break;
                    case R.id.select_category_radio:
                }
            }
        });
        select_category_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SelectCategorySheet bottomSheet = new SelectCategorySheet();
                        bottomSheet.show(getActivity().getSupportFragmentManager(), "exampleBottomSheet");
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
        items_filter_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FilterItemBottomSheet bottomSheet = new FilterItemBottomSheet();
                        bottomSheet.show(getActivity().getSupportFragmentManager(), "exampleBottomSheet");
                    }
                }
        );
        settings_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                            drawer.closeDrawer(Gravity.RIGHT);
                        } else {
                            drawer.openDrawer(Gravity.RIGHT);
                        }
                    }
                }
        );
        close_drawer_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                            drawer.closeDrawer(Gravity.RIGHT);
                        }
                    }
                }
        );
//        additional_items_column_linear.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(getActivity(), AdditionalItemColumnActivity.class);
//                        startActivity(intent);
//                    }
//                }
//        );
        online_store_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), OnlineStoreActivity.class);
                        startActivity(intent);
                    }
                }
        );
        bulk_upload_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), BulkUploadItemActivity.class);
                        startActivity(intent);
                    }
                }
        );
        add_mrp_to_invoice_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String xml = "<dataset><datatable>" +
                            "<Idname>2</Idname><IdValue>1</IdValue></datatable></dataset>";
                    SaveAdditionalSettings(xml);
                } else {
                    String xml = "<dataset><datatable>" +
                            "<Idname>2</Idname><IdValue>0</IdValue></datatable></dataset>";
                    SaveAdditionalSettings(xml);
                }
            }
        });
        set_wholesale_price_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String xml = "<dataset><datatable>" +
                            "<Idname>3</Idname><IdValue>1</IdValue></datatable></dataset>";
                    SaveAdditionalSettings(xml);
                } else {
                    String xml = "<dataset><datatable>" +
                            "<Idname>3</Idname><IdValue>0</IdValue></datatable></dataset>";
                    SaveAdditionalSettings(xml);
                }
            }
        });

        purchase_price_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String xml = "<dataset><datatable>" +
                                "<Idname>1</Idname><IdValue>1</IdValue></datatable></dataset>";
                        SaveAdditionalSettings(xml);
                    }
                }
        );
        sale_price_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String xml = "<dataset><datatable>" +
                                "<Idname>1</Idname><IdValue>2</IdValue></datatable></dataset>";
                        SaveAdditionalSettings(xml);
                    }
                }
        );
        getAdditionalSettings();
    }

    private void filter(String text) {
        ArrayList<ItemListModel> filteredList = new ArrayList<>();
        for (ItemListModel item : item_list) {
            if (item.getItem_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

    private void getAdditionalSettings() {

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
        Call<GetAdditionalSettingsModel> loginCall = apiHelper.getAdditionalSetting(user_id);
        loginCall.enqueue(new Callback<GetAdditionalSettingsModel>() {
            @Override
            public void onResponse(@NonNull Call<GetAdditionalSettingsModel> call,
                                   @NonNull Response<GetAdditionalSettingsModel> response) {
                mProgressDialog.hide();
                if (response.isSuccessful()) {
                    if (response != null) {
                        GetAdditionalSettingsModel m = response.body();
                        if (m.getResponse().equalsIgnoreCase("true")) {
                            for (int i = 0; i < m.getBody().getSettingList().get_SettingList().size(); i++) {

                                if (i == 0) {
                                    String setting_value = m.getBody().getSettingList().get_SettingList().get(i).getSettingValue();
                                    if (setting_value.equalsIgnoreCase("1")) {
                                        purchase_price_radio.setChecked(true);
                                    } else {
                                        sale_price_radio.setChecked(true);
                                    }
                                }
                                if (i == 1) {
                                    String setting_value = m.getBody().getSettingList().get_SettingList().get(i).getSettingValue();
                                    if (setting_value.equalsIgnoreCase("1")) {
                                        add_mrp_to_invoice_switch.setChecked(true);
                                    } else {
                                        add_mrp_to_invoice_switch.setChecked(false);
                                    }
                                }
                                if (i == 2) {

                                    String setting_value = m.getBody().getSettingList().get_SettingList().get(i).getSettingValue();
                                    if (setting_value.equalsIgnoreCase("1")) {
                                        set_wholesale_price_switch.setChecked(true);
                                    } else {
                                        set_wholesale_price_switch.setChecked(false);
                                    }
                                }
                            }
//                            CustomFieldModel model;
//                            if (m.getBody().getAdditionalColumn().get_AdditionalList() != null) {
//                                for (int i = 0; i < m.getBody().getAdditionalColumn().get_AdditionalList().size(); i++) {
//                                    model = new CustomFieldModel();
//                                    model.setFieldName(m.getBody().getAdditionalColumn().get_AdditionalList().get(i).getStrColumnName());
//                                    model.setFieldId(String.valueOf(m.getBody().getAdditionalColumn().get_AdditionalList().get(i).getBigintAdditionalId()));
//                                    custom_fields_list.add(model);
//                                }
//                            }
//                            if (custom_fields_list != null && custom_fields_list.size() > 0) {
//                                createCustomFields();
//                            }
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetAdditionalSettingsModel> call,
                                  @NonNull Throwable t) {
                mProgressDialog.hide();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

    private void SaveAdditionalSettings(String xml) {

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
        Call<SetAdditionalSettingsModel> loginCall = apiHelper.AdditionalSetting(user_id, xml);
        loginCall.enqueue(new Callback<SetAdditionalSettingsModel>() {
            @Override
            public void onResponse(@NonNull Call<SetAdditionalSettingsModel> call,
                                   @NonNull Response<SetAdditionalSettingsModel> response) {
                mProgressDialog.hide();
                if (response.isSuccessful()) {

                    if (response != null) {
                        SetAdditionalSettingsModel m = response.body();
                        if (m.getResponse().equalsIgnoreCase("true")) {


                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SetAdditionalSettingsModel> call,
                                  @NonNull Throwable t) {
                mProgressDialog.hide();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

    int qty = 2;

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

                            ItemListModel model;
                            if (m.getBody() != null && m.getBody().size() > 0) {
                                for (int i = 0; i < m.getBody().size(); i++) {
                                    model = new ItemListModel();
                                    model.setItem_name(m.getBody().get(i).getStrItemName());
//                                  model.setItem_weight(m.getBody().get(i).get);
                                    model.setPurchase_price(String.valueOf(m.getBody().get(i).getDcPurchasePrice()));
                                    model.setSale_price(String.valueOf(m.getBody().get(i).getDcSalePrice()));
                                    model.setQuantity(String.valueOf(String.valueOf(m.getBody().get(i).getDcOpeningStock())));
                                    model.setIntLowStock(String.valueOf(m.getBody().get(i).getIntLowStock()));
                                    model.setIntItemType(String.valueOf(m.getBody().get(i).getIntItemType()));
                                    model.setBigintItemId(String.valueOf(m.getBody().get(i).getBigintItemId()));
                                    model.setBigintUserId(String.valueOf(m.getBody().get(i).getBigintUserId()));
                                    model.setDcMRP(String.valueOf(m.getBody().get(i).getDcMRP()));
                                    model.setStrHSNCode(String.valueOf(m.getBody().get(i).getStrHSNCode()));
                                    model.setIntGst(String.valueOf(m.getBody().get(i).getIntGst()));
                                    model.setUnit(String.valueOf(m.getBody().get(i).getIntPriceUnit()));
                                    model.setBitisPurchaseTaxInclude(String.valueOf(m.getBody().get(i).getBitisPurchaseTaxInclude()));
                                    model.setBitisSaleTaxInclude(String.valueOf(m.getBody().get(i).getBitisSaleTaxInclude()));
                                    model.setStrItemCode(String.valueOf(m.getBody().get(i).getStrItemCode()));
                                    model.setBitIsAdditionalColumn(String.valueOf(m.getBody().get(i).getBitIsAdditionalColumn()));
                                    model.setBigintCategoryId(String.valueOf(m.getBody().get(i).getBigintCategoryId()));
                                    model.setNumber_of_items(String.valueOf(m.getBody().get(i).getNumber_of_items()));
                                    model.setCategoryName(m.getBody().get(i).getStrCategoryName());
                                    model.setStock_value(String.valueOf(m.getBody().get(i).getStockvalue()));
                                    model.setItem_description(m.getBody().get(i).getStrDescription());
                                    model.setUnit_name(m.getBody().get(i).getStrUnitName());
                                    model.setUnit_short_name(m.getBody().get(i).getStrShortName());
                                    item_list.add(model);
                                }
                                items_recyclerview.setHasFixedSize(true);
                                mLayoutManager = new LinearLayoutManager(context);
                                mAdapter = new ItemListAdapter(context, item_list);
                                items_recyclerview.setLayoutManager(mLayoutManager);
                                items_recyclerview.setAdapter(mAdapter);
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

    void FilterByLowStock() {
        ArrayList<ItemListModel> filteredList = new ArrayList<>();
        for (ItemListModel item : item_list) {
            if (Integer.parseInt(item.getQuantity()) <= 0) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

    public String sorting = "";

    public void AppyFilter(String txt) {
        if (txt.equals("qty_low_to_high")) {
            sorting = "a";
            sortByQty();
        } else if (txt.equals("high_to_low_qty")) {
            sorting = "d";
            sortByQty();
        } else if (txt.equals("a_to_z")) {
            sorting = "a_to_z";
            sortByQty();
        } else if (txt.equals("z_to_a")) {
            sorting = "z_to_a";
            sortByQty();
        }
    }

    public void sortByQty() {
        Collections.sort(item_list);
        mAdapter = new ItemListAdapter(context, item_list);
        items_recyclerview.setLayoutManager(mLayoutManager);
        items_recyclerview.setAdapter(mAdapter);
    }
}
