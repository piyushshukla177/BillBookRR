package com.service.billbook.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.service.billbook.R;
import com.service.billbook.model.CustomFieldModel;
import com.service.billbook.model.DynamicEdittextValuesModel;
import com.service.billbook.servicemodels.AddItemModel;
import com.service.billbook.servicemodels.CategoryListModel;
import com.service.billbook.servicemodels.GetAdditionalSettingsModel;
import com.service.billbook.servicemodels.GetAllDropDownModel;
import com.service.billbook.network.ApiHelper;
import com.service.billbook.network.RetrofitClient;
import com.service.billbook.network.VolleyMultipartRequest;
import com.service.billbook.servicemodels.SetAdditionalSettingsModel;
import com.service.billbook.util.FileUtils;
import com.service.billbook.util.PrefsHelper;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateItemActivity extends AppCompatActivity {

    String user_id;
    private ApiHelper apiHelper;
    Context context;
    public static CreateItemActivity cii = null;
    public ArrayList<CustomFieldModel> custom_fields_list = new ArrayList();
    Spinner gst_spinner, product_category_spinner, unit_spinner;
    Switch stock_warning_switch;
    EditText item_name_et, sale_price_et, purchase_price_et, mrp_et, opeining_stock_et, as_of_date_et, item_code_et, add_remark_et, hsn_et, low_stock_et;
    RadioButton price_with_tax_radio, price_without_tax_radio_radio, product_radio, service_radio, purchase_price_radio, sale_price_radio;
    RelativeLayout save_relative, save_and_new_relative;
    LinearLayout low_qty_linear, online_store_linear, bulk_upload_linear;
    View stock_war_view;
    TextView add_gst_tax_tv, add_custom_field_tv;
    LinearLayout add_gst_tv_linear, gst_linear;

    ImageView back_image, close_drawer_imageview, product_image_imageview, clear_imageview, product_image_imageview2, clear_imageview2, product_image_imageview3, clear_imageview3, settings_imageview;
    ArrayList gst_tax_list = new ArrayList();
    ArrayList category_type_list = new ArrayList();
    ArrayList unit_list = new ArrayList();

    LinearLayout linearLayoutForm, additional_items_column_linear;
    String[] appPermissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    Switch add_mrp_to_invoice_switch, set_wholesale_price_switch, scan_barcode_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);
        init();
    }

    Uri uri_1, uri_2, uri_3;

    void init() {
        cii = this;
        context = this;
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        checkPermissions();
        user_id = PrefsHelper.getString(context, "user_id");
        unit_spinner = findViewById(R.id.unit_spinner);
        item_name_et = findViewById(R.id.item_name_et);
        sale_price_et = findViewById(R.id.sale_price_et);
        purchase_price_et = findViewById(R.id.purchase_price_et);
        low_stock_et = findViewById(R.id.low_stock_et);
        mrp_et = findViewById(R.id.mrp_et);
        as_of_date_et = findViewById(R.id.as_of_date_et);
        item_code_et = findViewById(R.id.item_code_et);
        add_remark_et = findViewById(R.id.add_remark_et);
        opeining_stock_et = findViewById(R.id.opeining_stock_et);
        hsn_et = findViewById(R.id.hsn_et);
        save_and_new_relative = findViewById(R.id.save_and_new_relative);
        save_relative = findViewById(R.id.save_relative);
        price_without_tax_radio_radio = findViewById(R.id.price_without_tax_radio_radio);
        price_with_tax_radio = findViewById(R.id.price_with_tax_radio);
        gst_spinner = findViewById(R.id.gst_spinner);
        stock_warning_switch = findViewById(R.id.stock_warning_switch);
        low_qty_linear = findViewById(R.id.low_qty_linear);
        stock_war_view = findViewById(R.id.stock_war_view);
        add_gst_tax_tv = findViewById(R.id.add_gst_tax_tv);
        add_gst_tv_linear = findViewById(R.id.add_gst_tv_linear);
        gst_linear = findViewById(R.id.gst_linear);
        back_image = findViewById(R.id.back_image);
        add_custom_field_tv = findViewById(R.id.add_custom_field_tv);
        product_image_imageview = findViewById(R.id.product_image_imageview);
        clear_imageview = findViewById(R.id.clear_imageview);
        product_image_imageview2 = findViewById(R.id.product_image_imageview2);
        clear_imageview2 = findViewById(R.id.clear_imageview2);
        product_image_imageview3 = findViewById(R.id.product_image_imageview3);
        clear_imageview3 = findViewById(R.id.clear_imageview3);
        linearLayoutForm = findViewById(R.id.linearLayoutForm);
        product_category_spinner = findViewById(R.id.product_category_spinner);
        product_radio = findViewById(R.id.product_radio);
        service_radio = findViewById(R.id.service_radio);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        settings_imageview = findViewById(R.id.settings_imageview);
        close_drawer_imageview = findViewById(R.id.close_drawer_imageview);
        online_store_linear = findViewById(R.id.online_store_linear);
        bulk_upload_linear = findViewById(R.id.bulk_upload_linear);
        additional_items_column_linear = findViewById(R.id.additional_items_column_linear);
        //settings variavbles
        add_mrp_to_invoice_switch = findViewById(R.id.add_mrp_to_invoice_switch);
        set_wholesale_price_switch = findViewById(R.id.set_wholesale_price_switch);
        scan_barcode_switch = findViewById(R.id.scan_barcode_switch);
        purchase_price_radio = findViewById(R.id.purchase_price_radio);
        sale_price_radio = findViewById(R.id.sale_price_radio);

        online_store_linear.setVisibility(View.GONE);
        bulk_upload_linear.setVisibility(View.GONE);

        stock_warning_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked) {
                    low_qty_linear.setVisibility(View.VISIBLE);
                    stock_war_view.setVisibility(View.VISIBLE);
                } else {
                    low_qty_linear.setVisibility(View.GONE);
                    stock_war_view.setVisibility(View.GONE);
                }
            }
        });
        add_gst_tax_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gst_linear.setVisibility(View.VISIBLE);
                        add_gst_tv_linear.setVisibility(View.GONE);
                    }
                }
        );
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CreateItemActivity.super.onBackPressed();
                    }
                }
        );
        add_custom_field_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CreateItemActivity.this, AdditionalItemColumnActivity.class);
                        intent.putExtra("arraylist", custom_fields_list);
                        startActivity(intent);
                    }
                }
        );
        product_image_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkPermissions()) {
                            TedBottomPicker.with(CreateItemActivity.this)
                                    .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                                        @Override
                                        public void onImageSelected(Uri uri) {
                                            uri_1 = uri;
                                            imageFile1 = new File(uri.getPath());
                                            product_image_imageview.setImageURI(uri);
                                            clear_imageview.setVisibility(View.VISIBLE);
                                        }
                                    });
                        }
                    }
                }
        );
        clear_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        product_image_imageview.setImageResource(R.drawable.add_image);
                        clear_imageview.setVisibility(View.GONE);
                    }
                }
        );
        product_image_imageview2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkPermissions()) {
                            TedBottomPicker.with(CreateItemActivity.this)
                                    .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                                        @Override
                                        public void onImageSelected(Uri uri) {
                                            uri_2 = uri;
                                            imageFile2 = new File(uri.getPath());
                                            product_image_imageview2.setImageURI(uri);
                                            clear_imageview2.setVisibility(View.VISIBLE);
                                        }
                                    });
                        }
                    }
                }
        );
        clear_imageview2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        product_image_imageview2.setImageResource(R.drawable.add_image);
                        clear_imageview2.setVisibility(View.GONE);
                    }
                }
        );
        product_image_imageview3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkPermissions()) {
                            TedBottomPicker.with(CreateItemActivity.this)
                                    .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                                        @Override
                                        public void onImageSelected(Uri uri) {
                                            uri_3 = uri;
                                            imageFile3 = new File(uri.getPath());
                                            product_image_imageview3.setImageURI(uri);
                                            clear_imageview3.setVisibility(View.VISIBLE);
                                        }
                                    });
                        }
                    }
                }
        );
        clear_imageview3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        product_image_imageview3.setImageResource(R.drawable.add_image);
                        clear_imageview3.setVisibility(View.GONE);
                    }
                }
        );
        as_of_date_et.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePicker(as_of_date_et);
                    }
                }
        );
        save_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
