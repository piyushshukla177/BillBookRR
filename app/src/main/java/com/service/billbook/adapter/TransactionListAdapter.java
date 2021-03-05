package com.service.billbook.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.service.billbook.R;
import com.service.billbook.model.TransactionListModel;
import java.util.ArrayList;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.TransactionListViewHolder> {

    private ArrayList<TransactionListModel> transaction_list_list;
    private Context context;

    public class TransactionListViewHolder extends RecyclerView.ViewHolder {
        public TextView party_name_tv, invoice_number_tv, invoice_amount_tv, invoice_date_tv, unpaid_tv, paid_tv;
        View view;
        RelativeLayout unpaid_relative;

        public TransactionListViewHolder(View itemView) {
            super(itemView);
            party_name_tv = itemView.findViewById(R.id.party_name_tv);
            invoice_number_tv = itemView.findViewById(R.id.invoice_number_tv);
            invoice_amount_tv = itemView.findViewById(R.id.invoice_amount_tv);
            invoice_date_tv = itemView.findViewById(R.id.invoice_date_tv);
            unpaid_tv = itemView.findViewById(R.id.unpaid_tv);
            paid_tv = itemView.findViewById(R.id.paid_tv);
            view = itemView.findViewById(R.id.view);
            unpaid_relative = itemView.findViewById(R.id.unpaid_relative);
        }
    }

    public TransactionListAdapter(Context context, ArrayList<TransactionListModel> list) {
        transaction_list_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TransactionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_layout, parent, false);
        TransactionListViewHolder evh = new TransactionListViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final TransactionListViewHolder holder, int i) {
        final TransactionListModel currentItem = transaction_list_list.get(i);
        holder.party_name_tv.setText(currentItem.getParty_name());
        holder.invoice_number_tv.setText("Invoice # " + currentItem.getInvoice_number());
        holder.party_name_tv.setText(currentItem.getParty_name());
        holder.invoice_amount_tv.setText("₹ " + currentItem.getInvoice_amount());
        holder.invoice_date_tv.setText(currentItem.getInvoice_date());
        if (currentItem.getPayment_status().equalsIgnoreCase("Paid")) {
            holder.view.setVisibility(View.GONE);
            holder.unpaid_relative.setVisibility(View.GONE);
            holder.paid_tv.setVisibility(View.VISIBLE);
            holder.unpaid_tv.setVisibility(View.GONE);
        } else {
            holder.view.setVisibility(View.VISIBLE);
            holder.unpaid_relative.setVisibility(View.VISIBLE);
            holder.paid_tv.setVisibility(View.GONE);
            holder.unpaid_tv.setVisibility(View.VISIBLE);
        }
//     holder.payment_status_tv.setText(currentItem.getPayment_status());
    }

    @Override
    public int getItemCount() {
        return transaction_list_list.size();
    }
}
