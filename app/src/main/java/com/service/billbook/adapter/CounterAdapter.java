package com.service.billbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.service.billbook.R;
import com.service.billbook.activities.CounterPosActivity;
import com.service.billbook.activities.CreateBillActivity;
import com.service.billbook.model.CounterModel;

import java.util.ArrayList;
import java.util.HashMap;

public class CounterAdapter extends RecyclerView.Adapter<CounterAdapter.CounterViewHolder> {
    private ArrayList<CounterModel> item_list;
    private Context context;

    public class CounterViewHolder extends RecyclerView.ViewHolder {
        public TextView first_char_tv, item_name_tv, item_rate_tv, stock_tv, purchase_price_tv, mrp_tv;
        public Button add_button;
        public ElegantNumberButton qty_number_button;

        public CounterViewHolder(View itemView) {
            super(itemView);
            first_char_tv = itemView.findViewById(R.id.first_char_tv);
            item_name_tv = itemView.findViewById(R.id.item_name_tv);
            item_rate_tv = itemView.findViewById(R.id.item_rate_tv);
            stock_tv = itemView.findViewById(R.id.stock_tv);
            purchase_price_tv = itemView.findViewById(R.id.purchase_price_tv);
            mrp_tv = itemView.findViewById(R.id.mrp_tv);
            add_button = itemView.findViewById(R.id.add_button);
            qty_number_button = itemView.findViewById(R.id.qty_number_button);
        }
    }

    public CounterAdapter(Context context, ArrayList<CounterModel> list) {
        item_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CounterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.counter_pos_layout, parent, false);
        CounterViewHolder evh = new CounterViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final CounterViewHolder holder, int i) {
        final CounterModel currentItem = item_list.get(i);
        if (!currentItem.getItem_name().isEmpty()) {
            holder.first_char_tv.setText(currentItem.getItem_name().substring(0, 1));
        }
        holder.item_name_tv.setText(currentItem.getItem_name());
        holder.item_rate_tv.setText("₹ " + currentItem.getItem_rate());
        holder.stock_tv.setText("STOCK " + currentItem.getItem_stock() + " " + currentItem.getUnit());
        holder.purchase_price_tv.setText("₹ " + currentItem.getItem_sale_price());
        holder.mrp_tv.setText("₹  " + currentItem.getItem_purchase_price());
        if (Integer.parseInt(currentItem.getSelected_qty()) > 0) {
            holder.qty_number_button.setVisibility(View.VISIBLE);
            holder.add_button.setVisibility(View.GONE);
            holder.qty_number_button.setNumber(currentItem.getSelected_qty());
        }
        holder.add_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.qty_number_button.getVisibility() == View.VISIBLE) {

                        } else {
                            holder.qty_number_button.setVisibility(View.VISIBLE);
                            holder.qty_number_button.setNumber(String.valueOf(1));
                            holder.add_button.setVisibility(View.GONE);
                            currentItem.setSelected_qty(String.valueOf(1));
                        }
                        CalculateTotal();
                    }
                }
        );

        holder.qty_number_button.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
//              int digit = Integer.parseInt(holder.qty_number_button.getNumber()) + 1;
                String num = String.valueOf(holder.qty_number_button.getNumber());
                if (num.equals(String.valueOf(0))) {
                    holder.qty_number_button.setVisibility(View.GONE);
                    holder.add_button.setVisibility(View.VISIBLE);
                }
                currentItem.setSelected_qty(holder.qty_number_button.getNumber());
                CalculateTotal();
            }
        });
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }

    public void filterList(ArrayList<CounterModel> filteredList) {
        item_list = filteredList;
        notifyDataSetChanged();
    }

    public void CalculateTotal() {
        float total_amount = 0;
        int items = 0;
        int j = 0;
        while (j < item_list.size()) {
            CounterAdapter.CounterViewHolder childHolder = (CounterAdapter.CounterViewHolder) CounterPosActivity.cpa.counter_recyclerview.findViewHolderForLayoutPosition(j);
            if (childHolder.qty_number_button.getVisibility() == View.VISIBLE) {
                int qty = Integer.parseInt(childHolder.qty_number_button.getNumber());
                float amt = Float.parseFloat(item_list.get(j).getItem_sale_price());
                items = items + qty;
                total_amount = total_amount + (amt * qty);
            }
            j++;
        }
        ((CounterPosActivity) context).setTotal(items, total_amount);
    }

    ArrayList<HashMap<String, String>> selected_product_list = new ArrayList<>();
    HashMap<String, String> m = null;

    public void getAllSelectedProducts(String bill_type) {
        selected_product_list.clear();
        int j = 0;
        while (j < item_list.size()) {
            CounterAdapter.CounterViewHolder childHolder = (CounterAdapter.CounterViewHolder) CounterPosActivity.cpa.counter_recyclerview.findViewHolderForLayoutPosition(j);
            if (childHolder.qty_number_button.getVisibility() == View.VISIBLE) {
                m = new HashMap();
                m.put("name", item_list.get(j).getItem_name());
                m.put("rate", item_list.get(j).getItem_sale_price());
                m.put("unit", item_list.get(j).getUnit());
                m.put("qty", childHolder.qty_number_button.getNumber());
                m.put("barcode", item_list.get(j).getBarcode());
                m.put("index", String.valueOf(j));
                selected_product_list.add(m);
            }
            j++;
        }
        Intent intent = new Intent(context, CreateBillActivity.class);
        intent.putExtra("arraylist", selected_product_list);
        intent.putExtra("bill_type", bill_type);
        context.startActivity(intent);
//        if (fromActivity.equals("CreateBillActivity")) {
//
//        } else if (fromActivity.equals("CreatePurchaseActivity")) {
//            Intent intent = new Intent(context, CreatePurchaseActivity.class);
//            intent.putExtra("arraylist", selected_product_list);
//            context.startActivity(intent);
//        } else if (fromActivity.equals("QuotationActivity")) {
//            Intent intent = new Intent(context, QuotationActivity.class);
//            intent.putExtra("arraylist", selected_product_list);
//            context.startActivity(intent);
//        }
    }
}