//                            uploadFile();
                            if (check()) {
                                getTestValues(linearLayoutForm);
//                                Save();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
        );
        save_and_new_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        uploadFile();
                        if (check()) {
                            getTestValues(linearLayoutForm);
//                            Save();
                        }
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
        additional_items_column_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CreateItemActivity.this, AdditionalItemColumnActivity.class);
                        startActivity(intent);
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

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                R.layout.spinner_layout, category_type_list);
        adapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_layout);
        product_category_spinner.setAdapter(adapter1);

        product_category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//      settings action
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

        getAllDropDowns();
        getAllCategories();
        getAdditionalSettings();
    }

    public void createCustomFields() {
        int i = 0;
        linearLayoutForm.removeAllViews();
        while (i < custom_fields_list.size()) {
            final LinearLayout linear_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.custom_layout, null);
            //newView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 0);
            linear_layout.setLayoutParams(params);
            TextInputLayout textInputLayout = linear_layout.findViewById(R.id.text_input_layout);

            EditText edt = (EditText) linear_layout.findViewById(R.id.dynamic_edittext);
            CustomFieldModel m = custom_fields_list.get(i);
            textInputLayout.setHint(m.getFieldName());
            linearLayoutForm.addView(linear_layout);
            i++;
        }
    }

    public int PERMISSION_CODE = 100;

    //check all permissions
    public boolean checkPermissions() {
        List<String> lsitPermissionsNeeded = new ArrayList<>();
        for (String perm : appPermissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                lsitPermissionsNeeded.add(perm);
            }
        }
        //check for non granted permissions
        if (!lsitPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, lsitPermissionsNeeded.toArray(new String[lsitPermissionsNeeded.size()]), PERMISSION_CODE);
            return false;
        }
        //app have all permissions proceed ahead
        return true;
    }

    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    String todate = null, fromdate = null;

    public void datePicker(final EditText date) {
        calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String value = convertMonth(month);
                        if (value != (null)) {
                            String edit = String.valueOf(date);

                            String[] separated = edit.split("/");
                            String variable = separated[1];

                            if (variable.equals("edt_to_date}")) {
                                todate = (year + "-" + month + "-" + day);
                            } else {
                                fromdate = (year + "-" + month + "-" + day);
                            }
                            //date.setText(day + "-" + month + 1  + "-" + year);
                            date.setText(day + "-" + value + "-" + year);

                            if ((fromdate != null) && (todate != null)) {
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                                Date date1 = null;
                                Date date2 = null;
                                try {
                                    date1 = format.parse(fromdate);
                                    date2 = format.parse(todate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public String convertMonth(int month) {
        String value = null;
        if (month == 1) {
            value = "Jan";
        } else if (month == 2) {
            value = "Feb";
        } else if (month == 3) {
            value = "Mar";
        } else if (month == 4) {
            value = "Apr";
        } else if (month == 5) {
            value = "May";
        } else if (month == 6) {
            value = "Jun";
        } else if (month == 7) {
            value = "Jul";
        } else if (month == 8) {
            value = "Aug";
        } else if (month == 9) {
            value = "Sep";
        } else if (month == 10) {
            value = "Oct";
        } else if (month == 11) {
            value = "Nov";
        } else if (month == 12) {
            value = "Dec";
        }
        return value;
    }

    private MultipartBody.Part prepareImagePart(String path, String partName) {
        File file = new File(path);
        RequestBody requestbody = RequestBody.create(MediaType.parse(getContentResolver().getType(Uri.fromFile(file))), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestbody);
    }

    public String getURLForResource(int resourceId) {
        //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId).toString();
    }

    public File imageFile1, imageFile2, imageFile3;
    String str_xml;

    public void Save() {
        str_xml = "";
        if (values_list.size() > 0) {
            str_xml = "<AdditionalData>";
            for (int i = 0; i < values_list.size(); i++) {
                DynamicEdittextValuesModel m = values_list.get(i);
                str_xml = str_xml + "<AdditionalTable>" + "<strColumnName>" + m.getColumn_name() + "</strColumnName>" + "<strColumnValue>" + m.getColumn_value() + "</strColumnValue>" + "<bigintAdditionalId>" + m.getField_id() + "</bigintAdditionalId>" + "</AdditionalTable>";
            }
            str_xml = str_xml + "</AdditionalData >";
        }
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Saving Please Wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnCancelListener(new Dialog.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // DO SOME STUFF HERE
            }
        });
        mProgressDialog.show();
        String url = "http://192.168.29.156/api/HaxtaxAPI/Add-item?uid=" + user_id;
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            mProgressDialog.hide();
                            JSONObject obj = new JSONObject(new String(response.data));
                            Log.e("response", response.toString());
                            if (obj.getString("response").equalsIgnoreCase("true")) {
                                Toast.makeText(context, "Item Created Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            mProgressDialog.hide();
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String statusCode = String.valueOf(error.networkResponse.statusCode);
                        mProgressDialog.hide();
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String item_type = String.valueOf(1);
                if (service_radio.isChecked()) {
                    item_type = String.valueOf(2);
                }
                Map<String, String> params = new HashMap<>();
//              params.put("bigintItemId", String.valueOf(0));
                params.put("strItemName", item_name_et.getText().toString());
                params.put("dcSalePrice", sale_price_et.getText().toString());
                params.put("intPriceUnit", unit_value_map.get(unit_spinner.getSelectedItem().toString()));
                params.put("IntSecondaryUnit", String.valueOf(0));
                params.put("intItemType", item_type);
                params.put("bigintCategoryId", category_id_map.get(product_category_spinner.getSelectedItem().toString()));
                params.put("dcPurchasePrice", purchase_price_et.getText().toString());
                params.put("dcMRP", mrp_et.getText().toString());
//              params.put("stockvalue", opeining_stock_et.getText().toString());
                params.put("dcOpeningStock", opeining_stock_et.getText().toString());
                params.put("intLowStock", low_stock_et.getText().toString());
                params.put("strItemCode", item_code_et.getText().toString());
                params.put("strDescription", add_remark_et.getText().toString());
                if (price_with_tax_radio.isChecked()) {
                    params.put("bitisPurchaseTaxInclude", String.valueOf(true));
                    params.put("bitisSaleTaxInclude", String.valueOf(true));
                } else {
                    params.put("bitisPurchaseTaxInclude", String.valueOf(false));
                    params.put("bitisSaleTaxInclude", String.valueOf(false));
                }
                params.put("strHSNCode", hsn_et.getText().toString());
                params.put("intGst", gst_id_map.get(gst_spinner.getSelectedItem().toString()));
                params.put("xml", str_xml);
                if (custom_fields_list != null && custom_fields_list.size() > 0) {
                    params.put("bitIsAdditionalColumn", String.valueOf(true));
                } else {
                    params.put("bitIsAdditionalColumn", String.valueOf(false));
                }
                Log.e("post_ads_params", params.toString());
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();

                DataPart dp1 = null, dp2 = null, dp3 = null;

                if (imageFile1 != null) {
                    Bitmap test_image_bitmap = BitmapFactory.decodeFile(imageFile1.getAbsolutePath());
                    dp1 = new DataPart(imagename + ".png", getFileDataFromDrawable(test_image_bitmap));
                    params.put("ProfileImageA", dp1);
                }
                if (imageFile2 != null) {
                    Bitmap test_image_bitmap = BitmapFactory.decodeFile(imageFile2.getAbsolutePath());
                    dp2 = new DataPart(imagename + ".png", getFileDataFromDrawable(test_image_bitmap));
                    params.put("ProfileImageB", dp2);
                }
                if (imageFile3 != null) {
                    Bitmap test_image_bitmap = BitmapFactory.decodeFile(imageFile3.getAbsolutePath());
                    dp3 = new DataPart(imagename + ".png", getFileDataFromDrawable(test_image_bitmap));
                    params.put("ProfileImageC", dp3);
                }
                Log.e("photo_params", params.toString());
                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(10 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 0));
        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    private void SaveWithRetrofit() {
        str_xml = "";
        if (values_list.size() > 0) {
            str_xml = "<AdditionalData>";
            for (int i = 0; i < values_list.size(); i++) {
                DynamicEdittextValuesModel m = values_list.get(i);
                str_xml = str_xml + "<AdditionalTable>" + "<strColumnName>" + m.getColumn_name() + "</strColumnName>" + "<strColumnValue>" + m.getColumn_value() + "</strColumnValue>" + "<bigintAdditionalId>" + m.getField_id() + "</bigintAdditionalId>" + "</AdditionalTable>";
            }
            str_xml = str_xml + "</AdditionalData >";
        }
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Saving Please Wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnCancelListener(new Dialog.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // DO SOME STUFF HERE
            }
        });
        mProgressDialog.show();
        String item_type = String.valueOf(1);
        if (service_radio.isChecked()) {
            item_type = String.valueOf(2);
        }
        //MultipartBody.Part ProfileImageA = prepareFilePart("ProfileImageA", uri_1);
        File file1, file2, file3;
        RequestBody requestFile1, requestFile2, requestFile3;
        MultipartBody.Part ProfileImageA = null, ProfileImageB = null, ProfileImageC = null;

        if (uri_1 != null) {
            File reportFile = new File(uri_1.getPath());
            RequestBody requestFile = RequestBody.create(
                    MediaType.parse(reportFile.getAbsolutePath()), reportFile);
            ProfileImageA = MultipartBody.Part.createFormData("ProfileImageA",
                    reportFile.getName(), requestFile);
        }
        if (uri_2 != null) {
            File reportFile = new File(uri_2.getPath());
            RequestBody requestFile = RequestBody.create(
                    MediaType.parse(reportFile.getAbsolutePath()), reportFile);
            ProfileImageB = MultipartBody.Part.createFormData("ProfileImageB",
                    reportFile.getName(), requestFile);
        }
        if (uri_3 != null) {
            File reportFile = new File(uri_3.getPath());
            RequestBody requestFile = RequestBody.create(
                    MediaType.parse(reportFile.getAbsolutePath()), reportFile);
            ProfileImageC = MultipartBody.Part.createFormData("ProfileImageC",
                    reportFile.getName(), requestFile);
        }

        // create a map of data to pass along
        RequestBody strItemName = createPartFromString(item_name_et.getText().toString());
        RequestBody dcSalePrice = createPartFromString(sale_price_et.getText().toString());
        RequestBody intPriceUnit = createPartFromString(unit_value_map.get(unit_spinner.getSelectedItem().toString()));
        RequestBody IntSecondaryUnit = createPartFromString(String.valueOf(0));
        RequestBody intItemType = createPartFromString(item_type);
        RequestBody bigintCategoryId = createPartFromString(category_id_map.get(product_category_spinner.getSelectedItem().toString()));
        RequestBody dcPurchasePrice = createPartFromString(purchase_price_et.getText().toString());
        RequestBody dcMRP = createPartFromString(mrp_et.getText().toString());
        RequestBody stockvalue = createPartFromString(opeining_stock_et.getText().toString());
        RequestBody intLowStock = createPartFromString(low_stock_et.getText().toString());
        RequestBody strItemCode = createPartFromString(item_code_et.getText().toString());
        RequestBody strDescription = createPartFromString(add_remark_et.getText().toString());
        RequestBody xml = createPartFromString(str_xml);

        RequestBody bitisPurchaseTaxInclude = null;
        RequestBody bitisSaleTaxInclude = null;
        if (price_with_tax_radio.isChecked()) {
            bitisPurchaseTaxInclude = createPartFromString(String.valueOf(true));
            bitisSaleTaxInclude = createPartFromString(String.valueOf(true));
        } else {
            bitisPurchaseTaxInclude = createPartFromString(String.valueOf(false));
            bitisSaleTaxInclude = createPartFromString(String.valueOf(false));
        }

        RequestBody strHSNCode = createPartFromString(hsn_et.getText().toString());
        RequestBody intGst = createPartFromString(gst_id_map.get(gst_spinner.getSelectedItem().toString()));
        RequestBody bigintUserId = createPartFromString(user_id);
        RequestBody bitIsAdditionalColumn = createPartFromString(String.valueOf(false));

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("strItemName", strItemName);
        map.put("dcSalePrice", dcSalePrice);
        map.put("intPriceUnit", intPriceUnit);
        map.put("IntSecondaryUnit", IntSecondaryUnit);
        map.put("intItemType", intItemType);
        map.put("bigintCategoryId", bigintCategoryId);
        map.put("dcPurchasePrice", dcPurchasePrice);
        map.put("dcMRP", dcMRP);
        map.put("stockvalue", stockvalue);
        map.put("intLowStock", intLowStock);
        map.put("strItemCode", strItemCode);
        map.put("strDescription", strDescription);
        map.put("bitisPurchaseTaxInclude", bitisPurchaseTaxInclude);
        map.put("bitisSaleTaxInclude", bitisSaleTaxInclude);
        map.put("strHSNCode", strHSNCode);
        map.put("intGst", intGst);
        map.put("bigintUserId", bigintUserId);
        map.put("bitIsAdditionalColumn", bitIsAdditionalColumn);
        map.put("xml", xml);

        //creating a call and calling the upload image method
        Call<AddItemModel> call = apiHelper.addItem(user_id, map, ProfileImageA, ProfileImageB, ProfileImageC);

        //finally performing the call
        call.enqueue(new Callback<AddItemModel>() {
            @Override
            public void onResponse(Call<AddItemModel> call, Response<AddItemModel> response) {
                mProgressDialog.hide();
                if (response.isSuccessful()) {
                    AddItemModel m = response.body();
                    if (m.getResponse().equalsIgnoreCase("true")) {
                        Log.e("response", response.toString());
                        Toast.makeText(getApplicationContext(), "Item Created Successfully...", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    AddItemModel m = response.body();
                    Toast.makeText(getApplicationContext(), m.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddItemModel> call, Throwable t) {
                mProgressDialog.hide();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    HashMap<String, String> category_id_map = new HashMap<>();
    HashMap<String, String> unit_value_map = new HashMap<>();
    HashMap<String, String> gst_id_map = new HashMap<>();

    private void getAllCategories() {
        category_type_list.clear();
        category_id_map.clear();
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
        Call<CategoryListModel> loginCall = apiHelper.getCategoryList(user_id);
        loginCall.enqueue(new Callback<CategoryListModel>() {
            @Override
            public void onResponse(@NonNull Call<CategoryListModel> call,
                                   @NonNull Response<CategoryListModel> response) {
                mProgressDialog.hide();
                if (response.isSuccessful()) {

                    if (response != null) {
                        CategoryListModel m = response.body();
                        if (m.getResponse().equalsIgnoreCase("true")) {
                            category_type_list.add("Select Category");

                            if (m.getBody() != null) {
                                for (int i = 0; i < m.getBody().size(); i++) {
                                    category_type_list.add(m.getBody().get(i).getStrCategoryName());
                                    category_id_map.put(m.getBody().get(i).getStrCategoryName(), String.valueOf(m.getBody().get(i).getBigintCategoryId()));
                                }
                            }
                            ArrayAdapter<String> category_adapter = new ArrayAdapter<>(context,
                                    R.layout.spinner_layout, category_type_list);
                            category_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_layout);
                            product_category_spinner.setAdapter(category_adapter);
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoryListModel> call,
                                  @NonNull Throwable t) {
                mProgressDialog.hide();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

    private void getAllDropDowns() {
        unit_list.clear();
        unit_value_map.clear();
        gst_tax_list.clear();
        gst_id_map.clear();
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
        Call<GetAllDropDownModel> loginCall = apiHelper.getAllDropDownList(user_id);
        loginCall.enqueue(new Callback<GetAllDropDownModel>() {
            @Override
            public void onResponse(@NonNull Call<GetAllDropDownModel> call,
                                   @NonNull Response<GetAllDropDownModel> response) {
                mProgressDialog.hide();
                if (response.isSuccessful()) {

                    if (response != null) {
                        GetAllDropDownModel m = response.body();
                        if (m.getResponse().equalsIgnoreCase("true")) {
                            unit_list.add("Select Unit");
                            if (m.getBody() != null) {
                                for (int i = 0; i < m.getBody().getUnitType().size(); i++) {
                                    unit_list.add(m.getBody().getUnitType().get(i).getStrUnitName());
                                    unit_value_map.put(m.getBody().getUnitType().get(i).getStrUnitName(), String.valueOf(m.getBody().getUnitType().get(i).getIntUnitId()));
                                }
                            }
                            Log.e("unit", unit_list.size() + " data " + unit_list.toString());
                            ArrayAdapter<String> category_adapter = new ArrayAdapter<>(context,
                                    R.layout.spinner_layout, unit_list);
                            category_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_layout);
                            unit_spinner.setAdapter(category_adapter);

                            gst_tax_list.add("Select Tax");
                            if (m.getBody() != null) {
                                for (int j = 0; j < m.getBody().getGstType().size(); j++) {
                                    gst_tax_list.add(m.getBody().getGstType().get(j).getStrGstName());
                                    gst_id_map.put(m.getBody().getGstType().get(j).getStrGstName(), String.valueOf(m.getBody().getGstType().get(j).getIntGstId()));
                                }
                            }
                            ArrayAdapter<String> gst_adapter = new ArrayAdapter<>(context,
                                    R.layout.spinner_layout, gst_tax_list);
                            gst_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_layout);
                            gst_spinner.setAdapter(gst_adapter);
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetAllDropDownModel> call,
                                  @NonNull Throwable t) {
                mProgressDialog.hide();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

    public void getAdditionalSettings() {

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
                            CustomFieldModel model;
                            if (m.getBody().getAdditionalColumn().get_AdditionalList() != null) {
                                for (int i = 0; i < m.getBody().getAdditionalColumn().get_AdditionalList().size(); i++) {
                                    model = new CustomFieldModel();
                                    model.setFieldName(m.getBody().getAdditionalColumn().get_AdditionalList().get(i).getStrColumnName());
                                    model.setFieldId(String.valueOf(m.getBody().getAdditionalColumn().get_AdditionalList().get(i).getBigintAdditionalId()));
                                    custom_fields_list.add(model);
                                }
                            }
                            if (custom_fields_list != null && custom_fields_list.size() > 0) {
                                createCustomFields();
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
            public void onFailure(@NonNull Call<GetAdditionalSettingsModel> call,
                                  @NonNull Throwable t) {
                mProgressDialog.hide();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    boolean check() {
        boolean b = true;
        if (item_name_et.getText().toString().isEmpty()) {
            item_name_et.requestFocus();
            Toast.makeText(context, "Enter Item name", Toast.LENGTH_SHORT).show();
            b = false;
            return b;
        } else if (sale_price_et.getText().toString().isEmpty()) {
            sale_price_et.requestFocus();
            Toast.makeText(context, "Enter Sale Price", Toast.LENGTH_SHORT).show();
            b = false;
            return b;
        } else if (purchase_price_et.getText().toString().isEmpty()) {
            purchase_price_et.requestFocus();
            Toast.makeText(context, "Enter Purchase Price", Toast.LENGTH_SHORT).show();
            b = false;
            return b;
        } else if (mrp_et.getText().toString().isEmpty()) {
            mrp_et.requestFocus();
            Toast.makeText(context, "Enter MRP", Toast.LENGTH_SHORT).show();
            b = false;
            return b;
        } else if (unit_spinner.getSelectedItem().toString().equalsIgnoreCase("Select Unit")) {
            Toast.makeText(context, "Select Unit", Toast.LENGTH_SHORT).show();
            b = false;
            return b;
        } else if (stock_warning_switch.isChecked()) {
            if (low_stock_et.getText().toString().isEmpty()) {
                low_stock_et.requestFocus();
                Toast.makeText(context, "Enter Low Stock Quantity", Toast.LENGTH_SHORT).show();
                b = false;
                return b;
            }
        }
        return b;
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }

//    @NonNull
//    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
//        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
//        // use the FileUtils to get the actual file by uri
//        File file = FileUtils.getFile(this, fileUri);
//        // create RequestBody instance from file
//        RequestBody requestFile =
//                RequestBody.create(
//                        MediaType.parse(getContentResolver().getType(fileUri)),
//                        file
//                );
//        // MultipartBody.Part is used to send also the actual file name
//        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
//    }

    ArrayList<DynamicEdittextValuesModel> values_list = new ArrayList<>();

    public void getTestValues(LinearLayout parentLinearLayout) {

        if (parentLinearLayout != null) {

            DynamicEdittextValuesModel m;
            for (int i = 0; i < parentLinearLayout.getChildCount(); i++) {
                m = new DynamicEdittextValuesModel();
                EditText edt = parentLinearLayout.getChildAt(i).findViewById(R.id.dynamic_edittext);
                String hint = edt.getHint().toString();
                String value = edt.getText().toString();
                m.setColumn_name(hint);
                m.setColumn_value(value);
                String column_id = custom_fields_list.get(i).getFieldId();
                m.setField_id(column_id);
                values_list.add(m);
            }
            Save();
//            SaveWithRetrofit();
            Log.e("value_list", values_list.toString());
        }
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
}
