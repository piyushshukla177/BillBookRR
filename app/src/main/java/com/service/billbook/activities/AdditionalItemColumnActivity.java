package com.service.billbook.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.service.billbook.R;
import com.service.billbook.adapter.CustomFieldAdapter;
import com.service.billbook.model.CustomFieldModel;
import com.service.billbook.network.ApiHelper;
import com.service.billbook.network.RetrofitClient;
import com.service.billbook.servicemodels.DeleteSettingsModel;
import com.service.billbook.servicemodels.EncodeModel;
import com.service.billbook.servicemodels.GetAdditionalSettingsModel;
import com.service.billbook.servicemodels.SetAdditionalSettingsModel;
import com.service.billbook.util.PrefsHelper;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdditionalItemColumnActivity extends AppCompatActivity {

    RecyclerView custom_field_recyclerview;
    TextView add_custom_field_et;
    ImageView back_image;
    LinearLayout custom_linear;
    Button save_custom_field_btn, set_custom_fields_btn;
    EditText custom_field_et;
    Context context;
    private CustomFieldAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<CustomFieldModel> custom_field_list = new ArrayList<>();
    private ApiHelper apiHelper;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_item_column);
        init();
    }

    void init() {
        context = this;
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        user_id = PrefsHelper.getString(context, "user_id");
        add_custom_field_et = findViewById(R.id.add_custom_field_et);
        back_image = findViewById(R.id.back_image);
        custom_linear = findViewById(R.id.custom_linear);
        custom_field_recyclerview = findViewById(R.id.custom_field_recyclerview);
        save_custom_field_btn = findViewById(R.id.save_custom_field_btn);
        custom_field_et = findViewById(R.id.custom_field_et);
        set_custom_fields_btn = findViewById(R.id.set_custom_fields_btn);
        add_custom_field_et.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        add_custom_field_et.setVisibility(View.GONE);
                        custom_linear.setVisibility(View.VISIBLE);
                    }
                }
        );
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AdditionalItemColumnActivity.super.onBackPressed();
                    }
                }
        );
        save_custom_field_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!custom_field_et.getText().toString().isEmpty()) {
//                            CustomFieldModel m = new CustomFieldModel();
//                            m.setFieldName(custom_field_et.getText().toString());
//                            custom_field_list.add(m);
//                            custom_field_et.setText("");
//                            if (custom_field_list.size() > 0) {
//                                setRecylerview();
//                            }
                            String xml = "<dataset><datatable><strColumnName>" + custom_field_et.getText().toString() + "</strColumnName></datatable></dataset>";
                            custom_field_et.setText("");
                            SaveAdditionalSettings(xml);

                        } else {
                            Toast.makeText(context, "Enter Field Name", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        set_custom_fields_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CreateItemActivity.cii.custom_fields_list.clear();
                        CreateItemActivity.cii.getAdditionalSettings();
                        finish();
//                        if (custom_field_list.size() > 0) {
//                            String xml = "";
//                            for (int i = 0; i < custom_field_list.size(); i++) {
//                                CustomFieldModel m = custom_field_list.get(i);
//                                xml = xml + "<dataset><datatable><strColumnName>" + m.getFieldName() + "</strColumnName></datatable></dataset>";
//                            }
//                            SaveAdditionalSettings(xml);
//                        }
                    }
                }
        );
        getAdditionalSettings();
    }

    public void setRecylerview() {
        custom_field_recyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new CustomFieldAdapter(context, custom_field_list);
        custom_field_recyclerview.setLayoutManager(mLayoutManager);
        custom_field_recyclerview.setAdapter(mAdapter);
        custom_linear.setVisibility(View.GONE);
        add_custom_field_et.setVisibility(View.VISIBLE);
        if (custom_field_list.size() > 0) {
            set_custom_fields_btn.setVisibility(View.VISIBLE);
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
//                        String x = m.getResponse();
                        boolean b = m.getResponse().equalsIgnoreCase("true");
                        if (b) {
//                            CreateItemActivity.cii.custom_fields_list.clear();
//                            CreateItemActivity.cii.getAdditionalSettings();
//                            finish();
                            getAdditionalSettings();
//                            CreateItemActivity.cii.custom_fields_list = custom_field_list;
//                            CreateItemActivity.cii.createCustomFields();
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

    private void getAdditionalSettings() {
        custom_field_list.clear();
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
                            CustomFieldModel model;
                            if (m.getBody().getAdditionalColumn().get_AdditionalList() != null) {
                                for (int i = 0; i < m.getBody().getAdditionalColumn().get_AdditionalList().size(); i++) {
                                    model = new CustomFieldModel();
                                    model.setFieldName(m.getBody().getAdditionalColumn().get_AdditionalList().get(i).getStrColumnName());
                                    model.setFieldId(String.valueOf(m.getBody().getAdditionalColumn().get_AdditionalList().get(i).getBigintAdditionalId()));
                                    custom_field_list.add(model);
                                }
                            }
                            setRecylerview();
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

    //delete column
    private void DeleteColumn(String encodedCoumnId) {
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
        Call<DeleteSettingsModel> loginCall = apiHelper.DeleteSettings(user_id, encodedCoumnId);
        loginCall.enqueue(new Callback<DeleteSettingsModel>() {
            @Override
            public void onResponse(@NonNull Call<DeleteSettingsModel> call,
                                   @NonNull Response<DeleteSettingsModel> response) {
                mProgressDialog.hide();
                if (response.isSuccessful()) {

                    if (response != null) {
                        DeleteSettingsModel m = response.body();
                        if (m.getResponse().equalsIgnoreCase("true")) {
                            getAdditionalSettings();
                        }
//                        else {
//                            Toast.makeText(AdditionalItemColumnActivity.this, "", Toast.LENGTH_SHORT).show();
//                        }
                    }
                } else {
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DeleteSettingsModel> call,
                                  @NonNull Throwable t) {
                mProgressDialog.hide();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

    private void EncodeString(String str) {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Please Wait...");
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
                            DeleteColumn(m.getBody().getText());
                        } else {
                            Toast.makeText(AdditionalItemColumnActivity.this, "Enter valid Mobile Number", Toast.LENGTH_SHORT).show();
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
//       return encoded_value;
    }

    public void DeleteCustomColumn(String column_id, int index) {
        if (column_id != null) {
            //if column is saved in db then
            EncodeString(column_id);
        } else {
            custom_field_list.remove(index);
            mAdapter.notifyDataSetChanged();
        }
        if (custom_field_list.size() == 0) {
            set_custom_fields_btn.setVisibility(View.GONE);
        }
    }
}
