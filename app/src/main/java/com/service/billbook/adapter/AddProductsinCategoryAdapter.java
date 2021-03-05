package com.service.billbook.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.service.billbook.R;
import com.service.billbook.activities.AddProductsInCategoryActivity;
import com.service.billbook.model.AddProductsinCategoryModel;

import java.util.ArrayList;

public class AddProductsinCategoryAdapter extends RecyclerView.Adapter<AddProductsinCategoryAdapter.AddProductsinCategoryViewHolder> {

    private ArrayList<AddProductsinCategoryModel> product_list;
    private Context context;

    public class AddProductsinCategoryViewHolder extends RecyclerView.ViewHolder {
        public TextView product_name_tv, select_all_tv;
        public CheckBox select_product_checkbox;

        public AddProductsinCategoryViewHolder(View itemView) {
            super(itemView);
            product_name_tv = itemView.findViewById(R.id.product_name_tv);
            select_all_tv = itemView.findViewById(R.id.select_all_tv);
            select_product_checkbox = itemView.findViewById(R.id.select_product_checkbox);
        }
    }

    public AddProductsinCategoryAdapter(Context context, ArrayList<AddProductsinCategoryModel> list) {
        product_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AddProductsinCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_items_layout, parent, false);
        AddProductsinCategoryViewHolder evh = new AddProductsinCategoryViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final AddProductsinCategoryViewHolder holder, int i) {
        final AddProductsinCategoryModel currentItem = product_list.get(i);
        if (i == 0) {
            holder.select_all_tv.setVisibility(View.VISIBLE);
        }

        holder.product_name_tv.setText(currentItem.getProduct_name_tv());
        holder.select_product_checkbox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (i == 0) {
                            if (holder.select_product_checkbox.isChecked()) {
                                int j = 0;
                                while (j < product_list.size()) {
                                    AddProductsinCategoryViewHolder childHolder = (AddProductsinCategoryViewHolder) AddProductsInCategoryActivity.app.item_list_recyclerview.findViewHolderForLayoutPosition(j);
                                    childHolder.select_product_checkbox.setChecked(true);
                                    j++;
                                }
                            } else {
                                int j = 0;
                                while (j < product_list.size()) {
                                    AddProductsinCategoryViewHolder childHolder = (AddProductsinCategoryViewHolder) AddProductsInCategoryActivity.app.item_list_recyclerview.findViewHolderForLayoutPosition(j);
                                    childHolder.select_product_checkbox.setChecked(false);
                                    j++;
                                }
                            }
                        }

                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return product_list.size();
    }

}

