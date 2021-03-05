package com.service.billbook.bottomsheets;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.service.billbook.R;
import com.service.billbook.activities.CategoryListActivity;
import com.service.billbook.network.ApiHelper;
import com.service.billbook.network.RetrofitClient;
import com.service.billbook.servicemodels.CategoryListModel;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectCategorySheet extends BottomSheetDialogFragment {
    private CategoryListener mListener;

    ImageView close_imageview;
    RelativeLayout manage_category_relative;
    Spinner choose_category_spinner;
    ArrayList<String> categories = new ArrayList();
    private ApiHelper apiHelper;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category_bottom_layout, container, false);

        context = getActivity();
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        choose_category_spinner = v.findViewById(R.id.choose_category_spinner);
        close_imageview = v.findViewById(R.id.close_imageview);
        manage_category_relative = v.findViewById(R.id.manage_category_relative);
        close_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        manage_category_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent intent = new Intent(getActivity(), CategoryListActivity.class);
                startActivity(intent);
            }
        });
//        categories.add("All Items");
//        categories.add("Grains");
//        categories.add("Oils");
//        categories.add("Vegetables");
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
//                R.layout.spinner_layout, categories);
//        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_layout);
//        choose_category_spinner.setAdapter(adapter);
        choose_category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mListener.onCategorySelected(categories.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getAllCategories();
        return v;
    }

    public interface CategoryListener {
        void onCategorySelected(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (CategoryListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void getAllCategories() {
        categories.clear();
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
        Call<CategoryListModel> loginCall = apiHelper.getCategoryList("yqKN2FpZqwBY6ATwzOvuvA==");
        loginCall.enqueue(new Callback<CategoryListModel>() {
            @Override
            public void onResponse(@NonNull Call<CategoryListModel> call,
                                   @NonNull Response<CategoryListModel> response) {
                mProgressDialog.hide();
                if (response.isSuccessful()) {

                    if (response != null) {
                        CategoryListModel m = response.body();
                        if (m.getResponse().equalsIgnoreCase("true")) {
                            categories.add("Select Category");

                            for (int i = 0; i < m.getBody().size(); i++) {
                                categories.add(m.getBody().get(i).getStrCategoryName());
//                                category_id_map.put(m.getBody().get(i).getStrCategoryName(), String.valueOf(m.getBody().get(i).getBigintCategoryId()));
                            }
                            ArrayAdapter<String> category_adapter = new ArrayAdapter<>(context,
                                    R.layout.spinner_layout, categories);
                            category_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_layout);
                            choose_category_spinner.setAdapter(category_adapter);
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
}
