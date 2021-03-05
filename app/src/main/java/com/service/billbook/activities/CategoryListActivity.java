package com.service.billbook.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.service.billbook.R;
import com.service.billbook.adapter.CategoryListAdapter;
import com.service.billbook.model.CategoryListModel;
import com.service.billbook.network.ApiHelper;
import com.service.billbook.network.RetrofitClient;
import com.service.billbook.servicemodels.CreateCategoryModel;
import com.service.billbook.bottomsheets.CreateCategorySheet;
import com.service.billbook.util.PrefsHelper;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryListActivity extends AppCompatActivity implements CreateCategorySheet.CreateCategoryListener {

    Context context;
    RecyclerView category_list_recyclerview;
    ImageView back_image;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<CategoryListModel> category_list = new ArrayList<>();
    CardView create_new_category_card;
    private ApiHelper apiHelper;
    CreateCategorySheet bottomSheet;

    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        init();
    }

    CategoryListModel m;

    void init() {
        context = this;
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        category_list_recyclerview = findViewById(R.id.category_list_recyclerview);
        back_image = findViewById(R.id.back_image);
        create_new_category_card = findViewById(R.id.create_new_category_card);
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CategoryListActivity.super.onBackPressed();
                    }
                }
        );
        create_new_category_card.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheet = new CreateCategorySheet("create", "");
                        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                    }
                }
        );
//        m = new CategoryListModel();
//        m.setCategory_name("Grains");
//        m.setNumber_of_items("0");
//        category_list.add(m);
//
//        m = new CategoryListModel();
//        m.setCategory_name("Oil");
//        m.setNumber_of_items("0");
//        category_list.add(m);
//
//        category_list_recyclerview.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(context);
//        mAdapter = new CategoryListAdapter(context, category_list);
//        category_list_recyclerview.setLayoutManager(mLayoutManager);
//        category_list_recyclerview.setAdapter(mAdapter);
        user_id = PrefsHelper.getString(context, "user_id");
        getAllCategories();
    }


    private void getAllCategories() {
        category_list.clear();
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
        Call<com.service.billbook.servicemodels.CategoryListModel> loginCall = apiHelper.getCategoryList(user_id);
        loginCall.enqueue(new Callback<com.service.billbook.servicemodels.CategoryListModel>() {
            @Override
            public void onResponse(@NonNull Call<com.service.billbook.servicemodels.CategoryListModel> call,
                                   @NonNull Response<com.service.billbook.servicemodels.CategoryListModel> response) {
                mProgressDialog.hide();
                if (response.isSuccessful()) {

                    if (response != null) {
                        com.service.billbook.servicemodels.CategoryListModel m = response.body();
                        if (m.getResponse().equalsIgnoreCase("true")) {
                            CategoryListModel model;

                            if (m.getBody() != null) {
                                for (int i = 0; i < m.getBody().size(); i++) {
                                    model = new CategoryListModel();
                                    model.setCategory_name(m.getBody().get(i).getStrCategoryName());
                                    model.setCategory_id(String.valueOf(m.getBody().get(i).getBigintCategoryId()));
                                    model.setNumber_of_items(String.valueOf(m.getBody().get(i).getNumber_of_items()));
                                    category_list.add(model);
//                                category_type_list.add(m.getBody().get(i).getStrCategoryName());
//                                category_id_map.put(m.getBody().get(i).getStrCategoryName(), String.valueOf(m.getBody().get(i).getBigintCategoryId()));
                                }
                            }

                            category_list_recyclerview.setHasFixedSize(true);
                            mLayoutManager = new LinearLayoutManager(context);
                            mAdapter = new CategoryListAdapter(context, category_list);
                            category_list_recyclerview.setLayoutManager(mLayoutManager);
                            category_list_recyclerview.setAdapter(mAdapter);
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<com.service.billbook.servicemodels.CategoryListModel> call,
                                  @NonNull Throwable t) {
                mProgressDialog.hide();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }


    @Override
    public void onCreateCategory(String text) {
        CreateCategory(text);
//      CreateCategoryWithRetrofot(text);
    }

    private void CreateCategory(String category_name) {
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
        HashMap<String, String> map = new HashMap();
        map.put("strCategoryName", category_name);
        Call<CreateCategoryModel> loginCall = apiHelper.CreateCategory(user_id, category_name);
        loginCall.enqueue(new Callback<CreateCategoryModel>() {
            @Override
            public void onResponse(@NonNull Call<CreateCategoryModel> call,
                                   @NonNull Response<CreateCategoryModel> response) {
                mProgressDialog.hide();
                if (response.isSuccessful()) {

                    if (response != null) {
                        CreateCategoryModel m = response.body();
                        if (m.getResponse().equalsIgnoreCase("true")) {
                            Toast.makeText(context, "Category Created Successfully", Toast.LENGTH_SHORT).show();
                            bottomSheet.dismiss();
                            getAllCategories();
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CreateCategoryModel> call,
                                  @NonNull Throwable t) {
                mProgressDialog.hide();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

    /*   public void CreateCategory(String category_name) {
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
           String url = "http://192.168.29.156/api/HaxtaxAPI/Create-Category?Uid=sX26eLg7T35vpsOfca0AWA==";
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
                                   Toast.makeText(context, "Category Created Successfully", Toast.LENGTH_SHORT).show();
                                   bottomSheet.dismiss();
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
                           mProgressDialog.hide();
                           error.printStackTrace();
                           Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   }) {
               @Override
               protected Map<String, String> getParams() throws AuthFailureError {

                   Map<String, String> params = new HashMap<>();
                   params.put("strCategoryName", category_name);
                   Log.e("post_ads_params", params.toString());
                   return params;
               }

               *//*
     * Here we are passing image by renaming it with a unique name
     *//*

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(10 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 0));
        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
*/
}