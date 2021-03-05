package com.service.billbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.service.billbook.R;
import com.service.billbook.activities.AddProductsInCategoryActivity;
import com.service.billbook.activities.CategoryListActivity;
import com.service.billbook.activities.DashboardActivity;
import com.service.billbook.bottomsheets.AddStockSheet;
import com.service.billbook.bottomsheets.CreateCategorySheet;
import com.service.billbook.model.CategoryListModel;

import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder> {
    private ArrayList<CategoryListModel> category_list;
    private Context context;

    public class CategoryListViewHolder extends RecyclerView.ViewHolder {
        public TextView grains_tv, number_of_items_tv;
        ImageView menu_imageview, add_products_imageview;

        public CategoryListViewHolder(View itemView) {
            super(itemView);
            grains_tv = itemView.findViewById(R.id.grains_tv);
            number_of_items_tv = itemView.findViewById(R.id.number_of_items_tv);
            menu_imageview = itemView.findViewById(R.id.menu_imageview);
            add_products_imageview = itemView.findViewById(R.id.add_products_imageview);
        }
    }

    public CategoryListAdapter(Context context, ArrayList<CategoryListModel> list) {
        category_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_layout, parent, false);
        CategoryListViewHolder evh = new CategoryListViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryListViewHolder holder, int i) {
        final CategoryListModel currentItem = category_list.get(i);
        holder.grains_tv.setText(currentItem.getCategory_name());
        holder.number_of_items_tv.setText(currentItem.getNumber_of_items() + " item" + "(s)");
        holder.menu_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(context, holder.menu_imageview);
                        //Inflating the Popup using xml file
                        popup.getMenuInflater().inflate(R.menu.category_popup_menu, popup.getMenu());
                        //registering popup with OnMenuItemClickListener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getTitle().equals("Edit Category")) {
                                    CreateCategorySheet bottomSheetDialogFragment = new CreateCategorySheet("edit", currentItem.getCategory_name());
                                    bottomSheetDialogFragment.show(((CategoryListActivity) context).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                                } else if (item.getTitle().equals("Delete Category")) {

                                }
                                return true;
                            }
                        });
                        popup.show(); //showing popup menu
                    }
                }
        );
        holder.add_products_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, AddProductsInCategoryActivity.class);
                        intent.putExtra("category_name", currentItem.getCategory_name());
                        intent.putExtra("category_id", currentItem.getCategory_id());
                        intent.putExtra("category_items", currentItem.getNumber_of_items());
                        context.startActivity(intent);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return category_list.size();
    }
}
