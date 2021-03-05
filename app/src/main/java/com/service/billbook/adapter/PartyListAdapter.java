package com.service.billbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.service.billbook.R;
import com.service.billbook.activities.PartyDetailsActivity;
import com.service.billbook.model.PartyListModel;

import java.util.ArrayList;

public class PartyListAdapter extends RecyclerView.Adapter<PartyListAdapter.PartyListViewHolder> {

    private ArrayList<PartyListModel> party_list;
//  private ArrayList<PartyListModel> filter_party_list;
    private Context context;

    public class PartyListViewHolder extends RecyclerView.ViewHolder {
        public TextView party_name_tv, party_amount_tv, party_type_tv, send_reminder_tv;
        LinearLayout linear_layout;

        public PartyListViewHolder(View itemView) {
            super(itemView);
            party_name_tv = itemView.findViewById(R.id.party_name_tv);
            party_amount_tv = itemView.findViewById(R.id.party_amount_tv);
            party_type_tv = itemView.findViewById(R.id.party_type_tv);
            send_reminder_tv = itemView.findViewById(R.id.send_reminder_tv);
            linear_layout = itemView.findViewById(R.id.linear_layout);
        }
    }

    public PartyListAdapter(Context context, ArrayList<PartyListModel> list) {
        party_list = list;
//      filter_party_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PartyListAdapter.PartyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.party_list_layout, parent, false);
        PartyListAdapter.PartyListViewHolder evh = new PartyListAdapter.PartyListViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final PartyListAdapter.PartyListViewHolder holder, int i) {
        final PartyListModel currentItem = party_list.get(i);
        holder.party_name_tv.setText(currentItem.getParty_name());
        holder.party_amount_tv.setText("₹ " + currentItem.getParty_amount());
        holder.party_type_tv.setText(currentItem.getParty_type());
        if (Float.parseFloat(currentItem.getParty_amount()) > 0) {
            holder.send_reminder_tv.setVisibility(View.VISIBLE);
        }
        holder.linear_layout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, PartyDetailsActivity.class);
                        intent.putExtra("party_name", currentItem.getParty_name());
                        intent.putExtra("party_mobile", currentItem.getMobileNo());
                        intent.putExtra("party_id", currentItem.getBigintPartyId());
                        intent.putExtra("credit_limit", currentItem.getDcCreditLimit());
                        intent.putExtra("party_amount", currentItem.getParty_amount());
                        intent.putExtra("party_type", currentItem.getParty_type());
                        intent.putExtra("credit_period", currentItem.getIntCreditPeriod());
                        intent.putExtra("billing_address", currentItem.getBillingAddrtess());
                        intent.putExtra("shipping_address", currentItem.getShippingAddress());
                        intent.putExtra("gstin_number", currentItem.getStrGSTNo());
                        intent.putExtra("place_of_supply", currentItem.getPlaceOfSupply());
                        context.startActivity(intent);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return party_list.size();
    }


    //    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    filter_party_list = party_list;
//                } else {
//                    ArrayList<PartyListModel> filteredList = new ArrayList<>();
//                    for (PartyListModel row : party_list) {
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (row.getParty_name().toLowerCase().contains(charString.toLowerCase())) {
//                            filteredList.add(row);
//                        }
//                    }
//                    filter_party_list = filteredList;
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = filter_party_list;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                filter_party_list = (ArrayList<PartyListModel>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }
    public void filterList(ArrayList<PartyListModel> filteredList) {
        party_list = filteredList;
        notifyDataSetChanged();
    }
}
