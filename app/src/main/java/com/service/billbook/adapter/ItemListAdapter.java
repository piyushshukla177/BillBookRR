package com.service.billbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.service.billbook.R;
import com.service.billbook.activities.DashboardActivity;
import com.service.billbook.activities.ItemDetailsActivity;
import com.service.billbook.model.ItemListModel;
import com.service.billbook.bottomsheets.AddStockSheet;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder> {

    private ArrayList<ItemListModel> item_list;
    private Context context;

    public class ItemListViewHolder extends RecyclerView.ViewHolder {
        public TextView first_char_tv, item_name_tv, quantity_with_unit_tv, purchase_price_tv, sale_price_tv;
        ImageView edit_qty_imageview;
        RelativeLayout rel;

        public ItemListViewHolder(View itemView) {
            super(itemView);
            rel = itemView.findViewById(R.id.rel);
            first_char_tv = itemView.findViewById(R.id.first_char_tv);
            item_name_tv = itemView.findViewById(R.id.item_name_tv);
            quantity_with_unit_tv = itemView.findViewById(R.id.quantity_with_unit_tv);
//          item_weight_tv = itemView.findViewById(R.id.item_weight_tv);
            purchase_price_tv = itemView.findViewById(R.id.purchase_price_tv);
            sale_price_tv = itemView.findViewById(R.id.sale_price_tv);
            edit_qty_imageview = itemView.findViewById(R.id.edit_qty_imageview);
        }
    }

    public ItemListAdapter(Context context, ArrayList<ItemListModel> list) {
        item_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_layout, parent, false);
        ItemListViewHolder evh = new ItemListViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemListViewHolder holder, int i) {
        final ItemListModel currentItem = item_list.get(i);
        if (!currentItem.getItem_name().isEmpty()) {
            holder.first_char_tv.setText(currentItem.getItem_name().substring(0, 1));
        }
        holder.item_name_tv.setText(currentItem.getItem_name());
        holder.quantity_with_unit_tv.setText(currentItem.getQuantity() + " " + currentItem.getUnit_name());
//      holder.item_weight_tv.setText(currentItem.getItem_weight());
        holder.purchase_price_tv.setText("₹ " + currentItem.getPurchase_price());
        holder.sale_price_tv.setText("₹ " + currentItem.getSale_price());
        holder.edit_qty_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(context, holder.edit_qty_imageview);
                        //Inflating the Popup using xml file
                        popup.getMenuInflater().inflate(R.menu.add_stock_menu, popup.getMenu());
                        //registering popup with OnMenuItemClickListener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getTitle().equals("Add Stock")) {
                                    AddStockSheet bottomSheetDialogFragment = new AddStockSheet();
                                    bottomSheetDialogFragment.stock_type = "Add Stock";
                                    bottomSheetDialogFragment.show(((DashboardActivity) context).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

                                } else if (item.getTitle().equals("Reduce Stock")) {
                                    AddStockSheet bottomSheetDialogFragment = new AddStockSheet();
                                    bottomSheetDialogFragment.stock_type = "Reduce Stock";
                                    bottomSheetDialogFragment.show(((DashboardActivity) context).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                                }
                                return true;
                            }
                        });
                        popup.show(); //showing popup menu
                    }
                }
        );
        holder.rel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ItemDetailsActivity.class);
                        intent.putExtra("category_id", currentItem.getBigintCategoryId());
                        intent.putExtra("category_name", currentItem.getCategoryName());
                        intent.putExtra("item_id", currentItem.getBigintItemId());
                        intent.putExtra("mrp", currentItem.getDcMRP());
                        intent.putExtra("item_type", currentItem.getIntItemType());
                        intent.putExtra("item_name", currentItem.getItem_name());
                        intent.putExtra("quantity", currentItem.getQuantity());
                        intent.putExtra("sale_price", currentItem.getSale_price());
                        intent.putExtra("purchase_price", currentItem.getPurchase_price());
                        intent.putExtra("item_code", currentItem.getStrItemCode());
                        intent.putExtra("hsn_code", currentItem.getStrHSNCode());
                        intent.putExtra("gst", currentItem.getIntGst());
                        intent.putExtra("unit_id", currentItem.getUnit());
                        intent.putExtra("unit", currentItem.getUnit_name());
                        intent.putExtra("number_of_items", currentItem.getNumber_of_items());
                        intent.putExtra("stock_value", currentItem.getStock_value());
                        intent.putExtra("low_stock", currentItem.getIntLowStock());
                        intent.putExtra("description", currentItem.getItem_description());
                        context.startActivity(intent);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }

    public void filterList(ArrayList<ItemListModel> filteredList) {
        item_list = filteredList;
        notifyDataSetChanged();
    }
}
